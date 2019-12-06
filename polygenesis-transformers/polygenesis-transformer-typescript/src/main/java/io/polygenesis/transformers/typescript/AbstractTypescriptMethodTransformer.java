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

package io.polygenesis.transformers.typescript;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.ActivityRegistry;
import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.generators.java.shared.transformer.MethodTransformer;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The type Abstract typescript method transformer.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractTypescriptMethodTransformer<S extends FunctionProvider>
    extends AbstractTypescriptTransformer implements MethodTransformer<S> {

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

  /** The Activity registry. */
  protected final ActivityRegistry<S> activityRegistry;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract typescript method transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param activityRegistry the activity registry
   */
  public AbstractTypescriptMethodTransformer(
      DataTypeTransformer dataTypeTransformer, ActivityRegistry<S> activityRegistry) {
    super(dataTypeTransformer);
    this.activityRegistry = activityRegistry;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  @SuppressWarnings("CPD-START")
  public MethodRepresentation create(S source, Object... args) {
    return new MethodRepresentation(
        methodType(source, args),
        imports(source, args),
        annotations(source, args),
        description(source, args),
        modifiers(source, args),
        methodName(source, args),
        parameterRepresentations(source, args),
        returnValue(source, args),
        implementation(source, args),
        thrownExceptions(source, args));
  }

  @Override
  public MethodRepresentationType methodType(S source, Object... args) {
    return MethodRepresentationType.ANY;
  }

  @Override
  public Set<String> imports(S source, Object... args) {
    Set<String> imports = new TreeSet<>();

    if (source.getFunction().getReturnValue() != null
        && source.getFunction().getReturnValue().isDataGroup()) {
      DataObject dataObject = source.getFunction().getReturnValue().getAsDataObject();

      // TODO
      //            if (!dataObject.getPackageName().equals(source.getPackageName())) {
      imports.add(makeCanonicalObjectName(dataObject.getPackageName(), dataObject.getDataType()));
      //            }
    }

    source
        .getFunction()
        .getArguments()
        .getData()
        .stream()
        .filter(argument -> argument.isDataGroup())
        .map(DataObject.class::cast)
        .forEach(
            dataGroup -> {
              // TODO
              //              if (!dataGroup.getPackageName().equals(source.getPackageName())) {
              imports.add(
                  makeCanonicalObjectName(dataGroup.getPackageName(), dataGroup.getDataType()));
              //              }
            });

    return imports;
  }

  @Override
  public Set<String> annotations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(S source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(source.getFunction().getName().getText()));
    stringBuilder.append(".");

    return stringBuilder.toString();
  }

  @Override
  public String modifiers(S source, Object... args) {
    return "";
  }

  @Override
  public String methodName(S source, Object... args) {
    return source.getFunction().getName().getText();
  }

  @Override
  public Set<ParameterRepresentation> parameterRepresentations(S source, Object... args) {
    Set<ParameterRepresentation> parameterRepresentations = new LinkedHashSet<>();

    source
        .getFunction()
        .getArguments()
        .getData()
        .forEach(
            argument ->
                parameterRepresentations.add(
                    new ParameterRepresentation(
                        dataTypeTransformer.convert(argument.getDataType()),
                        argument.getVariableName().getText())));

    return parameterRepresentations;
  }

  @Override
  public String returnValue(S source, Object... args) {
    if (source.getFunction().getReturnValue() != null) {
      return makeVariableDataType(source.getFunction().getReturnValue());
    } else {
      return dataTypeTransformer.convert(PrimitiveType.VOID.name());
    }
  }

  @Override
  public String implementation(S source, Object... args) {
    if (activityRegistry.isActivitySupportedFor(source)) {
      return activityRegistry.activityFor(source, args);
    } else {
      if (source.getFunction().getReturnValue() == null) {
        return "\t\t// TODO: implementation";
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t\t// TODO: implementation\n");
        stringBuilder.append("\t\treturn null;");
        return stringBuilder.toString();
      }
    }
  }

  @Override
  public Set<String> thrownExceptions(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Make canonical object name string.
   *
   * @param packageName the package name
   * @param dataType the data type
   * @return the string
   */
  protected String makeCanonicalObjectName(PackageName packageName, String dataType) {
    return packageName.getText() + "." + TextConverter.toUpperCamel(dataType);
  }

  /**
   * Gets parameters comma separated.
   *
   * @param parameterRepresentations the parameter representations
   * @return the parameters comma separated
   */
  @SuppressWarnings("CPD-END")
  protected String getParametersCommaSeparated(
      Set<ParameterRepresentation> parameterRepresentations) {
    return parameterRepresentations
        .stream()
        .map(ParameterRepresentation::getVariableName)
        .collect(Collectors.joining(", "));
  }
}
