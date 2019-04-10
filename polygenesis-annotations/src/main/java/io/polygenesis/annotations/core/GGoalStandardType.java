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

package io.polygenesis.annotations.core;

/**
 * The suggested way to define a goal type is by using one of the following constants. For
 * convenience, the available types are grouped into Commands and Queries prefixed by CMD and QRY
 * respectively.
 *
 * <p>Custom goal types can also be defined, but you should also provide the required deducers, or
 * manually instantiate the technology models as they will not be identified by PolyGenesis.
 *
 * @author Christos Tsakostas
 */
public final class GGoalStandardType {

  private GGoalStandardType() {
    throw new IllegalStateException("Utility class");
  }

  /** The constant CMD_CREATE. */
  public static final String CMD_CREATE = "CREATE";
  /** The constant CMD_UPDATE. */
  public static final String CMD_UPDATE = "UPDATE";
  /** The constant CMD_DELETE. */
  public static final String CMD_DELETE = "DELETE";
  /** The constant CMD_RESET. */
  public static final String CMD_RESET = "RESET";

  /** The constant QRY_DETAIL. */
  public static final String QRY_DETAIL = "DETAIL";
  /** The constant QRY_COLLECTION. */
  public static final String QRY_COLLECTION = "COLLECTION";

  /** The constant QRY_VALIDATION. */
  public static final String QRY_VALIDATION = "VALIDATION";
  /** The constant QRY_CALCULATION. */
  public static final String QRY_CALCULATION = "CALCULATION";
}
