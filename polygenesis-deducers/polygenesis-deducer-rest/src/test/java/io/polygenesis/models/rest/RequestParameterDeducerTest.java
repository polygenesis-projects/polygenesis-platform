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

package io.polygenesis.models.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class RequestParameterDeducerTest extends AbstractRestDeducerTest {

  private RequestParameterDeducer requestParameterDeducer;

  @Before
  public void setUp() throws Exception {
    requestParameterDeducer = new RequestParameterDeducer();
  }

  @Test
  public void deduceFrom() {
    Set<RequestParameter> requestParameters = requestParameterDeducer.deduceFrom();

    // TODO
    assertThat(requestParameters).isNotNull();
    assertThat(requestParameters.size()).isEqualTo(0);
  }
}
