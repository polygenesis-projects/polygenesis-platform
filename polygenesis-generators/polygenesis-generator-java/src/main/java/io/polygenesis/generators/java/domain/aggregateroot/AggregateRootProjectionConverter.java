/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
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

package io.polygenesis.generators.java.domain.aggregateroot;

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.generators.java.shared.AbstractObjectProjectionMaker;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.MethodProjection;
import io.polygenesis.generators.java.shared.ObjectProjection;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.domain.Primitive;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Aggregate root projection converter.
 *
 * @author Christos Tsakostas
 */
public class AggregateRootProjectionConverter extends AbstractObjectProjectionMaker
    implements Converter<AggregateRoot, ObjectProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Aggregate root projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AggregateRootProjectionConverter(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection convert(AggregateRoot aggregateRoot, Object... arg) {
    PackageName rootPackageName = (PackageName) arg[0];

    return new ObjectProjection(
        aggregateRoot.getPackageName().getText(),
        projectImports(aggregateRoot, rootPackageName),
        projectDescription(aggregateRoot),
        projectObjectName(aggregateRoot),
        projectObjectNameWithOptionalExtendsImplements(aggregateRoot),
        projectVariables(aggregateRoot),
        projectConstructors(aggregateRoot),
        projectMethods());
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Project imports set.
   *
   * @param aggregateRoot the aggregate root
   * @param rootPackageName the root package name
   * @return the set
   */
  protected Set<String> projectImports(AggregateRoot aggregateRoot, PackageName rootPackageName) {
    Set<String> imports = new TreeSet<>();

    aggregateRoot
        .getProperties()
        .forEach(
            property -> {
              Optional<IoModelGroup> optionalIoModelGroup = property.getIoModelGroupAsOptional();
              if (optionalIoModelGroup.isPresent()) {
                IoModelGroup ioModelGroup = optionalIoModelGroup.get();
                imports.add(
                    ioModelGroup.getClassDataType().getOptionalPackageName().get().getText()
                        + "."
                        + TextConverter.toUpperCamel(
                            ioModelGroup.getDataType().getDataTypeName().getText()));
              }
            });

    // Additional imports
    imports.add("com.oregor.ddd4j.check.assertion.Assertion");
    imports.add("com.oregor.ddd4j.core.AggregateRoot");
    imports.add("javax.persistence.Entity");
    imports.add("javax.persistence.Table");
    imports.add(rootPackageName.getText() + ".Constants");

    return imports;
  }

  /**
   * Project description string.
   *
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String projectDescription(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(TextConverter.toUpperCamelSpaces(aggregateRoot.getName().getText()));

    stringBuilder.append(" Aggregate Root.");

    return stringBuilder.toString();
  }

  /**
   * Project object name string.
   *
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String projectObjectName(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getName().getText()));

    return stringBuilder.toString();
  }

  /**
   * Project object name with optional extends implements string.
   *
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String projectObjectNameWithOptionalExtendsImplements(AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getName().getText()));
    stringBuilder.append(" extends ");
    stringBuilder.append("AggregateRoot<BusinessId>");

    return stringBuilder.toString();
  }

  /**
   * Project variables set.
   *
   * @param aggregateRoot the aggregate root
   * @return the set
   */
  protected Set<KeyValue> projectVariables(AggregateRoot aggregateRoot) {
    Set<KeyValue> variables = new LinkedHashSet<>();

    aggregateRoot
        .getProperties()
        .forEach(
            property -> {
              Optional<IoModelGroup> optionalIoModelGroup = property.getIoModelGroupAsOptional();
              if (optionalIoModelGroup.isPresent()) {
                IoModelGroup ioModelGroup = optionalIoModelGroup.get();
                variables.add(
                    new KeyValue(
                        fromDataTypeToJavaConverter.getDeclaredVariableType(ioModelGroup),
                        ioModelGroup.getVariableName().getText()));
              } else {
                if (property instanceof Primitive) {
                  Primitive primitive = (Primitive) property;
                  variables.add(
                      new KeyValue(
                          fromDataTypeToJavaConverter.getDeclaredVariableType(
                              primitive.getIoModelPrimitive()),
                          primitive.getVariableName().getText()));
                } else {
                  throw new IllegalStateException();
                }
              }
            });

    return variables;
  }

  protected Set<ConstructorProjection> projectConstructors(AggregateRoot aggregateRoot) {
    // TODO
    return new LinkedHashSet<>(
        Arrays.asList(new ConstructorProjection(projectVariables(aggregateRoot), "")));
  }

  /**
   * Project methods set.
   *
   * @return the set
   */
  protected Set<MethodProjection> projectMethods() {
    // TODO
    return new LinkedHashSet<>();
  }
}
