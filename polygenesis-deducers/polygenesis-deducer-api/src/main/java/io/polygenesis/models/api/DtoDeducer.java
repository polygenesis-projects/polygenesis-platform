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

package io.polygenesis.models.api;

import io.polygenesis.core.Function;
import io.polygenesis.core.iomodel.IoModelGroup;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Dto deducer.
 *
 * @author Christos Tsakostas
 */
public class DtoDeducer {

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param functions the functions
   * @return the set
   */
  public Set<Dto> deduce(Set<Function> functions) {
    Set<Dto> dtos = new LinkedHashSet<>();

    functions
        .stream()
        .forEach(
            function -> {
              if (function.getReturnValue().getModel().isIoModelGroup()) {
                addDto(dtos, (IoModelGroup) function.getReturnValue().getModel());
              }

              function
                  .getArguments()
                  .forEach(
                      argument -> {
                        if (argument.getModel().isIoModelGroup()) {
                          addDto(dtos, (IoModelGroup) argument.getModel());
                        }
                      });
            });

    return dtos;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Add dto.
   *
   * @param dtos the dtos
   * @param ioModelGroup the io model group
   */
  private void addDto(Set<Dto> dtos, IoModelGroup ioModelGroup) {
    dtos.add(new Dto(ioModelGroup));

    // Add model group children of ioModelGroup recursively
    ioModelGroup
        .getModels()
        .forEach(
            model -> {
              if (model.isIoModelGroup()) {
                addDto(dtos, (IoModelGroup) model);
              }
            });
  }
}
