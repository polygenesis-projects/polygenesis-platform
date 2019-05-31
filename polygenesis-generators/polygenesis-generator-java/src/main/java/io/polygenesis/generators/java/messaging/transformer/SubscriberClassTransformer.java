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

package io.polygenesis.generators.java.messaging.transformer;

import io.polygenesis.models.messaging.subscriber.Subscriber;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformer.code.AbstractInterfaceTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import io.polygenesis.transformer.code.FunctionToMethodRepresentationTransformer;
import java.util.Set;

/**
 * The type Subscriber class transformer.
 *
 * @author Christos Tsakostas
 */
public class SubscriberClassTransformer extends AbstractInterfaceTransformer<Subscriber> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber class transformer.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param functionToMethodRepresentationTransformer the function to method representation
   *     transformer
   */
  public SubscriberClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FunctionToMethodRepresentationTransformer functionToMethodRepresentationTransformer) {
    super(fromDataTypeToJavaConverter, functionToMethodRepresentationTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<MethodRepresentation> methodRepresentations(Subscriber source, Object... args) {
    return null;
  }

  @Override
  public String packageName(Subscriber source, Object... args) {
    return null;
  }

  @Override
  public Set<String> imports(Subscriber source, Object... args) {
    return null;
  }

  @Override
  public Set<String> annotations(Subscriber source, Object... args) {
    return null;
  }

  @Override
  public String description(Subscriber source, Object... args) {
    return null;
  }

  @Override
  public String modifiers(Subscriber source, Object... args) {
    return null;
  }

  @Override
  public String simpleObjectName(Subscriber source, Object... args) {
    return null;
  }

  @Override
  public String fullObjectName(Subscriber source, Object... args) {
    return null;
  }
}
