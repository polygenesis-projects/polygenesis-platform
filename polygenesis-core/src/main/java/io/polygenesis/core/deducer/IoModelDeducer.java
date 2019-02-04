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

import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.datatype.PrimitiveDataType;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.GenericTypeName;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelArray;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Deduces the IOModel.
 *
 * @author Christos Tsakostas
 */
public class IoModelDeducer {

  private final JavaDataTypeConverter javaDataTypeConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model deducer.
   *
   * @param javaDataTypeConverter the data type converter
   */
  public IoModelDeducer(JavaDataTypeConverter javaDataTypeConverter) {
    this.javaDataTypeConverter = javaDataTypeConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce response io model.
   *
   * @param recursiveObject the recursive object
   * @return the io model
   */
  IoModel deduceResponse(RecursiveObject recursiveObject) {

    if (recursiveObject.isGenericInterface()) {
      // IoModelArray
      return new IoModelArray(
          new GenericTypeName(recursiveObject.getStrGenericType()),
          new ClassDataType(
              convertToDataTypeNameFrom(recursiveObject.getStrDataType()),
              convertToPackageName(recursiveObject.getStrDataType())),
          new VariableName(recursiveObject.getStrName()));
    } else if (!recursiveObject.isCustomObject()) {
      // IoModelPrimitive
      if (recursiveObject.getStrGenericType() != null) {
        throw new IllegalArgumentException("IoModelPrimitive cannot be Generic");
      }

      return new IoModelPrimitive(
          new PrimitiveDataType(convertToPrimitiveTypeFrom(recursiveObject.getStrDataType())),
          new VariableName(recursiveObject.getStrName()),
          safeGetAnnotationsFrom(recursiveObject),
          false);

    } else {
      // IoModelGroup
      if (recursiveObject.getStrGenericType() != null) {
        throw new IllegalStateException("Something is wrong! No Generic should be here.");
      }

      IoModelGroup modelGroupResponse =
          new IoModelGroup(
              new ClassDataType(
                  convertToDataTypeNameFrom(recursiveObject.getStrDataType()),
                  convertToPackageName(recursiveObject.getStrDataType())),
              new VariableName(recursiveObject.getStrName()));

      this.fillIoModelGroup(modelGroupResponse, recursiveObject);

      return modelGroupResponse;
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void fillIoModelGroup(IoModelGroup modelGroup, RecursiveObject recursiveObject) {

    recursiveObject
        .getChildren()
        .forEach(
            childRecursiveObject -> {
              if (childRecursiveObject.getStrGenericType() != null) {
                IoModelArray modelArray = new IoModelArray(modelGroup);

                fillIoModelGroup(modelArray, childRecursiveObject);

                modelGroup.addIoModelArray(modelArray);
              } else {
                if (childRecursiveObject.isCustomObject()) {
                  IoModelGroup modelGroupCustomObject = new IoModelGroup(modelGroup);

                  fillIoModelGroup(modelGroupCustomObject, childRecursiveObject);

                  modelGroup.addIoModelGroup(modelGroupCustomObject);
                } else {
                  // Should not add primitives for Ignored or automatically set fields.
                  IoModelPrimitive modelPrimitive =
                      new IoModelPrimitive(
                          new PrimitiveDataType(
                              convertToPrimitiveTypeFrom(childRecursiveObject.getStrDataType())),
                          new VariableName(childRecursiveObject.getStrName()),
                          modelGroup,
                          safeGetAnnotationsFrom(childRecursiveObject),
                          false);

                  modelGroup.addIoModelPrimitive(modelPrimitive);
                }
              }
            });
  }

  private Set<Annotation> safeGetAnnotationsFrom(RecursiveObject recursiveObject) {
    if (recursiveObject.getAnnotations() != null) {
      return new LinkedHashSet<>(Arrays.asList(recursiveObject.getAnnotations()));
    } else {
      return new LinkedHashSet<>();
    }
  }

  private DataTypeName convertToDataTypeNameFrom(String strDataType) {
    // TODO - maybe there is a better check?
    int index = strDataType.lastIndexOf('.');
    if (index > 0) {
      return new DataTypeName(strDataType.substring(index + 1));
    }

    return new DataTypeName(javaDataTypeConverter.convert(strDataType).name());
  }

  private PrimitiveType convertToPrimitiveTypeFrom(String strDataType) {
    return javaDataTypeConverter.convert(strDataType);
  }

  private PackageName convertToPackageName(String strDataType) {
    int index = strDataType.lastIndexOf('.');
    if (index > 0) {
      return new PackageName(strDataType.substring(0, index));
    } else {
      return new PackageName("");
    }
  }
}
