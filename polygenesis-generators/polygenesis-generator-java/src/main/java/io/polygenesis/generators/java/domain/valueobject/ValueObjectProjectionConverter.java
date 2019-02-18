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

package io.polygenesis.generators.java.domain.valueobject;

import io.polygenesis.commons.converter.Converter;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.generators.java.shared.AbstractObjectProjectionMaker;
import io.polygenesis.generators.java.shared.ObjectProjection;
import io.polygenesis.models.domain.ValueObject;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Value object projection converter.
 *
 * @author Christos Tsakostas
 */
public class ValueObjectProjectionConverter extends AbstractObjectProjectionMaker
    implements Converter<ValueObject, ObjectProjection> {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Value object projection converter.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public ValueObjectProjectionConverter(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection convert(ValueObject source, Object... args) {
    return make(source.getIoModelGroup());
  }

  @Override
  protected Set<String> projectImports(IoModelGroup modelGroup) {
    return new LinkedHashSet<>(Arrays.asList("javax.persistence.Embeddable"));
  }

  @Override
  protected String projectDescription(IoModelGroup modelGroup) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("The ");

    stringBuilder.append(
        TextConverter.toUpperCamelSpaces(modelGroup.getDataType().getDataTypeName().getText()));

    stringBuilder.append(" Value Object.");

    return stringBuilder.toString();
  }
}
