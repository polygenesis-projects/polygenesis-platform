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

package com.invoiceful.genesis.contexts.notification.subscribers;

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.dsl.ThingBuilder;
import io.polygenesis.commons.keyvalue.KeyValue;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/** @author Christos Tsakostas */
public class OnSignUpCreated {

  /** @return the thing */
  public static Thing create(Thing email, String rootPackageName) {
    Thing onSignUpCreated = ThingBuilder.domainMessageSubscriber("onSignUpCreated").createThing();

    onSignUpCreated.addMetadata(new KeyValue("relatedThing", email));

    onSignUpCreated.addMetadata(
        new KeyValue(
            "supportedMessageTypes",
            new LinkedHashSet<>(Collections.singletonList("SignUpCreated"))));

    onSignUpCreated.addMetadata(new KeyValue("messageData", messageData(rootPackageName)));

    //    onSignUpCreated.addMetadata(
    //        new KeyValue("ensureExistence", email.getFunctionByName("ensureExistence")));

    onSignUpCreated.addMetadata(new KeyValue("process", email.getFunctionByName("create")));

    return onSignUpCreated;
  }

  private static Set<Data> messageData(String rootPackageName) {
    return new LinkedHashSet<>();
    //    DataObject email = new DataObject(
    //        new ObjectName("email"),
    //        new PackageName("com.invoiceful.access.identity")
    //    );
    //    email.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("value")));
    //
    //    DataObject password = new DataObject(
    //        new ObjectName("password"),
    //        new PackageName("com.invoiceful.access.identity")
    //    );
    //    password.addData(DataPrimitive.of(PrimitiveType.STRING, new VariableName("value")));
    //
    //    return DataBuilder.create()
    //        .withTextProperty("userId").build()
    //        .withGroupData(email)
    //        .withGroupData(password)
    //        .build();
  }
}
