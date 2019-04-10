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

package io.polygenesis.core.deducer;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * RecursiveObject.
 *
 * @author Christos Tsakostas
 */
public class RecursiveObject {

  private RecursiveObject parent;

  private Set<RecursiveObject> children;

  private String strGenericType;

  private String strDataType;

  private String strName;

  private boolean genericInterface;

  private Annotation[] annotations;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Recursive object.
   *
   * @param strGenericType the generic type
   * @param strDataType the data type
   * @param strName the name
   */
  private RecursiveObject(String strGenericType, String strDataType, String strName) {
    this.strGenericType = strGenericType;
    this.strDataType = strDataType;
    this.strName = strName;

    this.parent = null;
    this.children = new LinkedHashSet<>();
  }

  /**
   * Instantiates a new Recursive object.
   *
   * @param strGenericType the generic type
   * @param strDataType the data type
   * @param name the name
   * @param parent the parent
   */
  public RecursiveObject(
      String strGenericType, String strDataType, String name, RecursiveObject parent) {
    this(strGenericType, strDataType, name);
    this.parent = parent;
  }

  // ===============================================================================================
  // APPENDERS
  // ===============================================================================================

  /**
   * Append child.
   *
   * @param child the child
   */
  public void appendChild(RecursiveObject child) {
    this.children.add(child);
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets parent.
   *
   * @return the parent
   */
  public RecursiveObject getParent() {
    return parent;
  }

  /**
   * Gets children.
   *
   * @return the children
   */
  public Set<RecursiveObject> getChildren() {
    return children;
  }

  /**
   * Gets generic type.
   *
   * @return the generic type
   */
  public String getStrGenericType() {
    return strGenericType;
  }

  /**
   * Gets data type.
   *
   * @return the data type
   */
  public String getStrDataType() {
    return strDataType;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getStrName() {
    return strName;
  }

  // ===============================================================================================
  // VALIDATIONS
  // ===============================================================================================

  /**
   * Is primitive boolean.
   *
   * @return the boolean
   */
  private boolean isPrimitive() {
    return !this.getStrDataType().contains(".");
  }

  /**
   * Is java object boolean.
   *
   * @return the boolean
   */
  private boolean isJavaObject() {
    return this.getStrDataType().contains("java");
  }

  /**
   * Is custom object boolean.
   *
   * @return the boolean
   */
  public boolean isCustomObject() {
    return !(isPrimitive() || isJavaObject());
  }

  /**
   * Is generic interface boolean.
   *
   * @return the boolean
   */
  public boolean isGenericInterface() {
    return genericInterface;
  }

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  /**
   * Sets generic interface.
   *
   * @param genericInterface the generic interface
   */
  public void setGenericInterface(boolean genericInterface) {
    this.genericInterface = genericInterface;
  }

  /**
   * Get annotations annotation [ ].
   *
   * @return the annotation [ ]
   */
  public Annotation[] getAnnotations() {
    return annotations;
  }

  /**
   * Sets annotations.
   *
   * @param annotations the annotations
   */
  public void setAnnotations(Annotation[] annotations) {
    this.annotations = annotations;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================
}
