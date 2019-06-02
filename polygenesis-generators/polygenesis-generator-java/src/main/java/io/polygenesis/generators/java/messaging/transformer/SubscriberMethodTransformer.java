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

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.generators.java.messaging.activity.SubscriberActivityRegistry;
import io.polygenesis.models.messaging.subscriber.SubscriberMethod;
import io.polygenesis.transformer.code.AbstractMethodTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Subscriber method transformer.
 *
 * @author Christos Tsakostas
 */
public class SubscriberMethodTransformer extends AbstractMethodTransformer<SubscriberMethod> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final SubscriberActivityRegistry subscriberActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber method transformer.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param freemarkerService the freemarker service
   * @param subscriberActivityRegistry the subscriber activity registry
   */
  public SubscriberMethodTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FreemarkerService freemarkerService,
      SubscriberActivityRegistry subscriberActivityRegistry) {
    super(fromDataTypeToJavaConverter);
    this.freemarkerService = freemarkerService;
    this.subscriberActivityRegistry = subscriberActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> annotations(SubscriberMethod source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Override"));
  }

  @Override
  public String implementation(SubscriberMethod source, Object... args) {
    Optional<String> optionalImplementation =
        subscriberActivityRegistry.implementation(freemarkerService, source);

    if (optionalImplementation.isPresent()) {
      return optionalImplementation.get();
    } else {
      if (source.getFunction().getPurpose().equals(Purpose.supportedMessages())) {
        return String.format("\t\treturn Arrays.asList(%s);", getSupportedMessageTypes(source));
      } else {
        return super.implementation(source, args);
      }
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Gets supported message types.
   *
   * @param source the source
   * @return the supported message types
   */
  @SuppressWarnings("unchecked")
  private String getSupportedMessageTypes(SubscriberMethod source) {
    Set<String> supportedMessageTypes = (Set<String>)
        source.getFunction().getActivity().getValue("supportedMessageTypes");

    return supportedMessageTypes
        .stream()
        .map(s -> "\"" + s + "\"")
        .collect(Collectors.joining(", "));
  }
}
