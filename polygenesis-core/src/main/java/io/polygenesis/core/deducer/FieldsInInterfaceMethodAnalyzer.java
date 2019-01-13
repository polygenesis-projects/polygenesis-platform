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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Extracts Fields From Interface Methods.
 *
 * @author Christos Tsakostas
 */
public class FieldsInInterfaceMethodAnalyzer {

  /**
   * Extract fields from interface methods.
   *
   * @param methods the methods
   * @param parent the parent
   * @param collectionDtoDataType the collection dto data type
   */
  public void extractFieldsFromInterfaceMethods(
      Method[] methods, RecursiveObject parent, String collectionDtoDataType) {

    RecursiveObject collectionDtoRecursiveObject =
        this.extractCollectionDtoFieldFromInterfaceMethods(methods, parent, collectionDtoDataType);

    Stream<Method> streamMethods = Stream.of(methods);
    streamMethods.forEach(
        method -> {
          Type methodGenericReturnType = method.getGenericReturnType();
          Class<?> methodReturnTypeClazz = method.getReturnType();
          String methodName = method.getName();

          if ((methodName.startsWith("get")
                  || methodName.startsWith("has")
                  || methodName.startsWith("is"))
              && !(methodGenericReturnType instanceof ParameterizedType)) {

            extractField(
                fixFieldName(methodName),
                collectionDtoRecursiveObject,
                methodReturnTypeClazz,
                parent);
          }
        });
  }

  private String fixFieldName(String methodName) {
    String fieldName = methodName;

    if (fieldName.startsWith("get")) {
      fieldName = methodName.replace("get", "");
    } else if (fieldName.startsWith("has")) {
      fieldName = methodName.replace("has", "");
    } else if (fieldName.startsWith("is")) {
      fieldName = methodName.replace("is", "");
    }

    return fieldName;
  }

  private void extractField(
      String fieldName,
      RecursiveObject collectionDtoRecursiveObject,
      Class<?> methodReturnTypeClazz,
      RecursiveObject parent) {
    // It could be the case that a method is called "get", so we want to avoid it
    // as we cannot determine what ti get.
    if (!fieldName.equals("")) {
      fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);

      // Check that we do not use the same name twice: e.g. getContent & hasContent
      if (collectionDtoRecursiveObject == null
          || !collectionDtoRecursiveObject.getName().equals(fieldName)) {
        RecursiveObject childRecursiveObject =
            new RecursiveObject(null, methodReturnTypeClazz.getTypeName(), fieldName, parent);

        parent.appendChild(childRecursiveObject);
      }
    }
  }

  /**
   * Extract collection dto field from interface methods recursive object.
   *
   * @param methods the methods
   * @param parent the parent
   * @param collectionDtoDataType the collection dto data type
   * @return the recursive object
   */
  private RecursiveObject extractCollectionDtoFieldFromInterfaceMethods(
      Method[] methods, RecursiveObject parent, String collectionDtoDataType) {
    List<RecursiveObject> collectionDtoRecursiveObjectList = new ArrayList<>();
    Stream<Method> streamMethods = Stream.of(methods);

    streamMethods.forEach(
        method -> {
          Type methodGenericReturnType = method.getGenericReturnType();
          Class<?> methodReturnTypeClazz = method.getReturnType();
          String methodName = method.getName();

          if (methodName.startsWith("get")
              && methodGenericReturnType instanceof ParameterizedType) {
            String fieldGenericType = methodReturnTypeClazz.getTypeName();
            String fieldName = methodName.replace("get", "");

            // It could be the case that a method is called "get", so we want to avoid it
            // as we cannot determine what ti get.
            if (!fieldName.equals("")) {
              fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);

              RecursiveObject childRecursiveObject =
                  new RecursiveObject(fieldGenericType, collectionDtoDataType, fieldName, parent);

              collectionDtoRecursiveObjectList.add(childRecursiveObject);

              parent.appendChild(childRecursiveObject);
            }
          }
        });

    if (collectionDtoRecursiveObjectList.size() == 1) {
      return collectionDtoRecursiveObjectList.get(0);
    } else {
      return null;
    }
  }
}
