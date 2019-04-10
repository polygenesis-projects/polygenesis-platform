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

package io.polygenesis.core.dsl.graph;

/**
 * The type graph builder.
 *
 * @author Christos Tsakostas
 */
public class GraphBuilder {
  private Graph graph;

  /** Instantiates a new graph builder. */
  public GraphBuilder() {
    graph = new Graph();
  }

  /**
   * graph graph builder.
   *
   * @return the graph builder
   */
  // Start the graph DSL with this method.
  public static GraphBuilder graph() {
    return new GraphBuilder();
  }

  /**
   * Edge edge builder.
   *
   * @return the edge builder
   */
  // Start the edge building with this method.
  public EdgeBuilder edge() {
    EdgeBuilder builder = new EdgeBuilder(this);

    getGraph().addEdge(builder.edge);

    return builder;
  }

  /**
   * Gets graph.
   *
   * @return the graph
   */
  public Graph getGraph() {
    return graph;
  }

  /** Print graph. */
  public void printGraph() {
    Graph.printGraph(graph);
  }
}
