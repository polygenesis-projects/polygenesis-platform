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

package io.polygenesis.models.domain;

import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate root property deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootPropertyDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from Thing.
   *
   * @param thing the thing
   * @return the set
   */
  public Set<AbstractProperty> deduceFrom(Thing thing) {
    Set<AbstractProperty> properties = new LinkedHashSet<>();

    thing.getFunctions().forEach(function -> properties.addAll(deduceFrom(function)));

    return properties;
  }

  /**
   * Deduce from Function.
   *
   * @param function the function
   * @return the set
   */
  public Set<AbstractProperty> deduceFrom(Function function) {
    Set<AbstractProperty> properties = new LinkedHashSet<>();

    function
        .getArguments()
        .forEach(
            argument -> {
              if (argument.getModel().isIoModelGroup()) {
                argument
                    .getAsIoModelGroup()
                    .getModels()
                    .forEach(
                        model -> {
                          properties.add(makeAbstractProperty(model));
                        });

              } else {
                throw new IllegalStateException();
              }
            });

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private AbstractProperty makeAbstractProperty(IoModel model) {
    switch (model.getDataType().getDataKind()) {
      case CLASS:
        IoModelGroup ioModelGroup = (IoModelGroup) model;

        IoModelGroup newIoModelGroup =
            ioModelGroup
                .withNewClassDataType(
                    ioModelGroup
                        .getClassDataType()
                        .changeDataTypeNameTo(
                            new DataTypeName(makeValueObjectVariableName(model.getVariableName()))))
                .withNewVariableName(
                    new VariableName(makeValueObjectVariableName(model.getVariableName())));

        return new ValueObject(
            newIoModelGroup,
            new VariableName(makeValueObjectVariableName(model.getVariableName())));
      case PRIMITIVE:
        return new Primitive((IoModelPrimitive) model, model.getVariableName());
      default:
        throw new IllegalStateException();
    }
  }

  private String makeValueObjectVariableName(VariableName variableName) {
    String text = variableName.getText();

    if (text.toLowerCase().endsWith("dto")) {
      return text.substring(0, text.length() - 3);
    } else {
      return text;
    }
  }
}
