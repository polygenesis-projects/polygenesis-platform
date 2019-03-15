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

package io.polygenesis.core.data;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DataGroupTest extends AbstractEqualityTest<DataGroup> {

  @Test
  public void shouldInitializeDataGroup() {
    DataGroup dataGroup =
        new DataGroup(
            new ObjectName("SomeClass"),
            new PackageName("com.dummy"),
            new VariableName("someVariableName"));

    assertThat(dataGroup).isNotNull();
    assertThat(dataGroup.getDataType()).isEqualTo(new ObjectName("SomeClass").getText());
    assertThat(dataGroup.getVariableName()).isEqualTo(new VariableName("someVariableName"));

    DataArray childDataArray = new DataArray(new VariableName("someVariableName"), dataGroup);
    dataGroup.addData(childDataArray);

    DataGroup childDataGroup =
        new DataGroup(
            new ObjectName("SomeClass"),
            new PackageName("com.dummy"),
            new VariableName("someVariableName"));
    dataGroup.addData(childDataGroup);

    DataPrimitive childDataPrimitive =
        new DataPrimitive(
            PrimitiveType.STRING, new VariableName("someVariableName"), null, DataBusinessType.ANY);
    dataGroup.addData(childDataPrimitive);

    assertThat(dataGroup.getModels().size()).isEqualTo(3);

    assertThat(childDataArray.isDataPrimitive()).isFalse();
    assertThat(childDataGroup.isDataPrimitive()).isFalse();
    assertThat(childDataPrimitive.isDataPrimitive()).isTrue();
  }

  @Override
  public DataGroup createObject1() {
    return new DataGroup(
        new ObjectName("SomeClass"),
        new PackageName("com.dummy"),
        new VariableName("someVariableName"));
  }

  @Override
  public DataGroup createObject2() {
    return new DataGroup(
        new ObjectName("SomeClass"),
        new PackageName("com.dummy"),
        new VariableName("someOtherVariableName"));
  }
}
