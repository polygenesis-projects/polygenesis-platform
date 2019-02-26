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

package io.polygenesis.generators.java.api;

import io.polygenesis.core.converter.FromDataTypeToJavaConverter;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.generators.java.shared.AbstractObjectProjectionMaker;
import io.polygenesis.generators.java.shared.ConstructorProjection;
import io.polygenesis.generators.java.shared.ObjectProjection;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Dto object projection maker.
 *
 * @author Christos Tsakostas
 */
public class DtoObjectProjectionMaker extends AbstractObjectProjectionMaker {

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Dto object projection maker.
   *
   * @param fromDataTypeToJavaConverter the from data type to java converter
   */
  public DtoObjectProjectionMaker(FromDataTypeToJavaConverter fromDataTypeToJavaConverter) {
    super(fromDataTypeToJavaConverter);
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectProjection make(IoModelGroup modelGroup) {
    return new ObjectProjection(
        projectPackageName(modelGroup),
        projectImports(modelGroup),
        projectDescription(modelGroup),
        projectObjectName(modelGroup),
        projectObjectNameWithOptionalExtendsImplements(modelGroup),
        modelGroup,
        projectVariables(modelGroup),
        projectConstructors(modelGroup),
        projectMethods());
  }

  protected Set<ConstructorProjection> projectConstructors(IoModelGroup modelGroup) {
    return new LinkedHashSet<>(
        Arrays.asList(
            new ConstructorProjection(new LinkedHashSet<>(), "super();"),
            new ConstructorProjection(projectVariables(modelGroup), "")));
  }
}
