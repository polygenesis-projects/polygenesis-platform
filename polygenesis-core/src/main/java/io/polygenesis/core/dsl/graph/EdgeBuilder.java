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
 * The type Edge builder.
 *
 * @author Christos Tsakostas
 */
public class EdgeBuilder {

  /** The Edge. */
  Edge edge;

  /** The G builder. */
  // Keep a back reference to the graph Builder.
  GraphBuilder graphBuilder;

  /**
   * Instantiates a new Edge builder.
   *
   * @param graphBuilder the g builder
   */
  public EdgeBuilder(GraphBuilder graphBuilder) {
    this.graphBuilder = graphBuilder;
    edge = new Edge();
  }

  /**
   * From edge builder.
   *
   * @param lbl the lbl
   * @return the edge builder
   */
  public EdgeBuilder from(String lbl) {
    Vertex v = new Vertex(lbl);
    edge.setFromVertex(v);
    graphBuilder.getGraph().addVertice(v);
    return this;
  }

  /**
   * To edge builder.
   *
   * @param lbl the lbl
   * @return the edge builder
   */
  public EdgeBuilder to(String lbl) {
    Vertex v = new Vertex(lbl);
    edge.setToVertex(v);
    graphBuilder.getGraph().addVertice(v);
    return this;
  }

  /**
   * Weight graph builder.
   *
   * @param d the d
   * @return the graph builder
   */
  public GraphBuilder weight(Double d) {
    edge.setWeight(d);
    return graphBuilder;
  }
}
