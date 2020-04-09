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

package com.invoiceful.genesis.contexts.access;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.Context;
import io.polygenesis.core.ContextGenerator;
import io.polygenesis.core.Deducer;
import io.polygenesis.craftsman.GenesisDefault;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorEnablement;
import io.polygenesis.generators.java.TrinityJavaContextGeneratorFactory;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

public class ContextAccessTest {

  private static final String JAVA_EXPORT_PATH = "tmp";
  private static final String JAVA_PROJECT_FOLDER = "invoiceful-backend";
  private static final String JAVA_ROOT_PACKAGE = "com.invoiceful";
  private static final String ACCESS_ROOT_PACKAGE = "com.invoiceful.access";

  // ===============================================================================================
  // TESTS
  // ===============================================================================================

  @Test
  public void shouldCreateContext() {
    Context<Thing> context =
        ContextAccess.get(
            new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "access")),
            contextGenerator("access", "access", "acs_", "access"),
            deducers("access"));

    assertThat(context).isNotNull();
    context.populateMetamodelRepositories(deducers("access"));

    makeAssertionsForConfirmation(context);
  }

  // ===============================================================================================
  // ASSERTIONS
  // ===============================================================================================

  private void makeAssertionsForConfirmation(Context<Thing> context) {
    PackageName accessRootPackageName = new PackageName(ACCESS_ROOT_PACKAGE);

    AbstractionRepository<Thing> thingRepository = context.getAbstractionRepository();
    assertThat(thingRepository).isNotNull();

    // =============================================================================================
    //  CONFIRMATION
    // =============================================================================================
    Thing confirmation =
        thingRepository
            .getAbstractionItemByObjectName(new ObjectName("confirmation"))
            .orElseThrow();

    // Parent
    assertThat(confirmation.hasParent()).isFalse();

    // Properties Size
    assertThat(confirmation.getThingProperties().getData()).hasSize(4);

    // Thing Identity
    assertThat(confirmation.getThingProperties().getData())
        .contains(
            DataPrimitive.ofDataBusinessTypeWithDataObject(
                DataPurpose.thingIdentity(),
                PrimitiveType.STRING,
                new VariableName(
                    String.format(
                        "%sId", TextConverter.toLowerCamel(confirmation.getThingName().getText()))),
                new DataObject(
                    new VariableName(
                        String.format(
                            "%sId",
                            TextConverter.toLowerCamel(confirmation.getThingName().getText()))),
                    DataPurpose.thingIdentity(),
                    DataValidator.empty(),
                    new ObjectName(
                        String.format(
                            "%sId",
                            TextConverter.toLowerCamel(confirmation.getThingName().getText()))),
                    confirmation.makePackageName(accessRootPackageName, confirmation),
                    new LinkedHashSet<>(),
                    DataSourceType.DEFAULT)));
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private static Set<Deducer<?>> deducers(String context) {
    Set<Deducer<?>> deducers =
        GenesisDefault.javaDeducers(String.format("%s.%s", JAVA_ROOT_PACKAGE, context));
    return deducers;
  }

  private static ContextGenerator contextGenerator(
      String contextFolder, String modulePrefix, String tablePrefix, String context) {
    TrinityJavaContextGeneratorEnablement trinityJavaContextGeneratorEnablement =
        new TrinityJavaContextGeneratorEnablement();

    return TrinityJavaContextGeneratorFactory.newInstance(
        Paths.get(JAVA_EXPORT_PATH),
        trinityJavaContextGeneratorEnablement,
        JAVA_EXPORT_PATH,
        JAVA_PROJECT_FOLDER,
        contextFolder,
        modulePrefix,
        context,
        tablePrefix,
        String.format("%s.%s", JAVA_ROOT_PACKAGE, context));
  }
}
