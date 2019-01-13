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

import io.polygenesis.commons.text.Text;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelArray;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
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

  private final DataTypeConverter dataTypeConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Io model deducer.
   *
   * @param dataTypeConverter the data type converter
   */
  public IoModelDeducer(DataTypeConverter dataTypeConverter) {
    this.dataTypeConverter = dataTypeConverter;
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
          new Text(recursiveObject.getGenericType()),
          new Text(recursiveObject.getDataType()),
          new Text(recursiveObject.getName()));
    } else if (!recursiveObject.isCustomObject()) {
      // IoModelPrimitive
      if (recursiveObject.getGenericType() != null) {
        throw new IllegalArgumentException("IoModelPrimitive cannot be Generic");
      }

      return new IoModelPrimitive(
          new Text(dataTypeConverter.convert(recursiveObject.getDataType()).name()),
          new Text(recursiveObject.getName()),
          safeGetAnnotationsFrom(recursiveObject));

    } else {
      // IoModelGroup
      IoModelGroup modelGroupResponse =
          new IoModelGroup(
              recursiveObject.getGenericType() != null
                  ? new Text(recursiveObject.getGenericType())
                  : null,
              new Text(recursiveObject.getDataType()),
              new Text(recursiveObject.getName()));

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
              if (childRecursiveObject.getGenericType() != null) {
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
                          new Text(
                              dataTypeConverter.convert(childRecursiveObject.getDataType()).name()),
                          new Text(childRecursiveObject.getName()),
                          modelGroup,
                          safeGetAnnotationsFrom(childRecursiveObject));

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
}
