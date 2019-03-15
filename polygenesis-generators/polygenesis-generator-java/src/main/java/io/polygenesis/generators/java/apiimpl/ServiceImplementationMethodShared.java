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

package io.polygenesis.generators.java.apiimpl;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.Argument;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.core.data.DataPrimitive;
import io.polygenesis.models.api.Method;
import io.polygenesis.models.domain.AggregateRoot;
import java.util.Optional;

/**
 * The type Service implementation method shared.
 *
 * @author Christos Tsakostas
 */
public abstract class ServiceImplementationMethodShared {

  /**
   * Restore aggregate root string.
   *
   * @param method the method
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String restoreAggregateRoot(Method method, AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append(" ");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append(" = ");
    stringBuilder.append(TextConverter.toLowerCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append("Persistence");
    stringBuilder.append(".restore(");
    stringBuilder.append(constructNewAggregateRootIdFromArgument(method, aggregateRoot));
    stringBuilder.append(").orElseThrow(() -> new IllegalArgumentException(\"Cannot restore ");
    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append("\"));");
    stringBuilder.append("\n");

    return stringBuilder.toString();
  }

  /**
   * Construct new aggregate root id from argument string.
   *
   * @param method the method
   * @param aggregateRoot the aggregate root
   * @return the string
   */
  protected String constructNewAggregateRootIdFromArgument(
      Method method, AggregateRoot aggregateRoot) {
    StringBuilder stringBuilder = new StringBuilder();

    Argument argument =
        method
            .getFunction()
            .getArguments()
            .stream()
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

    Optional<DataPrimitive> optionalDataPrimitive =
        method.getFunction().retrieveThingIdentityFromArgument(argument);

    if (!optionalDataPrimitive.isPresent()) {
      throw new IllegalStateException();
    }

    stringBuilder.append("new");
    stringBuilder.append(" ");
    stringBuilder.append(TextConverter.toUpperCamel(aggregateRoot.getObjectName().getText()));
    stringBuilder.append("Id");
    stringBuilder.append("(");
    stringBuilder.append("UUID.fromString(");
    stringBuilder.append(
        TextConverter.toLowerCamel(argument.getData().getVariableName().getText()));
    stringBuilder.append(".");
    stringBuilder.append("get");
    stringBuilder.append(
        TextConverter.toUpperCamel(optionalDataPrimitive.get().getVariableName().getText()));
    stringBuilder.append("()");
    stringBuilder.append(")"); // UUID.fromString

    if (aggregateRoot.getMultiTenant()) {
      stringBuilder.append(", UUID.fromString(");
      stringBuilder.append(
          TextConverter.toLowerCamel(argument.getData().getVariableName().getText()));
      stringBuilder.append(".");
      stringBuilder.append("getTenantId()");
      stringBuilder.append(")"); // UUID.fromString
    }

    stringBuilder.append(")");

    return stringBuilder.toString();
  }

  /**
   * Make return value string.
   *
   * @param method the method
   * @return the string
   */
  protected String makeReturnValue(Method method) {
    StringBuilder stringBuilder = new StringBuilder();

    ReturnValue returnValue = method.getFunction().getReturnValue();
    if (returnValue != null) {
      if (returnValue.getData().isDataGroup()) {
        stringBuilder.append(makeReturnValueForDataGroup(returnValue.getData().getAsDataGroup()));
      } else {
        throw new IllegalStateException(
            String.format(
                "Cannot make Return Value for function=%s",
                method.getFunction().getName().getText()));
      }
    }

    return stringBuilder.toString();
  }

  /**
   * Make return value for data group.
   *
   * @param modelGroup the model group
   * @return the string
   */
  protected String makeReturnValueForDataGroup(DataGroup modelGroup) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\t\t");
    stringBuilder.append(TextConverter.toUpperCamel(modelGroup.getDataType()));
    stringBuilder.append(" ");
    stringBuilder.append(TextConverter.toLowerCamel(modelGroup.getDataType()));
    stringBuilder.append(" = new ");
    stringBuilder.append(TextConverter.toUpperCamel(modelGroup.getDataType()));
    stringBuilder.append("();");
    stringBuilder.append("\n");

    stringBuilder.append("\t\t//TODO");
    stringBuilder.append("\n");

    stringBuilder.append("\t\t");
    stringBuilder.append("return");
    stringBuilder.append(" ");
    stringBuilder.append(TextConverter.toLowerCamel(modelGroup.getDataType()));
    stringBuilder.append(";");

    return stringBuilder.toString();
  }
}
