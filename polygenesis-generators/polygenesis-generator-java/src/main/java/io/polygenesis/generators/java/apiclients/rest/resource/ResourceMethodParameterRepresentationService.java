/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.apiclients.rest.resource;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class ResourceMethodParameterRepresentationService {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final DataTypeTransformer dataTypeTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Resource method parameter representation service.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public ResourceMethodParameterRepresentationService(DataTypeTransformer dataTypeTransformer) {
    this.dataTypeTransformer = dataTypeTransformer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Parameter representations set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  public Set<ParameterRepresentation> parameterRepresentations(Endpoint source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    Optional<Data> optionalParentThingIdentityData =
        source.getServiceMethod().getRequestDto().getParentThingIdentityAsOptional();

    if (optionalParentThingIdentityData.isPresent()) {
      parameterRepresentations.add(
          parameterRepresentationsForIdPathVariable(
              optionalParentThingIdentityData
                  .orElseThrow(IllegalArgumentException::new)
                  .getVariableName()
                  .getText()));
    }

    Optional<Data> thingIdentityData =
        source.getServiceMethod().getRequestDto().getThingIdentityAsOptional();

    switch (source.getHttpMethod()) {
      case GET:
        if (source.getServiceMethod().getFunction().getPurpose().isFetchOne()) {
          parameterRepresentations.add(
              parameterRepresentationsForIdPathVariable(
                  thingIdentityData
                      .orElseThrow(IllegalArgumentException::new)
                      .getVariableName()
                      .getText()));
        } else if (source.getServiceMethod().getFunction().getPurpose().isFetchPagedCollection()
            || source.getServiceMethod().getFunction().getPurpose().isEntityFetchAll()) {
          parameterRepresentations.addAll(parameterRepresentationsForPagedCollection());
        } else if (source.getServiceMethod().getFunction().getPurpose().isEntityFetch()) {
          parameterRepresentations.add(
              parameterRepresentationsForIdPathVariable(
                  source
                      .getServiceMethod()
                      .getFunction()
                      .getDelegatesToFunction()
                      .getThing()
                      .getThingIdentity()
                      .getVariableName()
                      .getText()));
        } else {
          throw new UnsupportedOperationException(
              source.getServiceMethod().getFunction().getPurpose().getText());
        }
        break;
      case PUT:
        parameterRepresentations.add(
            parameterRepresentationsForIdPathVariable(
                thingIdentityData
                    .orElseThrow(IllegalArgumentException::new)
                    .getVariableName()
                    .getText()));
        break;
      default:
        break;
    }

    if (source.getServiceMethod().getFunction().getPurpose().isCreate()
        || source.getServiceMethod().getFunction().getPurpose().isModify()) {
      parameterRepresentations.add(
          new ParameterRepresentation(
              dataTypeTransformer.convert(
                  source.getServiceMethod().getRequestDto().getDataObject().getDataType()),
              source.getServiceMethod().getRequestDto().getDataObject().getVariableName().getText(),
              new LinkedHashSet<>(Arrays.asList("@RequestBody"))));
    }

    parameterRepresentations.add(
        new ParameterRepresentation("HttpServletRequest", "httpServletRequest"));

    return parameterRepresentations;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Set<ParameterRepresentation> parameterRepresentationsForPagedCollection() {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            "Integer",
            "pageNumber",
            new LinkedHashSet<>(Arrays.asList("@RequestParam(defaultValue=\"0\")"))));

    parameterRepresentations.add(
        new ParameterRepresentation(
            "Integer",
            "pageSize",
            new LinkedHashSet<>(Arrays.asList("@RequestParam(defaultValue=\"20\")"))));

    parameterRepresentations.add(
        new ParameterRepresentation(
            "String",
            "query",
            new LinkedHashSet<>(
                Arrays.asList("@RequestParam(required = false, defaultValue = \"\")"))));

    return parameterRepresentations;
  }

  private ParameterRepresentation parameterRepresentationsForIdPathVariable(String idVariable) {
    return new ParameterRepresentation(
        "String",
        idVariable,
        new LinkedHashSet<>(Arrays.asList(String.format("@PathVariable(\"%s\")", idVariable))));
  }
}
