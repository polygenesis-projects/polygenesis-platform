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

package io.polygenesis.generators.java.apiclients.rest.resource;

import io.polygenesis.abstraction.thing.AbstractActivityRegistry;
import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.CreateAggregateEntityActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.CreateAggregateEntityActivityTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.FetchOneAggregateEntityActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.FetchOneAggregateEntityActivityTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.FetchPagedCollectionAggregateEntityActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.FetchPagedCollectionAggregateEntityActivityTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.ModifyAggregateEntityActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.entity.ModifyAggregateEntityActivityTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.CreateAggregateRootActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.CreateAggregateRootActivityTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.FetchOneAggregateRootActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.FetchOneAggregateRootActivityTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.FetchPagedCollectionAggregateRootActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.FetchPagedCollectionAggregateRootActivityTransformer;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.ModifyAggregateRootActivityGenerator;
import io.polygenesis.generators.java.apiclients.rest.resource.activity.root.ModifyAggregateRootActivityTransformer;
import io.polygenesis.models.rest.Endpoint;
import io.polygenesis.transformers.java.JavaDataTypeTransformer;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Resource activity registry.
 *
 * @author Christos Tsakostas
 */
public class ResourceActivityRegistry extends AbstractActivityRegistry<Endpoint> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final DataTypeTransformer dataTypeTransformer = new JavaDataTypeTransformer();
    final ResourceMethodParameterRepresentationService parameterRepresentationService =
        new ResourceMethodParameterRepresentationService(dataTypeTransformer);

    // AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.create()),
        new CreateAggregateRootActivityGenerator(
            new CreateAggregateRootActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.modify()),
        new ModifyAggregateRootActivityGenerator(
            new ModifyAggregateRootActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.fetchOne()),
        new FetchOneAggregateRootActivityGenerator(
            new FetchOneAggregateRootActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(
            AbstractionScope.domainAggregateRoot(), Purpose.fetchPagedCollection()),
        new FetchPagedCollectionAggregateRootActivityGenerator(
            new FetchPagedCollectionAggregateRootActivityTransformer(
                parameterRepresentationService),
            templateEngine));

    // AGGREGATE ENTITY
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityCreate()),
        new CreateAggregateEntityActivityGenerator(
            new CreateAggregateEntityActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityModify()),
        new ModifyAggregateEntityActivityGenerator(
            new ModifyAggregateEntityActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityFetch()),
        new FetchOneAggregateEntityActivityGenerator(
            new FetchOneAggregateEntityActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityFetchAll()),
        new FetchPagedCollectionAggregateEntityActivityGenerator(
            new FetchPagedCollectionAggregateEntityActivityTransformer(
                parameterRepresentationService),
            templateEngine));

    // AGGREGATE ENTITY
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.create()),
        new CreateAggregateEntityActivityGenerator(
            new CreateAggregateEntityActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.modify()),
        new ModifyAggregateEntityActivityGenerator(
            new ModifyAggregateEntityActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateEntity(), Purpose.fetchOne()),
        new FetchOneAggregateEntityActivityGenerator(
            new FetchOneAggregateEntityActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(
            AbstractionScope.domainAggregateEntity(), Purpose.fetchPagedCollection()),
        new FetchPagedCollectionAggregateEntityActivityGenerator(
            new FetchPagedCollectionAggregateEntityActivityTransformer(
                parameterRepresentationService),
            templateEngine));

    // PROJECTION
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.create()),
        new CreateAggregateRootActivityGenerator(
            new CreateAggregateRootActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.modify()),
        new ModifyAggregateRootActivityGenerator(
            new ModifyAggregateRootActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.fetchOne()),
        new FetchOneAggregateRootActivityGenerator(
            new FetchOneAggregateRootActivityTransformer(parameterRepresentationService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.fetchPagedCollection()),
        new FetchPagedCollectionAggregateRootActivityGenerator(
            new FetchPagedCollectionAggregateRootActivityTransformer(
                parameterRepresentationService),
            templateEngine));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Resource activity registry. */
  public ResourceActivityRegistry() {
    super(scopeAndPurposeMap);
  }
}
