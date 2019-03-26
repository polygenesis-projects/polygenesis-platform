/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.angular;

import io.polygenesis.core.ModelRepository;
import io.polygenesis.models.reactivestate.ReactiveStateModelRepository;
import io.polygenesis.models.ui.UiFeatureModelRepository;
import io.polygenesis.models.ui.UiLayoutContainerModelRepository;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/** @author Christos Tsakostas */
public abstract class AbstractAngularGeneratorTest {

  protected Set<ModelRepository> getModelRepositories() {
    Set<ModelRepository> modelRepositories =
        new LinkedHashSet<>(
            Arrays.asList(
                new ReactiveStateModelRepository(new LinkedHashSet<>()),
                new UiFeatureModelRepository(new LinkedHashSet<>()),
                new UiLayoutContainerModelRepository(new LinkedHashSet<>())
            ));

    return modelRepositories;
  }
}
