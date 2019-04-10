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

package io.polygenesis.representations.java;

import static java.util.stream.Collectors.toCollection;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.representations.commons.FieldRepresentation;
import io.polygenesis.representations.commons.ParameterRepresentation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Abstract class representable.
 *
 * @param <S> the type parameter
 * @author Christos Tsakostas
 */
public abstract class AbstractClassRepresentable<S> extends AbstractRepresentable
    implements ClassRepresentable<S> {

  /** The constant MODIFIER_ABSTRACT. */
  protected static final String MODIFIER_ABSTRACT = "abstract";
  /** The constant MODIFIER_PUBLIC. */
  protected static final String MODIFIER_PUBLIC = "public";

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Abstract class representable.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public AbstractClassRepresentable(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
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
        fieldRepresentations(source, args),
        constructorRepresentations(source, args),
        methodRepresentations(source, args));
  }

  // ===============================================================================================
  // PROTECTED
  // ===============================================================================================

  /**
   * Package name string.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String packageName(DataGroup modelGroup) {
    return modelGroup.getPackageName().getText();
  }

  /**
   * Imports set.
   *
   * @param modelGroup the model group
   * @return the set
   */
  protected Set<String> imports(DataGroup modelGroup) {
    Set<String> imports = new LinkedHashSet<>();

    modelGroup
        .getModels()
        .stream()
        .filter(model -> model.isDataGroup())
        .map(DataGroup.class::cast)
        .filter(model -> !model.getPackageName().equals(modelGroup.getPackageName()))
        .forEach(
            model -> {
              imports.add(makeCanonicalObjectName(model.getPackageName(), model.getDataType()));
            });

    return imports;
  }

  /**
   * Simple object name string.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String simpleObjectName(DataGroup modelGroup) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(TextConverter.toUpperCamel(modelGroup.getDataType()));

    return stringBuilder.toString();
  }

  /**
   * Full object name string.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String fullObjectName(DataGroup modelGroup) {
    return simpleObjectName(modelGroup);
  }

  /**
   * Field representations set.
   *
   * @param modelGroup the model group
   * @return the set
   */
  protected Set<FieldRepresentation> fieldRepresentations(DataGroup modelGroup) {
    Set<FieldRepresentation> variables = new LinkedHashSet<>();

    modelGroup
        .getModels()
        .forEach(
            model -> {
              variables.add(
                  new FieldRepresentation(
                      fromDataTypeToJavaConverter.getDeclaredVariableType(model.getDataType()),
                      model.getVariableName().getText()));
            });

    return variables;
  }

  /**
   * Create empty constructor with implementation constructor representation.
   *
   * @param dataType the data type
   * @param annotations the annotations
   * @param implementation the implementation
   * @return the constructor representation
   */
  protected ConstructorRepresentation createEmptyConstructorWithImplementation(
      String dataType, Set<String> annotations, String implementation) {
    String description =
        String.format("Instantiates a new %s.", TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        annotations, description, "public", new LinkedHashSet<>(), implementation);
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
        String.format("Instantiates a new %s.", TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(), description, "public", parameterRepresentations, implementation);
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
        String.format("Instantiates a new %s.", TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(),
        description,
        "public",
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
        String.format("Instantiates a new %s.", TextConverter.toUpperCamelSpaces(dataType));

    return new ConstructorRepresentation(
        new LinkedHashSet<>(),
        description,
        "public",
        parameterRepresentations,
        constructorImplementationWithDirectAssignment(parameterRepresentations));
  }

  /**
   * Create no args constructor for persistence constructor representation.
   *
   * @return the constructor representation
   */
  protected ConstructorRepresentation createNoArgsConstructorForPersistence() {
    String description = "No-args constructor for persistence frameworks.";

    return new ConstructorRepresentation(
        new LinkedHashSet<>(), description, "protected", new LinkedHashSet<>(), "\t\tsuper();");
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

    fieldRepresentations
        .stream()
        .limit(fieldRepresentations.size() - 1)
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
            .skip(fieldRepresentations.size() - 1)
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
        "public",
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
        modifiers = "public";
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
  private Set<ParameterRepresentation> convertFieldRepresentationsToParameterRepresentations(
      Set<FieldRepresentation> fieldRepresentations) {
    return fieldRepresentations
        .stream()
        .map(
            fieldRepresentation ->
                new ParameterRepresentation(
                    fieldRepresentation.getDataType(),
                    fieldRepresentation.getVariableName(),
                    fieldRepresentation.getAnnotations(),
                    fieldRepresentation.getThingIdentity()))
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
          if (parameterRepresentation.getThingIdentity()) {
            stringBuilder.append("\t\t");
            stringBuilder.append("super(");
            stringBuilder.append(
                TextConverter.toLowerCamel(parameterRepresentation.getVariableName()));
            stringBuilder.append(");");
            if (parameterRepresentations.size() > 1) {
              stringBuilder.append("\n");
            }
          } else {
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

    return stringBuilder.toString();
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

    parameterRepresentations.forEach(
        parameterRepresentation -> {
          if (parameterRepresentation.getThingIdentity()) {
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

    return stringBuilder.toString();
  }
}
