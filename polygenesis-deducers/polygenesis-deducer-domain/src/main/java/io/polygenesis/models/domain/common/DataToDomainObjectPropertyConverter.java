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

package io.polygenesis.models.domain.common;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.models.domain.AbstractAggregateRootId;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.AggregateEntityId;
import io.polygenesis.models.domain.AggregateRootId;
import io.polygenesis.models.domain.BaseProperty;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.GenericTypeParameter;
import io.polygenesis.models.domain.Mapper;
import io.polygenesis.models.domain.Primitive;
import io.polygenesis.models.domain.PrimitiveCollection;
import io.polygenesis.models.domain.ReferenceById;
import io.polygenesis.models.domain.ReferenceByValue;
import io.polygenesis.models.domain.ValueObject;
import io.polygenesis.models.domain.ValueObjectCollection;
import io.polygenesis.models.domain.ValueObjectType;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Data to domain object property converter.
 *
 * @author Christos Tsakostas
 */
public class DataToDomainObjectPropertyConverter {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  public Set<DomainObjectProperty<?>> convertMany(DomainObject domainObject, Set<Data> data) {
    return data.stream()
        .map(dataInStream -> convert(domainObject, dataInStream))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public DomainObjectProperty<?> convert(DomainObject domainObject, Data source, Object... args) {
    switch (source.getDataPrimaryType()) {
      case ARRAY:
        switch (source.getAsDataArray().getArrayElement().getDataPrimaryType()) {
          case PRIMITIVE:
            return new PrimitiveCollection(source.getAsDataArray());
          case OBJECT:
            return new ValueObjectCollection(source.getAsDataArray());
          case THING:
            return new AggregateEntityCollection(source.getAsDataArray());
          default:
            throw new UnsupportedOperationException(
                String.format(
                    "Cannot make Array from %s",
                    source.getAsDataArray().getArrayElement().getDataPrimaryType()));
        }
      case OBJECT:
        return new ValueObject(source.getAsDataObject());
      case PRIMITIVE:
        return convertPrimitive(domainObject, source.getAsDataPrimitive());
      case THING:
        if (source.getDataPurpose().equals(DataPurpose.referenceToThingById())) {
          return new ReferenceById(source);
        } else if (source.getDataPurpose().equals(DataPurpose.referenceToThingByValue())) {
          return new ReferenceByValue(source);
        } else {
          throw new UnsupportedOperationException(
              String.format(
                  "Cannot make DomainObjectProperty from %s", source.getDataPrimaryType()));
        }
      case MAP:
        return new Mapper(source.getAsDataMap());
      default:
        throw new UnsupportedOperationException(
            String.format("Cannot make DomainObjectProperty from %s", source.getDataPrimaryType()));
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private BaseProperty<?> convertPrimitive(DomainObject domainObject, DataPrimitive data) {
    if (data.getDataPurpose().equals(DataPurpose.thingIdentity())) {
      return makeDomainObjectIdentity(domainObject);
    }

    if (data.getAsDataPrimitive().getDataObject() != null) {
      return makeValueObjectFromPrimitive(data.getAsDataPrimitive());
    } else {
      return new Primitive(data.getAsDataPrimitive());
    }
  }

  private BaseProperty<?> makeDomainObjectIdentity(DomainObject domainObject) {
    DataObject dataObject =
        new DataObject(
            new ObjectName(String.format("%sId", domainObject.getObjectName().getText())),
            domainObject.getPackageName());

    if (domainObject.isAggregateRoot()) {
      return new AggregateRootId(dataObject);
    } else if (domainObject.isAbstractAggregateRoot()) {
      return new AbstractAggregateRootId(dataObject, new GenericTypeParameter("I"));
    } else if (domainObject.isAggregateEntity()) {
      return new AggregateEntityId(dataObject);
    } else if (domainObject.isAbstractAggregateEntity()) {
      return new AggregateEntityId(dataObject);
    } else {
      throw new UnsupportedOperationException(domainObject.getDomainObjectType().name());
    }
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
