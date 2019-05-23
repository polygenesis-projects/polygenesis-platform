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

package io.polygenesis.generators.java;

/**
 * The type Trinity java multi generator enablement.
 *
 * @author Christos Tsakostas
 */
public class TrinityJavaMultiGeneratorEnablement {

  private boolean javaApiGenerator = true;
  private boolean javaApiDetailGenerator = true;
  private boolean javaApiRestGenerator = true;
  private boolean javaDomainGenerator = true;
  private boolean domainServiceImplementationGenerator = true;
  private boolean javaRdbmsGenerator = true;

  /** Instantiates a new Trinity java multi generator enablement. */
  public TrinityJavaMultiGeneratorEnablement() {
    setJavaApiGenerator(true);
    setJavaApiDetailGenerator(true);
    setJavaApiRestGenerator(true);
    setJavaDomainGenerator(true);
    setDomainServiceImplementationGenerator(true);
    setJavaRdbmsGenerator(true);
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
}
