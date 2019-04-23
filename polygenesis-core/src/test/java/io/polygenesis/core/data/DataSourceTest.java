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

package io.polygenesis.core.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.core.Function;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DataSourceTest extends AbstractEqualityTest<DataSource> {

  @Test
  public void shouldInstantiateDataSourceUser() {
    DataSource dataSource = createObject1();

    assertThat(dataSource.isUser()).isTrue();
  }

  @Test
  public void shouldInstantiateDataSourceFunction() {
    DataSource dataSource = createObject2();

    assertThat(dataSource.isUser()).isFalse();
  }

  @Override
  public DataSource createObject1() {
    return DataSource.user();
  }

  @Override
  public DataSource createObject2() {
    Function function = mock(Function.class);
    return DataSource.function(function);
  }
}
