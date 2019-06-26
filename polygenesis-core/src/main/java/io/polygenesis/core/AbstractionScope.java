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

package io.polygenesis.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Abstraction scope.
 *
 * @author Christos Tsakostas
 */
public class AbstractionScope implements Serializable {

  private static final long serialVersionUID = 1L;

  /** The constant API. */
  public static final String API = "API";
  /** The constant API_DETAIL. */
  public static final String API_DETAIL = "API_DETAIL";
  /** The constant API_CLIENT_REST. */
  public static final String API_CLIENT_REST = "API_CLIENT_REST";
  /** The constant API_CLIENT_MESSAGING. */
  public static final String API_CLIENT_MESSAGING = "API_CLIENT_MESSAGING";
  /** The constant API_CLIENT_SCHEDULER. */
  public static final String API_CLIENT_SCHEDULER = "API_CLIENT_SCHEDULER";

  // ===============================================================================================
  // DOMAIN
  // ===============================================================================================

  /** The constant DOMAIN_SERVICE. */
  public static final String DOMAIN_SERVICE = "DOMAIN_SERVICE";
  /** The constant DOMAIN_ABSTRACT_AGGREGATE_ROOT. */
  public static final String DOMAIN_ABSTRACT_AGGREGATE_ROOT = "DOMAIN_ABSTRACT_AGGREGATE_ROOT";
  /** The constant DOMAIN_AGGREGATE_ROOT. */
  public static final String DOMAIN_AGGREGATE_ROOT = "DOMAIN_AGGREGATE_ROOT";
  /** The constant DOMAIN_AGGREGATE_ENTITY. */
  public static final String DOMAIN_AGGREGATE_ENTITY = "DOMAIN_AGGREGATE_ENTITY";
  /** The constant DOMAIN_SUPPORTIVE_ENTITY. */
  public static final String DOMAIN_SUPPORTIVE_ENTITY = "DOMAIN_SUPPORTIVE_ENTITY";

  /** The constant DOMAIN_DETAIL_PUBLISHER. */
  public static final String DOMAIN_DETAIL_PUBLISHER = "DOMAIN_DETAIL_PUBLISHER";

  // ===============================================================================================
  // PROJECTION
  // ===============================================================================================

  /** The constant PROJECTION. */
  public static final String PROJECTION = "PROJECTION";

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private String text;

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  /**
   * Custom abstraction scope.
   *
   * @param text the text
   * @return the abstraction scope
   */
  public static AbstractionScope custom(String text) {
    return new AbstractionScope(text);
  }

  /**
   * Api abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope api() {
    return new AbstractionScope(API);
  }

  /**
   * Api detail scope.
   *
   * @return the scope
   */
  public static AbstractionScope apiDetail() {
    return new AbstractionScope(API_DETAIL);
  }

  /**
   * Api client rest scope.
   *
   * @return the scope
   */
  public static AbstractionScope apiClientRest() {
    return new AbstractionScope(API_CLIENT_REST);
  }

  /**
   * Api client messaging abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope apiClientMessaging() {
    return new AbstractionScope(API_CLIENT_MESSAGING);
  }

  /**
   * Api client scheduler abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope apiClientScheduler() {
    return new AbstractionScope(API_CLIENT_SCHEDULER);
  }

  /**
   * Domain abstract aggregate root abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope domainAbstractAggregateRoot() {
    return new AbstractionScope(DOMAIN_ABSTRACT_AGGREGATE_ROOT);
  }

  /**
   * Domain aggregate root abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope domainAggregateRoot() {
    return new AbstractionScope(DOMAIN_AGGREGATE_ROOT);
  }

  /**
   * Domain aggregate entity abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope domainAggregateEntity() {
    return new AbstractionScope(DOMAIN_AGGREGATE_ENTITY);
  }

  /**
   * Domain supportive entity abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope domainSupportiveEntity() {
    return new AbstractionScope(DOMAIN_SUPPORTIVE_ENTITY);
  }

  /**
   * Domain service scope.
   *
   * @return the scope
   */
  public static AbstractionScope domainService() {
    return new AbstractionScope(DOMAIN_SERVICE);
  }

  /**
   * Domain detail publisher abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope domainDetailPublisher() {
    return new AbstractionScope(DOMAIN_DETAIL_PUBLISHER);
  }

  /**
   * Projection abstraction scope.
   *
   * @return the abstraction scope
   */
  public static AbstractionScope projection() {
    return new AbstractionScope(PROJECTION);
  }

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scope.
   *
   * @param text the text
   */
  public AbstractionScope(String text) {
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
    AbstractionScope that = (AbstractionScope) o;
    return Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }
}
