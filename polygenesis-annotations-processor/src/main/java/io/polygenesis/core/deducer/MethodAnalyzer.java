/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.core.deducer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The type Method analyzer.
 *
 * @author Christos Tsakostas
 */
public class MethodAnalyzer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Gets method output.
   *
   * @param method the method
   * @return the method output
   */
  public MethodOutputDescriptor getMethodOutput(Method method) {
    String genericType;
    String dataType = null;
    Class<?> clazz;

    Type genericReturnType = method.getGenericReturnType();
    clazz = method.getReturnType();

    genericType = clazz.getTypeName();

    if (genericReturnType instanceof ParameterizedType) {
      ParameterizedType paramType = (ParameterizedType) genericReturnType;
      Type[] argTypes = paramType.getActualTypeArguments();
      if (argTypes.length > 0) {
        dataType = argTypes[0].getTypeName();
        try {
          clazz = Class.forName(dataType);
        } catch (ClassNotFoundException e) {
          throw new IllegalStateException("Cannot create class forName");
        }
      }
    } else {
      genericType = null;
      dataType = clazz.getTypeName();
    }

    return new MethodOutputDescriptor(genericType, dataType, clazz);
  }

  /**
   * Gets method input.
   *
   * @param parameter the parameter
   * @return the method input
   */
  public MethodInputDescriptor getMethodInput(Parameter parameter) {
    return new MethodInputDescriptor(
        parameter.getType().getTypeName(), parameter.getName(), parameter.getType());
  }
}
