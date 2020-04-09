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

package io.polygenesis.craftsman.genesis;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;

public class Shared {

  /**
   * Name data group.
   *
   * @param rootPackageName the root package name
   * @return the data group
   */
  public static DataObject name(String rootPackageName) {
    DataObject dataObject =
        new DataObject(
            new ObjectName("name"), new PackageName(String.format("%s.shared", rootPackageName)));

    dataObject.addData(DataBuilder.create().withTextProperty("text").build().build());

    return dataObject;
  }

  /**
   * Money data group.
   *
   * @param rootPackageName the root package name
   * @return the data group
   */
  public static DataObject money(String rootPackageName) {
    DataObject dataObject =
        new DataObject(
            new ObjectName("money"), new PackageName(String.format("%s.shared", rootPackageName)));

    dataObject.addData(
        DataBuilder.create()
            .withDecimalProperty("amount")
            .build()
            .withTextProperty("currency")
            .build()
            .build());

    return dataObject;
  }
}
