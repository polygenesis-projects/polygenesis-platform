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

package io.polygenesis.core.dsl;

import io.polygenesis.core.data.IoModel;
import io.polygenesis.core.data.IoModelGroup;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.data.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Data model builder.
 *
 * @author Christos Tsakostas
 */
public class DataGroupBuilder {

  private final String name;
  private final Set<IoModel> models;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private DataGroupBuilder(String name) {
    this.name = name;
    this.models = new LinkedHashSet<>();
  }

  // ===============================================================================================
  // START
  // ===============================================================================================

  /**
   * Create group data builder.
   *
   * @param groupName the group name
   * @return the data builder
   */
  public static DataGroupBuilder create(String groupName) {
    return new DataGroupBuilder(groupName);
  }

  // ===============================================================================================
  // WITH
  // ===============================================================================================

  /**
   * With group data data group builder.
   *
   * @param models the models
   * @return the data group builder
   */
  public final DataGroupBuilder withGroupData(Set<IoModel> models) {
    this.models.addAll(models);
    return this;
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Build io model group.
   *
   * @return the io model group
   */
  public final IoModelGroup build() {
    IoModelGroup ioModelGroup =
        new IoModelGroup(new ObjectName(name), new PackageName("com.oregor"));

    models.forEach(
        model -> {
          ioModelGroup.addIoModel(model);
        });

    return ioModelGroup;
  }
}
