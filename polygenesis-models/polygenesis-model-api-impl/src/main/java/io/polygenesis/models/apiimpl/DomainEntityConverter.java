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

package io.polygenesis.models.apiimpl;

import com.oregor.ddd4j.check.assertion.Assertion;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Model;
import io.polygenesis.core.data.VariableName;
import io.polygenesis.models.domain.BaseDomainEntity;
import java.util.Objects;
import java.util.Set;

/**
 * The type Domain object converter.
 *
 * @author Christos Tsakostas
 */
public class DomainEntityConverter extends ServiceDependency implements Model {

  private BaseDomainEntity<?> domainEntity;
  private Set<DomainEntityConverterMethod> methods;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain object converter.
   *
   * @param domainEntity the domain object
   * @param methods the methods
   */
  public DomainEntityConverter(
      BaseDomainEntity<?> domainEntity, Set<DomainEntityConverterMethod> methods) {
    super(
        new ObjectName(
            String.format(
                "%sConverter", TextConverter.toUpperCamel(domainEntity.getObjectName().getText()))),
        domainEntity.getPackageName(),
        new VariableName(
            String.format(
                "%sConverter",
                TextConverter.toLowerCamel(domainEntity.getObjectName().getText()))));
    setDomainEntity(domainEntity);
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
  public BaseDomainEntity<?> getDomainEntity() {
    return domainEntity;
  }

  /**
   * Gets methods.
   *
   * @return the methods
   */
  public Set<DomainEntityConverterMethod> getMethods() {
    return methods;
  }

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  /**
   * Sets domain object.
   *
   * @param domainEntity the domain object
   */
  private void setDomainEntity(BaseDomainEntity<?> domainEntity) {
    Assertion.isNotNull(domainEntity, "domainEntity is required");
    this.domainEntity = domainEntity;
  }

  /**
   * Sets methods.
   *
   * @param methods the methods
   */
  private void setMethods(Set<DomainEntityConverterMethod> methods) {
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
    DomainEntityConverter that = (DomainEntityConverter) o;
    return Objects.equals(domainEntity, that.domainEntity) && Objects.equals(methods, that.methods);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), domainEntity, methods);
  }
}
