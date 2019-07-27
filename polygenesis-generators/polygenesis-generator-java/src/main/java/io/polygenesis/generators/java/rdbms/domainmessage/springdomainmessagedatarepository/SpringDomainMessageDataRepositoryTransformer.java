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

package io.polygenesis.generators.java.rdbms.domainmessage.springdomainmessagedatarepository;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.transformers.java.AbstractInterfaceTransformer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Spring domain message data repository transformer.
 *
 * @author Christos Tsakostas
 */
public class SpringDomainMessageDataRepositoryTransformer
    extends AbstractInterfaceTransformer<SpringDomainMessageDataRepository, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Spring domain message data repository transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public SpringDomainMessageDataRepositoryTransformer(
      DataTypeTransformer dataTypeTransformer,
      SpringDomainMessageDataRepositoryMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @SuppressWarnings("CPD-START")
  @Override
  public TemplateData transform(SpringDomainMessageDataRepository source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Interface.java.ftl");
  }

  @Override
  public String packageName(SpringDomainMessageDataRepository source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(SpringDomainMessageDataRepository source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.domain.SpringDataGenericRepository");
    imports.add("java.util.UUID");

    return imports;
  }

  @SuppressWarnings("CPD-END")
  @Override
  public String fullObjectName(SpringDomainMessageDataRepository source, Object... args) {
    ObjectName contextName = (ObjectName) args[0];
    return String.format(
        "%s%n\t\textends SpringDataGenericRepository<%sDomainMessageData, UUID>",
        simpleObjectName(source, args), TextConverter.toUpperCamel(contextName.getText()));
  }
}
