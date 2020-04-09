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

package io.polygenesis.generators.java.domaindetails.domainmessagepublisher.publishdto;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain message publish dto transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessagePublishDtoTransformer
    extends AbstractClassTransformer<DomainMessagePublishDto, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message publish dto transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessagePublishDtoTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessagePublishDtoMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(DomainMessagePublishDto source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public String packageName(DomainMessagePublishDto source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessagePublishDto source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("UUID", "messageId"));
    parameterRepresentations.add(new ParameterRepresentation("LocalDateTime", "occurredOn"));
    parameterRepresentations.add(new ParameterRepresentation("UUID", "rootId"));
    parameterRepresentations.add(new ParameterRepresentation("String", "messageName"));
    parameterRepresentations.add(new ParameterRepresentation("Integer", "messageVersion"));
    parameterRepresentations.add(new ParameterRepresentation("String", "messageBody"));
    parameterRepresentations.add(new ParameterRepresentation("String", "context"));

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    ConstructorRepresentation constructorRepresentation =
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source, args),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            "\t\tsuper(messageId, occurredOn, rootId, messageName, messageVersion, messageBody,\n"
                + "\t\t\t\tcontext);");

    constructorRepresentations.add(constructorRepresentation);

    return constructorRepresentations;
  }

  @Override
  public Set<String> imports(DomainMessagePublishDto source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.AbstractDomainMessagePublishDto");
    imports.add("com.oregor.trinity4j.domain.DomainMessageType");
    imports.add("java.time.LocalDateTime");
    imports.add("java.util.UUID");

    return imports;
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(DomainMessagePublishDto source, Object... args) {
    return String.format(
        "%s extends AbstractDomainMessagePublishDto", simpleObjectName(source, args));
  }
}
