/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.generators.java.apidetail.aspect;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ContextName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.ParameterRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ServiceAspectTransformer extends AbstractClassTransformer<ServiceAspect, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service aspect transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public ServiceAspectTransformer(
      DataTypeTransformer dataTypeTransformer, ServiceAspectMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(ServiceAspect source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ServiceAspect source, Object... args) {
    final ContextName contextName = (ContextName) args[1];

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    parameterRepresentations.add(
        new ParameterRepresentation(
            String.format(
                "%sPropertyFileAuxService", TextConverter.toUpperCamel(contextName.getText())),
            "propertyFileAuxService"));

    constructorRepresentations.add(
        createConstructorWithImplementation(
            simpleObjectName(source, args),
            parameterRepresentations,
            "\t\tsuper(propertyFileAuxService, \"en\");"));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(ServiceAspect source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getAround(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(ServiceAspect source, Object... args) {
    return source.getRootPackageName().getText();
  }

  @Override
  public Set<String> imports(ServiceAspect source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.addAll(methodTransformer.imports(source.getAround(), args));

    imports.add("com.oregor.trinity4j.api.AbstractServiceAspect");
    imports.add("org.aspectj.lang.ProceedingJoinPoint");
    imports.add("org.aspectj.lang.annotation.Around");
    imports.add("org.aspectj.lang.annotation.Aspect");
    imports.add("org.springframework.stereotype.Component");

    return imports;
  }

  @Override
  public Set<String> annotations(ServiceAspect source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Aspect", "@Component"));
  }

  @Override
  public String description(ServiceAspect source, Object... args) {
    return String.format(
        "The %s.", TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public String fullObjectName(ServiceAspect source, Object... args) {
    return String.format("%s extends AbstractServiceAspect", simpleObjectName(source, args));
  }
}
