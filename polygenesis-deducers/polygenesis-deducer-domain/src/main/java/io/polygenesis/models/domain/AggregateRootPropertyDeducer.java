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

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Function;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingScopeType;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.core.data.VariableName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Aggregate root property deducer.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootPropertyDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final AggregateEntityDeducer aggregateEntityDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public AggregateRootPropertyDeducer(AggregateEntityDeducer aggregateEntityDeducer) {
    this.aggregateEntityDeducer = aggregateEntityDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce from Thing.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObjectProperty> deduceFrom(Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    if (thing.getThingProperties().isEmpty()) {
      thing
          .getFunctions()
          .forEach(
              function ->
                  properties.addAll(deduceFromFunctionArguments(function, rootPackageName)));
    } else {
      properties.addAll(deduceFromThingProperties(thing));
    }

    properties.addAll(
        aggregateEntityDeducer.deduceAggregateEntityCollections(thing, rootPackageName));

    return properties;
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Deduce from Function.
   *
   * @param function the function
   * @param rootPackageName the root package name
   * @return the set
   */
  protected Set<DomainObjectProperty> deduceFromFunctionArguments(
      Function function, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    // Add Aggregate Root ID if the thing is not abstract
    if (!function
            .getThing()
            .getThingScopeType()
            .equals(ThingScopeType.ABSTRACT_DOMAIN_AGGREGATE_ROOT)
        && function.getGoal().isCreate()) {
      properties.add(makeAggregateRootId(function, rootPackageName));
    }

    if (function.getArguments() != null) {
      function
          .getArguments()
          .forEach(
              argument -> {
                if (argument.getData().isDataGroup()) {
                  argument
                      .getData()
                      .getAsDataGroup()
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
    }

    return properties;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Deduce from thing properties set.
   *
   * @param thing the thing
   * @return the set
   */
  private Set<DomainObjectProperty> deduceFromThingProperties(Thing thing) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    thing
        .getThingProperties()
        .stream()
        .map(thingProperty -> thingProperty.getData())
        .forEach(
            data -> {
              // TODO: check if more restrictions are required here
              properties.add(makeAbstractProperty(data));
            });

    return properties;
  }

  private DomainObjectProperty makeAbstractProperty(Data model) {
    switch (model.getDataPrimaryType()) {
      case ARRAY:
        DataArray dataArray = model.getAsDataArray();
        if (dataArray.getArrayElement().isDataPrimitive()) {
          return makePrimitiveCollection(dataArray);
        } else if (dataArray.getArrayElement().isDataGroup()) {
          return makeValueObjectCollection(dataArray);
        } else {
          throw new IllegalArgumentException();
        }
      case OBJECT:
        DataGroup originatingDataGroup = model.getAsDataGroup();

        DataGroup newDataGroup =
            originatingDataGroup
                .withNewObjectName(
                    new ObjectName(makeValueObjectVariableName(model.getVariableName())))
                .withNewVariableName(
                    new VariableName(makeValueObjectVariableName(model.getVariableName())));

        return new ValueObject(newDataGroup, originatingDataGroup);
      case PRIMITIVE:
        return new Primitive(model.getAsDataPrimitive());
      default:
        throw new IllegalStateException(
            String.format("Cannot make DomainObjectProperty for %s", model.getDataPrimaryType()));
    }
  }

  // ===============================================================================================
  // MAKE PROPERTIES
  // ===============================================================================================

  /**
   * Make primitive collection primitive collection.
   *
   * @param dataArray the data array
   * @return the primitive collection
   */
  protected PrimitiveCollection makePrimitiveCollection(DataArray dataArray) {
    return new PrimitiveCollection(dataArray);
  }

  /**
   * Make value object collection value object collection.
   *
   * @param dataArray the data array
   * @return the value object collection
   */
  protected ValueObjectCollection makeValueObjectCollection(DataArray dataArray) {
    throw new UnsupportedOperationException();
  }

  private AggregateRootId makeAggregateRootId(Function function, PackageName rootPackageName) {
    DataGroup dataGroup =
        new DataGroup(
            new ObjectName(function.getThing().getThingName().getText() + "Id"),
            function.getThing().makePackageName(rootPackageName, function.getThing()));

    return new AggregateRootId(dataGroup);
  }

  // ===============================================================================================
  // HELPER
  // ===============================================================================================

  private String makeValueObjectVariableName(VariableName variableName) {
    String text = variableName.getText();

    if (text.toLowerCase().endsWith("dto")) {
      return text.substring(0, text.length() - 3);
    } else {
      return text;
    }
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  private boolean isPropertyThingIdentity(Data model) {
    if (model.isDataPrimitive() && ((DataPrimitive) model).getThingIdentity()) {
      return true;
    }
    return false;
  }

  private boolean isPropertyPageNumber(Data model) {
    if (model.isDataPrimitive()
        && model.getDataBusinessType().equals(DataBusinessType.PAGE_NUMBER)) {
      return true;
    }
    return false;
  }

  private boolean isPropertyPageSize(Data model) {
    if (model.isDataPrimitive() && model.getDataBusinessType().equals(DataBusinessType.PAGE_SIZE)) {
      return true;
    }
    return false;
  }
}
