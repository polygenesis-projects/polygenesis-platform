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

import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.ThingRepositoryImpl;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.generators.java.api.JavaApiGeneratorFactory;
import io.polygenesis.models.api.ApiDeducerFactory;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

/** @author Christos Tsakostas */
public class JavaGenesisTest {

  @Test
  public void shouldGenerateForAnnotationsAndStateDeducer() {
    Genesis genesis = new Genesis();

    Set<Deducer> deducers =
        new LinkedHashSet<>(
            Arrays.asList(
                ApiDeducerFactory.newInstance(new PackageName("com.oregor.microservice.account"))));

    Set<Generator> generators =
        new LinkedHashSet<>(
            Arrays.asList(
                JavaApiGeneratorFactory.newInstance(
                    Paths.get("tmp/polygenesis-generator-java-api")),
                JavaApiGeneratorFactory.newInstance(
                    Paths.get("tmp/polygenesis-generator-java-api-impl"))));

    genesis.generate(
        new ThingRepositoryImpl(
            new LinkedHashSet<>(Arrays.asList(JavaGenesisThingCustomer.create()))),
        deducers,
        generators);
  }
}
