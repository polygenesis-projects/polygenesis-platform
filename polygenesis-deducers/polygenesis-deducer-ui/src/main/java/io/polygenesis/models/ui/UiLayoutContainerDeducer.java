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

package io.polygenesis.models.ui;

import io.polygenesis.core.Deducer;
import io.polygenesis.core.Model;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.core.ThingRepository;
import io.polygenesis.models.ui.container.LayoutContainer;
import java.util.Set;

/**
 * The type Ui layout container deducer.
 *
 * @author Christos Tsakostas
 */
public class UiLayoutContainerDeducer implements Deducer<UiLayoutContainerModelRepository> {

  private final LayoutDeducer layoutDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Ui layout container deducer.
   *
   * @param layoutDeducer the layout deducer
   */
  public UiLayoutContainerDeducer(LayoutDeducer layoutDeducer) {
    this.layoutDeducer = layoutDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================
  @Override
  public UiLayoutContainerModelRepository deduce(
      ThingRepository thingRepository, Set<ModelRepository<? extends Model>> modelRepositories) {

    Set<LayoutContainer> layoutContainers = layoutDeducer.deduceLayoutsFromFeatures();

    return new UiLayoutContainerModelRepository(layoutContainers);
  }
}
