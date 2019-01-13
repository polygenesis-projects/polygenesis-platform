/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===========================LICENSE_END==================================
 */

package io.polygenesis.core.deducer;

import io.polygenesis.annotations.core.GIgnore;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * RecursiveObjectFiller.
 *
 * @author Christos Tsakostas
 */
public class RecursiveObjectFiller {

  private final TypesAnalyzer typesAnalyzer;
  private final FieldsInInterfaceMethodAnalyzer fieldsInInterfaceMethodAnalyzer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Recursive object filler.
   *
   * @param typesAnalyzer the types analyzer
   * @param fieldsInInterfaceMethodAnalyzer the fields in interface method analyzer
   */
  public RecursiveObjectFiller(
      TypesAnalyzer typesAnalyzer,
      FieldsInInterfaceMethodAnalyzer fieldsInInterfaceMethodAnalyzer) {
    this.typesAnalyzer = typesAnalyzer;
    this.fieldsInInterfaceMethodAnalyzer = fieldsInInterfaceMethodAnalyzer;
  }

  /**
   * Fill Recursive Object @param genericType the generic type.
   *
   * @param genericType the generic type
   * @param dataType the data type
   * @param name the name
   * @param clazz the clazz
   * @param parent the parent
   * @return the recursive object
   */
  public RecursiveObject fillRecursiveObject(
      String genericType, String dataType, String name, Class<?> clazz, RecursiveObject parent) {

    RecursiveObject recursiveObject = new RecursiveObject(genericType, dataType, name, parent);

    RecursiveObject collectionDto =
        handleCasesLikeSpringPageAndSimilar(recursiveObject, genericType, dataType, parent);

    handleCasesLikeApiPageResponseAndSimilar(recursiveObject, collectionDto, clazz);

    return recursiveObject;
  }

  private RecursiveObject handleCasesLikeSpringPageAndSimilar(
      RecursiveObject recursiveObject,
      String genericType,
      String dataType,
      RecursiveObject parent) {
    // Handle org.springframework.data.domain.Page and similar cases.
    if (parent == null && genericType != null) {
      try {
        Class<?> genericClazz = Class.forName(genericType);
        if (genericClazz.isInterface()) {

          recursiveObject.setGenericInterface(true);

          Set<Class<?>> genericExtendedOrImplementedTypes =
              typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(genericClazz);

          genericExtendedOrImplementedTypes.forEach(
              extendedClazz -> {
                Method[] methods = extendedClazz.getDeclaredMethods();
                fieldsInInterfaceMethodAnalyzer.extractFieldsFromInterfaceMethods(
                    methods, recursiveObject, dataType);
              });

          return recursiveObject
              .getChildren()
              .stream()
              .filter(p -> p.getGenericType() != null)
              .findFirst()
              .orElse(null);
        }
      } catch (ClassNotFoundException e) {
        throw new IllegalStateException("Cannot create class forName");
      }
    } else {
      return null;
    }
    return null;
  }

  private void handleCasesLikeApiPageResponseAndSimilar(
      RecursiveObject recursiveObject, RecursiveObject collectionDto, Class<?> clazz) {
    if (clazz != null) {
      Set<Class<?>> extendedOrImplementedTypes =
          typesAnalyzer.getAllExtendedOrImplementedTypesRecursively(clazz);

      // Check if provided clazz extends a generic Superclass and identity the dataType
      // It will be used later, as a field of the generic Superclass
      //
      // i.e. AccountListResponse extends ApiPageResponse<AccountProjection>
      List<Type> genericSuperclassArgTypes = new ArrayList<>();

      Type genericSuperclassType = clazz.getGenericSuperclass();
      if (genericSuperclassType instanceof ParameterizedType) {
        ParameterizedType paramType = (ParameterizedType) genericSuperclassType;
        Type[] argTypes = paramType.getActualTypeArguments();
        genericSuperclassArgTypes.addAll(Arrays.asList(argTypes));
      }

      if (collectionDto != null) {
        extendedOrImplementedTypes.forEach(
            extendedClazz -> {
              Field[] fields = extendedClazz.getDeclaredFields();
              Stream<Field> streamFields = Stream.of(fields);

              this.extractFieldsFromClass(genericSuperclassArgTypes, streamFields, collectionDto);
            });
      } else {
        extendedOrImplementedTypes.forEach(
            extendedClazz -> {
              Field[] fields = extendedClazz.getDeclaredFields();
              Set<Field> fieldSet = new LinkedHashSet<>();
              Collections.addAll(fieldSet, fields);

              Stream<Field> streamFields = fieldSet.stream();

              this.extractFieldsFromClass(genericSuperclassArgTypes, streamFields, recursiveObject);
            });
      }
    }
  }

