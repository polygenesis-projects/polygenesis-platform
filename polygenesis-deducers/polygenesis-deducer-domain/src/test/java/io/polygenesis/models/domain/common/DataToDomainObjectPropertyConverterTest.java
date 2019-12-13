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

package io.polygenesis.models.domain.common;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.PropertyType;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DataToDomainObjectPropertyConverterTest {

  private DataToDomainObjectPropertyConverter dataToDomainObjectPropertyConverter;

  @Before
  public void setUp() throws Exception {
    dataToDomainObjectPropertyConverter = new DataToDomainObjectPropertyConverter();
  }

  @Test
  public void shouldConvertThingIdentity() {
    // Given
    DomainObject domainObject = mock(DomainObject.class);
    given(domainObject.getObjectName()).willReturn(new ObjectName("someObject"));
    given(domainObject.isAggregateRoot()).willReturn(true);

    DataObject dataObjectIdentity =
        new DataObject(
            new ObjectName(String.format("%sId", domainObject.getObjectName().getText())),
            domainObject.getPackageName());

    DataPrimitive data =
        DataPrimitive.ofDataBusinessTypeWithDataObject(
            DataPurpose.thingIdentity(),
            PrimitiveType.STRING,
            new VariableName("someId"),
            dataObjectIdentity);

    // When
    DomainObjectProperty<?> property =
        dataToDomainObjectPropertyConverter.convert(domainObject, data);

    // Then
    assertThat(property.getPropertyType()).isEqualTo(PropertyType.AGGREGATE_ROOT_ID);
    assertThat(property.getData()).isEqualTo(dataObjectIdentity);
  }

  @Test
  public void shouldConvertPrimitive() {
    // Given
    DomainObject domainObject = mock(DomainObject.class);

    DataPrimitive data = DataPrimitive.of(PrimitiveType.STRING, new VariableName("name"));

    // When
    DomainObjectProperty<?> property =
        dataToDomainObjectPropertyConverter.convert(domainObject, data);

    // Then
    assertThat(property.getPropertyType()).isEqualTo(PropertyType.PRIMITIVE);
    assertThat(property.getData()).isEqualTo(data);
  }

  @Test
  public void shouldConvertPrimitiveAsValueObject() {
    // Given
    DomainObject domainObject = mock(DomainObject.class);

    DataObject dataObject =
        new DataObject(
            new ObjectName("SomeObj"), new PackageName("com.oregor"), new VariableName("someName"));
    DataPrimitive data =
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("someName"), dataObject);

    // When
    DomainObjectProperty<?> property =
        dataToDomainObjectPropertyConverter.convert(domainObject, data);

    // Then
    assertThat(property.getPropertyType()).isEqualTo(PropertyType.VALUE_OBJECT);
    assertThat(property.getData()).isEqualTo(dataObject);
  }
}
