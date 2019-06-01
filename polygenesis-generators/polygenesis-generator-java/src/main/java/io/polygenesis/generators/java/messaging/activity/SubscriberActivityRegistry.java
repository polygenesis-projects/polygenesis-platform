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

package io.polygenesis.generators.java.messaging.activity;

import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.models.messaging.subscriber.SubscriberMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Subscriber activity registry.
 *
 * @author Christos Tsakostas
 */
public class SubscriberActivityRegistry {

  // ===============================================================================================
  // STATIC STATE
  // ===============================================================================================
  private static Map<Purpose, SubscriberActivity> purposeActivityMap = new HashMap<>();

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  static {
    purposeActivityMap.put(Purpose.process(), new ProcessSubscriberActivity());
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Implementation optional.
   *
   * @param freemarkerService the freemarker service
   * @param subscriberMethod the subscriber method
   * @return the optional
   */
  public Optional<String> implementation(
      FreemarkerService freemarkerService, SubscriberMethod subscriberMethod) {
    if (isActivitySupportedForFunction(subscriberMethod)) {
      return Optional.of(activityFor(subscriberMethod).body(freemarkerService, subscriberMethod));
    } else {
      return Optional.empty();
    }
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private boolean isActivitySupportedForFunction(SubscriberMethod subscriberMethod) {
    return purposeActivityMap.containsKey(subscriberMethod.getFunction().getPurpose());
  }

  private SubscriberActivity activityFor(SubscriberMethod subscriberMethod) {
    return purposeActivityMap.get(subscriberMethod.getFunction().getPurpose());
  }
}
