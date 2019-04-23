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

package io.polygenesis.core;

/**
 * Singletons of PolyGenesis Core Services and Components.
 *
 * @author Christos Tsakostas
 */
public final class CoreRegistry {

  private CoreRegistry() {
    throw new IllegalStateException("Utility class");
  }

  private static final MetamodelRepositoryResolver metamodelRepositoryResolver;

  static {
    metamodelRepositoryResolver = new MetamodelRepositoryResolver();
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets model repository resolver.
   *
   * @return the model repository resolver
   */
  public static MetamodelRepositoryResolver getMetamodelRepositoryResolver() {
    return metamodelRepositoryResolver;
  }
}
