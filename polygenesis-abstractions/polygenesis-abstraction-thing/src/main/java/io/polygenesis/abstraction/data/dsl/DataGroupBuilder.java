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

package io.polygenesis.abstraction.data.dsl;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.LinkedHashSet;
import java.util.Set;

public class DataGroupBuilder {

  private final String name;
  private final Set<Data> models;

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
  public final DataGroupBuilder withGroupData(Set<Data> models) {
    this.models.addAll(models);
    return this;
  }

  // ===============================================================================================
  // END
  // ===============================================================================================

  /**
   * Build data group.
   *
   * @return the data group
   */
  public final DataObject build() {
    DataObject dataObject = new DataObject(new ObjectName(name), new PackageName("com.oregor"));

    models.forEach(dataObject::addData);

    return dataObject;
  }
}
