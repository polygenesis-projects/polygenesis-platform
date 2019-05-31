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

package io.polygenesis.generators.java.transformers.rdbms.testing;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.models.domain.Persistence;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.transformer.code.AbstractClassTransformer;
import io.polygenesis.transformer.code.FromDataTypeToJavaConverter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Persistence impl test class representable.
 *
 * @author Christos Tsakostas
 */
public class PersistenceImplTestClassTransformer extends AbstractClassTransformer<Persistence> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Persistence impl class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public PersistenceImplTestClassTransformer(
      FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public Set<FieldRepresentation> fieldRepresentations(Persistence source, Object... args) {
    return new LinkedHashSet<>(
        Arrays.asList(
            new FieldRepresentation(
                TextConverter.toUpperCamel(source.getObjectName().getText()),
                TextConverter.toLowerCamel(source.getObjectName().getText()),
                new LinkedHashSet<>(Arrays.asList("@Autowired")))));
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(
      Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\t\t// TODO [PolyGenesis]: write test case\n");
    stringBuilder.append(
        String.format(
            "\t\tassertThat(%s).isNotNull();",
            TextConverter.toLowerCamel(source.getObjectName().getText())));

    MethodRepresentation methodRepresentation =
        new MethodRepresentation(
            MethodRepresentationType.TEST,
            new LinkedHashSet<>(),
            new LinkedHashSet<>(Arrays.asList("@Test")),
            "Should store and restore aggregate root.",
            MODIFIER_PUBLIC,
            "shouldStoreAndRestore",
            new LinkedHashSet<>(),
            "void",
            stringBuilder.toString());

    return new LinkedHashSet<>(Arrays.asList(methodRepresentation));
  }

  @Override
  public String packageName(Persistence source, Object... args) {
    return source.getPackageName().getText();
  }

  @Override
  public Set<String> imports(Persistence source, Object... args) {
    PackageName rootPackageName = (PackageName) args[0];

    Set<String> imports = new TreeSet<>();

    imports.add(rootPackageName.getText() + "." + "RdbmsTest");
    imports.add("org.junit.Test");
    imports.add("org.springframework.beans.factory.annotation.Autowired");

    imports.add("static org.assertj.core.api.Assertions.assertThat");

    return imports;
  }

  @Override
  public Set<String> annotations(Persistence source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("Tests for the ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));

    stringBuilder.append(" Implementation.");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(Persistence source, Object... args) {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String simpleObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(source.getObjectName().getText()));
    stringBuilder.append("ImplTest");

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(Persistence source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));
    stringBuilder.append("ImplTest");
    stringBuilder.append(" ");
    stringBuilder.append("extends RdbmsTest");

    return stringBuilder.toString();
  }
}
