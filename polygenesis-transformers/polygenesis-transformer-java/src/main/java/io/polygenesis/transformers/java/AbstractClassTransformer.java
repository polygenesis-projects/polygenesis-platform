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

package io.polygenesis.transformers.java;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.thing.FunctionProvider;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.DataTypeTransformer;
import io.polygenesis.core.Nameable;
import io.polygenesis.generators.java.shared.transformer.ClassTransformer;
import io.polygenesis.generators.java.shared.transformer.MethodTransformer;
import io.polygenesis.representations.code.ClassRepresentation;
import io.polygenesis.representations.code.ConstructorRepresentation;
import io.polygenesis.representations.code.FieldRepresentation;
import io.polygenesis.representations.code.MethodRepresentation;
import io.polygenesis.representations.code.MethodRepresentationType;
import io.polygenesis.representations.code.ParameterRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Abstract class representable.
 *
 * @param <S> the type parameter
 * @param <F> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractClassTransformer<S extends Nameable, F extends FunctionProvider>
    extends AbstractTransformer implements ClassTransformer<S> {

  private static final String INSTANTIATES_A_NEW_S = "Instantiates a new %s.";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  /** The Method transformer. */
  protected final MethodTransformer<F> methodTransformer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract new class transformer.
   *
   * @param dataTypeTransformer the data type transformer
   * @param methodTransformer the method transformer
   */
  public AbstractClassTransformer(
      DataTypeTransformer dataTypeTransformer, MethodTransformer<F> methodTransformer) {
    super(dataTypeTransformer);
    this.methodTransformer = methodTransformer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ClassRepresentation create(S source, Object... args) {
    return new ClassRepresentation(
        packageName(source, args),
        imports(source, args),
        annotations(source, args),
        description(source, args),
        modifiers(source, args),
        simpleObjectName(source, args),
        fullObjectName(source, args),
        staticFieldRepresentations(source, args),
        fieldRepresentations(source, args),
        constructorRepresentations(source, args),
        methodRepresentations(source, args));
  }

  @Override
  public Set<FieldRepresentation> staticFieldRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<FieldRepresentation> fieldRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<ConstructorRepresentation> constructorRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<MethodRepresentation> methodRepresentations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String packageName(S source, Object... args) {
    return "";
  }

  @Override
  public Set<String> imports(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public Set<String> annotations(S source, Object... args) {
    return new LinkedHashSet<>();
  }

  @Override
  public String description(S source, Object... args) {
    return String.format(
        "The %s type.", TextConverter.toUpperCamelSpaces(source.getObjectName().getText()));
  }

  @Override
  public String modifiers(S source, Object... args) {
    return dataTypeTransformer.getModifierPublic();
  }

  @Override
  public String simpleObjectName(S source, Object... args) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(source.getObjectName().getText()));

    return stringBuilder.toString();
  }

  @Override
  public String fullObjectName(S source, Object... args) {
    return simpleObjectName(source);
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Create empty constructor with implementation constructor representation.
   *
   * @param dataType the data type
   * @param annotations the annotations
   * @param modifiers the modifiers
   * @param implementation the implementation
   * @return the constructor representation
   */
  @SuppressWarnings("CPD-START")
  protected ConstructorRepresentation createEmptyConstructorWithImplementation(
      String dataType,
      Set<String> annotations,
      String modifiers,
      String implementation) {
    String description =
        String.format(INSTANTIATES_A_NEW_S, TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        annotations,
        description,
        modifiers,
        new LinkedHashSet<>(),
        implementation);
  }

  /**
   * Create constructor with implementation constructor representation.
   *
   * @param dataType the data type
   * @param parameterRepresentations the parameter representations
   * @param implementation the implementation
   * @return the constructor representation
   */
  protected ConstructorRepresentation createConstructorWithImplementation(
      String dataType,
      Set<ParameterRepresentation> parameterRepresentations,
      String implementation) {
    String description =
        String.format(INSTANTIATES_A_NEW_S, TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(),
        description,
        dataTypeTransformer.getModifierPublic(),
        parameterRepresentations,
        implementation);
  }

  /**
   * Create constructor constructor representation.
   *
   * @param dataType the data type
   * @param parameterRepresentations the parameter representations
   * @return the constructor representation
   */
  protected ConstructorRepresentation createConstructorWithSetters(
      String dataType, Set<ParameterRepresentation> parameterRepresentations) {
    String description =
        String.format(INSTANTIATES_A_NEW_S, TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(),
        description,
        dataTypeTransformer.getModifierPublic(),
        parameterRepresentations,
        constructorImplementationWithSetters(parameterRepresentations));
  }

  /**
   * Create constructor with direct assignment constructor representation.
   *
   * @param dataType the data type
   * @param parameterRepresentations the parameter representations
   * @return the constructor representation
   */
  protected ConstructorRepresentation createConstructorWithDirectAssignment(
      String dataType, Set<ParameterRepresentation> parameterRepresentations) {

    String description =
        String.format(INSTANTIATES_A_NEW_S, TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(),
        description,
        dataTypeTransformer.getModifierPublic(),
        parameterRepresentations,
        constructorImplementationWithDirectAssignment(parameterRepresentations));
  }

  /**
   * Create no args constructor for persistence constructor representation.
   *
   * @param modifier the modifier
   * @return the constructor representation
   */
  protected ConstructorRepresentation createNoArgsConstructorForPersistence(String modifier) {
    String description = "No-args constructor for reflections-based frameworks.";

    return new ConstructorRepresentation(
        new LinkedHashSet<>(), description, modifier, new LinkedHashSet<>(), "\t\tsuper();");
  }

  /**
   * Create constructor with setters from field representations constructor representation.
   *
   * @param dataType the data type
   * @param fieldRepresentations the field representations
   * @return the constructor representation
   */
  protected ConstructorRepresentation createConstructorWithSettersFromFieldRepresentations(
      String dataType, Set<FieldRepresentation> fieldRepresentations) {

    return createConstructorWithSetters(
        dataType, convertFieldRepresentationsToParameterRepresentations(fieldRepresentations));
  }

  /**
   * Create constructor with direct assignment from field representations constructor
   * representation.
   *
   * @param dataType the data type
   * @param fieldRepresentations the field representations
   * @return the constructor representation
   */
  protected ConstructorRepresentation createConstructorWithDirectAssignmentFromFieldRepresentations(
      String dataType, Set<FieldRepresentation> fieldRepresentations) {

    return createConstructorWithDirectAssignment(
        dataType, convertFieldRepresentationsToParameterRepresentations(fieldRepresentations));
  }

  /**
   * Method representations for getters and setters set.
   *
   * @param fieldRepresentations the field representations
   * @return the set
   */
  protected Set<MethodRepresentation> methodRepresentationsForGettersAndSetters(
      Set<FieldRepresentation> fieldRepresentations) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    if (fieldRepresentations.isEmpty()) {
      return methodRepresentations;
    }

    fieldRepresentations
        .stream()
        .limit(fieldRepresentations.size() - 1L)
        .forEach(
            fieldRepresentation -> {
              methodRepresentations.add(
                  createGetterMethod(fieldRepresentation, new LinkedHashSet<>()));
              methodRepresentations.add(
                  createSetterMethod(fieldRepresentation, new LinkedHashSet<>()));
            });

    FieldRepresentation fieldRepresentationLast =
        fieldRepresentations
            .stream()
            .skip(fieldRepresentations.size() - 1L)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    methodRepresentations.add(createGetterMethod(fieldRepresentationLast, new LinkedHashSet<>()));
    methodRepresentations.add(
        createSetterMethod(
            fieldRepresentationLast,
            new LinkedHashSet<>(Arrays.asList("@SuppressWarnings(\"CPD-END\")"))));

    return methodRepresentations;
  }

  /**
   * Method representations for getters and guards set.
   *
   * @param fieldRepresentations the field representations
   * @return the set
   */
  protected Set<MethodRepresentation> methodRepresentationsForGettersAndGuards(
      Set<FieldRepresentation> fieldRepresentations) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    fieldRepresentations.forEach(
        fieldRepresentation -> {
          methodRepresentations.add(createGetterMethod(fieldRepresentation, new LinkedHashSet<>()));
          methodRepresentations.add(createGuardMethod(fieldRepresentation, new LinkedHashSet<>()));
        });

    return methodRepresentations;
  }

  /**
   * Method representations for getters set.
   *
   * @param fieldRepresentations the field representations
   * @return the set
   */
  protected Set<MethodRepresentation> methodRepresentationsForGetters(
      Set<FieldRepresentation> fieldRepresentations) {
    Set<MethodRepresentation> methodRepresentations = new LinkedHashSet<>();

    fieldRepresentations.forEach(
        fieldRepresentation -> {
          methodRepresentations.add(createGetterMethod(fieldRepresentation, new LinkedHashSet<>()));
        });

    return methodRepresentations;
  }

  /**
   * Create getter method representation.
   *
   * @param fieldRepresentation the field representation
   * @param annotations the annotations
   * @return the method representation
   */
  protected MethodRepresentation createGetterMethod(
      FieldRepresentation fieldRepresentation, Set<String> annotations) {
    return new MethodRepresentation(
        MethodRepresentationType.GETTER,
        new LinkedHashSet<>(),
        annotations,
        String.format(
            "Gets the %s.",
            TextConverter.toUpperCamelSpaces(fieldRepresentation.getVariableName())),
        dataTypeTransformer.getModifierPublic(),
        String.format("get%s", TextConverter.toUpperCamel(fieldRepresentation.getVariableName())),
        new LinkedHashSet<>(),
        fieldRepresentation.getDataType(),
        String.format("\t\treturn this.%s;", fieldRepresentation.getVariableName()));
  }

  /**
   * Create guard method method representation.
   *
   * @param fieldRepresentation the field representation
   * @param annotations the annotations
   * @return the method representation
   */
  protected MethodRepresentation createGuardMethod(
      FieldRepresentation fieldRepresentation, Set<String> annotations) {
    return createSetterOrGuardMethod(
        fieldRepresentation, MethodRepresentationType.GUARD, annotations);
  }

  /**
   * Create setter method method representation.
   *
   * @param fieldRepresentation the field representation
   * @param annotations the annotations
   * @return the method representation
   */
  protected MethodRepresentation createSetterMethod(
      FieldRepresentation fieldRepresentation, Set<String> annotations) {
    return createSetterOrGuardMethod(
        fieldRepresentation, MethodRepresentationType.SETTER, annotations);
  }

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

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Create setter or guard method method representation.
   *
   * @param fieldRepresentation the field representation
   * @param methodRepresentationType the method type
   * @param annotations the annotations
   * @return the method representation
   */
  private MethodRepresentation createSetterOrGuardMethod(
      FieldRepresentation fieldRepresentation,
      MethodRepresentationType methodRepresentationType,
      Set<String> annotations) {
    String modifiers;
    switch (methodRepresentationType) {
      case GUARD:
        modifiers = "private";
        break;
      case SETTER:
        modifiers = dataTypeTransformer.getModifierPublic();
        break;
      default:
        throw new IllegalArgumentException();
    }

    StringBuilder stringBuilder = new StringBuilder();

    if (methodRepresentationType.equals(MethodRepresentationType.GUARD)) {
      stringBuilder.append("\t\t");
      stringBuilder.append("Assertion.isNotNull(");
      stringBuilder.append(fieldRepresentation.getVariableName());
      stringBuilder.append(",");
      stringBuilder.append(" \"");
      stringBuilder.append(TextConverter.toUpperCamelSpaces(fieldRepresentation.getVariableName()));
      stringBuilder.append(" is required\"");
      stringBuilder.append(");");
      stringBuilder.append("\n");
    }

    stringBuilder.append("\t\t");
    stringBuilder.append("this.");
    stringBuilder.append(fieldRepresentation.getVariableName());
    stringBuilder.append(" = ");
    stringBuilder.append(fieldRepresentation.getVariableName());
    stringBuilder.append(";");

    Set<ParameterRepresentation> parameterRepresentations =
        new LinkedHashSet<>(
            Arrays.asList(
                new ParameterRepresentation(
                    fieldRepresentation.getDataType(), fieldRepresentation.getVariableName())));

    return new MethodRepresentation(
        methodRepresentationType,
        new LinkedHashSet<>(),
        annotations,
        String.format(
            "Sets the %s.",
            TextConverter.toUpperCamelSpaces(fieldRepresentation.getVariableName())),
        modifiers,
        String.format("set%s", TextConverter.toUpperCamel(fieldRepresentation.getVariableName())),
        parameterRepresentations,
        "void",
        stringBuilder.toString());
  }

  /**
   * Convert field representations to parameter representations set.
   *
   * @param fieldRepresentations the field representations
   * @return the set
   */
  protected Set<ParameterRepresentation> convertFieldRepresentationsToParameterRepresentations(
      Set<FieldRepresentation> fieldRepresentations) {
    return fieldRepresentations
        .stream()
        .map(
            fieldRepresentation ->
                new ParameterRepresentation(
                    fieldRepresentation.getDataType(),
                    fieldRepresentation.getVariableName(),
                    fieldRepresentation.getAnnotations(),
                    fieldRepresentation.getDataPurpose()))
        .collect(toCollection(LinkedHashSet::new));
  }

  /**
   * Constructor implementation with setters string.
   *
   * @param parameterRepresentations the parameter representations
   * @return the string
   */
  private String constructorImplementationWithSetters(
      Set<ParameterRepresentation> parameterRepresentations) {
    StringBuilder stringBuilder = new StringBuilder();

    parameterRepresentations.forEach(
        parameterRepresentation -> {
          if (parameterRepresentation.getDataPurpose().equals(DataPurpose.thingIdentity())) {
            stringBuilder.append("\t\t");
            stringBuilder.append("super(");
            stringBuilder.append(
                TextConverter.toLowerCamel(parameterRepresentation.getVariableName()));

            Optional<ParameterRepresentation> optionalTenantIdentity =
                parameterRepresentations
                    .stream()
                    .filter(
                        parameterRepresentation1 ->
                            parameterRepresentation1
                                .getDataPurpose()
                                .equals(DataPurpose.tenantIdentity()))
                    .findFirst();

            if (optionalTenantIdentity.isPresent()) {
              stringBuilder.append(", ");
              stringBuilder.append(
                  TextConverter.toLowerCamel(
                      optionalTenantIdentity
                          .orElseThrow(IllegalArgumentException::new)
                          .getVariableName()));
            }

            stringBuilder.append(");");
            if (parameterRepresentations.size() > 1) {
              stringBuilder.append("\n");
            }
          }

          if (!parameterRepresentation.getDataPurpose().equals(DataPurpose.thingIdentity())
              && !parameterRepresentation.getDataPurpose().equals(DataPurpose.tenantIdentity())) {
            stringBuilder.append("\t\t");
            stringBuilder.append("set");
            stringBuilder.append(
                TextConverter.toUpperCamel(parameterRepresentation.getVariableName()));
            stringBuilder.append("(");
            stringBuilder.append(
                TextConverter.toLowerCamel(parameterRepresentation.getVariableName()));
            stringBuilder.append(");");
            stringBuilder.append("\n");
          }
        });

    String impl = stringBuilder.toString();

    // Get rid of the last \n character
    if(impl.endsWith("\n")) {
      impl = impl.substring(0, impl.length() - 1);
    }

    return impl;
  }

  /**
   * Constructor implementation with direct assignment string.
   *
   * @param parameterRepresentations the parameter representations
   * @return the string
   */
  private String constructorImplementationWithDirectAssignment(
      Set<ParameterRepresentation> parameterRepresentations) {
    StringBuilder stringBuilder = new StringBuilder();

    String callSuperWithParameters = callSuperWithParameters(parameterRepresentations);
    if (!callSuperWithParameters.equals("")) {
      stringBuilder.append(String.format("\t\tsuper(%s);%n", callSuperWithParameters));
    }

    parameterRepresentations
        .stream()
        .filter(
            parameterRepresentation ->
                !parameterRepresentation.getDataPurpose().equals(DataPurpose.superclassParameter()))
        .forEach(
            parameterRepresentation -> {
              if (parameterRepresentation.getDataPurpose().equals(DataPurpose.thingIdentity())) {
                throw new UnsupportedOperationException();
              } else {
                stringBuilder.append("\t\t");
                stringBuilder.append("this.");
                stringBuilder.append(
                    TextConverter.toLowerCamel(parameterRepresentation.getVariableName()));
                stringBuilder.append(" = ");
                stringBuilder.append(
                    TextConverter.toLowerCamel(parameterRepresentation.getVariableName()));
                stringBuilder.append(";");
                stringBuilder.append("\n");
              }
            });

    String impl = stringBuilder.toString();

    // Get rid of the last \n character
    if(impl.endsWith("\n")) {
      impl = impl.substring(0, impl.length() - 1);
    }

    return impl;
  }

  /**
   * Call super with parameters string.
   *
   * @param parameterRepresentations the parameter representations
   * @return the string
   */
  @SuppressWarnings("CPD-END")
  private String callSuperWithParameters(Set<ParameterRepresentation> parameterRepresentations) {
    return parameterRepresentations
        .stream()
        .filter(
            parameterRepresentation ->
                parameterRepresentation.getDataPurpose().equals(DataPurpose.superclassParameter()))
        .map(ParameterRepresentation::getVariableName)
        .collect(Collectors.joining(", "));
  }
}
