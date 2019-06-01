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

package io.polygenesis.abstraction.thing;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Purpose.
 *
 * @author Christos Tsakostas
 */
public class Purpose implements Serializable {

  private static final long serialVersionUID = 1L;

  // ===============================================================================================
  // DEFAULT COMMAND PURPOSES
  // ===============================================================================================
  private static final String CREATE = "CREATE";
  private static final String MODIFY = "MODIFY";
  private static final String DELETE = "DELETE";

  // ===============================================================================================
  // DEFAULT QUERY PURPOSES
  // ===============================================================================================
  private static final String FETCH_ONE = "FETCH_ONE";
  private static final String FETCH_COLLECTION = "FETCH_COLLECTION";
  private static final String FETCH_PAGED_COLLECTION = "FETCH_PAGED_COLLECTION";

  // ===============================================================================================
  // AGGREGATE ROOTS
  // ===============================================================================================
  private static final String AGGREGATE_ROOT_CREATE_ENTITY = "AGGREGATE_ROOT_CREATE_ENTITY";
  private static final String AGGREGATE_ROOT_UPDATE_ENTITY = "AGGREGATE_ROOT_UPDATE_ENTITY";
  private static final String AGGREGATE_ROOT_DELETE_ENTITY = "AGGREGATE_ROOT_DELETE_ENTITY";

  // ===============================================================================================
  // CONVERTERS
  // ===============================================================================================
  private static final String CONVERT_DTO_TO_VO = "CONVERT_DTO_TO_VO";
  private static final String CONVERT_VO_TO_DTO = "CONVERT_VO_TO_DTO";
  private static final String CONVERT_DOMAIN_OBJECT_TO_COLLECTION_RECORD =
      "CONVERT_DOMAIN_OBJECT_TO_COLLECTION_RECORD";

  // ===============================================================================================
  // REDUX
  // ===============================================================================================
  private static final String RESET = "RESET";

  // ===============================================================================================
  // MESSAGING
  // ===============================================================================================
  private static final String PROCESS = "PROCESS";

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String text;
  private CqsType cqsType;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Custom purpose.
   *
   * @param text the text
   * @param cqsType the cqs type
   * @return the purpose
   */
  public static Purpose custom(String text, CqsType cqsType) {
    return new Purpose(text, cqsType);
  }

  /**
   * Create purpose.
   *
   * @return the purpose
   */
  public static Purpose create() {
    return new Purpose(CREATE, CqsType.COMMAND);
  }

  /**
   * Modify purpose.
   *
   * @return the purpose
   */
  public static Purpose modify() {
    return new Purpose(MODIFY, CqsType.COMMAND);
  }

  /**
   * Delete purpose.
   *
   * @return the purpose
   */
  public static Purpose delete() {
    return new Purpose(DELETE, CqsType.COMMAND);
  }

  /**
   * Fetch one purpose.
   *
   * @return the purpose
   */
  public static Purpose fetchOne() {
    return new Purpose(FETCH_ONE, CqsType.QUERY);
  }

  /**
   * Fetch collection purpose.
   *
   * @return the purpose
   */
  public static Purpose fetchCollection() {
    return new Purpose(FETCH_COLLECTION, CqsType.QUERY);
  }

  /**
   * Fetch paged collection purpose.
   *
   * @return the purpose
   */
  public static Purpose fetchPagedCollection() {
    return new Purpose(FETCH_PAGED_COLLECTION, CqsType.QUERY);
  }

  /**
   * Aggregate root create entity purpose.
   *
   * @return the purpose
   */
  public static Purpose aggregateRootCreateEntity() {
    return new Purpose(AGGREGATE_ROOT_CREATE_ENTITY, CqsType.MUTATION);
  }

  /**
   * Aggregate root update entity purpose.
   *
   * @return the purpose
   */
  public static Purpose aggregateRootUpdateEntity() {
    return new Purpose(AGGREGATE_ROOT_UPDATE_ENTITY, CqsType.MUTATION);
  }

  /**
   * Aggregate root delete entity purpose.
   *
   * @return the purpose
   */
  public static Purpose aggregateRootDeleteEntity() {
    return new Purpose(AGGREGATE_ROOT_DELETE_ENTITY, CqsType.MUTATION);
  }

  /**
   * Convert dto to vo purpose.
   *
   * @return the purpose
   */
  public static Purpose convertDtoToVo() {
    return new Purpose(CONVERT_DTO_TO_VO, CqsType.MUTATION);
  }

  /**
   * Convert vo to dto purpose.
   *
   * @return the purpose
   */
  public static Purpose convertVoToDto() {
    return new Purpose(CONVERT_VO_TO_DTO, CqsType.MUTATION);
  }

  /**
   * Convert domain object to collection record purpose.
   *
   * @return the purpose
   */
  public static Purpose convertDomainObjectToCollectionRecord() {
    return new Purpose(CONVERT_DOMAIN_OBJECT_TO_COLLECTION_RECORD, CqsType.MUTATION);
  }

  /**
   * Reset purpose.
   *
   * @return the purpose
   */
  public static Purpose reset() {
    return new Purpose(RESET, CqsType.MUTATION);
  }

  /**
   * Process purpose.
   *
   * @return the purpose
   */
  public static Purpose process() {
    return new Purpose(PROCESS, CqsType.COMMAND);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Purpose.
   *
   * @param text the text
   * @param cqsType the cqs type
   */
  public Purpose(String text, CqsType cqsType) {
    this.text = text;
    this.cqsType = cqsType;
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

  /**
   * Gets cqs type.
   *
   * @return the cqs type
   */
  public CqsType getCqsType() {
    return cqsType;
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Is command boolean.
   *
   * @return the boolean
   */
  public boolean isCommand() {
    return getCqsType().equals(CqsType.COMMAND);
  }

  /**
   * Is query boolean.
   *
   * @return the boolean
   */
  public boolean isQuery() {
    return getCqsType().equals(CqsType.QUERY);
  }

  /**
   * Is create boolean.
   *
   * @return the boolean
   */
  public boolean isCreate() {
    return getText().equals(CREATE);
  }

  /**
   * Is modify boolean.
   *
   * @return the boolean
   */
  public boolean isModify() {
    return getText().equals(MODIFY);
  }

  /**
   * Is delete boolean.
   *
   * @return the boolean
   */
  public boolean isDelete() {
    return getText().equals(DELETE);
  }

  /**
   * Is fetch one boolean.
   *
   * @return the boolean
   */
  public boolean isFetchOne() {
    return getText().equals(FETCH_ONE);
  }

  /**
   * Is fetch collection boolean.
   *
   * @return the boolean
   */
  public boolean isFetchCollection() {
    return getText().equals(FETCH_COLLECTION);
  }

  /**
   * Is fetch paged collection boolean.
   *
   * @return the boolean
   */
  public boolean isFetchPagedCollection() {
    return getText().equals(FETCH_PAGED_COLLECTION);
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
    Purpose purpose = (Purpose) o;
    return Objects.equals(text, purpose.text) && cqsType == purpose.cqsType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, cqsType);
  }
}
