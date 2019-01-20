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

package io.polygenesis.models.reactivestate;

/**
 * Groups related {@link Action Actions} together.
 *
 * <p>For example, suppose you call an {@link Action} named {@code CreateSomeThing}. An {@link
 * Effect} is automatically executed and a REST call is made to the backend. Depending on the result
 * the two related {@link Action Actions} may be {@code CreateSomeThingOnSuccess} and {@code
 * CreateSomeThingOnFailure}.
 *
 * <p>{@link Store} is the container of the reactive state management modelling.
 *
 * @author Christos Tsakostas
 */
public class ActionGroup {}
