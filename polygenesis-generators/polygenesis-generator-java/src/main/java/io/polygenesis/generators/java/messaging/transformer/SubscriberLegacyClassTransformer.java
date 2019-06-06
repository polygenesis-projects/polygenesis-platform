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

import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.generators.java.shared.transformer.AbstractLegacyClassTransformer;
import io.polygenesis.generators.java.shared.transformer.FromDataTypeToJavaConverter;
import io.polygenesis.models.messaging.subscriber.Subscriber;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Subscriber class transformer.
 *
 * @author Christos Tsakostas
 */
public class SubscriberLegacyClassTransformer extends AbstractLegacyClassTransformer<Subscriber> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final SubscriberLegacyMethodTransformer subscriberMethodTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public SubscriberLegacyClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter,
      SubscriberLegacyMethodTransformer subscriberMethodTransformer) {
    super(fromDataTypeToJavaConverter);
    this.subscriberMethodTransformer = subscriberMethodTransformer;
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
    imports.add("com.oregor.trinity4j.api.AbstractMessageSubscriber");
    imports.add("java.util.List");
    imports.add("java.util.Arrays");

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

    source
        .getSubscriberMethods()
        .forEach(
            subscriberMethod -> {
              imports.add(
                  String.format(
                      "%s.%s",
                      subscriberMethod
                          .getCommandServiceMethod()
                          .getRequestDto()
                          .getDataObject()
                          .getPackageName()
                          .getText(),
                      TextConverter.toUpperCamel(
                          subscriberMethod
                              .getCommandServiceMethod()
                              .getRequestDto()
                              .getDataObject()
                              .getObjectName()
                              .getText())));

              imports.add(
                  String.format(
                      "%s.%s",
                      subscriberMethod
                          .getQueryServiceMethod()
                          .getRequestDto()
                          .getDataObject()
                          .getPackageName()
                          .getText(),
                      TextConverter.toUpperCamel(
                          subscriberMethod
                              .getCommandServiceMethod()
                              .getRequestDto()
                              .getDataObject()
                              .getObjectName()
                              .getText())));
            });

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
    return String.format(
        "Message handler for %s.", TextConverter.toUpperCamelSpaces(source.getName().getText()));
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
    return String.format(
        "%s extends AbstractMessageSubscriber",
        TextConverter.toUpperCamel(source.getName().getText()));
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(Subscriber source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

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

    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            "ObjectMapper", "objectMapper", DataPurpose.superclassParameter()));

    parameterRepresentations.addAll(
        convertFieldRepresentationsToParameterRepresentations(fieldRepresentations(source, args)));

    ConstructorRepresentation constructorRepresentation =
        createConstructorWithDirectAssignment(source.getName().getText(), parameterRepresentations);

    return new LinkedHashSet<>(Arrays.asList(constructorRepresentation));
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Subscriber source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    source
        .getSubscriberMethods()
        .forEach(
            subscriberMethod ->
                methodRepresentations.add(subscriberMethodTransformer.create(subscriberMethod)));

    return methodRepresentations;
  }
}
