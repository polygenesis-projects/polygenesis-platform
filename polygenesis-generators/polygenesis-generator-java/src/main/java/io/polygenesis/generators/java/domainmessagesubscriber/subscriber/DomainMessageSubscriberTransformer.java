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

package io.polygenesis.generators.java.domainmessagesubscriber.subscriber;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber.DomainMessageAbstractSubscriber;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.text.StrBuilder;

/**
 * The type Domain message subscriber transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageSubscriberTransformer
    extends AbstractClassTransformer<DomainMessageSubscriber, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message subscriber transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessageSubscriberTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessageMethodSubscriberTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(DomainMessageSubscriber source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      DomainMessageSubscriber source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    if (source.getEnsureExistenceServiceMethod() != null) {
      fieldRepresentations.add(
          new FieldRepresentation(
              "final "
                  + TextConverter.toUpperCamel(
                      source
                          .getEnsureExistenceServiceMethod()
                          .getService()
                          .getServiceName()
                          .getText()),
              TextConverter.toLowerCamel(
                  source
                      .getEnsureExistenceServiceMethod()
                      .getService()
                      .getServiceName()
                      .getText())));
    }

    if (source.getCommandServiceMethod() != null) {
      fieldRepresentations.add(
          new FieldRepresentation(
              "final "
                  + TextConverter.toUpperCamel(
                      source.getCommandServiceMethod().getService().getServiceName().getText()),
              TextConverter.toLowerCamel(
                  source.getCommandServiceMethod().getService().getServiceName().getText())));
    }

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessageSubscriber source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("ObjectMapper", "objectMapper"));

    if (source.getEnsureExistenceServiceMethod() != null) {
      parameterRepresentations.add(
          new ParameterRepresentation(
              TextConverter.toUpperCamel(
                  source.getEnsureExistenceServiceMethod().getService().getServiceName().getText()),
              TextConverter.toLowerCamel(
                  source
                      .getEnsureExistenceServiceMethod()
                      .getService()
                      .getServiceName()
                      .getText())));
    }

    if (source.getCommandServiceMethod() != null) {
      parameterRepresentations.add(
          new ParameterRepresentation(
              TextConverter.toUpperCamel(
                  source.getCommandServiceMethod().getService().getServiceName().getText()),
              TextConverter.toLowerCamel(
                  source.getCommandServiceMethod().getService().getServiceName().getText())));
    }

    StrBuilder implementation = new StrBuilder();

    implementation.append("\t\t");
    implementation.append("super(objectMapper);");
    implementation.append("\n");
    implementation.append("\t\t");

    if (source.getEnsureExistenceServiceMethod() != null) {
      implementation.append(
          String.format(
              "this.%s = %s;",
              TextConverter.toLowerCamel(
                  source.getEnsureExistenceServiceMethod().getService().getServiceName().getText()),
              TextConverter.toLowerCamel(
                  source
                      .getEnsureExistenceServiceMethod()
                      .getService()
                      .getServiceName()
                      .getText())));
    }

    if (source.getCommandServiceMethod() != null) {
      implementation.append(
          String.format(
              "this.%s = %s;",
              TextConverter.toLowerCamel(
                  source.getCommandServiceMethod().getService().getServiceName().getText()),
              TextConverter.toLowerCamel(
                  source.getCommandServiceMethod().getService().getServiceName().getText())));
    }

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    constructorRepresentations.add(
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            implementation.toString()));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      DomainMessageSubscriber source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getProcess(), args));
    methodRepresentations.add(methodTransformer.create(source.getGetSupportedMessageTypes(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(DomainMessageSubscriber source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessageSubscriber source, Object... args) {
    DomainMessageAbstractSubscriber domainMessageAbstractSubscriber =
        (DomainMessageAbstractSubscriber) args[0];
    Set<String> imports = new TreeSet<>();

    imports.add(
        String.format(
            "%s.%s",
            domainMessageAbstractSubscriber.getPackageName().getText(),
            TextConverter.toUpperCamel(domainMessageAbstractSubscriber.getObjectName().getText())));

    if (source.getEnsureExistenceServiceMethod() != null) {
      imports.add(
          String.format(
              "%s.%s",
              source.getEnsureExistenceServiceMethod().getService().getPackageName().getText(),
              TextConverter.toUpperCamel(
                  source
                      .getEnsureExistenceServiceMethod()
                      .getService()
                      .getServiceName()
                      .getText())));
    }

    if (source.getCommandServiceMethod() != null) {
      imports.add(
          String.format(
              "%s.%s",
              source.getCommandServiceMethod().getService().getPackageName().getText(),
              TextConverter.toUpperCamel(
                  source.getCommandServiceMethod().getService().getServiceName().getText())));
    }

    if (source.getCommandServiceMethod() != null) {
      imports.add(
          String.format(
              "%s.%s",
              source
                  .getCommandServiceMethod()
                  .getRequestDto()
                  .getDataObject()
                  .getPackageName()
                  .getText(),
              TextConverter.toUpperCamel(
                  source
                      .getCommandServiceMethod()
                      .getRequestDto()
                      .getDataObject()
                      .getObjectName()
                      .getText())));
    }

    imports.add("com.fasterxml.jackson.databind.ObjectMapper");
    imports.add("com.fasterxml.jackson.databind.JsonNode");
    imports.add("java.util.Arrays");
    imports.add("java.util.List");
    imports.add("org.springframework.stereotype.Service");

    imports.addAll(methodTransformer.imports(source.getProcess(), args));
    imports.addAll(methodTransformer.imports(source.getGetSupportedMessageTypes(), args));

    if (source.getProcess().getActivity().hasValue("ensureExistenceServiceMethod")) {
      ServiceMethod ensureExistenceServiceMethod =
          (ServiceMethod)
              source.getProcess().getActivity().getValue("ensureExistenceServiceMethod");
      imports.addAll(methodTransformer.imports(ensureExistenceServiceMethod.getFunction(), args));
    }

    if (source.getProcess().getActivity().hasValue("commandServiceMethod")) {
      ServiceMethod commandServiceMethod =
          (ServiceMethod) source.getProcess().getActivity().getValue("commandServiceMethod");
      imports.addAll(methodTransformer.imports(commandServiceMethod.getFunction(), args));
    }

    return imports;
  }

  @Override
  public Set<String> annotations(DomainMessageSubscriber source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Service"));
  }

  @Override
  public String description(DomainMessageSubscriber source, Object... args) {
    return String.format(
        "The %s Domain Message Subscriber.",
        TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public String fullObjectName(DomainMessageSubscriber source, Object... args) {
    DomainMessageAbstractSubscriber domainMessageAbstractSubscriber =
        (DomainMessageAbstractSubscriber) args[0];

    return String.format(
        "%s extends %s",
        simpleObjectName(source),
        TextConverter.toUpperCamel(domainMessageAbstractSubscriber.getObjectName().getText()));
  }
}