  private void extractFieldsFromClass(
      List<Type> genericSuperclassArgTypes, Stream<Field> streamFields, RecursiveObject parent) {
    // Iterate on Fields
    streamFields
        .filter(field -> !field.isAnnotationPresent(GIgnore.class))
        .forEach(
            field -> {

              // Exclude static, final and transient fields
              if (!Modifier.isStatic(field.getModifiers())
                  && !Modifier.isFinal(field.getModifiers())
                  && !Modifier.isTransient(field.getModifiers())) {

                // Continue
                Type typeGeneric = field.getGenericType();
                Type type = field.getType();

                GenericTypeAndDataTypeDescriptor genericTypeAndDataTypeDescriptor =
                    new GenericTypeAndDataTypeDescriptor(null, type.getTypeName());

                if (typeGeneric instanceof ParameterizedType) {
                  genericTypeAndDataTypeDescriptor =
                      handleCaseWhereTypeGenericIsParameterizedType(
                          typeGeneric, type, genericSuperclassArgTypes);
                }

                RecursiveObject childRecursiveObject =
                    new RecursiveObject(
                        genericTypeAndDataTypeDescriptor.getGenericType(),
                        genericTypeAndDataTypeDescriptor.getDataType(),
                        field.getName(),
                        parent);

                // Set Annotations Metadata
                childRecursiveObject.setAnnotations(field.getAnnotations());

                // Check if is Custom Object
                if (childRecursiveObject.isCustomObject()) {
                  handleCaseWhereChildRecursiveObjectIsCustomObject(
                      genericTypeAndDataTypeDescriptor.getGenericType(),
                      genericTypeAndDataTypeDescriptor.getDataType(),
                      field,
                      parent,
                      childRecursiveObject);
                } else {
                  parent.appendChild(childRecursiveObject);
                }
              }
            });
  }

  private GenericTypeAndDataTypeDescriptor handleCaseWhereTypeGenericIsParameterizedType(
      Type typeGeneric, Type type, List<Type> genericSuperclassArgTypes) {
    String fieldGenericType = type.getTypeName();
    String fieldDataType = null;

    ParameterizedType paramType = (ParameterizedType) typeGeneric;
    Type[] argTypes = paramType.getActualTypeArguments();
    if (argTypes.length > 0) {
      fieldDataType = argTypes[0].getTypeName();

      // The following solution is Very Hacky!!
      if (fieldDataType.length() <= 2) {
        fieldDataType = genericSuperclassArgTypes.get(0).getTypeName();
      }
    }

    return new GenericTypeAndDataTypeDescriptor(fieldGenericType, fieldDataType);
  }

  private void handleCaseWhereChildRecursiveObjectIsCustomObject(
      String fieldGenericType,
      String fieldDataType,
      Field field,
      RecursiveObject parent,
      RecursiveObject childRecursiveObject) {
    try {
      RecursiveObject childOfChildRecursiveObject =
          this.fillRecursiveObject(
              fieldGenericType,
              fieldDataType,
              field.getName(),
              Class.forName(fieldDataType),
              parent);

      // Metadata
      childRecursiveObject.setAnnotations(field.getAnnotations());

      parent.appendChild(childOfChildRecursiveObject);

    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("Cannot create class forName");
    }
  }
}
