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

package io.polygenesis.commons.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.commons.test.AbstractEqualityTest;
import java.nio.file.Paths;
import org.junit.Test;

/** @author Christos Tsakostas */
public class PackageNameTest extends AbstractEqualityTest<PackageName> {

  @Test
  public void shouldSucceedToInstantiate() {
    PackageName packageName = new PackageName("abc");

    assertThat(packageName.getText()).isEqualTo("abc");
    assertThat(packageName.toPath()).isEqualTo(Paths.get("abc"));
  }

  @Test
  public void shouldSucceedToInstantiateWithSubPackage() {
    PackageName packageName = new PackageName("abc");
    PackageName newPackageName = packageName.withSubPackage("sub");

    assertThat(newPackageName.getText()).isEqualTo("abc.sub");
    assertThat(newPackageName.toPath()).isEqualTo(Paths.get("abc", "sub"));
  }

  @Override
  public PackageName createObject1() {
    return new PackageName("xxx");
  }

  @Override
  public PackageName createObject2() {
    return new PackageName("yyy");
  }
}
