/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis System
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

package io.polygenesis.annotations.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for API methods.
 *
 * @author Christos Tsakostas
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GGoal {

  /**
   * Name.
   *
   * @return the string
   */
  String name() default "";

  /**
   * The goal type of a method. e.g. create, collection, etc.
   *
   * <p>Struggle to use one of the values provided at {@link GGoalStandardType}.
   *
   * @return the string
   */
  String goal();

  /**
   * Thing name.
   *
   * @return the string
   */
  String thingName();

  /**
   * Thing type.
   *
   * @return the string
   */
  String thingType() default "ANY";
}
