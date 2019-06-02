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

package io.polygenesis.models.apiimpl;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import org.junit.Test;

/** @author Christos Tsakostas */
public class ServiceDependencyTest extends AbstractEqualityTest<ServiceDependency> {

  @Test
  public void shouldInstantiate() {
    ServiceDependency serviceDependency = createObject1();

    assertThat(serviceDependency.getObjectName()).isEqualTo(new ObjectName("objectName1"));
    assertThat(serviceDependency.getPackageName()).isEqualTo(new PackageName("com.oregor"));
    assertThat(serviceDependency.getVariableName()).isEqualTo(new VariableName("var1"));
  }

  @Override
  public ServiceDependency createObject1() {
    return new ServiceDependency(
        new ObjectName("objectName1"), new PackageName("com.oregor"), new VariableName("var1"));
  }

  @Override
  public ServiceDependency createObject2() {
    return new ServiceDependency(
        new ObjectName("objectName2"), new PackageName("com.oregor"), new VariableName("var1"));
  }
}
