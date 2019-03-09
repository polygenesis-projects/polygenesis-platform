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

package io.polygenesis.generators.angular.reactivestate.action;

import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.FeatureName;
import io.polygenesis.models.reactivestate.ActionGroup;
import io.polygenesis.representations.typescript.FromDataTypeToTypescriptConverter;
import io.polygenesis.representations.typescript.action.ActionClass;
import io.polygenesis.representations.typescript.action.ActionEnumeration;
import io.polygenesis.representations.typescript.action.ActionGroupRepresentation;
import io.polygenesis.representations.typescript.action.ActionUnion;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Action group representable.
 *
 * @author Christos Tsakostas
 */
public class ActionGroupRepresentable {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FromDataTypeToTypescriptConverter fromDataTypeToTypescriptConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Action group representable.
   *
   * @param fromDataTypeToTypescriptConverter the from data type to typescript converter
   */
  public ActionGroupRepresentable(
      FromDataTypeToTypescriptConverter fromDataTypeToTypescriptConverter) {
    this.fromDataTypeToTypescriptConverter = fromDataTypeToTypescriptConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Create action group representation.
   *
   * @param featureName the feature name
   * @param actionGroup the action group
   * @return the action group representation
   */
  public ActionGroupRepresentation create(FeatureName featureName, ActionGroup actionGroup) {
    return new ActionGroupRepresentation(
        importObjects(actionGroup),
        actionEnumeration(featureName, actionGroup),
        actionClasses(featureName, actionGroup),
        actionUnion(featureName, actionGroup));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Map<Set<String>, String> importObjects(ActionGroup actionGroup) {
    Map<Set<String>, String> importObjects = new LinkedHashMap<>();

    importObjects.put(new LinkedHashSet<>(Arrays.asList("Action")), "@ngrx/store");

    actionGroup
        .getActions()
        .forEach(
            action -> {
              if (action.getPayloadModel().getModel().isIoModelGroup()) {
                importObjects.put(
                    new LinkedHashSet<>(
                        Arrays.asList(
                            TextConverter.toUpperCamel(
                                action.getPayloadModel().getModel().getDataType()))),
                    String.format(
                        "../models/%s.model",
                        TextConverter.toLowerHyphen(
                            action.getPayloadModel().getModel().getDataType())));
              }
            });

    return importObjects;
  }

  private ActionEnumeration actionEnumeration(FeatureName featureName, ActionGroup actionGroup) {
    return new ActionEnumeration(
        getEnumerationName(featureName), getEnumerationKeyValues(featureName, actionGroup));
  }

  private Set<ActionClass> actionClasses(FeatureName featureName, ActionGroup actionGroup) {
    Set<ActionClass> actionClasses = new LinkedHashSet<>();

    actionGroup
        .getActions()
        .forEach(
            action -> {
              actionClasses.add(
                  new ActionClass(
                      TextConverter.toUpperCamel(action.getName().getText()),
                      String.format(
                          "%s.%s",
                          getEnumerationName(featureName),
                          TextConverter.toUpperCamel(action.getName().getText())),
                      fromDataTypeToTypescriptConverter.getDeclaredVariableType(
                          action.getPayloadModel().getModel())));
            });

    return actionClasses;
  }

  private ActionUnion actionUnion(FeatureName featureName, ActionGroup actionGroup) {
    return new ActionUnion(
        String.format("%sApiActionsUnion", TextConverter.toUpperCamel(featureName.getText())),
        getEnumerationKeyValues(featureName, actionGroup));
  }

  private String getEnumerationName(FeatureName featureName) {
    return String.format("%sApiActionTypes", TextConverter.toUpperCamel(featureName.getText()));
  }

  private Set<KeyValue> getEnumerationKeyValues(FeatureName featureName, ActionGroup actionGroup) {
    Set<KeyValue> keyValues = new LinkedHashSet<>();

    actionGroup
        .getActions()
        .forEach(
            action -> {
              String text =
                  String.format("%s", TextConverter.toUpperCamel(action.getName().getText()));

              keyValues.add(
                  new KeyValue(
                      TextConverter.toUpperCamel(text),
                      String.format(
                          "[%s] %s",
                          TextConverter.toUpperCamelSpaces(featureName.getText()),
                          TextConverter.toUpperCamelSpaces(text))));
            });

    return keyValues;
  }
}
