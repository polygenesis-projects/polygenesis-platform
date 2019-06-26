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

package io.polygenesis.generators.java.scheduler;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.generators.java.shared.transformer.MethodTransformer;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.scheduler.Scheduler;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Scheduler transformer.
 *
 * @author Christos Tsakostas
 */
public class SchedulerTransformer extends AbstractClassTransformer<Scheduler, ServiceMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public SchedulerTransformer(
      DataTypeTransformer dataTypeTransformer, MethodTransformer<ServiceMethod> methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(Scheduler source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Scheduler source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    methodRepresentations.add(
        new MethodRepresentation(
            MethodRepresentationType.ANY,
            new LinkedHashSet<>(),
            new LinkedHashSet<>(Arrays.asList("@Override")),
            "",
            dataTypeTransformer.getModifierPublic(),
            "configure",
            new LinkedHashSet<>(),
            dataTypeTransformer.getVoid(),
            ""));

    return methodRepresentations;
  }

  @Override
  public String packageName(Scheduler source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Scheduler source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("org.apache.camel.spring.SpringRouteBuilder");
    imports.add("org.springframework.stereotype.Component");

    return imports;
  }

  @Override
  public String description(Scheduler source, Object... args) {
    return String.format(
        "The %s Route.", TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public Set<String> annotations(Scheduler source, Object... args) {
    Set<String> annotations = new LinkedHashSet<>();

    annotations.add("@Component");

    return annotations;
  }

  @Override
  public String simpleObjectName(Scheduler source, Object... args) {
    return String.format("%sRoute", super.simpleObjectName(source, args));
  }

  @Override
  public String fullObjectName(Scheduler source, Object... args) {
    return String.format("%s extends SpringRouteBuilder", simpleObjectName(source, args));
  }
}
