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
import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.datatype.DataKind;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.DataBusinessType;
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
  public Set<AbstractProperty> deduceFrom(Thing thing, PackageName rootPackageName) {
    Set<AbstractProperty> properties = new LinkedHashSet<>();

    thing
        .getFunctions()
        .forEach(function -> properties.addAll(deduceFrom(function, rootPackageName)));

    return properties;
  }

  /**
   * Deduce from Function.
   *
   * @param function the function
   * @return the set
   */
  public Set<AbstractProperty> deduceFrom(Function function, PackageName rootPackageName) {
    Set<AbstractProperty> properties = new LinkedHashSet<>();

    // Add Aggregate Root ID
    if (function.getGoal().isCreate()) {
      properties.add(makeAggregateRootId(function, rootPackageName));
    }

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
                          if (!isPropertyThingIdentity(model)
                              && !isPropertyPageNumber(model)
                              && !isPropertyPageSize(model)) {
                            properties.add(makeAbstractProperty(model));
                          }
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
        IoModelGroup originatingIoModelGroup = (IoModelGroup) model;

        IoModelGroup newIoModelGroup =
            originatingIoModelGroup
                .withNewClassDataType(
                    originatingIoModelGroup
                        .getClassDataType()
                        .changeDataTypeNameTo(
                            new DataTypeName(makeValueObjectVariableName(model.getVariableName()))))
                .withNewVariableName(
                    new VariableName(makeValueObjectVariableName(model.getVariableName())));

        return new ValueObject(
            originatingIoModelGroup,
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

  private AggregateRootId makeAggregateRootId(Function function, PackageName rootPackageName) {
    IoModelGroup ioModelGroup =
        new IoModelGroup(
            new ClassDataType(
                new DataTypeName(function.getThing().getName().getText() + "Id"),
                new PackageName(
                    String.format(
                        "%s.%s",
                        rootPackageName.getText(),
                        function.getThing().getName().getText().toLowerCase()))));

    return new AggregateRootId(
        ioModelGroup, new VariableName(function.getThing().getName().getText() + "Id"));
  }

  private boolean isPropertyThingIdentity(IoModel model) {
    if (model.getDataType().getDataKind().equals(DataKind.PRIMITIVE)
        && ((IoModelPrimitive) model).getThingIdentity()) {
      return true;
    }
    return false;
  }

  private boolean isPropertyPageNumber(IoModel model) {
    if (model.getDataType().getDataKind().equals(DataKind.PRIMITIVE)
        && ((IoModelPrimitive) model).getDataBusinessType().equals(DataBusinessType.PAGE_NUMBER)) {
      return true;
    }
    return false;
  }

  private boolean isPropertyPageSize(IoModel model) {
    if (model.getDataType().getDataKind().equals(DataKind.PRIMITIVE)
        && ((IoModelPrimitive) model).getDataBusinessType().equals(DataBusinessType.PAGE_SIZE)) {
      return true;
    }
    return false;
  }
}
