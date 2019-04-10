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

package io.polygenesis.models.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.data.DataGroup;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DtoTest extends AbstractEqualityTest<Dto> {

  @Test
  public void shouldCreateDto() {
    Dto dto =
        new Dto(
            DtoType.API_REQUEST,
            new DataGroup(new ObjectName("asd"), new PackageName("com.oregor")),
            false);

    assertThat(dto).isNotNull();
    assertThat(dto.getDataGroup()).isNotNull();
    assertThat(dto.getDataGroup())
        .isEqualTo(new DataGroup(new ObjectName("asd"), new PackageName("com.oregor")));
  }

  @Override
  public Dto createObject1() {
    return new Dto(
        DtoType.API_REQUEST,
        new DataGroup(new ObjectName("asd"), new PackageName("com.oregor")),
        false);
  }

  @Override
  public Dto createObject2() {
    return new Dto(
        DtoType.API_REQUEST,
        new DataGroup(new ObjectName("xyz"), new PackageName("com.oregor")),
        false);
  }
}
