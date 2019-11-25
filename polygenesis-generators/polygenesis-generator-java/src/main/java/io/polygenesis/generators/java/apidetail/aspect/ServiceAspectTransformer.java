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

package io.polygenesis.generators.java.apidetail.aspect;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Service aspect transformer.
 *
 * @author Christos Tsakostas
 */
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
  public Set<FieldRepresentation> fieldRepresentations(ServiceAspect source, Object... args) {
    Set<FieldRepresentation> fieldRepresentations = new LinkedHashSet<>();

    fieldRepresentations.add(
        new FieldRepresentation("PropertyFileAuxService", "propertyFileAuxService"));

    return fieldRepresentations;
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      ServiceAspect source, Object... args) {
    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    constructorRepresentations.add(
        createConstructorWithDirectAssignmentFromFieldRepresentations(
            simpleObjectName(source, args), fieldRepresentations(source, args)));

    return constructorRepresentations;
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(ServiceAspect source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(methodTransformer.create(source.getAround(), args));
    methodRepresentations.add(methodTransformer.create(source.getGetReturnValue(), args));

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
    imports.addAll(methodTransformer.imports(source.getGetReturnValue(), args));

    imports.add("com.oregor.trinity4j.api.ApiError");
    imports.add("com.oregor.trinity4j.api.ApiRequest");
    imports.add("com.oregor.trinity4j.domain.DomainException");
    imports.add("com.oregor.trinity4j.properties.PropertyFileAuxService");
    imports.add("java.util.Locale");
    imports.add("org.aspectj.lang.ProceedingJoinPoint");
    imports.add("org.aspectj.lang.Signature");
    imports.add("org.aspectj.lang.annotation.Around");
    imports.add("org.aspectj.lang.annotation.Aspect");
    imports.add("org.aspectj.lang.reflect.MethodSignature");
    imports.add("org.springframework.context.MessageSource");
    imports.add("org.springframework.stereotype.Component");

    // TODO
    imports.remove("java.lang.Class<?>");
    imports.remove("java.lang.Object");

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
}
