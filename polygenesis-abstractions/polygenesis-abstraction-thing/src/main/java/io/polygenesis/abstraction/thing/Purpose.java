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

import java.io.Serializable;
import java.util.Objects;

public class Purpose implements Serializable {

  private static final long serialVersionUID = 1L;

  // ===============================================================================================
  // GENERIC PURPOSES
  // ===============================================================================================
  private static final String GENERIC_COMMAND = "GENERIC_COMMAND";
  private static final String GENERIC_QUERY = "GENERIC_QUERY";

  // ===============================================================================================
  // DEFAULT COMMAND PURPOSES
  // ===============================================================================================
  private static final String CREATE = "CREATE";
  private static final String ENSURE_EXISTENCE = "ENSURE_EXISTENCE";
  private static final String MODIFY = "MODIFY";
  private static final String DELETE = "DELETE";

  // ===============================================================================================
  // DEFAULT QUERY PURPOSES
  // ===============================================================================================
  private static final String FETCH_ONE = "FETCH_ONE";
  private static final String FETCH_COLLECTION = "FETCH_COLLECTION";
  private static final String FETCH_PAGED_COLLECTION = "FETCH_PAGED_COLLECTION";

  // ===============================================================================================
  // ASPECT
  // ===============================================================================================
  private static final String ASPECT_AROUND = "ASPECT_AROUND";

  // ===============================================================================================
  // AGGREGATE ENTITIES
  // ===============================================================================================
  private static final String ENTITY_CREATE = "ENTITY_CREATE";
  private static final String ENTITY_MODIFY = "ENTITY_MODIFY";
  private static final String ENTITY_REMOVE = "ENTITY_REMOVE";
  private static final String ENTITY_FETCH = "ENTITY_FETCH";
  private static final String ENTITY_FETCH_ALL = "ENTITY_FETCH_ALL";

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
  private static final String SUPPORTED_MESSAGES = "SUPPORTED_MESSAGES";

  // ===============================================================================================
  // GENERIC
  // ===============================================================================================
  private static final String BUILD = "BUILD";
  private static final String DECIDE = "DECIDE";
  private static final String ENCRYPT = "encrypt";
  private static final String DECRYPT = "decrypt";
  private static final String VALIDATE = "validate";
  private static final String CHECK_BOOLEAN = "checkBoolean";
  private static final String GENERATE = "generate";

  // ===============================================================================================
  // PERIODIC_PROCESS
  // ===============================================================================================
  private static final String PERIODIC_PROCESS_NEXT_PAGE = "PERIODIC_PROCESS_NEXT_PAGE";
  private static final String PERIODIC_PROCESS_PROCESS_ONE = "PERIODIC_PROCESS_PROCESS_ONE";
  private static final String PERIODIC_PROCESS_COMMAND = "PERIODIC_PROCESS_COMMAND";
  private static final String PERIODIC_PROCESS_QUERY = "PERIODIC_PROCESS_QUERY";

  // ===============================================================================================
  // DOMAIN MESSAGE SUBSCRIBER
  // ===============================================================================================
  private static final String DOMAIN_MESSAGE_SUBSCRIBER_PROCESS =
      "DOMAIN_MESSAGE_SUBSCRIBER_PROCESS";
  private static final String DOMAIN_MESSAGE_SUBSCRIBER_SUPPORTED_TYPES =
      "DOMAIN_MESSAGE_SUBSCRIBER_SUPPORTED_TYPES";

  // ===============================================================================================
  // INCOMING DOMAIN MESSAGE
  // ===============================================================================================
  private static final String INCOMING_DOMAIN_MESSAGE_GET_MESSAGE_ID =
      "INCOMING_DOMAIN_MESSAGE_GET_MESSAGE_ID";
  private static final String INCOMING_DOMAIN_MESSAGE_GET_ROOT_ID =
      "INCOMING_DOMAIN_MESSAGE_GET_ROOT_ID";
  private static final String INCOMING_DOMAIN_MESSAGE_GET_MESSAGE_BODY =
      "INCOMING_DOMAIN_MESSAGE_GET_MESSAGE_BODY";

  // ===============================================================================================
  // BATCH PROCESS
  // ===============================================================================================
  private static final String BATCH_PROCESS_SERVICE_NAME = "BATCH_PROCESS_SERVICE_NAME";

  // ===============================================================================================
  // DOMAIN MESSAGE PUBLISHER
  // ===============================================================================================
  private static final String DOMAIN_MESSAGE_PUBLISHER_GET_CONTEXT =
      "DOMAIN_MESSAGE_PUBLISHER_GET_CONTEXT";

