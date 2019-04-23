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
 * The type graph dsl sample.
 *
 * @author Christos Tsakostas
 */
public class GraphDslSample {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {

    GraphBuilder.graph()
        .edge()
        .from("a")
        .to("b")
        .weight(40.0)
        .edge()
        .from("b")
        .to("c")
        .weight(20.0)
        .edge()
        .from("d")
        .to("e")
        .weight(50.5)
        .printGraph();

    GraphBuilder.graph()
        .edge()
        .from("w")
        .to("y")
        .weight(23.0)
        .edge()
        .from("d")
        .to("e")
        .weight(34.5)
        .edge()
        .from("e")
        .to("y")
        .weight(50.5)
        .printGraph();
  }
}
