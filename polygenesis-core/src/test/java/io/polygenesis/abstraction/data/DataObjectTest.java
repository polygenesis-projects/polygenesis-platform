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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DataObjectTest extends AbstractEqualityTest<DataObject> {

  @Test
  public void shouldInitializeDataGroup() {
    DataObject dataObject =
        new DataObject(
            new ObjectName("SomeClass"),
            new PackageName("com.dummy"),
            new VariableName("someVariableName"));

    assertThat(dataObject).isNotNull();
    assertThat(dataObject.getDataType()).isEqualTo(new ObjectName("SomeClass").getText());
    assertThat(dataObject.getVariableName()).isEqualTo(new VariableName("someVariableName"));

    DataArray childDataArray = new DataArray(new VariableName("someVariableName"), dataObject);
    dataObject.addData(childDataArray);

    DataObject childDataObject =
        new DataObject(
            new ObjectName("SomeClass"),
            new PackageName("com.dummy"),
            new VariableName("someVariableName"));
    dataObject.addData(childDataObject);

    DataPrimitive childDataPrimitive =
        new DataPrimitive(
            PrimitiveType.STRING, new VariableName("someVariableName"), null, DataPurpose.any());
    dataObject.addData(childDataPrimitive);

    assertThat(dataObject.getModels().size()).isEqualTo(3);

    assertThat(childDataArray.isDataPrimitive()).isFalse();
    assertThat(childDataObject.isDataPrimitive()).isFalse();
    assertThat(childDataPrimitive.isDataPrimitive()).isTrue();

    assertThat(dataObject.getAsDataObject()).isNotNull();
    assertThat(dataObject.asDto()).isNotNull();
    assertThat(dataObject.getDataType()).isEqualTo("someClass");

    assertThatThrownBy(() -> dataObject.getAsDataArray()).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> dataObject.getAsDataPrimitive())
        .isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> dataObject.getAsDataMap()).isInstanceOf(IllegalStateException.class);
  }

  @Override
  public DataObject createObject1() {
    return new DataObject(
        new ObjectName("SomeClass"),
        new PackageName("com.dummy"),
        new VariableName("someVariableName"));
  }

  @Override
  public DataObject createObject2() {
    return new DataObject(
        new ObjectName("SomeClass"),
        new PackageName("com.dummy"),
        new VariableName("someOtherVariableName"));
  }
}