  // ===============================================================================================
  // INITIALIZER
  // ===============================================================================================
  private static final String AFTER_PROPERTIES_SET = "AFTER_PROPERTIES_SET";

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String text;
  private CqsType cqsType;

  // ===============================================================================================
  // GENERIC PURPOSES
  // ===============================================================================================

  /**
   * Generic command purpose.
   *
   * @return the purpose
   */
  public static Purpose genericCommand() {
    return new Purpose(GENERIC_COMMAND, CqsType.COMMAND);
  }

  /**
   * Generic query purpose.
   *
   * @return the purpose
   */
  public static Purpose genericQuery() {
    return new Purpose(GENERIC_QUERY, CqsType.QUERY);
  }

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
   * Ensure existence purpose.
   *
   * @return the purpose
   */
  public static Purpose ensureExistence() {
    return new Purpose(ENSURE_EXISTENCE, CqsType.COMMAND);
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

  // ===============================================================================================
  // ASPECT
  // ===============================================================================================

  /**
   * Aspect around purpose.
   *
   * @return the purpose
   */
  public static Purpose aspectAround() {
    return new Purpose(ASPECT_AROUND, CqsType.COMMAND);
  }

  // ===============================================================================================
  // API CLIENT REST
  // ===============================================================================================

  // ===============================================================================================
  //
  // ===============================================================================================

  /**
   * Fetch paged collection purpose.
   *
   * @return the purpose
   */
  public static Purpose fetchPagedCollection() {
    return new Purpose(FETCH_PAGED_COLLECTION, CqsType.QUERY);
  }

  /**
   * Entity create purpose.
   *
   * @return the purpose
   */
  public static Purpose entityCreate() {
    return new Purpose(ENTITY_CREATE, CqsType.COMMAND);
  }

  /**
   * Entity modify purpose.
   *
   * @return the purpose
   */
  public static Purpose entityModify() {
    return new Purpose(ENTITY_MODIFY, CqsType.COMMAND);
  }

  /**
   * Entity remove purpose.
   *
   * @return the purpose
   */
  public static Purpose entityRemove() {
    return new Purpose(ENTITY_REMOVE, CqsType.COMMAND);
  }

  /**
   * Entity fetch purpose.
   *
   * @return the purpose
   */
  public static Purpose entityFetch() {
    return new Purpose(ENTITY_FETCH, CqsType.QUERY);
  }

  /**
   * Entity fetch all purpose.
   *
   * @return the purpose
   */
  public static Purpose entityFetchAll() {
    return new Purpose(ENTITY_FETCH_ALL, CqsType.QUERY);
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

  /**
   * Supported messages purpose.
   *
   * @return the purpose
   */
  public static Purpose supportedMessages() {
    return new Purpose(SUPPORTED_MESSAGES, CqsType.QUERY);
  }

  // ===============================================================================================
  // GENERIC
  // ===============================================================================================

  /**
   * Build purpose.
   *
   * @return the purpose
   */
  public static Purpose build() {
    return new Purpose(BUILD, CqsType.COMMAND);
  }

  /**
   * Decide purpose.
   *
   * @return the purpose
   */
  public static Purpose decide() {
    return new Purpose(DECIDE, CqsType.COMMAND);
  }

  /**
   * Encrypt purpose.
   *
   * @return the purpose
   */
  public static Purpose encrypt() {
    return new Purpose(ENCRYPT, CqsType.COMMAND);
  }

  /**
   * Decrypt purpose.
   *
   * @return the purpose
   */
  public static Purpose decrypt() {
    return new Purpose(DECRYPT, CqsType.COMMAND);
  }

  /**
   * Validate purpose.
   *
   * @return the purpose
   */
  public static Purpose validate() {
    return new Purpose(VALIDATE, CqsType.COMMAND);
  }

  /**
   * Check boolean purpose.
   *
   * @return the purpose
   */
  public static Purpose checkBoolean() {
    return new Purpose(CHECK_BOOLEAN, CqsType.COMMAND);
  }

  /**
   * Generate purpose.
   *
   * @return the purpose
   */
  public static Purpose generate() {
    return new Purpose(GENERATE, CqsType.COMMAND);
  }

  // ===============================================================================================
  // PERIODIC_PROCESS
  // ===============================================================================================

  /**
   * Periodic process next page purpose.
   *
   * @return the purpose
   */
  public static Purpose periodicProcessNextPage() {
    return new Purpose(PERIODIC_PROCESS_NEXT_PAGE, CqsType.QUERY);
  }

  /**
   * Periodic process process one purpose.
   *
   * @return the purpose
   */
  public static Purpose periodicProcessProcessOne() {
    return new Purpose(PERIODIC_PROCESS_PROCESS_ONE, CqsType.COMMAND);
  }

  /**
   * Periodic process command purpose.
   *
   * @return the purpose
   */
  public static Purpose periodicProcessCommand() {
    return new Purpose(PERIODIC_PROCESS_COMMAND, CqsType.COMMAND);
  }

  /**
   * Periodic process query purpose.
   *
   * @return the purpose
   */
  public static Purpose periodicProcessQuery() {
    return new Purpose(PERIODIC_PROCESS_QUERY, CqsType.QUERY);
  }

  // ===============================================================================================
  // DOMAIN MESSAGE SUBSCRIBER
  // ===============================================================================================

  /**
   * Domain message subscriber process purpose.
   *
   * @return the purpose
   */
  public static Purpose domainMessageSubscriberProcess() {
    return new Purpose(DOMAIN_MESSAGE_SUBSCRIBER_PROCESS, CqsType.COMMAND);
  }

  /**
   * Domain message subscriber supported types purpose.
   *
   * @return the purpose
   */
  public static Purpose domainMessageSubscriberSupportedTypes() {
    return new Purpose(DOMAIN_MESSAGE_SUBSCRIBER_SUPPORTED_TYPES, CqsType.QUERY);
  }

  // ===============================================================================================
  // INCOMING DOMAIN MESSAGE
  // ===============================================================================================

  /**
   * Incoming domain message get message id purpose.
   *
   * @return the purpose
   */
  public static Purpose incomingDomainMessageGetMessageId() {
    return new Purpose(INCOMING_DOMAIN_MESSAGE_GET_MESSAGE_ID, CqsType.QUERY);
  }

  /**
   * Incoming domain message get root id purpose.
   *
   * @return the purpose
   */
  public static Purpose incomingDomainMessageGetRootId() {
    return new Purpose(INCOMING_DOMAIN_MESSAGE_GET_ROOT_ID, CqsType.QUERY);
  }

  /**
   * Incoming domain message get message body purpose.
   *
   * @return the purpose
   */
  public static Purpose incomingDomainMessageGetMessageBody() {
    return new Purpose(INCOMING_DOMAIN_MESSAGE_GET_MESSAGE_BODY, CqsType.QUERY);
  }

  // ===============================================================================================
  // BATCH PROCESS
  // ===============================================================================================

  /**
   * Batch process service name purpose.
   *
   * @return the purpose
   */
  public static Purpose batchProcessServiceName() {
    return new Purpose(BATCH_PROCESS_SERVICE_NAME, CqsType.QUERY);
  }

  // ===============================================================================================
  // DOMAIN MESSAGE PUBLISHER
  // ===============================================================================================

  /**
   * Domain message publisher get context purpose.
   *
   * @return the purpose
   */
  public static Purpose domainMessagePublisherGetContext() {
    return new Purpose(DOMAIN_MESSAGE_PUBLISHER_GET_CONTEXT, CqsType.QUERY);
  }

  // ===============================================================================================
  // INITIALIZER
  // ===============================================================================================

  /**
   * After properties set purpose.
   *
   * @return the purpose
   */
  public static Purpose afterPropertiesSet() {
    return new Purpose(AFTER_PROPERTIES_SET, CqsType.COMMAND);
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
   * Is ensure existence boolean.
   *
   * @return the boolean
   */
  public boolean isEnsureExistence() {
    return getText().equals(ENSURE_EXISTENCE);
  }

  /**
   * Is modify boolean.
   *
   * @return the boolean
   */
  public boolean isModify() {
    return getText().equals(MODIFY)
        || getText().equals(ENTITY_CREATE)
        || getText().equals(ENTITY_MODIFY)
        || getText().equals(ENTITY_REMOVE);
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
  // AGGREGATE ENTITIES
  // ===============================================================================================

  /**
   * Is entity create boolean.
   *
   * @return the boolean
   */
  public boolean isEntityCreate() {
    return getText().equals(ENTITY_CREATE);
  }

  /**
   * Is entity modify boolean.
   *
   * @return the boolean
   */
  public boolean isEntityModify() {
    return getText().equals(ENTITY_MODIFY);
  }

  /**
   * Is entity remove boolean.
   *
   * @return the boolean
   */
  public boolean isEntityRemove() {
    return getText().equals(ENTITY_REMOVE);
  }

  /**
   * Is entity fetch boolean.
   *
   * @return the boolean
   */
  public boolean isEntityFetch() {
    return getText().equals(ENTITY_FETCH);
  }

  /**
   * Is entity fetch all boolean.
   *
   * @return the boolean
   */
  public boolean isEntityFetchAll() {
    return getText().equals(ENTITY_FETCH_ALL);
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
