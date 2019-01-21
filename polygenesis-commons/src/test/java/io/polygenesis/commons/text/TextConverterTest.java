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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.Test;

/** @author Christos Tsakostas */
public class TextConverterTest {

  @Test
  public void shouldFailToInstantiate() throws NoSuchMethodException {
    Constructor<TextConverter> constructor = TextConverter.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
  }

  @Test
  public void shouldSucceedForSimpleWord() {
    String input = "customer";

    assertThat(TextConverter.toLowerCamel(input)).isEqualTo("customer");
    assertThat(TextConverter.toLowerCamelSpaces(input)).isEqualTo("customer");
    assertThat(TextConverter.toLowerCase(input)).isEqualTo("customer");
    assertThat(TextConverter.toLowerHyphen(input)).isEqualTo("customer");

    assertThat(TextConverter.toUpperCamel(input)).isEqualTo("Customer");
    assertThat(TextConverter.toUpperCamelSpaces(input)).isEqualTo("Customer");
    assertThat(TextConverter.toUpperCase(input)).isEqualTo("CUSTOMER");
    assertThat(TextConverter.toUpperUnderscore(input)).isEqualTo("CUSTOMER");
    assertThat(TextConverter.toUpperUnderscore(input)).isEqualTo("CUSTOMER");

    String inputPlural = TextConverter.toPlural(input);
    assertThat(inputPlural).isNotNull();

    assertThat(TextConverter.toUpperCamel(inputPlural)).isEqualTo("Customers");

    assertThat(TextConverter.toLowerCamel(inputPlural)).isEqualTo("customers");
    assertThat(TextConverter.toLowerCamelSpaces(inputPlural)).isEqualTo("customers");
    assertThat(TextConverter.toLowerCase(inputPlural)).isEqualTo("customers");
    assertThat(TextConverter.toLowerHyphen(inputPlural)).isEqualTo("customers");

    assertThat(TextConverter.toUpperCamel(inputPlural)).isEqualTo("Customers");
    assertThat(TextConverter.toUpperCamelSpaces(inputPlural)).isEqualTo("Customers");
    assertThat(TextConverter.toUpperCase(inputPlural)).isEqualTo("CUSTOMERS");
    assertThat(TextConverter.toUpperUnderscore(inputPlural)).isEqualTo("CUSTOMERS");
    assertThat(TextConverter.toUpperUnderscore(inputPlural)).isEqualTo("CUSTOMERS");
  }

  @Test
  public void shouldSucceedForTwoWords() {
    String input = "customerAddress";

    assertThat(TextConverter.toLowerCamel(input)).isEqualTo("customerAddress");
    assertThat(TextConverter.toLowerCamelSpaces(input)).isEqualTo("customer address");
    assertThat(TextConverter.toLowerCase(input)).isEqualTo("customeraddress");
    assertThat(TextConverter.toLowerHyphen(input)).isEqualTo("customer-address");

    assertThat(TextConverter.toUpperCamel(input)).isEqualTo("CustomerAddress");
    assertThat(TextConverter.toUpperCamelSpaces(input)).isEqualTo("Customer Address");
    assertThat(TextConverter.toUpperCase(input)).isEqualTo("CUSTOMERADDRESS");
    assertThat(TextConverter.toUpperUnderscore(input)).isEqualTo("CUSTOMER_ADDRESS");

    String inputPlural = TextConverter.toPlural(input);
    assertThat(inputPlural).isNotNull();

    assertThat(TextConverter.toLowerCamel(inputPlural)).isEqualTo("customerAddresses");
    assertThat(TextConverter.toLowerCamelSpaces(inputPlural)).isEqualTo("customer addresses");
    assertThat(TextConverter.toLowerCase(inputPlural)).isEqualTo("customeraddresses");
    assertThat(TextConverter.toLowerHyphen(inputPlural)).isEqualTo("customer-addresses");

    assertThat(TextConverter.toUpperCamel(inputPlural)).isEqualTo("CustomerAddresses");
    assertThat(TextConverter.toUpperCamelSpaces(inputPlural)).isEqualTo("Customer Addresses");
    assertThat(TextConverter.toUpperCase(inputPlural)).isEqualTo("CUSTOMERADDRESSES");
    assertThat(TextConverter.toUpperUnderscore(inputPlural)).isEqualTo("CUSTOMER_ADDRESSES");
  }

  @Test
  public void shouldPluralizeSimpleWords() {
    assertThat(TextConverter.toPlural("customer")).isEqualTo("customers");
    assertThat(TextConverter.toPlural("user")).isEqualTo("users");
  }

  @Test
  public void shouldPluralizeTwoWords() {
    assertThat(TextConverter.toPlural("customerAddress")).isEqualTo("customerAddresses");
    assertThat(TextConverter.toPlural("userProfile")).isEqualTo("userProfiles");
  }

  @Test
  public void shouldPluralizeIrregularWords() {
    assertThat(TextConverter.toPlural("city")).isEqualTo("cities");
    assertThat(TextConverter.toPlural("man")).isEqualTo("men");
  }

  @Test
  public void shouldSucceedToLowerHyphen() {
    assertThat(TextConverter.toLowerHyphen("someThing")).isEqualTo("some-thing");
    assertThat(TextConverter.toLowerHyphen("SomeThing")).isEqualTo("some-thing");
  }
}
