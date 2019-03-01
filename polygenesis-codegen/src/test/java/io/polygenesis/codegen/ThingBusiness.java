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
import io.polygenesis.core.datatype.ClassDataType;
import io.polygenesis.core.datatype.DataTypeName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.datatype.PrimitiveDataType;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.dsl.DataBuilder;
import io.polygenesis.core.dsl.ThingBuilder;
import io.polygenesis.core.iomodel.IoModel;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import java.util.LinkedHashSet;
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
        .build();
  }

  // ===============================================================================================
  // DATA FETCH ONE
  // ===============================================================================================

  private static Set<IoModel> fetchOneData() {
    Set<IoModel> models = new LinkedHashSet<>();

    // name
    models.add(
        IoModelPrimitive.of(new PrimitiveDataType(PrimitiveType.STRING), new VariableName("name")));

    // postal address
    // TODO
    // models.add(postalAddress());

    return models;
  }

  // ===============================================================================================
  // DATA FETCH COLLECTION
  // ===============================================================================================

  private static Set<IoModel> fetchCollectionData() {
    Set<IoModel> models = new LinkedHashSet<>();

    // name
    models.add(
        IoModelPrimitive.of(new PrimitiveDataType(PrimitiveType.STRING), new VariableName("name")));

    // postal address
    // TODO
    // models.add(postalAddress());

    return models;
  }

  // ===============================================================================================
  // DTOs
  // ===============================================================================================

  // ===============================================================================================
  // POSTAL ADDRESS
  // ===============================================================================================

  private static IoModelGroup postalAddress() {
    IoModelGroup postalAddress =
        new IoModelGroup(
            new ClassDataType(
                new DataTypeName("PostalAddressDto"),
                new PackageName("com.oregor.ddd4j.example.shared")));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.ofParent(
            postalAddress,
            new PrimitiveDataType(PrimitiveType.STRING),
            new VariableName("streetAddress1")));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.ofParent(
            postalAddress,
            new PrimitiveDataType(PrimitiveType.STRING),
            new VariableName("streetAddress2")));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.ofParent(
            postalAddress, new PrimitiveDataType(PrimitiveType.STRING), new VariableName("city")));

    return postalAddress;
  }
}
