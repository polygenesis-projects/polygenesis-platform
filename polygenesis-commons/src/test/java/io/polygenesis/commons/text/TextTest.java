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

package io.polygenesis.commons.text;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.polygenesis.commons.test.AbstractEqualityTest;
import org.junit.Test;

/** @author Christos Tsakostas */
public class TextTest extends AbstractEqualityTest {

  @Test
  public void shouldSucceedForSimpleWord() {
    Text text = new Text("customer");

    assertThat(text.getOriginal()).isEqualTo("customer");
    assertThat(text.getModified()).isEqualTo("customer");

    assertThat(text.getLowerCamel()).isEqualTo("customer");
    assertThat(text.getLowerCamelSpaces()).isEqualTo("customer");
    assertThat(text.getLowerCase()).isEqualTo("customer");
    assertThat(text.getLowerHyphen()).isEqualTo("customer");

    assertThat(text.getUpperCamel()).isEqualTo("Customer");
    assertThat(text.getUpperCamelSpaces()).isEqualTo("Customer");
    assertThat(text.getUpperCase()).isEqualTo("CUSTOMER");
    assertThat(text.getUpperUnderscore()).isEqualTo("CUSTOMER");
    assertThat(text.getUpperUnderscore()).isEqualTo("CUSTOMER");

    assertThat(text.getPlural()).isNotNull();

    assertThat(text.getPlural().getOriginal()).isEqualTo("customers");
    assertThat(text.getPlural().getModified()).isEqualTo("customers");
    assertThat(text.getPlural().getUpperCamel()).isEqualTo("Customers");

    assertThat(text.getPlural().getLowerCamel()).isEqualTo("customers");
    assertThat(text.getPlural().getLowerCamelSpaces()).isEqualTo("customers");
    assertThat(text.getPlural().getLowerCase()).isEqualTo("customers");
    assertThat(text.getPlural().getLowerHyphen()).isEqualTo("customers");

    assertThat(text.getPlural().getUpperCamel()).isEqualTo("Customers");
    assertThat(text.getPlural().getUpperCamelSpaces()).isEqualTo("Customers");
    assertThat(text.getPlural().getUpperCase()).isEqualTo("CUSTOMERS");
    assertThat(text.getPlural().getUpperUnderscore()).isEqualTo("CUSTOMERS");
    assertThat(text.getPlural().getUpperUnderscore()).isEqualTo("CUSTOMERS");
  }

  @Test
  public void shouldSucceedForTwoWords() {
    Text text = new Text("customerAddress");

    assertThat(text.getOriginal()).isEqualTo("customerAddress");
    assertThat(text.getModified()).isEqualTo("customerAddress");

    assertThat(text.getLowerCamel()).isEqualTo("customerAddress");
    assertThat(text.getLowerCamelSpaces()).isEqualTo("customer address");
    assertThat(text.getLowerCase()).isEqualTo("customeraddress");
    assertThat(text.getLowerHyphen()).isEqualTo("customer-address");

    assertThat(text.getUpperCamel()).isEqualTo("CustomerAddress");
    assertThat(text.getUpperCamelSpaces()).isEqualTo("Customer Address");
    assertThat(text.getUpperCase()).isEqualTo("CUSTOMERADDRESS");
    assertThat(text.getUpperUnderscore()).isEqualTo("CUSTOMER_ADDRESS");

    assertThat(text.getPlural()).isNotNull();

    assertThat(text.getPlural().getOriginal()).isEqualTo("customerAddresses");
    assertThat(text.getPlural().getModified()).isEqualTo("customerAddresses");

    assertThat(text.getPlural().getLowerCamel()).isEqualTo("customerAddresses");
    assertThat(text.getPlural().getLowerCamelSpaces()).isEqualTo("customer addresses");
    assertThat(text.getPlural().getLowerCase()).isEqualTo("customeraddresses");
    assertThat(text.getPlural().getLowerHyphen()).isEqualTo("customer-addresses");

    assertThat(text.getPlural().getUpperCamel()).isEqualTo("CustomerAddresses");
    assertThat(text.getPlural().getUpperCamelSpaces()).isEqualTo("Customer Addresses");
    assertThat(text.getPlural().getUpperCase()).isEqualTo("CUSTOMERADDRESSES");
    assertThat(text.getPlural().getUpperUnderscore()).isEqualTo("CUSTOMER_ADDRESSES");
  }

  @Test
  public void shouldSucceedForAllUpperCase() {
    Text text = new Text("CUSTOMER");

    assertThat(text.getModified()).isEqualTo("customer");
  }

  @Test
  public void shouldPluralizeIrregularWords() {
    Text text1 = new Text("city");
    assertThat(text1.getPlural().getOriginal()).isEqualTo("cities");

    Text text2 = new Text("man");
    assertThat(text2.getPlural().getOriginal()).isEqualTo("men");
  }

  @Test
  public void shouldConvertUnderscoreToLowerCamel() {
    Text text = new Text("hello_world");

    assertThat(text.getModified()).isEqualTo("helloWorld");
  }

  @Test
  public void shouldSucceedForPackageName() {
    Text text = new Text("com.oregor.CreateUserRequest");

    assertThat(text.getOriginal()).isEqualTo("com.oregor.CreateUserRequest");
    assertThat(text.getModified()).isEqualTo("createUserRequest");
  }

  @Test
  public void shouldFailForEmptyInput() {
    assertThatThrownBy(() -> new Text("")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldFailForNullInput() {
    assertThatThrownBy(() -> new Text(null)).isInstanceOf(IllegalArgumentException.class);
  }

  // ===============================================================================================
  // Equality and Hash
  // ===============================================================================================

  @Override
  public Object createObject1() {
    return new Text("xxx");
  }

  @Override
  public Object createObject2() {
    return new Text("yyy");
  }
}
