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

package io.polygenesis.generators.java.apidetail.converter.activity;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.ValueObject;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractConverterActivityTransformer {

  // ===============================================================================================
  // TRANSFORMERS
  // ===============================================================================================

  /**
   * Create parameter representations set.
   *
   * @param source the source
   * @return the set
   */
  public Set<ParameterRepresentation> createParameterRepresentations(
      DomainObjectConverterMethod source) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    if (source.getFrom() instanceof DomainObject) {
      DomainObject domainObject = DomainObject.class.cast(source.getFrom());

      parameterRepresentations.add(
          new ParameterRepresentation(
              TextConverter.toUpperCamel(domainObject.getObjectName().getText()),
              TextConverter.toLowerCamel(domainObject.getObjectName().getText())));
    } else if (source.getFrom() instanceof Dto) {
      Dto dto = Dto.class.cast(source.getFrom());

      parameterRepresentations.add(
          new ParameterRepresentation(
              TextConverter.toUpperCamel(dto.getObjectName().getText()),
              TextConverter.toLowerCamel(dto.getObjectName().getText())));
    } else if (source.getFrom() instanceof ValueObject) {
      ValueObject valueObject = ValueObject.class.cast(source.getFrom());

      parameterRepresentations.add(
          new ParameterRepresentation(
              TextConverter.toUpperCamel(valueObject.getObjectName().getText()),
              TextConverter.toLowerCamel(valueObject.getObjectName().getText())));
    } else {
      throw new UnsupportedOperationException();
    }

    return parameterRepresentations;
  }
}
