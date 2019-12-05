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

package io.polygenesis.generators.java.domainmessagesubscriber.subscriber;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.domainmessagesubscriber.subscriber.activity.DomainMessageSubscriberActivityRegistry;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Domain message method subscriber transformer.
 *
 * @author Christos Tsakostas
 */
public class DomainMessageMethodSubscriberTransformer extends AbstractMethodTransformer<Function> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  private DomainMessageSubscriberActivityRegistry domainMessageSubscriberActivityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Domain message method subscriber transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param domainMessageSubscriberActivityRegistry the subscriber method activity registry
   */
  public DomainMessageMethodSubscriberTransformer(
      DataTypeTransformer dataTypeTransformer,
      DomainMessageSubscriberActivityRegistry domainMessageSubscriberActivityRegistry) {
    super(dataTypeTransformer);
    this.domainMessageSubscriberActivityRegistry = domainMessageSubscriberActivityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<String> annotations(Function source, Object... args) {
    return new LinkedHashSet<>(Arrays.asList("@Override"));
  }

  @Override
  public String description(Function source, Object... args) {
    return "";
  }

  @Override
  public String implementation(Function source, Object... args) {
    if (domainMessageSubscriberActivityRegistry.isActivitySupportedFor(source)) {
      return domainMessageSubscriberActivityRegistry.activityFor(source, args);
    } else {
      return super.implementation(source, args);
    }
  }

  @Override
  public Set<String> imports(Function source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getReturnValue() != null && source.getReturnValue().isDataGroup()) {
      DataObject dataObject = source.getReturnValue().getAsDataObject();

      // TODO
      // if (!dataObject.getPackageName().equals(source.getService().getPackageName())) {
      imports.add(makeCanonicalObjectName(dataObject.getPackageName(), dataObject.getDataType()));
      // }
    }

    source
        .getArguments()
        .stream()
        .filter(argument -> argument.isDataGroup())
        .map(DataObject.class::cast)
        .forEach(
            dataGroup -> {
              // TODO
              // if (!dataGroup.getPackageName().equals(source.getService().getPackageName())) {
              imports.add(
                  makeCanonicalObjectName(dataGroup.getPackageName(), dataGroup.getDataType()));
              // }
            });

    return imports;
  }
}
