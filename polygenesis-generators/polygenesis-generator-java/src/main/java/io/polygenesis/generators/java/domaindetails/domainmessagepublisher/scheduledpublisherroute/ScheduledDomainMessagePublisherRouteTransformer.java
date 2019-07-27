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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.scheduledpublisherroute;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
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

/**
 * The type Scheduled domain message publisher route transformer.
 *
 * @author Christos Tsakostas
 */
public class ScheduledDomainMessagePublisherRouteTransformer
    extends AbstractClassTransformer<ScheduledDomainMessagePublisherRoute, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Scheduled domain message publisher route transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ScheduledDomainMessagePublisherRouteTransformer(
      DataTypeTransformer dataTypeTransformer,
      ScheduledDomainMessagePublisherRouteMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(ScheduledDomainMessagePublisherRoute source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(
      ScheduledDomainMessagePublisherRoute source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(
        new FieldRepresentation(
            TextConverter.toUpperCamel(
                source.getScheduledDomainMessagePublisher().getObjectName().getText()),
            "publisher"));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ScheduledDomainMessagePublisherRoute source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            TextConverter.toUpperCamel(
                source.getScheduledDomainMessagePublisher().getObjectName().getText()),
            "publisher"));

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    ConstructorRepresentation constructorRepresentation =
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source, args),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tthis.publisher = publisher;");

    constructorRepresentations.add(constructorRepresentation);

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      ScheduledDomainMessagePublisherRoute source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getConfigure(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(ScheduledDomainMessagePublisherRoute source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(ScheduledDomainMessagePublisherRoute source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("org.apache.camel.spring.SpringRouteBuilder");
    imports.add("org.springframework.stereotype.Component");
    imports.add("org.springframework.beans.factory.annotation.Value");

    return imports;
  }

  @Override
  public Set<String> annotations(ScheduledDomainMessagePublisherRoute source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Component"));
  }

  @Override
  public String description(ScheduledDomainMessagePublisherRoute source, Object... args) {
    return super.description(source, args);
  }

  @Override
  public String modifiers(ScheduledDomainMessagePublisherRoute source, Object... args) {
    return super.modifiers(source, args);
  }

  @Override
  public String simpleObjectName(ScheduledDomainMessagePublisherRoute source, Object... args) {
    return super.simpleObjectName(source, args);
  }

  @Override
  public String fullObjectName(ScheduledDomainMessagePublisherRoute source, Object... args) {
    return String.format("%s extends SpringRouteBuilder", simpleObjectName(source, args));
  }
}
