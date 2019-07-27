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

package io.polygenesis.generators.java.shared.transformer;

import io.polygenesis.core.TemplateTransformer;
import io.polygenesis.representations.code.ClassRepresentation;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.Set;

/**
 * The interface Class representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public interface ClassTransformer<S> extends TemplateTransformer<S> {

  /**
   * Create class representation.
   *
   * @param source the source
   * @param args the args
   * @return the class representation
   */
  ClassRepresentation create(S source, Object... args);

  /**
   * Static field representations set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<FieldRepresentation> staticFieldRepresentations(S source, Object... args);

  /**
   * Field representations set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<FieldRepresentation> fieldRepresentations(S source, Object... args);

  /**
   * Constructor representations set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<ConstructorRepresentation> constructorRepresentations(S source, Object... args);

  /**
   * Method representations set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<MethodRepresentation> methodRepresentations(S source, Object... args);

  /**
   * Package name string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String packageName(S source, Object... args);

  /**
   * Imports set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<String> imports(S source, Object... args);

  /**
   * Annotations set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<String> annotations(S source, Object... args);

  /**
   * Description string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String description(S source, Object... args);

  /**
   * Modifiers string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String modifiers(S source, Object... args);

  /**
   * Simple object name string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String simpleObjectName(S source, Object... args);

  /**
   * Full object name string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String fullObjectName(S source, Object... args);
}
