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

import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.models.messaging.subscriber.SubscriberMethod;

/**
 * The interface Subscriber activity.
 *
 * @author Christos Tsakostas
 */
public interface SubscriberActivity {

  /**
   * Body string.
   *
   * @param freemarkerService the freemarker service
   * @param subscriberMethod the subscriber method
   * @return the string
   */
  String body(FreemarkerService freemarkerService, SubscriberMethod subscriberMethod);
}
