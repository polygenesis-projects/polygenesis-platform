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

package io.polygenesis.generators.java;

import io.polygenesis.commons.valueobjects.ObjectName;
import io.polygenesis.commons.valueobjects.PackageName;
import io.polygenesis.core.Generator;
import io.polygenesis.generators.java.exporters.api.JavaApiGenerator;
import io.polygenesis.generators.java.exporters.api.JavaApiGeneratorFactory;
import io.polygenesis.generators.java.exporters.apidetail.JavaApiDetailGenerator;
import io.polygenesis.generators.java.exporters.apidetail.JavaApiDetailGeneratorFactory;
import io.polygenesis.generators.java.exporters.domain.JavaDomainGenerator;
import io.polygenesis.generators.java.exporters.domain.JavaDomainGeneratorFactory;
import io.polygenesis.generators.java.exporters.domainserviceimpl.DomainServiceImplementationGenerator;
import io.polygenesis.generators.java.exporters.domainserviceimpl.DomainServiceImplementationGeneratorFactory;
import io.polygenesis.generators.java.exporters.rdbms.JavaRdbmsGenerator;
import io.polygenesis.generators.java.exporters.rdbms.JavaRdbmsGeneratorFactory;
import io.polygenesis.generators.java.exporters.rest.JavaApiRestGenerator;
import io.polygenesis.generators.java.exporters.rest.JavaApiRestGeneratorFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Trinity java multi generator factory.
 *
 * @author Christos Tsakostas
 */
public final class TrinityJavaMultiGeneratorFactory {

  // ===============================================================================================
  // STATIC FINAL
  // ===============================================================================================

  private static final String API = "api";
  private static final String API_DETAIL = "api-detail";
  private static final String API_CLIENTS = "api-clients";
  private static final String API_CLIENT_REST_SPRING = "api-client-rest-spring";
  private static final String DOMAIN = "domain";
  private static final String DOMAIN_DETAILS = "domain-details";
  private static final String DOMAIN_DETAIL_SERVICES = "domain-detail-services";
  private static final String DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA =
      "domain-detail-repository-springdatajpa";

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // STATIC INITIALIZATION OF DEPENDENCIES
  // ===============================================================================================

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  private TrinityJavaMultiGeneratorFactory() {
    throw new IllegalStateException("Utility class");
  }

  // ===============================================================================================
  // NEW INSTANCE
  // ===============================================================================================

  /**
   * New instance trinity java multi generator.
   *
   * @param generationPath the generation path
   * @param trinityJavaMultiGeneratorEnablement the trinity java multi generator enablement
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @return the trinity java multi generator
   */
  public static TrinityJavaMultiGenerator newInstance(
      Path generationPath,
      TrinityJavaMultiGeneratorEnablement trinityJavaMultiGeneratorEnablement,
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String tablePrefix,
      String rootPackageName) {
    Set<Generator> generators = new LinkedHashSet<>();

    if (trinityJavaMultiGeneratorEnablement.isJavaApiGenerator()) {
      generators.add(javaApiGenerator(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaMultiGeneratorEnablement.isJavaApiDetailGenerator()) {
      generators.add(
          javaApiDetailGenerator(exportPath, projectFolder, modulePrefix, rootPackageName));
    }

    if (trinityJavaMultiGeneratorEnablement.isJavaApiRestGenerator()) {
      generators.add(
          javaApiRestGenerator(exportPath, projectFolder, modulePrefix, context, rootPackageName));
    }

    if (trinityJavaMultiGeneratorEnablement.isJavaDomainGenerator()) {
      generators.add(
          javaDomainGenerator(
              exportPath, projectFolder, modulePrefix, tablePrefix, rootPackageName));
    }

    if (trinityJavaMultiGeneratorEnablement.isDomainServiceImplementationGenerator()) {
      generators.add(domainServiceImplementationGenerator(exportPath, projectFolder, modulePrefix));
    }

    if (trinityJavaMultiGeneratorEnablement.isJavaRdbmsGenerator()) {
      generators.add(
          javaRdbmsGenerator(exportPath, projectFolder, modulePrefix, context, rootPackageName));
    }

    return new TrinityJavaMultiGenerator(generationPath, generators);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Java api generator java api generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the java api generator
   */
  public static JavaApiGenerator javaApiGenerator(
      String exportPath, String projectFolder, String modulePrefix) {
    return JavaApiGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + API));
  }

  /**
   * Java api detail generator java api detail generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param rootPackageName the root package name
   * @return the java api detail generator
   */
  public static JavaApiDetailGenerator javaApiDetailGenerator(
      String exportPath, String projectFolder, String modulePrefix, String rootPackageName) {
    return JavaApiDetailGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + API_DETAIL),
        new PackageName(rootPackageName));
  }

  /**
   * Java api rest generator java api rest generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param rootPackageName the root package name
   * @return the java api rest generator
   */
  public static JavaApiRestGenerator javaApiRestGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String rootPackageName) {
    return JavaApiRestGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + API_CLIENTS,
            modulePrefix + "-" + API_CLIENT_REST_SPRING),
        new PackageName(rootPackageName),
        new ObjectName(context));
  }

  /**
   * Java rdbms generator java rdbms generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param context the context
   * @param rootPackageName the root package name
   * @return the java rdbms generator
   */
  public static JavaRdbmsGenerator javaRdbmsGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String context,
      String rootPackageName) {
    return JavaRdbmsGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_REPOSITORY_SPRING_DATA_JPA),
        new PackageName(rootPackageName),
        new ObjectName(context));
  }

  /**
   * Java domain generator java domain generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @param tablePrefix the table prefix
   * @param rootPackageName the root package name
   * @return the java domain generator
   */
  public static JavaDomainGenerator javaDomainGenerator(
      String exportPath,
      String projectFolder,
      String modulePrefix,
      String tablePrefix,
      String rootPackageName) {
    return JavaDomainGeneratorFactory.newInstance(
        Paths.get(exportPath, projectFolder, modulePrefix + "-" + DOMAIN),
        new PackageName(rootPackageName),
        tablePrefix);
  }

  /**
   * Domain service implementation generator domain service implementation generator.
   *
   * @param exportPath the export path
   * @param projectFolder the project folder
   * @param modulePrefix the module prefix
   * @return the domain service implementation generator
   */
  public static DomainServiceImplementationGenerator domainServiceImplementationGenerator(
      String exportPath, String projectFolder, String modulePrefix) {
    return DomainServiceImplementationGeneratorFactory.newInstance(
        Paths.get(
            exportPath,
            projectFolder,
            modulePrefix + "-" + DOMAIN_DETAILS,
            modulePrefix + "-" + DOMAIN_DETAIL_SERVICES));
  }
}
