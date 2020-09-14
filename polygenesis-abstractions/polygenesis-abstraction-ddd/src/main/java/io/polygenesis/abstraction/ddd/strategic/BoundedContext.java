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

package io.polygenesis.abstraction.ddd.strategic;

import io.polygenesis.abstraction.ddd.tactical.AggregateRoot;
import io.polygenesis.abstraction.ddd.tactical.DomainService;
import io.polygenesis.commons.valueobjects.Name;
import io.polygenesis.commons.valueobjects.PackageName;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Bounded context.
 *
 * @author Christos Tsakostas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoundedContext {

  private Name name;
  private PackageName rootPackageName;
  private Set<AggregateRoot> aggregateRoots;
  private Set<DomainService> domainServices;
}
