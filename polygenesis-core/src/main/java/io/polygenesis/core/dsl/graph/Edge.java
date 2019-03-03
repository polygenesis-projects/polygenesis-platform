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

package io.polygenesis.core.dsl.graph;

/**
 * The type Edge.
 *
 * @author Christos Tsakostas
 */
public class Edge {
  private Vertex fromVertex;
  private Vertex toVertex;
  private Double weight;

  /** Instantiates a new Edge. */
  public Edge() {}

  /**
   * Instantiates a new Edge.
   *
   * @param fromVertex the from vertex
   * @param toVertex the to vertex
   * @param weight the weight
   */
  public Edge(Vertex fromVertex, Vertex toVertex, Double weight) {
    this.fromVertex = fromVertex;
    this.toVertex = toVertex;
    this.weight = weight;
  }

  @Override
  public String toString() {
    return fromVertex.getLabel() + " to " + toVertex.getLabel() + " with weight " + getWeight();
  }

  /**
   * Gets from vertex.
   *
   * @return the from vertex
   */
  public Vertex getFromVertex() {
    return fromVertex;
  }

  /**
   * Sets from vertex.
   *
   * @param fromVertex the from vertex
   */
  public void setFromVertex(Vertex fromVertex) {
    this.fromVertex = fromVertex;
  }

  /**
   * Gets to vertex.
   *
   * @return the to vertex
   */
  public Vertex getToVertex() {
    return toVertex;
  }

  /**
   * Sets to vertex.
   *
   * @param toVertex the to vertex
   */
  public void setToVertex(Vertex toVertex) {
    this.toVertex = toVertex;
  }

  /**
   * Gets weight.
   *
   * @return the weight
   */
  public Double getWeight() {
    return weight;
  }

  /**
   * Sets weight.
   *
   * @param weight the weight
   */
  public void setWeight(Double weight) {
    this.weight = weight;
  }
}
