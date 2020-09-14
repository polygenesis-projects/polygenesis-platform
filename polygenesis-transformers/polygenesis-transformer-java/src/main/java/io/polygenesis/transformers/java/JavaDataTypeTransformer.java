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

package io.polygenesis.transformers.java;

import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.DataTypeTransformer;
import java.util.EnumMap;
import java.util.stream.Stream;

public class JavaDataTypeTransformer implements DataTypeTransformer {

  private static final String MODIFIER_FINAL = "final";
  private static final String MODIFIER_PUBLIC = "public";
  private static final String MODIFIER_PROTECTED = "protected";
  private static final String MODIFIER_PRIVATE = "private";
  private static final String MODIFIER_ABSTRACT = "abstract";
  private static final String VOID = "void";

  // ===============================================================================================
  // STATIC
  // ===============================================================================================

  private static EnumMap<PrimitiveType, String> dataTypeMap;

  static {
    initialize();
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public String convert(String dataType) {
    String candidate = TextConverter.toUpperCamel(dataType);

    String[] parts = candidate.split(";");
    if (parts.length > 1) {
      return getArrayOfElements(parts[1]);
    }

    return Stream.of(PrimitiveType.values())
        .filter(value -> value.name().equalsIgnoreCase(candidate))
        .findFirst()
        .map(primaryType -> dataTypeMap.get(primaryType))
        .orElse(candidate);
  }

  @Override
  public String getModifierPublic() {
    return MODIFIER_PUBLIC;
  }

  @Override
  public String getModifierProtected() {
    return MODIFIER_PROTECTED;
  }

  @Override
  public String getModifierPrivate() {
    return MODIFIER_PRIVATE;
  }

  @Override
  public String getModifierPrivateCommentedOut() {
    return String.format("// TODO: %s", MODIFIER_PRIVATE);
  }

  @Override
  public String getModifierPrivateFinal() {
    return MODIFIER_PRIVATE + " " + MODIFIER_FINAL;
  }

  @Override
  public String getModifierAbstract() {
    return MODIFIER_ABSTRACT;
  }

  @Override
  public String getVoid() {
    return VOID;
  }

  @Override
  public String getArrayOfElements(String elementDataType) {
    return String.format("%s[]", elementDataType);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static void initialize() {
    dataTypeMap = new EnumMap<>(PrimitiveType.class);

    dataTypeMap.put(PrimitiveType.VOID, "void");
    dataTypeMap.put(PrimitiveType.STRING, "String");
    dataTypeMap.put(PrimitiveType.INTEGER, "Integer");
    dataTypeMap.put(PrimitiveType.LONG, "Long");
    dataTypeMap.put(PrimitiveType.BOOLEAN, "Boolean");
    dataTypeMap.put(PrimitiveType.DATE, "LocalDate");
    dataTypeMap.put(PrimitiveType.DATETIME, "LocalDateTime");
    dataTypeMap.put(PrimitiveType.DECIMAL, "BigDecimal");
    dataTypeMap.put(PrimitiveType.URI, "String");
    dataTypeMap.put(PrimitiveType.URL, "String");
    dataTypeMap.put(PrimitiveType.UUID, "UUID");
  }
}
