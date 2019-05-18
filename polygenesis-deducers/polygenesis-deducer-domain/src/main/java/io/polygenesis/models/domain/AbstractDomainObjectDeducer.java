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

package io.polygenesis.models.domain;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.ThingType;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataArray;
import io.polygenesis.core.data.DataBusinessType;
import io.polygenesis.core.data.DataGroup;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract domain object deducer.
 *
 * @author Christos Tsakostas
 */
public class AbstractDomainObjectDeducer {

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
  @SuppressWarnings("rawtypes")
  protected Set<DomainObjectProperty> deduceFromFunctionArguments(
      Function function, PackageName rootPackageName) {
    Set<DomainObjectProperty> properties = new LinkedHashSet<>();

    // Add Aggregate Root ID if the thing is not abstract
    if (function.getThing().getThingType().equals(ThingType.DOMAIN_AGGREGATE_ROOT)
        && function.getGoal().isCreate()) {
      properties.add(makeAggregateRootId(function, rootPackageName));

      if (function.getThing().getMultiTenant()) {
        properties.add(makeTenantId());
      }
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
                                && !isPropertyParentThingIdentity(model)
                                && !isPropertyPageNumber(model)
                                && !isPropertyPageSize(model)) {
                              properties.add(makeDomainObjectProperty(model));
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

  @SuppressWarnings("rawtypes")
  private DomainObjectProperty makeDomainObjectProperty(Data model) {
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
        return new ValueObject(model.getAsDataGroup());
      case PRIMITIVE:
        return new Primitive(model.getAsDataPrimitive());
      case THING:
        return new Reference(model);
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

  /**
   * Make aggregate root id aggregate root id.
   *
   * @param function the function
   * @param rootPackageName the root package name
   * @return the aggregate root id
   */
  private AggregateRootId makeAggregateRootId(Function function, PackageName rootPackageName) {
    DataGroup dataGroup =
        new DataGroup(
            new ObjectName(function.getThing().getThingName().getText() + "Id"),
            function.getThing().makePackageName(rootPackageName, function.getThing()));

    return new AggregateRootId(dataGroup);
  }

  /**
   * Make tenant id tenant id.
   *
   * @return the tenant id
   */
  private TenantId makeTenantId() {
    DataGroup dataGroup =
        new DataGroup(new ObjectName("TenantId"), new PackageName("com.oregor.trinity4j.domain"));

    return new TenantId(dataGroup);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Is property thing identity boolean.
   *
   * @param model the model
   * @return the boolean
   */
  private boolean isPropertyThingIdentity(Data model) {
    return model.getDataBusinessType().equals(DataBusinessType.THING_IDENTITY);
  }

  /**
   * Is property parent thing identity boolean.
   *
   * @param model the model
   * @return the boolean
   */
  private boolean isPropertyParentThingIdentity(Data model) {
    return model.getDataBusinessType().equals(DataBusinessType.PARENT_THING_IDENTITY);
  }

  /**
   * Is property page number boolean.
   *
   * @param model the model
   * @return the boolean
   */
  private boolean isPropertyPageNumber(Data model) {
    return model.getDataBusinessType().equals(DataBusinessType.PAGE_NUMBER);
  }

  /**
   * Is property page size boolean.
   *
   * @param model the model
   * @return the boolean
   */
  private boolean isPropertyPageSize(Data model) {
    return model.getDataBusinessType().equals(DataBusinessType.PAGE_SIZE);
  }
}
