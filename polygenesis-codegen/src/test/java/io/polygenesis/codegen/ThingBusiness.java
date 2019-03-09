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

package io.polygenesis.codegen;

import io.polygenesis.core.Thing;
import io.polygenesis.core.data.IoModel;
import io.polygenesis.core.data.IoModelArray;
import io.polygenesis.core.data.IoModelGroup;
import io.polygenesis.core.data.IoModelPrimitive;
import io.polygenesis.core.data.PrimitiveType;
import io.polygenesis.core.data.VariableName;
import io.polygenesis.core.dsl.DataArrayBuilder;
import io.polygenesis.core.dsl.DataBuilder;
import io.polygenesis.core.dsl.DataGroupBuilder;
import io.polygenesis.core.dsl.ThingBuilder;
import java.util.Set;

/** @author Christos Tsakostas */
public class ThingBusiness {

  public static Thing create() {
    Thing business =
        ThingBuilder.createMultiTenant("business", OregorDdd4jExampleGenesisTest.JAVA_ROOT_PACKAGE)
            .withFunctionCreate(createData())
            .withFunctionFetchOne(fetchOneData())
            .withFunctionFetchPagedCollection(fetchCollectionData())
            .get();

    return business;
  }

  // ===============================================================================================
  // DATA CREATE
  // ===============================================================================================

  private static Set<IoModel> createData() {

    return DataBuilder.create()
        .withTextProperty("name")
        .build()
        .withTextProperty("taxId")
        .build()
        .withGroupData(postalAddress())
        .withArrayData(emailAddresses())
        .build();
  }

  // ===============================================================================================
  // DATA FETCH ONE
  // ===============================================================================================

  private static Set<IoModel> fetchOneData() {
    return DataBuilder.create()
        .withTextProperty("name")
        .build()
        .withTextProperty("taxId")
        .build()
        .build();
  }

  // ===============================================================================================
  // DATA FETCH COLLECTION
  // ===============================================================================================

  private static Set<IoModel> fetchCollectionData() {
    return DataBuilder.create()
        .withTextProperty("name")
        .build()
        .withTextProperty("taxId")
        .build()
        .build();
  }

  // ===============================================================================================
  // INNER DTOs
  // ===============================================================================================
  private static IoModelGroup postalAddress() {

    return DataGroupBuilder.create("postalAddress")
        .withGroupData(
            DataBuilder.create()
                .withTextProperty("streetAddress1")
                .build()
                .withTextProperty("streetAddress2")
                .build()
                .withTextProperty("city")
                .build()
                .build())
        .build();
  }

  private static IoModelArray emailAddresses() {
    IoModel arrayElement =
        IoModelPrimitive.of(PrimitiveType.STRING, new VariableName("emailAddress"));

    return DataArrayBuilder.create("emailAddresses", arrayElement).build();
  }
}
