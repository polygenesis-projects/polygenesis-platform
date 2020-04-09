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

package io.polygenesis.generators.java.apidetail.converter;

import io.polygenesis.abstraction.thing.AbstractActivityRegistry;
import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.apidetail.converter.activity.DtoToVoActivityGenerator;
import io.polygenesis.generators.java.apidetail.converter.activity.DtoToVoActivityTransformer;
import io.polygenesis.generators.java.apidetail.converter.activity.ToCollectionRecordActivityGenerator;
import io.polygenesis.generators.java.apidetail.converter.activity.ToCollectionRecordActivityTransformer;
import io.polygenesis.generators.java.apidetail.converter.activity.VoToDtoActivityGenerator;
import io.polygenesis.generators.java.apidetail.converter.activity.VoToDtoActivityTransformer;
import io.polygenesis.models.apiimpl.DomainObjectConverterMethod;
import java.util.HashMap;
import java.util.Map;

public class DomainObjectConverterActivityRegistry
    extends AbstractActivityRegistry<DomainObjectConverterMethod> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  static {
    TemplateEngine templateEngine = new FreemarkerTemplateEngine();

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.convertDtoToVo()),
        new DtoToVoActivityGenerator(new DtoToVoActivityTransformer(), templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.convertVoToDto()),
        new VoToDtoActivityGenerator(new VoToDtoActivityTransformer(), templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(
            AbstractionScope.domainAggregateRoot(),
            Purpose.convertDomainObjectToCollectionRecord()),
        new ToCollectionRecordActivityGenerator(
            new ToCollectionRecordActivityTransformer(), templateEngine));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Domain object converter activity registry. */
  public DomainObjectConverterActivityRegistry() {
    super(scopeAndPurposeMap);
  }
}
