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

package io.polygenesis.codegen;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.Generator;
import io.polygenesis.core.Model;
import io.polygenesis.core.ModelRepository;
import io.polygenesis.deducers.apiimpl.DomainEntityConverterDeducerFactory;
import io.polygenesis.deducers.apiimpl.ServiceImplementationDeducerFactory;
import io.polygenesis.deducers.sql.SqlIndexDeducerFactory;
import io.polygenesis.deducers.sql.SqlTableDeducerFactory;
import io.polygenesis.generators.angular.AngularGeneratorFactory;
import io.polygenesis.generators.java.api.JavaApiGeneratorFactory;
import io.polygenesis.generators.java.apidetail.JavaApiDetailGeneratorFactory;
import io.polygenesis.generators.java.domain.JavaDomainGeneratorFactory;
import io.polygenesis.generators.java.domainserviceimpl.DomainServiceImplementationGeneratorFactory;
import io.polygenesis.generators.java.rdbms.JavaRdbmsGeneratorFactory;
import io.polygenesis.generators.java.rest.JavaApiRestGeneratorFactory;
import io.polygenesis.generators.sql.SqlGeneratorFactory;
import io.polygenesis.models.api.ApiDeducerFactory;
import io.polygenesis.models.domain.DomainDeducerFactory;
import io.polygenesis.models.domain.DomainServiceDeducerFactory;
import io.polygenesis.models.domain.SupportiveEntityDeducerFactory;
import io.polygenesis.models.reactivestate.ReactiveStateFactory;
import io.polygenesis.models.rest.RestDeducerFactory;
import io.polygenesis.models.ui.UiFeatureDeducerFactory;
import io.polygenesis.models.ui.UiLayoutContainerDeducerFactory;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Genesis default.
 *
 * @author Christos Tsakostas
 */
public class GenesisDefault {

  /**
   * Java deducers set.
   *
   * @param rootPackageName the root package name
   * @return the set
   */
  public static Set<Deducer<? extends ModelRepository<? extends Model>>> javaDeducers(
      String rootPackageName) {
    PackageName packageName = new PackageName(rootPackageName);

    return new LinkedHashSet<>(
        Arrays.asList(
            ApiDeducerFactory.newInstance(packageName),
            DomainDeducerFactory.newInstance(packageName),
            DomainServiceDeducerFactory.newInstance(packageName),
            DomainEntityConverterDeducerFactory.newInstance(packageName),
            SupportiveEntityDeducerFactory.newInstance(packageName),
            ServiceImplementationDeducerFactory.newInstance(packageName),
            RestDeducerFactory.newInstance(packageName),
            SqlTableDeducerFactory.newInstance(),
            SqlIndexDeducerFactory.newInstance()));
  }

  /**
   * Java generators set.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @return the set
   */
  public static Set<Generator> javaGenerators(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String tablePrefix,
      String rootPackageName) {
    PackageName packageName = new PackageName(rootPackageName);

    return new LinkedHashSet<>(
        Arrays.asList(
            JavaApiGeneratorFactory.newInstance(
                Paths.get(exportPath, projectFolder, modulePrefix + "-api")),
            JavaApiDetailGeneratorFactory.newInstance(
                Paths.get(exportPath, projectFolder, modulePrefix + "-api-detail"), packageName),
            JavaApiRestGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-api-clients",
                    modulePrefix + "-api-client-rest-spring"),
                packageName,
                new ObjectName(context)),
            JavaRdbmsGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-domain-details",
                    modulePrefix + "-domain-detail-repository-springdatajpa"),
                packageName,
                new ObjectName(context)),
            JavaDomainGeneratorFactory.newInstance(
                Paths.get(exportPath, projectFolder, modulePrefix + "-domain"),
                packageName,
                tablePrefix),
            DomainServiceImplementationGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-domain-details",
                    modulePrefix + "-domain-detail-services")),
            SqlGeneratorFactory.newInstance(
                Paths.get(
                    exportPath,
                    projectFolder,
                    modulePrefix + "-domain-details",
                    modulePrefix + "-domain-detail-repository-springdatajpa"),
                tablePrefix)));
  }

  /**
   * Angular deducers set.
   *
   * @return the set
   */
  public static Set<Deducer<? extends ModelRepository<? extends Model>>> angularDeducers() {
    PackageName packageName = new PackageName("com.oregor.dummy");

    return new LinkedHashSet<>(
        Arrays.asList(
            ApiDeducerFactory.newInstance(packageName),
            ReactiveStateFactory.newInstance(),
            UiFeatureDeducerFactory.newInstance(),
            UiLayoutContainerDeducerFactory.newInstance()));
  }

  /**
   * Angular generators set.
   *
   * @param angularExportPath the angular export path
   * @return the set
   */
  public static Set<Generator> angularGenerators(String angularExportPath) {
    return new LinkedHashSet<>(
        Arrays.asList(AngularGeneratorFactory.newInstance(Paths.get(angularExportPath))));
  }
}
