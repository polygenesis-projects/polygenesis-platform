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

package io.polygenesis.abstraction.thing;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

public class PurposeTest extends AbstractEqualityTest<Purpose> {

  @Test
  public void shouldInitialize() {
    Purpose purpose = Purpose.create();
    assertThat(purpose).isNotNull();
  }

  @Test
  public void shouldInitializeWithCustomPurpose() {
    Purpose purpose = Purpose.custom("some custom purpose", CqsType.COMMAND);
    assertThat(purpose).isNotNull();
  }

  @Test
  public void shouldBeCreate() {
    Purpose purpose = Purpose.create();

    assertThat(purpose.isCommand()).isTrue();
    assertThat(purpose.isCreate()).isTrue();
  }

  @Test
  public void shouldBeModify() {
    Purpose purpose = Purpose.modify();

    assertThat(purpose.isCommand()).isTrue();
    assertThat(purpose.isModify()).isTrue();
  }

  @Test
  public void shouldBeDelete() {
    Purpose purpose = Purpose.delete();

    assertThat(purpose.isCommand()).isTrue();
    assertThat(purpose.isDelete()).isTrue();
  }

  @Test
  public void shouldBeFetchOne() {
    Purpose purpose = Purpose.fetchOne();

    assertThat(purpose.isCommand()).isFalse();
    assertThat(purpose.isFetchOne()).isTrue();
  }

  @Test
  public void shouldBeFetchCollection() {
    Purpose purpose = Purpose.fetchCollection();

    assertThat(purpose.isCommand()).isFalse();
    assertThat(purpose.isFetchCollection()).isTrue();
  }

  @Test
  public void shouldBeFetchPagedCollection() {
    Purpose purpose = Purpose.fetchPagedCollection();

    assertThat(purpose.isCommand()).isFalse();
    assertThat(purpose.isFetchPagedCollection()).isTrue();
  }

  @Override
  public Purpose createObject1() {
    return Purpose.create();
  }

  @Override
  public Purpose createObject2() {
    return Purpose.modify();
  }
}
