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

package io.polygenesis.generators.java.domain.supportiveentity;

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.models.domain.InstantiationType;
import io.polygenesis.models.domain.SupportiveEntity;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/** @author Christos Tsakostas */
public class SupportiveEntityIdExporter {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FreemarkerService freemarkerService;
  private final SupportiveEntityIdLegacyClassTransformer supportiveEntityIdLegacyClassTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public SupportiveEntityIdExporter(
      FreemarkerService freemarkerService,
      SupportiveEntityIdLegacyClassTransformer supportiveEntityIdLegacyClassTransformer) {
    this.freemarkerService = freemarkerService;
    this.supportiveEntityIdLegacyClassTransformer = supportiveEntityIdLegacyClassTransformer;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  public void export(Path generationPath, SupportiveEntity supportiveEntity) {
    Map<String, Object> dataModel = new HashMap<>();
    if (supportiveEntity.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      return;
    }

    dataModel.put(
        "representation", supportiveEntityIdLegacyClassTransformer.create(supportiveEntity));

    freemarkerService.export(
        dataModel,
        "polygenesis-representation-java/Class.java.ftl",
        makeFileName(generationPath, supportiveEntity));
  }

  private Path makeFileName(Path generationPath, SupportiveEntity supportiveEntity) {

    return Paths.get(
        generationPath.toString(),
        "src/main/java",
        supportiveEntity.getPackageName().toPath().toString(),
        TextConverter.toUpperCamel(supportiveEntity.getObjectName().getText()) + "Id.java");
  }
}
