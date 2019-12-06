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

package io.polygenesis.generators.java.batchprocessscheduler.scheduler.activity;

import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.TemplateData;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Configure scheduler route activity transformer.
 *
 * @author Christos Tsakostas
 */
public class ConfigureSchedulerRouteActivityTransformer
    implements ActivityTemplateTransformer<Function> {

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public TemplateData transform(Function source, Object... args) {
    ConfigureSchedulerRouteActivityTemplateData data =
        new ConfigureSchedulerRouteActivityTemplateData();

    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", data);

    return new TemplateData(
        dataModel, "polygenesis-trinity-java-batch-process/configure-scheduler-route.java.ftl");
  }
}
