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

package io.polygenesis.deducers.stateredux;

import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.metamodels.stateredux.ActionGroup;
import io.polygenesis.metamodels.stateredux.EffectGroup;
import io.polygenesis.metamodels.stateredux.Model;
import io.polygenesis.metamodels.stateredux.ReducerGroup;
import io.polygenesis.metamodels.stateredux.Store;
import io.polygenesis.models.api.ServiceMetamodelRepository;
import java.util.Set;

public class StoreDeducer {

  private FeatureNameDeducer featureNameDeducer;
  private ActionGroupDeducer actionGroupDeducer;
  private EffectGroupDeducer effectGroupDeducer;
  private ModelDeducer modelDeducer;
  private ReducerGroupDeducer reducerGroupDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Store deducer.
   *
   * @param featureNameDeducer the feature name deducer
   * @param actionGroupDeducer the action group deducer
   * @param effectGroupDeducer the effect group deducer
   * @param modelDeducer the model deducer
   * @param reducerGroupDeducer the reducer group deducer
   */
  public StoreDeducer(
      FeatureNameDeducer featureNameDeducer,
      ActionGroupDeducer actionGroupDeducer,
      EffectGroupDeducer effectGroupDeducer,
      ModelDeducer modelDeducer,
      ReducerGroupDeducer reducerGroupDeducer) {
    this.featureNameDeducer = featureNameDeducer;
    this.actionGroupDeducer = actionGroupDeducer;
    this.effectGroupDeducer = effectGroupDeducer;
    this.modelDeducer = modelDeducer;
    this.reducerGroupDeducer = reducerGroupDeducer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce {@link Store} from {@link Thing}.
   *
   * @param thing the thing
   * @param serviceModelRepository the service model repository
   * @return the store
   */
  public Store deduceStoreFromThing(
      Thing thing, ServiceMetamodelRepository serviceModelRepository) {
    Set<ActionGroup> actionGroups = actionGroupDeducer.deduce(thing, serviceModelRepository);
    Set<EffectGroup> effectGroups = effectGroupDeducer.deduce(thing);
    Set<Model> models = modelDeducer.deduce(thing, serviceModelRepository);
    Set<ReducerGroup> reducerGroups = reducerGroupDeducer.deduce();

    return new Store(
        featureNameDeducer.from(thing), actionGroups, effectGroups, models, reducerGroups);
  }
}
