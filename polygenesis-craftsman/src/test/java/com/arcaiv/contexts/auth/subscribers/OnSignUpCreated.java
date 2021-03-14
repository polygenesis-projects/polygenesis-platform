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

package com.arcaiv.contexts.auth.subscribers;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.data.dsl.DataBuilder;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class OnSignUpCreated {

  /** @return the thing */
  public static Thing create(Thing user, PackageName rootPackageName) {
    Thing onSignUpCreated =
        ThingBuilder.domainMessageSubscriber("onSignUpCreated").createThing();

    onSignUpCreated.addMetadata(new KeyValue("relatedThing", user));

    onSignUpCreated.addMetadata(
        new KeyValue(
            "supportedMessageTypes",
            new LinkedHashSet<>(Collections.singletonList("SignUpCreated"))));

    onSignUpCreated.addMetadata(new KeyValue("messageData", messageData(rootPackageName)));

    onSignUpCreated.addMetadata(new KeyValue("process", user.getFunctionByName("create")));

    return onSignUpCreated;
  }

  private static Set<Data> messageData(PackageName rootPackageName) {
    DataObject email =
        new DataObject(new ObjectName("email"), rootPackageName);
    email.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("value")));

    DataObject password =
        new DataObject(new ObjectName("password"), rootPackageName);
    password.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("value")));

    return DataBuilder.create()
        .withTextProperty("userId")
        .build()
        .withGroupData(email)
        .withGroupData(password)
        .build();
  }
}
