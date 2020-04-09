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

package io.polygenesis.generators.java.auxdetails.propertyfile.propertyfile;

import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.TemplateData;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.transformers.java.AbstractClassTransformer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PropertyFileImplTransformer
    extends AbstractClassTransformer<PropertyFileImpl, Function> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Property file impl transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public PropertyFileImplTransformer(
      DataTypeTransformer dataTypeTransformer,
      PropertyFileImplMethodTransformer methodTransformer) {
    super(dataTypeTransformer, methodTransformer);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public TemplateData transform(PropertyFileImpl source, Object... args) {
    Map<String, Object> dataModel = new HashMap<>();
    dataModel.put("representation", create(source, args));

    return new TemplateData(dataModel, "polygenesis-representation-java/Class.java.ftl");
  }

  @Override
  public String packageName(PropertyFileImpl source, Object... args) {
    return source.getRootPackageName().getText();
  }

  @Override
  public Set<String> imports(PropertyFileImpl source, Object... args) {
    Set<String> imports = new TreeSet<>();

    imports.add("com.oregor.trinity4j.properties.AbstractPropertyFileAuxService");
    imports.add("org.springframework.stereotype.Service");

    return imports;
  }

  @Override
  public Set<String> annotations(PropertyFileImpl source, Object... args) {
    return new LinkedHashSet<>(Collections.singletonList("@Service"));
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      PropertyFileImpl source, Object... args) {

    Set<ConstructorRepresentation> constructorRepresentations = new LinkedHashSet<>();

    constructorRepresentations.add(
        createConstructorWithImplementation(
            simpleObjectName(source, args),
            new LinkedHashSet<>(),
            "\t\tsuper(\"classpath:i18n/BackendMessages\");"));

    return constructorRepresentations;
  }

  @Override
  public String fullObjectName(PropertyFileImpl source, Object... args) {
    return String.format(
        "%s extends AbstractPropertyFileAuxService implements %s",
        simpleObjectName(source, args), simpleObjectName(source, args).replace("Impl", ""));
  }
}
