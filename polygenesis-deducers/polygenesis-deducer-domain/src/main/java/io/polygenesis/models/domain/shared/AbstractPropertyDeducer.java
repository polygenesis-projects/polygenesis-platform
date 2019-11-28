/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.models.domain.shared;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingProperty;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.Mapper;
import io.polygenesis.models.domain.Primitive;
import io.polygenesis.models.domain.PrimitiveCollection;
import io.polygenesis.models.domain.Reference;
import io.polygenesis.models.domain.ValueObject;
import io.polygenesis.models.domain.ValueObjectCollection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Abstract property deducer.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractPropertyDeducer {

  /**
   * Make identity domain object properties set.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  protected abstract Set<DomainObjectProperty<?>> makeIdentityDomainObjectProperties(
      Thing thing, PackageName rootPackageName);

  /**
   * Deduce from set.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<DomainObjectProperty<?>> deduceFromThing(Thing thing, PackageName rootPackageName) {
    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    assertThatThingHasIdentity(thing);

    properties.addAll(makeIdentityDomainObjectProperties(thing, rootPackageName));

    properties.addAll(
        getThingPropertiesSuitableForDomainModel(thing)
            .stream()
            .map(this::toDomainObjectProperty)
            .collect(Collectors.toCollection(LinkedHashSet::new)));

    return properties;
  }

  /**
   * Deduce constructors set.
   *
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<Constructor> deduceConstructors(Thing thing, PackageName rootPackageName) {
    Set<Constructor> constructors = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(
            function ->
                function.getPurpose().isCreate() || function.getPurpose().isEnsureExistence())
        .forEach(
            function -> {
              Set<ThingProperty> thingProperties = getThingPropertiesFromFunction(function);

              Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

              properties.addAll(makeIdentityDomainObjectProperties(thing, rootPackageName));
              properties.addAll(this.toDomainObjectProperties(thingProperties));

              constructors.add(new Constructor(function, properties));
            });

    return constructors;
  }

  /**
   * To domain object properties set.
   *
   * @param thingProperties the thing properties
   * @return the set
   */
  protected Set<DomainObjectProperty<?>> toDomainObjectProperties(
      Set<ThingProperty> thingProperties) {
    return thingProperties
        .stream()
        .map(this::toDomainObjectProperty)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * To domain object property domain object property.
   *
   * @param thingProperty the thing property
   * @return the domain object property
   */
  protected DomainObjectProperty<?> toDomainObjectProperty(ThingProperty thingProperty) {
    switch (thingProperty.getData().getDataPrimaryType()) {
      case ARRAY:
        switch (thingProperty.getData().getAsDataArray().getArrayElement().getDataPrimaryType()) {
          case PRIMITIVE:
            return new PrimitiveCollection(thingProperty.getData().getAsDataArray());
          case OBJECT:
            return new ValueObjectCollection(thingProperty.getData().getAsDataArray());
          default:
            throw new UnsupportedOperationException();
        }
      case OBJECT:
        return new ValueObject(thingProperty.getData().getAsDataObject());
      case PRIMITIVE:
        if (thingProperty.getData().getAsDataPrimitive().getDataObject() != null) {
          return makeValueObjectFromPrimitive(thingProperty.getData().getAsDataPrimitive());
        } else {
          return new Primitive(thingProperty.getData().getAsDataPrimitive());
        }
      case THING:
        return new Reference(thingProperty.getData());
      case MAP:
        return new Mapper(thingProperty.getData().getAsDataMap());
      default:
        throw new UnsupportedOperationException(
            String.format(
                "Cannot make DomainObjectProperty for %s",
                thingProperty.getData().getDataPrimaryType()));
    }
  }

  /**
   * Assert that thing has identity.
   *
   * @param thing the thing
   */
  protected void assertThatThingHasIdentity(Thing thing) {
    Set<Data> data =
        thing
            .getThingProperties()
            .stream()
            .map(ThingProperty::getData)
            .filter(this::isDataThingIdentity)
            .collect(Collectors.toSet());

    if (data.size() == 0) {
      throw new IllegalStateException(
          String.format("No thing identity found for thing=%s", thing.getThingName().getText()));
    } else if (data.size() > 1) {
      throw new IllegalStateException(
          String.format(
              "More than one thing identities found for thing=%s", thing.getThingName().getText()));
    }
  }

  /**
   * Gets thing properties suitable for domain model.
   *
   * @param thing the thing
   * @return the thing properties for domain
   */
  protected Set<ThingProperty> getThingPropertiesSuitableForDomainModel(Thing thing) {
    return thing
        .getThingProperties()
        .stream()
        .filter(
            thingProperty ->
                !isDataThingIdentity(thingProperty.getData())
                    && !isDataParentThingIdentity(thingProperty.getData())
                    && !isDataPageNumber(thingProperty.getData())
                    && !isDataPageSize(thingProperty.getData()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Gets thing properties from function.
   *
   * @param function the function
   * @return the thing properties from function
   */
  protected Set<ThingProperty> getThingPropertiesFromFunction(Function function) {
    Set<ThingProperty> thingProperties = new LinkedHashSet<>();

    if (function.getArguments() != null) {
      function
          .getArguments()
          .forEach(
              argument -> {
                if (argument.getData().isDataGroup()) {
                  argument
                      .getData()
                      .getAsDataObject()
                      .getModels()
                      .forEach(
                          model -> {
                            if (!isDataThingIdentity(model)
                                && !isDataParentThingIdentity(model)
                                && !isDataPageNumber(model)
                                && !isDataPageSize(model)) {
                              thingProperties.add(new ThingProperty(model));
                            }
                          });

                } else {
                  throw new IllegalStateException();
                }
              });
    }

    return thingProperties;
  }

  // ===============================================================================================
  // PRIVATE QUERIES
  // ===============================================================================================

  private boolean isDataThingIdentity(Data data) {
    return data.getDataPurpose().equals(DataPurpose.thingIdentity());
  }

  private boolean isDataParentThingIdentity(Data data) {
    return data.getDataPurpose().equals(DataPurpose.parentThingIdentity());
  }

  private boolean isDataPageNumber(Data data) {
    return data.getDataPurpose().equals(DataPurpose.pageNumber());
  }

  private boolean isDataPageSize(Data data) {
    return data.getDataPurpose().equals(DataPurpose.pageSize());
  }

  private ValueObject makeValueObjectFromPrimitive(DataPrimitive dataPrimitive) {
    DataObject dataObject =
        new DataObject(
            dataPrimitive.getDataObject().getObjectName(),
            dataPrimitive.getDataObject().getPackageName(),
            dataPrimitive.getVariableName());

    if (dataPrimitive.getDataObject().getModels().isEmpty()) {
      dataObject.addData(dataPrimitive.withVariableName("value"));
    } else {
      dataObject.addData(dataPrimitive.getDataObject().getModels());
    }

    return new ValueObject(dataObject);
  }
}
