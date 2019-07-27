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

package io.polygenesis.deducers.stateprovider.internal;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.valueobjects.FeatureName;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.metamodels.stateprovider.Provider;
import io.polygenesis.metamodels.stateprovider.ProviderType;

/**
 * The type Provider detail deducer.
 *
 * @author Christos Tsakostas
 */
public class ProviderDetailDeducer {

  /**
   * Deduce provider.
   *
   * @param function the function
   * @return the provider
   */
  public Provider deduce(Function function) {
    Provider provider =
        new Provider(
            new Name(
                String.format("%sDetailProvider", function.getThing().getThingName().getText())),
            new FeatureName(function.getThing().getThingName().getText()),
            ProviderType.DETAIL);

    return provider;
  }
}
