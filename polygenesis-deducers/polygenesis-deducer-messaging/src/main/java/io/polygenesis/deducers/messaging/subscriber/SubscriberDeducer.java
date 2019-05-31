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

package io.polygenesis.deducers.messaging.subscriber;

import io.polygenesis.models.messaging.subscriber.Subscriber;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Subscriber deducer.
 *
 * @author Christos Tsakostas
 */
public class SubscriberDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @return the set
   */
  public Set<Subscriber> deduce() {
    Set<Subscriber> subscribers = new LinkedHashSet<>();

    return subscribers;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

}
