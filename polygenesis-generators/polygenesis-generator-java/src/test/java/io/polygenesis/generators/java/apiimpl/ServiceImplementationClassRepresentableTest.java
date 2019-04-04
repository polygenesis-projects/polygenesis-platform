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

package io.polygenesis.generators.java.apiimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import io.polygenesis.models.api.Service;
import io.polygenesis.models.api.ServiceName;
import io.polygenesis.models.apiimpl.ServiceImplementation;
import io.polygenesis.representations.java.FromDataTypeToJavaConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Api impl service projection maker test.
 *
 * @author Christos Tsakostas
 */
public class ServiceImplementationClassRepresentableTest {

  private FromDataTypeToJavaConverter fromDataTypeToJavaConverter;
  private ServiceMethodImplementationRepresentable methodProjectionMaker;
  private ServiceImplementationClassRepresentable serviceImplementationClassRepresentable;

  /** Sets up. */
  @Before
  public void setUp() {
    fromDataTypeToJavaConverter = mock(FromDataTypeToJavaConverter.class);
    methodProjectionMaker = mock(ServiceMethodImplementationRepresentable.class);
    serviceImplementationClassRepresentable =
        new ServiceImplementationClassRepresentable(
            fromDataTypeToJavaConverter, methodProjectionMaker);
  }

  /** Should successfully project object name with optional extends implements. */
  @Test
  public void shouldSuccessfullyProjectObjectNameWithOptionalExtendsImplements() {
    Service service = mock(Service.class);
    ServiceImplementation serviceImplementation = mock(ServiceImplementation.class);
    ServiceName serviceName = new ServiceName("someServiceName");

    given(service.getServiceName()).willReturn(serviceName);
    given(serviceImplementation.getService()).willReturn(service);

    String fullObjectName =
        serviceImplementationClassRepresentable.fullObjectName(serviceImplementation);

    assertThat(fullObjectName).isEqualTo("SomeServiceNameImpl implements SomeServiceName");
  }
}
