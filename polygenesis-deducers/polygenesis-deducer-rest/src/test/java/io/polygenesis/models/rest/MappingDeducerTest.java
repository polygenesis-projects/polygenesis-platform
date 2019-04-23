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

package io.polygenesis.models.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Mapping deducer test.
 *
 * @author Christos Tsakostas
 */
public class MappingDeducerTest extends AbstractRestDeducerTest {

  private MappingDeducer mappingDeducer;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    mappingDeducer = new MappingDeducer();
  }

  /** Should succeed to deduce mapping. */
  @Test
  public void shouldSucceedToDeduceCreateMapping() {
    Set<Mapping> mappings = mappingDeducer.deduceFrom(makeFunctionCreate(), HttpMethod.POST);

    assertThat(mappings).isNotNull();
    assertThat(mappings.size()).isEqualTo(1);
    assertThat(mappings)
        .contains(new Mapping(new LinkedHashSet<>(Arrays.asList(new PathConstant("customers")))));
  }

  /** Should succeed to deduce put mapping. */
  @Test
  public void shouldSucceedToDeducePutMapping() {
    Set<Mapping> mappings = mappingDeducer.deduceFrom(makeFunctionModify(), HttpMethod.PUT);

    assertThat(mappings).isNotNull();
    assertThat(mappings.size()).isEqualTo(1);
    assertThat(mappings)
        .contains(
            new Mapping(
                new LinkedHashSet<>(
                    Arrays.asList(new PathConstant("customers"), new PathVariable("customerId")))));
  }

  /** Should succeed to deduce delete mapping. */
  @Test
  public void shouldSucceedToDeduceDeleteMapping() {
    Set<Mapping> mappings = mappingDeducer.deduceFrom(makeFunctionDelete(), HttpMethod.DELETE);

    assertThat(mappings).isNotNull();
    assertThat(mappings.size()).isEqualTo(1);
    assertThat(mappings)
        .contains(
            new Mapping(
                new LinkedHashSet<>(
                    Arrays.asList(new PathConstant("customers"), new PathVariable("customerId")))));
  }

  /** Should succeed to deduce get mapping for fetch one. */
  @Test
  public void shouldSucceedToDeduceGetMappingForFetchOne() {
    Set<Mapping> mappings = mappingDeducer.deduceFrom(makeFunctionFetchOne(), HttpMethod.GET);

    assertThat(mappings).isNotNull();
    assertThat(mappings.size()).isEqualTo(1);
    assertThat(mappings)
        .contains(
            new Mapping(
                new LinkedHashSet<>(
                    Arrays.asList(new PathConstant("customers"), new PathVariable("customerId")))));
  }

  /** Should succeed to deduce get mapping for fetch collection. */
  @Test
  public void shouldSucceedToDeduceGetMappingForFetchCollection() {
    Set<Mapping> mappings =
        mappingDeducer.deduceFrom(makeFunctionFetchCollection(), HttpMethod.GET);

    assertThat(mappings).isNotNull();
    assertThat(mappings.size()).isEqualTo(1);
    assertThat(mappings)
        .contains(new Mapping(new LinkedHashSet<>(Arrays.asList(new PathConstant("customers")))));
  }

  /** Should succeed to deduce get mapping for fetch paged collection. */
  @Test
  public void shouldSucceedToDeduceGetMappingForFetchPagedCollection() {
    Set<Mapping> mappings =
        mappingDeducer.deduceFrom(makeFunctionFetchPagedCollection(), HttpMethod.GET);

    assertThat(mappings).isNotNull();
    assertThat(mappings.size()).isEqualTo(1);
    assertThat(mappings)
        .contains(new Mapping(new LinkedHashSet<>(Arrays.asList(new PathConstant("customers")))));
  }

  /** Should fail to deduce mapping. */
  @Test
  public void shouldFailToDeduceMapping() {
    assertThatThrownBy(() -> mappingDeducer.deduceFrom(makeInvalidGetFunction(), HttpMethod.GET))
        .isInstanceOf(IllegalStateException.class);
  }
}
