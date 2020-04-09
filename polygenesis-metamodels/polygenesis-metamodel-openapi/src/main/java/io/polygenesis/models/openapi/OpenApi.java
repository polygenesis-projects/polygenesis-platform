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

package io.polygenesis.models.openapi;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.core.Generatable;
import io.polygenesis.core.Metamodel;
import io.polygenesis.models.openapi.info.Info;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class OpenApi implements Generatable, Metamodel {

  private ObjectName objectName;

  @NonNull private String openapi;
  @NonNull private Info info;

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public ObjectName getObjectName() {
    return null;
  }
}
