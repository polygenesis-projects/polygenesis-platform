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

package io.polygenesis.models.ui.container;

/**
 * The type Abstract container.
 *
 * @author Christos Tsakostas
 */
public abstract class AbstractContainer {

  private ContainerName containerName;
  private Top top;
  private Right right;
  private Bottom bottom;
  private Left left;
  private Center center;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract container.
   *
   * @param containerName the container name
   * @param top the top
   * @param right the right
   * @param bottom the bottom
   * @param left the left
   * @param center the center
   */
  public AbstractContainer(
      ContainerName containerName, Top top, Right right, Bottom bottom, Left left, Center center) {
    this.containerName = containerName;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
    this.center = center;
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets container name.
   *
   * @return the container name
   */
  public ContainerName getContainerName() {
    return containerName;
  }

  /**
   * Gets top.
   *
   * @return the top
   */
  public Top getTop() {
    return top;
  }

  /**
   * Gets right.
   *
   * @return the right
   */
  public Right getRight() {
    return right;
  }

  /**
   * Gets bottom.
   *
   * @return the bottom
   */
  public Bottom getBottom() {
    return bottom;
  }

  /**
   * Gets left.
   *
   * @return the left
   */
  public Left getLeft() {
    return left;
  }

  /**
   * Gets center.
   *
   * @return the center
   */
  public Center getCenter() {
    return center;
  }
}
