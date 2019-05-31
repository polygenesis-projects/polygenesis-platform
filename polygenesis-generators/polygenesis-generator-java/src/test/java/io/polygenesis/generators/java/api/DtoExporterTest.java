/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.polygenesis.abstraction.data.DataGroup;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.VariableName;
import io.polygenesis.abstraction.thing.Argument;
import io.polygenesis.abstraction.thing.CqsType;
import io.polygenesis.abstraction.thing.Function;
import io.polygenesis.abstraction.thing.FunctionName;
import io.polygenesis.abstraction.thing.Purpose;
import io.polygenesis.abstraction.thing.ReturnValue;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingBuilder;
import io.polygenesis.abstraction.thing.ThingName;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.generators.java.exporters.api.DtoClassRepresentable;
import io.polygenesis.generators.java.exporters.api.DtoExporter;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.DtoType;
import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceMethod;
import io.polygenesis.models.api.ServiceName;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/** @author Christos Tsakostas */
public class DtoExporterTest {

  private Path generationPath;
  private Service service;
  private FreemarkerService freemarkerService;
  private DtoClassRepresentable dtoClassRepresentable;
  private DtoExporter dtoExporter;

  @Before
  public void setUp() {
    generationPath = Paths.get("tmp");
    service = makeService();
    freemarkerService = mock(FreemarkerService.class);
    dtoClassRepresentable = mock(DtoClassRepresentable.class);
    dtoExporter = new DtoExporter(freemarkerService, dtoClassRepresentable);
  }

  @Test
  public void shouldExport() {
    dtoExporter.export(generationPath, service);

    verify(dtoClassRepresentable, times(2)).create(any(Dto.class));

    // TODO
    //    verify(freemarkerService).export(
    //        any(HashMap.class),
    //        eq("polygenesis-generator-java/Class.java.ftl"),
    //
    // eq(Paths.get("tmp/src/main/java/com/oregor/microservice/some/business/CreateBusinessRequest.java")));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Service makeService() {
    ThingName thingName = new ThingName("someThingName");
    Set<ServiceMethod> serviceMethods = new LinkedHashSet<>();

    DataGroup returnValueDataGroup =
        new DataGroup(
            new ObjectName("CreateBusinessResponse"),
            new PackageName("com.oregor.microservice.some.business"));
    ReturnValue createReturnValue = new ReturnValue(returnValueDataGroup);

    Set<Argument> createArguments = new LinkedHashSet<>();
    DataGroup argumentDataGroup =
        new DataGroup(
            new ObjectName("CreateBusinessRequest"),
            new PackageName("com.oregor.microservice.some.business"));

    // postal address
    argumentDataGroup.addData(postalAddress());

    Argument argument = new Argument(argumentDataGroup);

    createArguments.add(argument);

    ServiceMethod createServiceMethod =
        new ServiceMethod(
            makeFunctionCreate(),
            new Dto(DtoType.API_REQUEST, argument.getData().getAsDataGroup(), false),
            new Dto(DtoType.API_RESPONSE, createReturnValue.getData().getAsDataGroup(), false));

    serviceMethods.add(createServiceMethod);

    Set<Dto> dtos = new LinkedHashSet<>();
    dtos.add(new Dto(DtoType.API_REQUEST, argument.getData().getAsDataGroup(), false));
    dtos.add(new Dto(DtoType.API_RESPONSE, createReturnValue.getData().getAsDataGroup(), false));

    return new Service(
        new PackageName("com.oregor"),
        new ServiceName("someServiceName"),
        serviceMethods,
        CqsType.COMMAND,
        thingName,
        dtos);
  }

  // postalAddress
  private DataGroup postalAddress() {
    DataGroup postalAddress =
        new DataGroup(
            new ObjectName("PostalAddressDto"),
            new PackageName("com.oregor.microservice.some.shared"));

    postalAddress.addData(
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("streetAddress1")));

    postalAddress.addData(
        DataPrimitive.of(PrimitiveType.STRING, new VariableName("streetAddress2")));

    postalAddress.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("city")));

    return postalAddress;
  }

  private Function makeFunctionCreate() {
    Thing thing = ThingBuilder.endToEnd().setThingName(new ThingName("customer")).createThing();
    ReturnValue returnValue =
        new ReturnValue(DataPrimitive.of(PrimitiveType.STRING, new VariableName("someRet")));
    return new Function(
        thing, Purpose.create(), new FunctionName("create"), new LinkedHashSet<>(), returnValue);
  }
}
