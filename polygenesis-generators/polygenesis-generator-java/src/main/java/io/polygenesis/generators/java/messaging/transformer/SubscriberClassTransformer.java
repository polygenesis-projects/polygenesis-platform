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

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.messaging.activity.SubscriberActivityRegistry;
import io.polygenesis.models.messaging.subscriber.Subscriber;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformer.code.AbstractClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import io.polygenesis.transformer.code.FunctionToMethodRepresentationTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The type Subscriber class transformer.
 *
 * @author Christos Tsakostas
 */
public class SubscriberClassTransformer extends AbstractClassTransformer<Subscriber> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final FunctionToMethodRepresentationTransformer functionToMethodRepresentationTransformer;
  private final SubscriberActivityRegistry subscriberActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Subscriber class transformer.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   * @param freemarkerService the freemarker service
   * @param functionToMethodRepresentationTransformer the function to method representation
   *     transformer
   * @param subscriberActivityRegistry the subscriber activity registry
   */
  public SubscriberClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      FreemarkerService freemarkerService,
      FunctionToMethodRepresentationTransformer functionToMethodRepresentationTransformer,
      SubscriberActivityRegistry subscriberActivityRegistry) {
    super(fromDataTypeToJavaConverter);
    this.freemarkerService = freemarkerService;
    this.functionToMethodRepresentationTransformer = functionToMethodRepresentationTransformer;
    this.subscriberActivityRegistry = subscriberActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String packageName(Subscriber source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Subscriber source, Object... args) {
    Set<String> imports = new LinkedHashSet<>();

    imports.add("com.fasterxml.jackson.databind.JsonNode");
    imports.add("com.fasterxml.jackson.databind.ObjectMapper");
    imports.add("java.io.IOException");
    imports.add("org.springframework.stereotype.Component");

    imports.add(
        String.format(
            "%s.%s",
            source.getCommandServiceMethod().getService().getPackageName().getText(),
            TextConverter.toUpperCamel(
                source.getCommandServiceMethod().getService().getServiceName().getText())));

    imports.add(
        String.format(
            "%s.%s",
            source.getQueryServiceMethod().getService().getPackageName().getText(),
            TextConverter.toUpperCamel(
                source.getQueryServiceMethod().getService().getServiceName().getText())));

    return imports;
  }

  @Override
  public Set<String> annotations(Subscriber source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Component");

    return annotations;
  }

  @Override
  public String description(Subscriber source, Object... args) {
    return "";
  }

  @Override
  public String modifiers(Subscriber source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(Subscriber source, Object... args) {
    return TextConverter.toUpperCamel(source.getName().getText());
  }

  @Override
  public String fullObjectName(Subscriber source, Object... args) {
    return TextConverter.toUpperCamel(source.getName().getText());
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(Subscriber source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(new FieldRepresentation("ObjectMapper", "objectMapper"));

    fieldRepresentations.add(
        new FieldRepresentation(
            TextConverter.toUpperCamel(
                source.getCommandServiceMethod().getService().getServiceName().getText()),
            TextConverter.toLowerCamel(
                source.getCommandServiceMethod().getService().getServiceName().getText())));

    fieldRepresentations.add(
        new FieldRepresentation(
            TextConverter.toUpperCamel(
                source.getQueryServiceMethod().getService().getServiceName().getText()),
            TextConverter.toLowerCamel(
                source.getQueryServiceMethod().getService().getServiceName().getText())));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Subscriber source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            createConstructorWithDirectAssignmentFromFieldRepresentations(
                source.getName().getText(), fieldRepresentations(source, args))));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Subscriber source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    source
        .getSubscriberMethods()
        .forEach(
            subscriberMethod -> {
              MethodRepresentation methodRepresentation =
                  functionToMethodRepresentationTransformer.create(subscriberMethod.getFunction());

              Optional<String> optionalImplementation =
                  subscriberActivityRegistry.implementation(freemarkerService, subscriberMethod);

              if (optionalImplementation.isPresent()) {
                methodRepresentation.changeImplementationTo(optionalImplementation.get());
              }

              methodRepresentations.add(methodRepresentation);
            });

    return methodRepresentations;
  }
}
