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

package io.polygenesis.generators.java.apidetail.service;

import io.polygenesis.abstraction.thing.AbstractActivityRegistry;
import io.polygenesis.abstraction.thing.AbstractActivityTemplateGenerator;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ScopePurposeTuple;
import io.polygenesis.core.AbstractionScope;
import io.polygenesis.core.FreemarkerTemplateEngine;
import io.polygenesis.core.TemplateEngine;
import io.polygenesis.generators.java.apidetail.service.activity.root.CreateAggregateRootGenerator;
import io.polygenesis.generators.java.apidetail.service.activity.root.CreateAggregateRootTransformer;
import io.polygenesis.generators.java.apidetail.service.activity.root.EnsureExistenceOfAggregateRootGenerator;
import io.polygenesis.generators.java.apidetail.service.activity.root.EnsureExistenceOfAggregateRootTransformer;
import io.polygenesis.generators.java.apidetail.service.activity.root.FetchOneAggregateRootGenerator;
import io.polygenesis.generators.java.apidetail.service.activity.root.FetchOneAggregateRootTransformer;
import io.polygenesis.generators.java.apidetail.service.activity.root.FetchPagedCollectionAggregateRootGenerator;
import io.polygenesis.generators.java.apidetail.service.activity.root.FetchPagedCollectionAggregateRootTransformer;
import io.polygenesis.generators.java.apidetail.service.activity.root.UpdateAggregateRootGenerator;
import io.polygenesis.generators.java.apidetail.service.activity.root.UpdateAggregateRootTransformer;
import io.polygenesis.generators.java.apidetail.service.activity.root.entity.CreateAggregateEntityGenerator;
import io.polygenesis.generators.java.apidetail.service.activity.root.entity.CreateAggregateEntityTransformer;
import io.polygenesis.generators.java.apidetail.service.activity.root.entity.UpdateAggregateEntityGenerator;
import io.polygenesis.generators.java.apidetail.service.activity.root.entity.UpdateAggregateEntityTransformer;
import io.polygenesis.generators.java.common.AggregateEntityDataService;
import io.polygenesis.generators.java.common.ParentCallingChildDataService;
import io.polygenesis.models.apiimpl.ServiceMethodImplementation;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Service detail method activity registry.
 *
 * @author Christos Tsakostas
 */
public class ServiceDetailMethodActivityRegistry
    extends AbstractActivityRegistry<ServiceMethodImplementation> {

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static Map<ScopePurposeTuple, AbstractActivityTemplateGenerator<?>> scopeAndPurposeMap =
      new HashMap<>();

  static {
    final TemplateEngine templateEngine = new FreemarkerTemplateEngine();
    final ParentCallingChildDataService parentCallingChildDataService =
        new ParentCallingChildDataService();
    final AggregateEntityDataService aggregateEntityDataService = new AggregateEntityDataService();

    // AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.ensureExistence()),
        new EnsureExistenceOfAggregateRootGenerator(
            new EnsureExistenceOfAggregateRootTransformer(
                aggregateEntityDataService, parentCallingChildDataService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.create()),
        new CreateAggregateRootGenerator(
            new CreateAggregateRootTransformer(
                aggregateEntityDataService, parentCallingChildDataService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.modify()),
        new UpdateAggregateRootGenerator(
            new UpdateAggregateRootTransformer(
                aggregateEntityDataService, parentCallingChildDataService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.fetchOne()),
        new FetchOneAggregateRootGenerator(
            new FetchOneAggregateRootTransformer(
                aggregateEntityDataService, parentCallingChildDataService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(
            AbstractionScope.domainAggregateRoot(), Purpose.fetchPagedCollection()),
        new FetchPagedCollectionAggregateRootGenerator(
            new FetchPagedCollectionAggregateRootTransformer(
                aggregateEntityDataService, parentCallingChildDataService),
            templateEngine));

    // AGGREGATE ENTITY MANAGED BY AGGREGATE ROOT
    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityCreate()),
        new CreateAggregateEntityGenerator(
            new CreateAggregateEntityTransformer(
                aggregateEntityDataService, parentCallingChildDataService),
            templateEngine));

    scopeAndPurposeMap.put(
        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityModify()),
        new UpdateAggregateEntityGenerator(
            new UpdateAggregateEntityTransformer(
                aggregateEntityDataService, parentCallingChildDataService),
            templateEngine));

    //    scopeAndPurposeMap.put(
    //        new ScopePurposeTuple(AbstractionScope.domainAggregateRoot(), Purpose.entityFetch()),
    //        new FetchOneAggregateEntityGenerator(
    //            new FetchOneAggregateEntityTransformer(aggregateEntityDataService,
    // parentCallingChildDataService), templateEngine));
    //
    //    scopeAndPurposeMap.put(
    //        new ScopePurposeTuple(
    //            AbstractionScope.domainAggregateRoot(), Purpose.entityFetchAll()),
    //        new FetchPagedCollectionAggregateEntityGenerator(
    //            new FetchPagedCollectionAggregateEntityTransformer(aggregateEntityDataService,
    // parentCallingChildDataService),
    //            templateEngine));

    // TODO: PROJECTION
    //    scopeAndPurposeMap.put(
    //        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.create()),
    //        new CreateAggregateRootGenerator(
    //            new CreateAggregateRootTransformer(aggregateEntityDataService,
    // parentCallingChildDataService),
    // templateEngine));
    //
    //    scopeAndPurposeMap.put(
    //        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.modify()),
    //        new UpdateAggregateRootGenerator(
    //            new UpdateAggregateRootTransformer(aggregateEntityDataService,
    // parentCallingChildDataService),
    // templateEngine));
    //
    //    scopeAndPurposeMap.put(
    //        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.fetchOne()),
    //        new FetchOneAggregateRootGenerator(
    //            new FetchOneAggregateRootTransformer(aggregateEntityDataService,
    // parentCallingChildDataService),
    // templateEngine));
    //
    //    scopeAndPurposeMap.put(
    //        new ScopePurposeTuple(AbstractionScope.projection(), Purpose.fetchPagedCollection()),
    //        new FetchPagedCollectionAggregateRootGenerator(
    //            new FetchPagedCollectionAggregateRootTransformer(aggregateEntityDataService,
    // parentCallingChildDataService),
    //            templateEngine));
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Service detail method activity registry. */
  public ServiceDetailMethodActivityRegistry() {
    super(scopeAndPurposeMap);
  }
}
