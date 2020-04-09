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

package com.staticfy.genesis.contexts.staticization;

import static org.assertj.core.api.Assertions.assertThat;

import io.polygenesis.abstraction.data.DataArray;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.DataPurpose;
import io.polygenesis.abstraction.data.DataReferenceToThingByValue;
import io.polygenesis.abstraction.data.DataSourceType;
import io.polygenesis.abstraction.data.DataValidator;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.abstraction.thing.Thing;
import io.polygenesis.abstraction.thing.ThingName;
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
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ContextStaticizationTest {

  private static final String JAVA_EXPORT_PATH = "tmp";
  private static final String JAVA_PROJECT_FOLDER = "staticfy-backend";
  private static final String JAVA_ROOT_PACKAGE = "com.staticfy";
  private static final String staticization_ROOT_PACKAGE = "com.staticfy.staticization";

  // ===============================================================================================
  // TESTS
  // ===============================================================================================

  @Test
  public void shouldCreateContext() {
    Context<Thing> context =
        ContextStaticization.get(
            new PackageName(String.format("%s.%s", JAVA_ROOT_PACKAGE, "staticization")),
            contextGenerator("staticization", "staticization", "stc_", "staticization"),
            deducers("staticization"));

    assertThat(context).isNotNull();

    makeAssertionsForInvoice(context);
    makeAssertionsForInvoiceItem(context);
  }

  // ===============================================================================================
  // ASSERTIONS
  // ===============================================================================================

  private void makeAssertionsForInvoice(Context<Thing> context) {
    PackageName staticizationRootPackageName = new PackageName(staticization_ROOT_PACKAGE);

    AbstractionRepository<Thing> thingRepository = context.getAbstractionRepository();
    assertThat(thingRepository).isNotNull();

    // =============================================================================================
    // INVOICE
    // =============================================================================================
    Thing invoice =
        thingRepository.getAbstractionItemByObjectName(new ObjectName("invoice")).orElseThrow();

    // Parent
    assertThat(invoice.hasParent()).isFalse();

    // Properties Size
    assertThat(invoice.getThingProperties().getData()).hasSize(3);

    // Thing Identity
    assertThat(invoice.getThingProperties().getData())
        .contains(
            DataPrimitive.ofDataBusinessTypeWithDataObject(
                DataPurpose.thingIdentity(),
                PrimitiveType.STRING,
                new VariableName(
                    String.format(
                        "%sId", TextConverter.toLowerCamel(invoice.getThingName().getText()))),
                new DataObject(
                    new VariableName(
                        String.format(
                            "%sId", TextConverter.toLowerCamel(invoice.getThingName().getText()))),
                    DataPurpose.thingIdentity(),
                    DataValidator.empty(),
                    new ObjectName(
                        String.format(
                            "%sId", TextConverter.toLowerCamel(invoice.getThingName().getText()))),
                    invoice.makePackageName(staticizationRootPackageName, invoice),
                    new LinkedHashSet<>(),
                    DataSourceType.DEFAULT)));

    // Tenant Identity
    assertThat(invoice.getThingProperties().getData())
        .contains(
            DataPrimitive.ofDataBusinessTypeWithDataObject(
                DataPurpose.tenantIdentity(),
                PrimitiveType.STRING,
                new VariableName("tenantId"),
                new DataObject(
                    new VariableName("tenantId"),
                    DataPurpose.tenantIdentity(),
                    DataValidator.empty(),
                    new ObjectName("tenantId"),
                    new PackageName("com.oregor.trinity4j.domain"),
                    new LinkedHashSet<>(),
                    DataSourceType.EXTERNALLY_PROVIDED)));

    // Invoice Items
    Thing invoiceItem =
        invoice.getChildren().stream()
            .filter(thing -> thing.getThingName().equals(new ThingName("invoiceItem")))
            .findFirst()
            .orElseThrow();

    String variableName = TextConverter.toUpperCamel(invoiceItem.getThingName().getText());
    DataReferenceToThingByValue dataReferenceToThingByValue =
        DataReferenceToThingByValue.of(
            invoiceItem, TextConverter.toPlural(invoiceItem.getThingName().getText()));

    assertThat(invoice.getThingProperties().getData())
        .contains(DataArray.of(dataReferenceToThingByValue, variableName));
  }

  private void makeAssertionsForInvoiceItem(Context<Thing> context) {
    PackageName staticizationRootPackageName = new PackageName(staticization_ROOT_PACKAGE);

    AbstractionRepository<Thing> thingRepository = context.getAbstractionRepository();
    assertThat(thingRepository).isNotNull();

    // =============================================================================================
    // INVOICE
    // =============================================================================================
    Thing invoice =
        thingRepository.getAbstractionItemByObjectName(new ObjectName("invoice")).orElseThrow();

    // =============================================================================================
    // INVOICE ITEM
    // =============================================================================================
    Thing invoiceItem =
        invoice.getChildren().stream()
            .filter(thing -> thing.getThingName().equals(new ThingName("invoiceItem")))
            .findFirst()
            .orElseThrow();

    // Parent
    assertThat(invoiceItem.hasParent()).isTrue();

    // Properties Size
    assertThat(invoiceItem.getThingProperties().getData()).hasSize(8);

    // Thing Identity
    assertThat(invoiceItem.getThingProperties().getData())
        .contains(
            DataPrimitive.ofDataBusinessTypeWithDataObject(
                DataPurpose.thingIdentity(),
                PrimitiveType.STRING,
                new VariableName(
                    String.format(
                        "%sId", TextConverter.toLowerCamel(invoiceItem.getThingName().getText()))),
                new DataObject(
                    new VariableName(
                        String.format(
                            "%sId",
                            TextConverter.toLowerCamel(invoiceItem.getThingName().getText()))),
                    DataPurpose.thingIdentity(),
                    DataValidator.empty(),
                    new ObjectName(
                        String.format(
                            "%sId",
                            TextConverter.toLowerCamel(invoiceItem.getThingName().getText()))),
                    invoiceItem.makePackageName(staticizationRootPackageName, invoiceItem),
                    new LinkedHashSet<>(),
                    DataSourceType.DEFAULT)));

    // Parent Thing Identity
    assertThat(invoiceItem.getThingProperties().getData())
        .contains(
            DataPrimitive.ofDataBusinessTypeWithDataObject(
                DataPurpose.parentThingIdentity(),
                PrimitiveType.STRING,
                new VariableName(
                    String.format(
                        "%sId", TextConverter.toLowerCamel(invoice.getThingName().getText()))),
                new DataObject(
                    new VariableName(invoice.getThingName().getText()),
                    DataPurpose.referenceToThingByValue(),
                    DataValidator.empty(),
                    new ObjectName(invoice.getThingName().getText()),
                    invoice.makePackageName(staticizationRootPackageName, invoice),
                    new LinkedHashSet<>(),
                    DataSourceType.DEFAULT)));

    // title
    DataPrimitive title =
        DataPrimitive.of(
            PrimitiveType.STRING,
            new VariableName("title"),
            Shared.title(staticizationRootPackageName));
    assertThat(invoiceItem.getThingProperties().getData()).contains(title);

    // description
    DataPrimitive description =
        DataPrimitive.of(
            PrimitiveType.STRING,
            new VariableName("description"),
            Shared.description(staticizationRootPackageName));
    assertThat(invoiceItem.getThingProperties().getData()).contains(description);

    // quantity
    DataPrimitive quantity =
        DataPrimitive.of(
            PrimitiveType.DECIMAL,
            new VariableName("quantity"),
            Shared.quantity(staticizationRootPackageName));
    assertThat(invoiceItem.getThingProperties().getData()).contains(quantity);

    // unitCost
    DataPrimitive unitCost =
        DataPrimitive.of(
            PrimitiveType.DECIMAL,
            new VariableName("unitCost"),
            Shared.unitCost(staticizationRootPackageName));
    assertThat(invoiceItem.getThingProperties().getData()).contains(unitCost);

    // lineTotal
    DataPrimitive lineTotal =
        DataPrimitive.of(
            PrimitiveType.DECIMAL,
            new VariableName("lineTotal"),
            Shared.lineTotal(staticizationRootPackageName));
    assertThat(invoiceItem.getThingProperties().getData()).contains(lineTotal);

    // discount
    DataObject discount = Shared.discount(staticizationRootPackageName);
    assertThat(invoiceItem.getThingProperties().getData()).contains(discount);
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
