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

package io.polygenesis.core.data;

/**
 * Encodes business types, which are vital for the code generation process, such as unique ID of an
 * entity, page size etc.
 *
 * @author Christos Tsakostas
 */
public enum DataBusinessType {
  /** Any type of business data, which is not directly relevant to the code generation process. */
  ANY,
  /** Parent thing identity data business type. */
  PARENT_THING_IDENTITY,
  /** Denotes a Thing's identity. */
  THING_IDENTITY,
  /** Denotes a Tenant's identity. */
  TENANT_IDENTITY,
  /** Page number used for fetching collections. */
  PAGE_NUMBER,
  /** Page size used for fetching collections. */
  PAGE_SIZE;
}
