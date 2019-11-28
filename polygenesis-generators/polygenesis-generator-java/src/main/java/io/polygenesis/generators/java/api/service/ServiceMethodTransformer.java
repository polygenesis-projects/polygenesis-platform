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

package io.polygenesis.generators.java.api.service;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.thing.Argument;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.transformers.java.AbstractMethodTransformer;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Service method transformer.
 *
 * @author Christos Tsakostas
 */
public class ServiceMethodTransformer extends AbstractMethodTransformer<ServiceMethod> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Service method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   */
  public ServiceMethodTransformer(DataTypeTransformer dataTypeTransformer) {
    super(dataTypeTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String modifiers(ServiceMethod source, Object... args) {
    return "";
  }

  @Override
  public Set<String> imports(ServiceMethod source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getFunction().getReturnValue() != null
        && source.getFunction().getReturnValue().getData().isDataGroup()) {
      DataObject dataObject = source.getFunction().getReturnValue().getData().getAsDataObject();

      if (!dataObject.getPackageName().equals(source.getService().getPackageName())) {
        imports.add(makeCanonicalObjectName(dataObject.getPackageName(), dataObject.getDataType()));
      }
    }

    source
        .getFunction()
        .getArguments()
        .stream()
        .filter(argument -> argument.getData().isDataGroup())
        .map(Argument::getData)
        .map(DataObject.class::cast)
        .forEach(
            dataGroup -> {
              if (!dataGroup.getPackageName().equals(source.getService().getPackageName())) {
                imports.add(
                    makeCanonicalObjectName(dataGroup.getPackageName(), dataGroup.getDataType()));
              }
            });

    return imports;
  }
}
