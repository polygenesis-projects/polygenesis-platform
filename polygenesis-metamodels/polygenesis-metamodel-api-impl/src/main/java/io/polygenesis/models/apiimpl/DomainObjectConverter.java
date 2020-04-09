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

package io.polygenesis.models.apiimpl;

import io.polygenesis.commons.assertion.Assertion;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.domain.DomainObject;
import java.util.Objects;
import java.util.Set;

/**
 * The type Domain object converter.
 *
 * @author Christos Tsakostas
 */
public class DomainObjectConverter extends ServiceDependency implements Metamodel, Generatable {

  private DomainObject domainObject;
  private Set<DomainObjectConverterMethod> methods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter.
   *
   * @param domainObject the domain entity
   * @param methods the methods
   */
  public DomainObjectConverter(
      DomainObject domainObject, Set<DomainObjectConverterMethod> methods) {
    super(
        new ObjectName(
            String.format(
                "%sConverter", TextConverter.toUpperCamel(domainObject.getObjectName().getText()))),
        domainObject.getPackageName(),
        new VariableName(
            String.format(
                "%sConverter",
                TextConverter.toLowerCamel(domainObject.getObjectName().getText()))));
    setDomainObject(domainObject);
    setMethods(methods);
  }

  // ===============================================================================================
  // IMPLEMENTATIONS
  // ===============================================================================================

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets domain object.
   *
   * @return the domain object
   */
  public DomainObject getDomainObject() {
    return domainObject;
  }

  /**
   * Gets methods.
   *
   * @return the methods
   */
  public Set<DomainObjectConverterMethod> getMethods() {
    return methods;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets domain object.
   *
   * @param domainObject the domain object
   */
  private void setDomainObject(DomainObject domainObject) {
    Assertion.isNotNull(domainObject, "domainObject is required");
    this.domainObject = domainObject;
  }

  /**
   * Sets methods.
   *
   * @param methods the methods
   */
  private void setMethods(Set<DomainObjectConverterMethod> methods) {
    Assertion.isNotNull(methods, "methods is required");
    this.methods = methods;
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
    if (!super.equals(o)) {
      return false;
    }
    DomainObjectConverter that = (DomainObjectConverter) o;
    return Objects.equals(domainObject, that.domainObject) && Objects.equals(methods, that.methods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), domainObject, methods);
  }
}
