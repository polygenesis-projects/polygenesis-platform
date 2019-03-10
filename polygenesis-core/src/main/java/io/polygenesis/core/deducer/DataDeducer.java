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

import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.data.PackageName;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Deduces the IOModel.
 *
 * @author Christos Tsakostas
 */
public class DataDeducer {

  private final JavaDataTypeConverter javaDataTypeConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model deducer.
   *
   * @param javaDataTypeConverter the data type converter
   */
  public DataDeducer(JavaDataTypeConverter javaDataTypeConverter) {
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
  Data deduceResponse(RecursiveObject recursiveObject) {

    if (recursiveObject.isGenericInterface()
        || recursiveObject.getStrDataType() == "java.util.List") {
      // TODO: check if recursiveObject.getStrGenericType() plays a role?
      // DataArray
      return new DataArray(new VariableName(recursiveObject.getStrName()));
    } else if (!recursiveObject.isCustomObject()) {
      // DataPrimitive
      if (recursiveObject.getStrGenericType() != null) {
        throw new IllegalArgumentException("DataPrimitive cannot be Generic");
      }

      return new DataPrimitive(
          convertToPrimitiveTypeFrom(recursiveObject.getStrDataType()),
          new VariableName(recursiveObject.getStrName()),
          safeGetAnnotationsFrom(recursiveObject),
          DataBusinessType.ANY);

    } else {
      // DataGroup
      if (recursiveObject.getStrGenericType() != null) {
        throw new IllegalStateException("Something is wrong! No Generic should be here.");
      }

      DataGroup modelGroupResponse =
          new DataGroup(
              convertToObjectNameFrom(recursiveObject.getStrDataType()),
              convertToPackageName(recursiveObject.getStrDataType()),
              new VariableName(recursiveObject.getStrName()));

      this.fillIoModelGroup(modelGroupResponse, recursiveObject);

      return modelGroupResponse;
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void fillIoModelGroup(DataGroup modelGroup, RecursiveObject recursiveObject) {

    recursiveObject
        .getChildren()
        .forEach(
            childRecursiveObject -> {
              if (childRecursiveObject.getStrGenericType() != null) {
                DataArray modelArray = new DataArray();

                // TODO: fillIoModelArray Element
                // fillIoModelGroup(modelArray, childRecursiveObject);

                modelGroup.addIoModelArray(modelArray);
              } else {
                if (childRecursiveObject.isCustomObject()) {
                  DataGroup modelGroupCustomObject = new DataGroup();

                  fillIoModelGroup(modelGroupCustomObject, childRecursiveObject);

                  modelGroup.addIoModelGroup(modelGroupCustomObject);
                } else {
                  // Should not add primitives for Ignored or automatically set fields.
                  DataPrimitive modelPrimitive =
                      new DataPrimitive(
                          convertToPrimitiveTypeFrom(childRecursiveObject.getStrDataType()),
                          new VariableName(childRecursiveObject.getStrName()),
                          safeGetAnnotationsFrom(childRecursiveObject),
                          DataBusinessType.ANY);

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

  private ObjectName convertToObjectNameFrom(String strDataType) {
    // TODO - maybe there is a better check?
    int index = strDataType.lastIndexOf('.');
    if (index > 0) {
      return new ObjectName(strDataType.substring(index + 1));
    }

    return new ObjectName(javaDataTypeConverter.convert(strDataType).name());
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
