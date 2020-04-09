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

package io.polygenesis.generators.java;

public class TrinityJavaContextGeneratorEnablement {

  private boolean javaApiGenerator = true;
  private boolean javaApiDetailGenerator = true;
  private boolean javaApiRestGenerator = true;
  private boolean javaDomainGenerator = true;
  private boolean domainServiceImplementationGenerator = true;
  private boolean javaRdbmsGenerator = true;
  private boolean apiClientDomainMessageSubscriber = true;
  private boolean apiClientDomainMessageSubscriberActiveMq = true;
  private boolean javaDomainSqlGenerator = true;
  private boolean apiClientBatchProcess = true;
  private boolean apiClientBatchProcessMessageSubscriber = true;
  private boolean apiClientBatchProcessMessagingActivemq = true;
  private boolean apiClientBatchProcessSchedulerCamel = true;
  private boolean aux = true;
  private boolean auxDetailPropertyFile = true;

  private boolean domainDetailDomainMessagePublisher = true;
  private boolean domainDetailRepositoryInMemory = true;

  private boolean openApi = true;

  /** Instantiates a new Trinity java context generator enablement. */
  public TrinityJavaContextGeneratorEnablement() {
    setJavaApiGenerator(true);
    setJavaApiDetailGenerator(true);
    setJavaApiRestGenerator(true);
    setJavaDomainGenerator(true);
    setDomainServiceImplementationGenerator(true);
    setJavaRdbmsGenerator(true);

    setApiClientDomainMessageSubscriber(true);
    setApiClientDomainMessageSubscriberActiveMq(true);

    setJavaDomainSqlGenerator(true);
    setApiClientBatchProcess(true);
    setApiClientBatchProcessMessageSubscriber(true);
    setApiClientBatchProcessMessagingActivemq(true);
    setApiClientBatchProcessSchedulerCamel(true);

    setAux(true);
    setAuxDetailPropertyFile(true);

    setDomainDetailDomainMessagePublisher(true);
    setDomainDetailRepositoryInMemory(true);

    setOpenApi(true);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Is java api generator boolean.
   *
   * @return the boolean
   */
  public boolean isJavaApiGenerator() {
    return javaApiGenerator;
  }

  /**
   * Is java api detail generator boolean.
   *
   * @return the boolean
   */
  public boolean isJavaApiDetailGenerator() {
    return javaApiDetailGenerator;
  }

  /**
   * Is java api rest generator boolean.
   *
   * @return the boolean
   */
  public boolean isJavaApiRestGenerator() {
    return javaApiRestGenerator;
  }

  /**
   * Is java domain generator boolean.
   *
   * @return the boolean
   */
  public boolean isJavaDomainGenerator() {
    return javaDomainGenerator;
  }

  /**
   * Is domain service implementation generator boolean.
   *
   * @return the boolean
   */
  public boolean isDomainServiceImplementationGenerator() {
    return domainServiceImplementationGenerator;
  }

  /**
   * Is java rdbms generator boolean.
   *
   * @return the boolean
   */
  public boolean isJavaRdbmsGenerator() {
    return javaRdbmsGenerator;
  }

  /**
   * Is api client messaging boolean.
   *
   * @return the boolean
   */
  public boolean isApiClientDomainMessageSubscriber() {
    return apiClientDomainMessageSubscriber;
  }

  /**
   * Is api client domain message subscriber active mq boolean.
   *
   * @return the boolean
   */
  public boolean isApiClientDomainMessageSubscriberActiveMq() {
    return apiClientDomainMessageSubscriberActiveMq;
  }

  /**
   * Is java domain sql generator boolean.
   *
   * @return the boolean
   */
  public boolean isJavaDomainSqlGenerator() {
    return javaDomainSqlGenerator;
  }

  /**
   * Is api client periodic process boolean.
   *
   * @return the boolean
   */
  public boolean isApiClientBatchProcess() {
    return apiClientBatchProcess;
  }

  /**
   * Is api client batch process message subscriber boolean.
   *
   * @return the boolean
   */
  public boolean isApiClientBatchProcessMessageSubscriber() {
    return apiClientBatchProcessMessageSubscriber;
  }

  /**
   * Is api client batch process messaging activemq boolean.
   *
   * @return the boolean
   */
  public boolean isApiClientBatchProcessMessagingActivemq() {
    return apiClientBatchProcessMessagingActivemq;
  }

  /**
   * Is api client batch process scheduler camel boolean.
   *
   * @return the boolean
   */
  public boolean isApiClientBatchProcessSchedulerCamel() {
    return apiClientBatchProcessSchedulerCamel;
  }

  /**
   * Is aux boolean.
   *
   * @return the boolean
   */
  public boolean isAux() {
    return aux;
  }

  /**
   * Is aux detail property file boolean.
   *
   * @return the boolean
   */
  public boolean isAuxDetailPropertyFile() {
    return auxDetailPropertyFile;
  }

  /**
   * Is domain detail domain message publisher boolean.
   *
   * @return the boolean
   */
  public boolean isDomainDetailDomainMessagePublisher() {
    return domainDetailDomainMessagePublisher;
  }

  /**
   * Is domain detail repository in memory boolean.
   *
   * @return the boolean
   */
  public boolean isDomainDetailRepositoryInMemory() {
    return domainDetailRepositoryInMemory;
  }

  /**
   * Is open api boolean.
   *
   * @return the boolean
   */
  public boolean isOpenApi() {
    return openApi;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets java api generator.
   *
   * @param javaApiGenerator the java api generator
   */
  public void setJavaApiGenerator(boolean javaApiGenerator) {
    this.javaApiGenerator = javaApiGenerator;
  }

  /**
   * Sets java api detail generator.
   *
   * @param javaApiDetailGenerator the java api detail generator
   */
  public void setJavaApiDetailGenerator(boolean javaApiDetailGenerator) {
    this.javaApiDetailGenerator = javaApiDetailGenerator;
  }

  /**
   * Sets java api rest generator.
   *
   * @param javaApiRestGenerator the java api rest generator
   */
  public void setJavaApiRestGenerator(boolean javaApiRestGenerator) {
    this.javaApiRestGenerator = javaApiRestGenerator;
  }

  /**
   * Sets java domain generator.
   *
   * @param javaDomainGenerator the java domain generator
   */
  public void setJavaDomainGenerator(boolean javaDomainGenerator) {
    this.javaDomainGenerator = javaDomainGenerator;
  }

  /**
   * Sets domain service implementation generator.
   *
   * @param domainServiceImplementationGenerator the domain service implementation generator
   */
  public void setDomainServiceImplementationGenerator(
      boolean domainServiceImplementationGenerator) {
    this.domainServiceImplementationGenerator = domainServiceImplementationGenerator;
  }

  /**
   * Sets java rdbms generator.
   *
   * @param javaRdbmsGenerator the java rdbms generator
   */
  public void setJavaRdbmsGenerator(boolean javaRdbmsGenerator) {
    this.javaRdbmsGenerator = javaRdbmsGenerator;
  }

  /**
   * Sets api client messaging.
   *
   * @param apiClientDomainMessageSubscriber the api client messaging
   */
  public void setApiClientDomainMessageSubscriber(boolean apiClientDomainMessageSubscriber) {
    this.apiClientDomainMessageSubscriber = apiClientDomainMessageSubscriber;
  }

  /**
   * Sets api client domain message subscriber active mq.
   *
   * @param apiClientDomainMessageSubscriberActiveMq the api client domain message subscriber active
   *     mq
   */
  public void setApiClientDomainMessageSubscriberActiveMq(
      boolean apiClientDomainMessageSubscriberActiveMq) {
    this.apiClientDomainMessageSubscriberActiveMq = apiClientDomainMessageSubscriberActiveMq;
  }

  /**
   * Sets java domain sql generator.
   *
   * @param javaDomainSqlGenerator the java domain sql generator
   */
  public void setJavaDomainSqlGenerator(boolean javaDomainSqlGenerator) {
    this.javaDomainSqlGenerator = javaDomainSqlGenerator;
  }

  /**
   * Sets api client periodic process.
   *
   * @param apiClientBatchProcess the api client periodic process
   */
  public void setApiClientBatchProcess(boolean apiClientBatchProcess) {
    this.apiClientBatchProcess = apiClientBatchProcess;
  }

  /**
   * Sets api client batch process message subscriber.
   *
   * @param apiClientBatchProcessMessageSubscriber the api client batch process message subscriber
   */
  public void setApiClientBatchProcessMessageSubscriber(
      boolean apiClientBatchProcessMessageSubscriber) {
    this.apiClientBatchProcessMessageSubscriber = apiClientBatchProcessMessageSubscriber;
  }

  /**
   * Sets api client batch process messaging activemq.
   *
   * @param apiClientBatchProcessMessagingActivemq the api client batch process messaging activemq
   */
  public void setApiClientBatchProcessMessagingActivemq(
      boolean apiClientBatchProcessMessagingActivemq) {
    this.apiClientBatchProcessMessagingActivemq = apiClientBatchProcessMessagingActivemq;
  }

  /**
   * Sets api client batch process scheduler camel.
   *
   * @param apiClientBatchProcessSchedulerCamel the api client batch process scheduler camel
   */
  public void setApiClientBatchProcessSchedulerCamel(boolean apiClientBatchProcessSchedulerCamel) {
    this.apiClientBatchProcessSchedulerCamel = apiClientBatchProcessSchedulerCamel;
  }

  /**
   * Sets aux.
   *
   * @param aux the aux
   */
  public void setAux(boolean aux) {
    this.aux = aux;
  }

  /**
   * Sets aux detail property file.
   *
   * @param auxDetailPropertyFile the aux detail property file
   */
  public void setAuxDetailPropertyFile(boolean auxDetailPropertyFile) {
    this.auxDetailPropertyFile = auxDetailPropertyFile;
  }

  /**
   * Sets domain detail domain message publisher.
   *
   * @param domainDetailDomainMessagePublisher the domain detail domain message publisher
   */
  public void setDomainDetailDomainMessagePublisher(boolean domainDetailDomainMessagePublisher) {
    this.domainDetailDomainMessagePublisher = domainDetailDomainMessagePublisher;
  }

  /**
   * Sets domain detail repository in memory.
   *
   * @param domainDetailRepositoryInMemory the domain detail repository in memory
   */
  public void setDomainDetailRepositoryInMemory(boolean domainDetailRepositoryInMemory) {
    this.domainDetailRepositoryInMemory = domainDetailRepositoryInMemory;
  }

  /**
   * Sets open api.
   *
   * @param openApi the open api
   */
  public void setOpenApi(boolean openApi) {
    this.openApi = openApi;
  }
}
