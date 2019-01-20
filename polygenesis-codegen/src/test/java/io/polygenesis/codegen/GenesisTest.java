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

package io.polygenesis.codegen;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.generators.angular.PolyGenesisAngularGeneratorFactory;
import io.polygenesis.models.reactivestate.ReactiveStateRegistry;
import io.polygenesis.models.ui.UiRegistry;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class GenesisTest {

  @Test
  public void shouldGenerateForAnnotationsAndStateDeducer() {
    GenesisRequest genesisRequest =
        GenesisRequestBuilder.empty()
            .withAnnotationsRequest(
                AnnotationsRequestBuilder.empty()
                    .withPackagesToScan(new LinkedHashSet<>(Arrays.asList("io.polygenesis")))
                    .build())
            .build();

    Genesis genesis = new Genesis();

    Set<Deducer> deducers =
        new LinkedHashSet<>(
            Arrays.asList(
                ReactiveStateRegistry.getReactiveStateDeducer(), UiRegistry.getUiDeducer()));

    Set<Generator> generators =
        new LinkedHashSet<>(
            Arrays.asList(
                PolyGenesisAngularGeneratorFactory.newInstance(
                    Paths.get("tmp/polygenesis-angular-generator"))));

    genesis.generate(genesisRequest, deducers, generators);
  }

  @Test
  public void shouldFailToGenerateForMissingStateRepository() {
    GenesisRequest genesisRequest =
        GenesisRequestBuilder.empty()
            .withAnnotationsRequest(
                AnnotationsRequestBuilder.empty()
                    .withPackagesToScan(new LinkedHashSet<>(Arrays.asList("io.polygenesis")))
                    .build())
            .build();

    Genesis genesis = new Genesis();

    Set<Deducer> deducers = new LinkedHashSet<>();

    Set<Generator> generators =
        new LinkedHashSet<>(
            Arrays.asList(
                PolyGenesisAngularGeneratorFactory.newInstance(
                    Paths.get("tmp/polygenesis-angular-generator"))));

    assertThatThrownBy(() -> genesis.generate(genesisRequest, deducers, generators))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
