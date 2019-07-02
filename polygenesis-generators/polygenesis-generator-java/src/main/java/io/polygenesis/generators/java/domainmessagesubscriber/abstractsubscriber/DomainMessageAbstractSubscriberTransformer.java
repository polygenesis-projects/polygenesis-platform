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

package io.polygenesis.generators.java.domainmessagesubscriber.abstractsubscriber;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain message abstract subscriber transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageAbstractSubscriberTransformer
    extends AbstractClassTransformer<DomainMessageAbstractSubscriber, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message abstract subscriber transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public DomainMessageAbstractSubscriberTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessageMethodAbstractSubscriberTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(DomainMessageAbstractSubscriber source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      DomainMessageAbstractSubscriber source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(new ParameterRepresentation("ObjectMapper", "objectMapper"));

    ContextName contextName = (ContextName) args[0];
    constructorRepresentations.add(
        new ConstructorRepresentation(
            new LinkedHashSet<>(),
            description(source),
            dataTypeTransformer.getModifierPublic(),
            parameterRepresentations,
            String.format(
                "\t\tsuper(objectMapper, %sIncomingDomainMessage.class);",
                TextConverter.toUpperCamel(contextName.getText()))));

    return constructorRepresentations;
  }

  @Override
  public String packageName(DomainMessageAbstractSubscriber source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(DomainMessageAbstractSubscriber source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.fasterxml.jackson.databind.ObjectMapper");
    imports.add("com.oregor.trinity4j.api.clients.domainmessage.AbstractDomainMessageSubscriber");

    return imports;
  }

  @Override
  public String modifiers(DomainMessageAbstractSubscriber source, Object... args) {
    return String.format(
        "%s %s",
        dataTypeTransformer.getModifierPublic(), dataTypeTransformer.getModifierAbstract());
  }

  @Override
  public String fullObjectName(DomainMessageAbstractSubscriber source, Object... args) {
    ContextName contextName = (ContextName) args[0];
    return String.format(
        "%s%n\t\textends AbstractDomainMessageSubscriber<%sIncomingDomainMessage>",
        super.simpleObjectName(source, args), TextConverter.toUpperCamel(contextName.getText()));
  }
}
