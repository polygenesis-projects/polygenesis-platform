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

import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Set;

/**
 * The interface Method representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public interface MethodTransformer<S extends FunctionProvider> {

  /**
   * Create method representation.
   *
   * @param source the source
   * @param args the args
   * @return the method representation
   */
  MethodRepresentation create(S source, Object... args);

  /**
   * Method type method type.
   *
   * @param source the source
   * @param args the args
   * @return the method type
   */
  MethodRepresentationType methodType(S source, Object... args);

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
   * Method name string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String methodName(S source, Object... args);

  /**
   * Parameter representations set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<ParameterRepresentation> parameterRepresentations(S source, Object... args);

  /**
   * Return value string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String returnValue(S source, Object... args);

  /**
   * Implementation string.
   *
   * @param source the source
   * @param args the args
   * @return the string
   */
  String implementation(S source, Object... args);

  /**
   * Thrown exceptions set.
   *
   * @param source the source
   * @param args the args
   * @return the set
   */
  Set<String> thrownExceptions(S source, Object... args);
}
