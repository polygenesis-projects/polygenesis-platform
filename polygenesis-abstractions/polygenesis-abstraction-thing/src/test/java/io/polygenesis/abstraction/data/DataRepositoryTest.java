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

package io.polygenesis.abstraction.data;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.valueobjects.VariableName;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DataRepositoryTest {

  private DataRepository dataRepository;

  @Before
  public void setUp() {
    dataRepository = new DataRepository();
  }

  @Test
  public void findByDataPurpose() {
    dataRepository.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("asd")));
    dataRepository.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("xyz")));

    assertThat(dataRepository.findByDataPurpose(DataPurpose.thingIdentity())).isEmpty();
    assertThat(
            dataRepository.findByDataPurpose(
                DataPurpose.thingIdentity(), DataPurpose.tenantIdentity()))
        .isEmpty();
    assertThat(dataRepository.findByDataPurpose(DataPurpose.any())).hasSize(2);
    assertThat(dataRepository.findByDataPurpose(DataPurpose.thingIdentity(), DataPurpose.any()))
        .hasSize(2);
  }
}
