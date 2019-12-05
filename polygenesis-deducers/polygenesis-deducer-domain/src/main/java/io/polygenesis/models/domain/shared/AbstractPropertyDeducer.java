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
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.Constructor;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.Mapper;
import io.polygenesis.models.domain.Primitive;
import io.polygenesis.models.domain.PrimitiveCollection;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.models.domain.Reference;
import io.polygenesis.models.domain.ValueObject;
import io.polygenesis.models.domain.ValueObjectCollection;
import io.polygenesis.models.domain.ValueObjectType;
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
   * Deduce domain object properties from function set.
   *
   * @param function the function
   * @return the set
   */
  public Set<DomainObjectProperty<?>> deduceDomainObjectPropertiesFromFunction(Function function) {
    Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

    Set<Data> thingProperties = getThingPropertiesFromFunction(function);

    properties.addAll(this.toDomainObjectProperties(thingProperties));

    return properties;
  }

  /**
   * Deduce constructors set.
   *
   * @param superClass the super class
   * @param thing the thing
   * @param rootPackageName the root package name
   * @return the set
   */
  public Set<Constructor> deduceConstructors(
      BaseDomainEntity superClass, Thing thing, PackageName rootPackageName) {
    Set<Constructor> constructors = new LinkedHashSet<>();

    thing
        .getFunctions()
        .stream()
        .filter(
            function ->
                function.getPurpose().isCreate() || function.getPurpose().isEnsureExistence())
        .forEach(
            function -> {
              Set<Data> thingProperties = getThingPropertiesFromFunction(function);
              Set<DomainObjectProperty<?>> properties = new LinkedHashSet<>();

              properties.addAll(makeIdentityDomainObjectProperties(thing, rootPackageName));
              properties.addAll(this.toDomainObjectProperties(thingProperties));

              // Inherit properties from superclass
              if (superClass != null && !superClass.getConstructors().isEmpty()) {
                superClass
                    .getConstructors()
                    .stream()
                    .forEach(
                        superClassConstructor -> {
                          // Super class
                          Set<DomainObjectProperty<?>> superClassProperties =
                              superClassConstructor
                                  .getProperties()
                                  .stream()
                                  .filter(
                                      property ->
                                          !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.AGGREGATE_ROOT_ID)
                                              && !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID)
                                              && !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.AGGREGATE_ENTITY_ID)
                                              && !property
                                                  .getPropertyType()
                                                  .equals(PropertyType.TENANT_ID))
                                  .collect(Collectors.toCollection(LinkedHashSet::new));

                          // Add
                          constructors.add(
                              new Constructor(null, function, properties, superClassProperties));
                        });
              } else {
                // Add
                constructors.add(new Constructor(null, function, properties));
              }
            });

    return constructors;
  }

  /**
   * To domain object properties set.
   *
   * @param thingProperties the thing properties
   * @return the set
   */
  protected Set<DomainObjectProperty<?>> toDomainObjectProperties(Set<Data> thingProperties) {
    return thingProperties
        .stream()
        .map(this::toDomainObjectProperty)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * To domain object property domain object property.
   *
   * @param data the thing property
   * @return the domain object property
   */
  protected DomainObjectProperty<?> toDomainObjectProperty(Data data) {
    switch (data.getDataPrimaryType()) {
      case ARRAY:
        switch (data.getAsDataArray().getArrayElement().getDataPrimaryType()) {
          case PRIMITIVE:
            return new PrimitiveCollection(data.getAsDataArray());
          case OBJECT:
            return new ValueObjectCollection(data.getAsDataArray());
          default:
            throw new UnsupportedOperationException();
        }
      case OBJECT:
        return new ValueObject(data.getAsDataObject());
      case PRIMITIVE:
        if (data.getAsDataPrimitive().getDataObject() != null) {
          return makeValueObjectFromPrimitive(data.getAsDataPrimitive());
        } else {
          return new Primitive(data.getAsDataPrimitive());
        }
      case THING:
        return new Reference(data);
      case MAP:
        return new Mapper(data.getAsDataMap());
      default:
        throw new UnsupportedOperationException(
            String.format("Cannot make DomainObjectProperty for %s", data.getDataPrimaryType()));
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
  protected Set<Data> getThingPropertiesSuitableForDomainModel(Thing thing) {
    return thing
        .getThingProperties()
        .stream()
        .filter(
            data ->
                !isDataThingIdentity(data)
                    && !isDataParentThingIdentity(data)
                    && !isDataPageNumber(data)
                    && !isDataPageSize(data))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Gets thing properties from function.
   *
   * @param function the function
   * @return the thing properties from function
   */
  protected Set<Data> getThingPropertiesFromFunction(Function function) {
    Set<Data> thingProperties = new LinkedHashSet<>();

    if (function.getArguments() != null) {
      function
          .getArguments()
          .stream()
          .filter(
              data ->
                  !isDataThingIdentity(data)
                      && !isDataParentThingIdentity(data)
                      && !isDataPageNumber(data)
                      && !isDataPageSize(data))
          .forEach(data -> thingProperties.add(data));
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
      return new ValueObject(dataObject);
    } else {
      dataObject.addData(dataPrimitive.getDataObject().getModels());
      return new ValueObject(dataObject, ValueObjectType.REFERENCE_TO_AGGREGATE_ROOT_ID);
    }
  }
}
