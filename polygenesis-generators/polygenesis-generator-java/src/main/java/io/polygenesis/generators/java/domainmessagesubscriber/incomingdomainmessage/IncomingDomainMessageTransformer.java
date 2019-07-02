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

package io.polygenesis.generators.java.domainmessagesubscriber.incomingdomainmessage;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.generators.java.shared.transformer.AbstractClassTransformer;
import io.polygenesis.representations.code.MethodRepresentation;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Incoming domain message transformer.
 *
 * @author Christos Tsakostas
 */
public class IncomingDomainMessageTransformer
    extends AbstractClassTransformer<IncomingDomainMessage, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Incoming domain message transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public IncomingDomainMessageTransformer(
      DataTypeTransformer dataTypeTransformer,
      IncomingDomainMessageMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(IncomingDomainMessage source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(
      IncomingDomainMessage source, Object... args) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    //    methodRepresentations.add(methodTransformer.create(source.getGetMessageId(), args));
    //    methodRepresentations.add(methodTransformer.create(source.getGetRootId(), args));
    //    methodRepresentations.add(methodTransformer.create(source.getGetMessageBody(), args));

    return methodRepresentations;
  }

  @Override
  public String packageName(IncomingDomainMessage source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(IncomingDomainMessage source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.api.clients.domainmessage.AbstractIncomingDomainMessage");

    return imports;
  }

  @Override
  public String fullObjectName(IncomingDomainMessage source, Object... args) {
    return String.format(
        "%s extends AbstractIncomingDomainMessage", super.simpleObjectName(source, args));
  }
}
