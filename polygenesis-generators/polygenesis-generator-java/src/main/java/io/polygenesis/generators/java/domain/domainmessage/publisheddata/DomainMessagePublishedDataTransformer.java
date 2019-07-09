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

package io.polygenesis.generators.java.domain.domainmessage.publisheddata;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain message published data transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublishedDataTransformer
    extends AbstractClassTransformer<DomainMessagePublishedData, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message published data transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessagePublishedDataTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessagePublishedDataMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(DomainMessagePublishedData source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessagePublishedData source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("UUID", "messageId"));
    parameterRepresentations.add(new ParameterRepresentation("LocalDateTime", "occurredOn"));
    parameterRepresentations.add(new ParameterRepresentation("UUID", "rootId"));
    parameterRepresentations.add(new ParameterRepresentation("Integer", "rootVersion"));
    parameterRepresentations.add(new ParameterRepresentation("DomainMessageType", "messageType"));
    parameterRepresentations.add(new ParameterRepresentation("String", "messageName"));
    parameterRepresentations.add(new ParameterRepresentation("Integer", "messageVersion"));
    parameterRepresentations.add(new ParameterRepresentation("String", "messageBody"));
    parameterRepresentations.add(new ParameterRepresentation("UUID", "userId"));
    parameterRepresentations.add(new ParameterRepresentation("String", "ipAddress"));
    parameterRepresentations.add(new ParameterRepresentation("LocalDateTime", "sentOn"));

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    constructorRepresentations.add(
        createEmptyConstructorWithImplementation(
            simpleObjectName(source), new LinkedHashSet<>(), "\t\tsuper();"));

    ConstructorRepresentation constructorRepresentation =
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source, args),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(messageId, occurredOn, rootId, rootVersion, messageType, "
                + "messageName, messageVersion, messageBody, userId,\n"
                + "\t\t\t\tipAddress, sentOn);");

    constructorRepresentations.add(constructorRepresentation);

    return constructorRepresentations;
  }

  @Override
  public String packageName(DomainMessagePublishedData source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessagePublishedData source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.AbstractDomainMessagePublishedData");
    imports.add("javax.persistence.Entity");
    imports.add("javax.persistence.Table");

    imports.add("com.oregor.trinity4j.domain.DomainMessageType");
    imports.add("java.time.LocalDateTime");
    imports.add("java.util.UUID");

    return imports;
  }

  @Override
  public Set<String> annotations(DomainMessagePublishedData source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            "@Entity",
            "@Table(name = Constants.DEFAULT_TABLE_PREFIX + \"domain_message_published_data\")"));
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(DomainMessagePublishedData source, Object... args) {
    return String.format(
        "%s extends AbstractDomainMessagePublishedData", simpleObjectName(source, args));
  }
}
