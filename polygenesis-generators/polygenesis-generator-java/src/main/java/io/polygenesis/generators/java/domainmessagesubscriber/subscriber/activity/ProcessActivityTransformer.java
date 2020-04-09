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

package io.polygenesis.generators.java.domainmessagesubscriber.subscriber.activity;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.ActivityTemplateTransformer;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.TemplateData;
import io.polygenesis.models.api.ServiceMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcessActivityTransformer implements ActivityTemplateTransformer<Function> {

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public TemplateData transform(Function source, Object... args) {
    Set<Data> messageData = null;
    ServiceMethod ensureExistenceServiceMethod = null;
    ServiceMethod commandServiceMethod = null;

    if (source.getActivity().hasValue("messageData")) {
      messageData = (Set<Data>) source.getActivity().getValue("messageData");
    }

    if (source.getActivity().hasValue("ensureExistenceServiceMethod")) {
      ensureExistenceServiceMethod =
          (ServiceMethod) source.getActivity().getValue("ensureExistenceServiceMethod");
    }

    if (source.getActivity().hasValue("commandServiceMethod")) {
      commandServiceMethod = (ServiceMethod) source.getActivity().getValue("commandServiceMethod");
    }

    ProcessActivityTemplateData data =
        new ProcessActivityTemplateData(
            messageData, ensureExistenceServiceMethod, commandServiceMethod);

    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("data", data);

    return new TemplateData(
        dataModel,
        "polygenesis-trinity-java/api-clients/"
            + "api-client-domain-message-subscriber/subscriber-process.java.ftl");
  }
}
