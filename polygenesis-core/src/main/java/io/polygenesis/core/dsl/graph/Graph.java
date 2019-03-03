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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type graph.
 *
 * @author Christos Tsakostas
 */
public class Graph {

  private List<Edge> edges;
  private Set<Vertex> vertices;

  /** Instantiates a new graph. */
  public Graph() {
    edges = new ArrayList<>();
    vertices = new TreeSet<>();
  }

  /**
   * Add edge.
   *
   * @param edge the edge
   */
  public void addEdge(Edge edge) {
    getEdges().add(edge);
  }

  /**
   * Add vertice.
   *
   * @param v the v
   */
  public void addVertice(Vertex v) {
    getVertices().add(v);
  }

  /**
   * Gets edges.
   *
   * @return the edges
   */
  public List<Edge> getEdges() {
    return edges;
  }

  /**
   * Gets vertices.
   *
   * @return the vertices
   */
  public Set<Vertex> getVertices() {
    return vertices;
  }

  /**
   * Print graph.
   *
   * @param g the g
   */
  public static void printGraph(Graph g) {
    System.out.println("Vertices...");
    for (Vertex v : g.getVertices()) {
      System.out.print(v.getLabel() + " ");
    }
    System.out.println("");
    System.out.println("Edges...");
    for (Edge e : g.getEdges()) {
      System.out.println(e);
    }
  }
}
