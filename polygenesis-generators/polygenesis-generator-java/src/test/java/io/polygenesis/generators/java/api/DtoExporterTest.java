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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.polygenesis.annotations.core.CqsType;
import io.polygenesis.annotations.core.GoalType;
import io.polygenesis.commons.freemarker.FreemarkerService;
import io.polygenesis.core.Argument;
import io.polygenesis.core.Function;
import io.polygenesis.core.FunctionName;
import io.polygenesis.core.Goal;
import io.polygenesis.core.ReturnValue;
import io.polygenesis.core.Thing;
import io.polygenesis.core.ThingName;
import io.polygenesis.core.data.ObjectName;
import io.polygenesis.core.datatype.PackageName;
import io.polygenesis.core.datatype.PrimitiveType;
import io.polygenesis.core.iomodel.IoModelGroup;
import io.polygenesis.core.iomodel.IoModelPrimitive;
import io.polygenesis.core.iomodel.VariableName;
import io.polygenesis.models.api.Dto;
import io.polygenesis.models.api.DtoType;
import io.polygenesis.models.api.Method;
import io.polygenesis.models.api.Service;
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
    Set<Method> methods = new LinkedHashSet<>();

    IoModelGroup returnValueIoModelGroup =
        new IoModelGroup(
            new ObjectName("CreateBusinessResponse"),
            new PackageName("com.oregor.microservice.some.business"));
    ReturnValue createReturnValue = new ReturnValue(returnValueIoModelGroup);

    Set<Argument> createArguments = new LinkedHashSet<>();
    IoModelGroup argumentIoModelGroup =
        new IoModelGroup(
            new ObjectName("CreateBusinessRequest"),
            new PackageName("com.oregor.microservice.some.business"));

    // postal address
    argumentIoModelGroup.addIoModelGroup(postalAddress(argumentIoModelGroup));

    Argument argument = new Argument(argumentIoModelGroup);

    createArguments.add(argument);

    Method createMethod =
        new Method(
            makeFunctionCreate(),
            new Dto(DtoType.API_REQUEST, argument.getAsIoModelGroup()),
            new Dto(DtoType.API_RESPONSE, createReturnValue.getAsIoModelGroup()));

    methods.add(createMethod);

    Set<Dto> dtos = new LinkedHashSet<>();
    dtos.add(new Dto(DtoType.API_REQUEST, argument.getAsIoModelGroup()));
    dtos.add(new Dto(DtoType.API_RESPONSE, createReturnValue.getAsIoModelGroup()));

    return new Service(
        new PackageName("com.oregor"),
        new ServiceName("someServiceName"),
        methods,
        CqsType.COMMAND,
        thingName,
        dtos);
  }

  // postalAddress
  private IoModelGroup postalAddress(IoModelGroup parent) {
    IoModelGroup postalAddress =
        new IoModelGroup(
            new ObjectName("PostalAddressDto"),
            new PackageName("com.oregor.microservice.some.shared"));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.of(
            PrimitiveType.STRING,
            new VariableName("streetAddress1")));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.of(
            PrimitiveType.STRING,
            new VariableName("streetAddress2")));

    postalAddress.addIoModelPrimitive(
        IoModelPrimitive.of(PrimitiveType.STRING, new VariableName("city")));

    return postalAddress;
  }

  private Function makeFunctionCreate() {
    Thing thing = new Thing(new ThingName("customer"));
    ReturnValue returnValue =
        new ReturnValue(
            IoModelPrimitive.of(
                PrimitiveType.STRING, new VariableName("someRet")));
    return new Function(
        thing,
        new Goal(GoalType.CREATE),
        new FunctionName("create"),
        new LinkedHashSet<>(),
        returnValue);
  }
}
