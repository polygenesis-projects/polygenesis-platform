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

package io.polygenesis.generators.java.auxdetails.propertyfile.propertyfile;

import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Nameable;

/**
 * The type Property file.
 *
 * @author Christos Tsakostas
 */
public class PropertyFileImpl implements Nameable {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private ContextName contextName;
  private PackageName rootPackageName;
  private Name name;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Property file.
   *
   * @param contextName the context name
   * @param rootPackageName the root package name
   */
  public PropertyFileImpl(ContextName contextName, PackageName rootPackageName) {
    this.contextName = contextName;
    this.rootPackageName = rootPackageName;
    this.name = new Name(String.format("%sPropertyFileAuxServiceImpl", contextName.getText()));
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets context name.
   *
   * @return the context name
   */
  public ContextName getContextName() {
    return contextName;
  }

  /**
   * Gets root package name.
   *
   * @return the root package name
   */
  public PackageName getRootPackageName() {
    return rootPackageName;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public Name getName() {
    return name;
  }

  @Override
  public ObjectName getObjectName() {
    return new ObjectName(getName().getText());
  }
}
