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

import java.io.Serializable;
import java.util.Objects;

/**
 * Encodes business types, which are vital for the code generation process, such as unique ID of an
 * entity, page size etc.
 *
 * @author Christos Tsakostas
 */
public class DataPurpose implements Serializable {

  private static final long serialVersionUID = 1L;

  // ===============================================================================================
  // ANY
  // ===============================================================================================
  private static final String ANY = "ANY";

  // ===============================================================================================
  // IDENTITIES
  // ===============================================================================================
  private static final String THING_IDENTITY = "THING_IDENTITY";
  private static final String TENANT_IDENTITY = "TENANT_IDENTITY";
  private static final String PARENT_THING_IDENTITY = "PARENT_THING_IDENTITY";

  // ===============================================================================================
  // PAGING
  // ===============================================================================================
  private static final String PAGE_NUMBER = "PAGE_NUMBER";
  private static final String PAGE_SIZE = "PAGE_SIZE";

  // ===============================================================================================
  // REFERENCES
  // ===============================================================================================
  private static final String REFERENCE_TO_THING_BY_ID = "REFERENCE_TO_THING_BY_ID";
  private static final String REFERENCE_TO_THING_BY_VALUE = "REFERENCE_TO_THING_BY_VALUE";

  // ===============================================================================================
  // SUBCLASSES / SUPERCLASSES
  // ===============================================================================================
  private static final String SUPERCLASS_PARAMETER = "SUPERCLASS_PARAMETER";

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String text;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Any thing data type.
   *
   * @return the thing data type
   */
  public static DataPurpose any() {
    return new DataPurpose(ANY);
  }

  /**
   * Thing identity thing data type.
   *
   * @return the thing data type
   */
  public static DataPurpose thingIdentity() {
    return new DataPurpose(THING_IDENTITY);
  }

  /**
   * Tenant identity thing data type.
   *
   * @return the thing data type
   */
  public static DataPurpose tenantIdentity() {
    return new DataPurpose(TENANT_IDENTITY);
  }

  /**
   * Parent thing identity thing data type.
   *
   * @return the thing data type
   */
  public static DataPurpose parentThingIdentity() {
    return new DataPurpose(PARENT_THING_IDENTITY);
  }

  /**
   * Page number thing data type.
   *
   * @return the thing data type
   */
  public static DataPurpose pageNumber() {
    return new DataPurpose(PAGE_NUMBER);
  }

  /**
   * Page size thing data type.
   *
   * @return the thing data type
   */
  public static DataPurpose pageSize() {
    return new DataPurpose(PAGE_SIZE);
  }

  /**
   * Reference to thing data business type.
   *
   * @return the data business type
   */
  public static DataPurpose referenceToThingById() {
    return new DataPurpose(REFERENCE_TO_THING_BY_ID);
  }

  /**
   * Reference to thing by value data purpose.
   *
   * @return the data purpose
   */
  public static DataPurpose referenceToThingByValue() {
    return new DataPurpose(REFERENCE_TO_THING_BY_VALUE);
  }

  /**
   * Superclass parameter data purpose.
   *
   * @return the data purpose
   */
  public static DataPurpose superclassParameter() {
    return new DataPurpose(SUPERCLASS_PARAMETER);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Thing data type.
   *
   * @param text the text
   */
  public DataPurpose(String text) {
    this.text = text;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataPurpose that = (DataPurpose) o;
    return Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }
}
