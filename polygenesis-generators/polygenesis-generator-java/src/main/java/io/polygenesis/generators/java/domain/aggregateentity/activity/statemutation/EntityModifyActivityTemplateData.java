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

package io.polygenesis.generators.java.domain.aggregateentity.activity.statemutation;

import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.models.domain.DomainEvent;
import io.polygenesis.models.domain.PropertyType;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Entity modify activity template data.
 *
 * @author Christos Tsakostas
 */
public class EntityModifyActivityTemplateData {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<ParameterRepresentation> superClassParameterRepresentations;
  private Set<ParameterRepresentation> thisClassParameterRepresentations;
  private DomainEvent domainEvent;
  private Set<ParameterRepresentation> domainEventParameterRepresentations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Entity modify activity template data.
   *
   * @param parameterRepresentations the parameter representations
   * @param domainEvent the domain event
   */
  @SuppressWarnings("CPD-START")
  public EntityModifyActivityTemplateData(
      Set<ParameterRepresentation> parameterRepresentations, DomainEvent domainEvent) {
    distinguishBetweenSuperAndThisClassParameters(parameterRepresentations);
    produceDomainEventParameterRepresentations(domainEvent);
    this.domainEvent = domainEvent;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets super class parameter representations.
   *
   * @return the super class parameter representations
   */
  public Set<ParameterRepresentation> getSuperClassParameterRepresentations() {
    return superClassParameterRepresentations;
  }

  /**
   * Gets this class parameter representations.
   *
   * @return the this class parameter representations
   */
  public Set<ParameterRepresentation> getThisClassParameterRepresentations() {
    return thisClassParameterRepresentations;
  }

  /**
   * Gets domain event.
   *
   * @return the domain event
   */
  public DomainEvent getDomainEvent() {
    return domainEvent;
  }

  /**
   * Gets domain event parameter representations.
   *
   * @return the domain event parameter representations
   */
  public Set<ParameterRepresentation> getDomainEventParameterRepresentations() {
    return domainEventParameterRepresentations;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private void distinguishBetweenSuperAndThisClassParameters(
      Set<ParameterRepresentation> parameterRepresentations) {
    superClassParameterRepresentations =
        parameterRepresentations
            .stream()
            .filter(
                parameterRepresentation ->
                    parameterRepresentation.getDataPurpose().equals(DataPurpose.thingIdentity())
                        || parameterRepresentation
                            .getDataPurpose()
                            .equals(DataPurpose.superclassParameter()))
            .collect(Collectors.toCollection(LinkedHashSet::new));

    thisClassParameterRepresentations =
        parameterRepresentations
            .stream()
            .filter(
                parameterRepresentation ->
                    !parameterRepresentation.getDataPurpose().equals(DataPurpose.thingIdentity())
                        && !parameterRepresentation
                            .getDataPurpose()
                            .equals(DataPurpose.superclassParameter()))
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private void produceDomainEventParameterRepresentations(DomainEvent domainEvent) {
    domainEventParameterRepresentations = new LinkedHashSet<>();

    if (domainEvent != null) {
      domainEvent
          .getProperties()
          .stream()
          .filter(
              property ->
                  !property.getPropertyType().equals(PropertyType.AGGREGATE_ROOT_ID)
                      && !property
                          .getPropertyType()
                          .equals(PropertyType.ABSTRACT_AGGREGATE_ROOT_ID))
          .forEach(
              property -> {
                domainEventParameterRepresentations.add(
                    new ParameterRepresentation(
                        property.getData().getDataType(),
                        property.getData().getVariableName().getText()));
              });
    }
  }
}
