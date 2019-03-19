<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <packaging>pom</packaging>
  <modules>
<#if projectDescription.microservice>
<#list projectDescription.layers as layer>
  <#if layer == 'APP'>
    <module>${ projectDescription.modulePrefix }-app</module>
  <#elseif layer == 'API'>
    <module>${ projectDescription.modulePrefix }-api</module>
  <#elseif layer == 'API_IMPL'>
    <module>${ projectDescription.modulePrefix }-api-impl</module>
  <#elseif layer == 'DOMAIN_MODEL'>
    <module>${ projectDescription.modulePrefix }-domain-model</module>
  <#elseif layer == 'DOMAIN_SERVICES'>
    <module>${ projectDescription.modulePrefix }-domain-services-impl</module>
  <#elseif layer == 'PROJECTION_MODEL'>
  </#if>
</#list>
  <#if projectDescription.hasPrimaryAdapters()>
    <module>${ projectDescription.modulePrefix }-primary-adapters</module>
  </#if>
  <#if projectDescription.hasSecondaryAdapters()>
    <module>${ projectDescription.modulePrefix }-secondary-adapters</module>
  </#if>
</#if>
<#list projectDescription.extraModules as extraModule>
    <module>${ projectDescription.modulePrefix }-${ extraModule }</module>
</#list>
  </modules>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.3.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>
  <groupId>${ projectDescription.groupId }</groupId>
  <artifactId>${ projectDescription.artifactId }</artifactId>
  <version>${ projectDescription.version }</version>
  <name>${ projectDescription.name }</name>
  <description>${ projectDescription.description }</description>
  <url>${ projectDescription.url }</url>

  <inceptionYear>${ projectDescription.inceptionYear }</inceptionYear>

  <organization>
    <name>${ projectDescription.organizationName }</name>
    <url>${ projectDescription.organizationUrl }</url>
  </organization>

  <licenses>
    <license>
      <name>${ projectDescription.licenseName }</name>
      <url>${ projectDescription.url }</url>
    </license>
  </licenses>

  <developers>
    <developer>
      <name>Christos Tsakostas</name>
      <email>c.tsakostas@gmail.com</email>
      <organization>OREGOR LTD</organization>
      <organizationUrl>https://www.oregor.com</organizationUrl>
    </developer>
  </developers>

  <scm>
    <connection>
      ${ projectDescription.scmConnection }
    </connection>
    <developerConnection>
      ${ projectDescription.scmDeveloperConnection }
    </developerConnection>
    <url>
      ${ projectDescription.scmUrl }
    </url>
    <tag>HEAD</tag>
  </scm>

  <distributionManagement>
    <snapshotRepository>
      <id>${ projectDescription.distributionProfile }</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </snapshotRepository>
    <repository>
      <id>${ projectDescription.distributionProfile }</id>
      <url>https://oss.sonatype.org/service/local/staging/deploy/maven2/</url>
    </repository>
  </distributionManagement>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>

    <!--BUILD-->
    <oregor-build-tools-java.version>0.0.3</oregor-build-tools-java.version>
    <maven-compiler-plugin.version>3.7.0</maven-compiler-plugin.version>
    <maven-deploy-plugin.version>2.8.2</maven-deploy-plugin.version>
    <nexus-staging-maven-plugin.version>1.6.7</nexus-staging-maven-plugin.version>
    <maven-source-plugin.version>2.2.1</maven-source-plugin.version>
    <maven-javadoc-plugin.version>2.9.1</maven-javadoc-plugin.version>
    <maven-gpg-plugin.version>1.6</maven-gpg-plugin.version>
    <nexus-staging-maven-plugin.version>1.6.7</nexus-staging-maven-plugin.version>
    <maven-release-plugin.version>2.5.3</maven-release-plugin.version>
    <sonar-maven-plugin.version>3.5.0.1254</sonar-maven-plugin.version>
    <license-maven-plugin.version>1.14</license-maven-plugin.version>
    <fmt-maven-plugin.version>2.6.0</fmt-maven-plugin.version>
    <maven-checkstyle-plugin.version>3.0.0</maven-checkstyle-plugin.version>
    <checkstyle.version>8.15</checkstyle.version>
    <findbugs-maven-plugin.version>3.0.0</findbugs-maven-plugin.version>
    <maven-pmd-plugin.version>3.11.0</maven-pmd-plugin.version>
    <maven-jxr-plugin.version>2.4</maven-jxr-plugin.version>
    <maven-project-info-reports-plugin.version>2.7</maven-project-info-reports-plugin.version>
    <maven-site-plugin.version>3.7.1</maven-site-plugin.version>
    <taglist-maven-plugin.version>2.4</taglist-maven-plugin.version>
    <maven-dependency-plugin.version>2.9</maven-dependency-plugin.version>
    <cobertura-maven-plugin.version>2.6</cobertura-maven-plugin.version>
    <versions-maven-plugin.version>2.7</versions-maven-plugin.version>
    <querydsl-apt-maven-plugin.version>1.1.3</querydsl-apt-maven-plugin.version>

    <!--SPRING SECURITY OAUTH2-->
    <spring-security-oauth2.version>2.3.5.RELEASE</spring-security-oauth2.version>
    <spring-security-oauth2-autoconfigure.version>2.1.3.RELEASE</spring-security-oauth2-autoconfigure.version>

    <!--APACHE-->
    <commons-lang3.version>3.5</commons-lang3.version>
    <commons-io-version>2.4</commons-io-version>
    <commons-validator.version>1.5.1</commons-validator.version>

    <!--GUAVA-->
    <guava.version>26.0-jre</guava.version>

    <!--FREEMARKER-->
    <freemarker.version>2.3.28</freemarker.version>

    <!--REFLECTIONS-->
    <reflections.version>0.9.11</reflections.version>

    <!--INFLECTOR-->
    <evo-inflector.version>1.2.2</evo-inflector.version>

    <!--OREGOR-->
    <oregor-ddd4j.version>0.0.2-SNAPSHOT</oregor-ddd4j.version>
  </properties>

  <dependencies>
  </dependencies>

  <dependencyManagement>
    <dependencies>
      <!--APACHE-->
      <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <#noparse>
        <version>${commons-lang3.version}</version>
        </#noparse>
      </dependency>
      <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <#noparse>
        <version>${commons-io-version}</version>
        </#noparse>
      </dependency>
      <dependency>
        <groupId>commons-validator</groupId>
        <artifactId>commons-validator</artifactId>
        <#noparse>
        <version>${commons-validator.version}</version>
        </#noparse>
      </dependency>

<#if projectDescription.microservice>
      <!--API-->
      <dependency>
        <groupId>${ projectDescription.groupId }</groupId>
        <artifactId>${ projectDescription.modulePrefix }-api</artifactId>
        <#noparse>
        <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!--API IMPLEMENTATION-->
      <dependency>
        <groupId>${ projectDescription.groupId }</groupId>
        <artifactId>${ projectDescription.modulePrefix }-api-impl</artifactId>
        <#noparse>
        <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!--DOMAIN MODEL-->
      <dependency>
        <groupId>${ projectDescription.groupId }</groupId>
        <artifactId>${ projectDescription.modulePrefix }-domain-model</artifactId>
        <#noparse>
        <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!--DOMAIN SERVICES IMPLEMENTATION-->
      <dependency>
        <groupId>${ projectDescription.groupId }</groupId>
        <artifactId>${ projectDescription.modulePrefix }-domain-services-impl</artifactId>
        <#noparse>
          <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!-- ===================================================================================== -->
      <!-- PRIMARY ADAPTERS                                                                      -->
      <!-- ===================================================================================== -->

      <!--REST SPRING-->
      <dependency>
        <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-primary-adapters</groupId>
        <artifactId>${ projectDescription.modulePrefix }-rest-spring</artifactId>
        <#noparse>
        <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!--SUBSCRIBER ACTIVEMQ-->
      <dependency>
        <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-primary-adapters</groupId>
        <artifactId>${ projectDescription.modulePrefix }-subscriber-activemq</artifactId>
        <#noparse>
        <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!-- ===================================================================================== -->
      <!-- SECONDARY ADAPTERS                                                                    -->
      <!-- ===================================================================================== -->

      <!--PERSISTENCE RDBMS-->
      <dependency>
        <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-secondary-adapters</groupId>
        <artifactId>${ projectDescription.modulePrefix }-persistence-rdbms</artifactId>
        <#noparse>
        <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!--PUBLISHER ACTIVEMQ-->
      <dependency>
        <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-secondary-adapters</groupId>
        <artifactId>${ projectDescription.modulePrefix }-publisher-activemq</artifactId>
        <#noparse>
        <version>${project.version}</version>
        </#noparse>
      </dependency>

      <!-- ===================================================================================== -->
      <!-- OREGOR                                                                                -->
      <!-- ===================================================================================== -->
      <dependency>
        <groupId>com.oregor.ddd4j</groupId>
        <artifactId>ddd4j-bom</artifactId>
        <#noparse>
          <version>${oregor-ddd4j.version}</version>
        </#noparse>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
</#if>
    </dependencies>
  </dependencyManagement>

<#noparse>

  <build>
    <!-- ===================================================================== -->
    <!-- Build Plugin Management                                               -->
    <!-- ===================================================================== -->
    <pluginManagement>
      <plugins>
        <!-- ===================================================================== -->
        <!-- Set the Java version (source and target)                              -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>${maven-compiler-plugin.version}</version>
          <configuration>
            <source>${java.version}</source>
            <target>${java.version}</target>
            <encoding>${project.build.sourceEncoding}</encoding>
          </configuration>
        </plugin>
        <!-- ===================================================================== -->
        <!-- License management                                                    -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>license-maven-plugin</artifactId>
          <version>${license-maven-plugin.version}</version>
          <configuration>
            <licenseName>apache_v2</licenseName>
            </#noparse>
            <projectName>${ projectDescription.name }</projectName>
            <#noparse>
            <addJavaLicenseAfterPackage>false</addJavaLicenseAfterPackage>
            <emptyLineAfterHeader>true</emptyLineAfterHeader>
            <failOnMissingHeader>true</failOnMissingHeader>
            <failOnNotUptodateHeader>true</failOnNotUptodateHeader>
            <processStartTag>
              ==========================LICENSE_START=================================
            </processStartTag>
            <processEndTag>===========================LICENSE_END==================================
            </processEndTag>
            <sectionDelimiter>
              ========================================================================
            </sectionDelimiter>
            <excludes>
              <exclude>**/package-info.java</exclude>
              <exclude>**/*.txt</exclude>
              <exclude>**/*.properties</exclude>
              <exclude>**/*.yml</exclude>
            </excludes>
          </configuration>
        </plugin>
        <!-- ===================================================================== -->
        <!-- CODE FORMAT                                                           -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>com.coveo</groupId>
          <artifactId>fmt-maven-plugin</artifactId>
          <version>${fmt-maven-plugin.version}</version>
          <configuration>
            <displayFiles>true</displayFiles>
            <style>google</style>
          </configuration>
        </plugin>
        <!-- ===================================================================== -->
        <!-- SCA: CHECKSTYLE                                                       -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-checkstyle-plugin</artifactId>
          <version>${maven-checkstyle-plugin.version}</version>
          <dependencies>
            <dependency>
              <groupId>com.puppycrawl.tools</groupId>
              <artifactId>checkstyle</artifactId>
              <version>${checkstyle.version}</version>
            </dependency>
            <dependency>
              <groupId>com.oregor</groupId>
              <artifactId>oregor-build-tools-java</artifactId>
              <version>${oregor-build-tools-java.version}</version>
            </dependency>
          </dependencies>
          <configuration>
            <configLocation>oregor/build/tools/checkstyle/checkstyle.xml</configLocation>
            <suppressionsLocation>
              oregor/build/tools/checkstyle/checkstyle-suppressions.xml
            </suppressionsLocation>
            <consoleOutput>true</consoleOutput>
            <failsOnError>true</failsOnError>
            <failOnViolation>true</failOnViolation>
            <logViolationsToConsole>true</logViolationsToConsole>
            <sourceDirectories>
              <sourceDirectory>${project.build.sourceDirectory}</sourceDirectory>
            </sourceDirectories>
            <excludes>**/module-info.java</excludes>
            <violationSeverity>warning</violationSeverity>
            <encoding>${project.build.sourceEncoding}</encoding>
          </configuration>
          <executions>
            <execution>
              <goals>
                <goal>check</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
        <!-- ===================================================================== -->
        <!-- SCA: Findbugs                                                         -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>findbugs-maven-plugin</artifactId>
          <version>${findbugs-maven-plugin.version}</version>
          <dependencies>
            <dependency>
              <groupId>com.oregor</groupId>
              <artifactId>oregor-build-tools-java</artifactId>
              <version>${oregor-build-tools-java.version}</version>
            </dependency>
          </dependencies>
          <configuration>
            <xmlOutput>true</xmlOutput>
            <effort>Max</effort>
            <threshold>Low</threshold>
            <excludeFilterFile>oregor/build/tools/findbugs/findbugs-exclude.xml
            </excludeFilterFile>
            <failOnError>true</failOnError>
          </configuration>
        </plugin>
        <!-- ================================================================= -->
        <!-- SCA: PMD                                                          -->
        <!-- ================================================================= -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-pmd-plugin</artifactId>
          <version>${maven-pmd-plugin.version}</version>
          <dependencies>
            <dependency>
              <groupId>com.oregor</groupId>
              <artifactId>oregor-build-tools-java</artifactId>
              <version>${oregor-build-tools-java.version}</version>
            </dependency>
          </dependencies>
          <configuration>
            <linkXref>true</linkXref>
            <sourceEncoding>${project.build.sourceEncoding}</sourceEncoding>
            <minimumTokens>100</minimumTokens>
            <targetJdk>${java.version}</targetJdk>
            <excludes>
              <exclude>**/*Bean.java</exclude>
              <exclude>**/generated/*.java</exclude>
            </excludes>
            <excludeRoots>
              <excludeRoot>target/generated-sources/stubs</excludeRoot>
            </excludeRoots>
            <rulesets>
              <ruleset>oregor/build/tools/pmd/pmd-rules.xml</ruleset>
            </rulesets>
          </configuration>
        </plugin>
        <!-- ===================================================================== -->
        <!-- JAVADOCS                                                              -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-javadoc-plugin</artifactId>
          <version>${maven-javadoc-plugin.version}</version>
          <configuration>
            <additionalparam>-Xdoclint:none</additionalparam>
            <failOnError>false</failOnError>
          </configuration>
        </plugin>
        <!-- ===================================================================== -->
        <!-- RELEASE                                                               -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-release-plugin</artifactId>
          <version>${maven-release-plugin.version}</version>
          <configuration>
            <tagNameFormat>v@{project.version}</tagNameFormat>
            <autoVersionSubmodules>true</autoVersionSubmodules>
            <useReleaseProfile>false</useReleaseProfile>
            <releaseProfiles>sources,javadocs,codesign,nexus</releaseProfiles>
            <goals>deploy</goals>
          </configuration>
        </plugin>
        <!-- ===================================================================== -->
        <!-- VERSIONS                                                              -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>versions-maven-plugin</artifactId>
          <version>${versions-maven-plugin.version}</version>
        </plugin>
        <!-- ===================================================================== -->
        <!-- SONAR                                                                 -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.sonarsource.scanner.maven</groupId>
          <artifactId>sonar-maven-plugin</artifactId>
          <version>${sonar-maven-plugin.version}</version>
        </plugin>
        <!-- ===================================================================== -->
        <!-- The JXR plugin provides cross-reference information for SCA plugins   -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-jxr-plugin</artifactId>
          <version>${maven-jxr-plugin.version}</version>
        </plugin>
        <!-- ===================================================================== -->
        <!-- Site configuration                                                    -->
        <!-- ===================================================================== -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-site-plugin</artifactId>
          <version>${maven-site-plugin.version}</version>
          <inherited>false</inherited>
          <executions>
            <execution>
              <id>attach-descriptor</id>
              <goals>
                <goal>attach-descriptor</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
        <!-- ================================================================= -->
        <!-- Dependency plugin                                                 -->
        <!-- ================================================================= -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-dependency-plugin</artifactId>
          <version>${maven-dependency-plugin.version}</version>
          <executions>
            <execution>
              <id>dep-resolve</id>
              <phase>validate</phase>
              <goals>
                <goal>resolve</goal>
              </goals>
              <configuration>
                <excludeTransitive>false</excludeTransitive>
                <outputFile>${project.build.directory}/dep_current.txt</outputFile>
              </configuration>
            </execution>
            <execution>
              <id>analyze-dep-mgt</id>
              <phase>validate</phase>
              <goals>
                <goal>analyze-dep-mgt</goal>
              </goals>
              <configuration>
                <failBuild>true</failBuild>
                <ignoreDirect>false</ignoreDirect>
              </configuration>
            </execution>
            <execution>
              <id>dep-copy</id>
              <phase>package</phase>
              <goals>
                <goal>copy-dependencies</goal>
              </goals>
              <configuration>
                <outputDirectory>${project.build.directory}/dependency</outputDirectory>
                <stripVersion>true</stripVersion>
              </configuration>
            </execution>
            <execution>
              <id>dep-src-copy</id>
              <phase>package</phase>
              <goals>
                <goal>copy-dependencies</goal>
              </goals>
              <configuration>
                <classifier>sources</classifier>
                <outputDirectory>${project.build.directory}/dependency-sources</outputDirectory>
                <stripVersion>true</stripVersion>
                <failOnMissingClassifierArtifact>false</failOnMissingClassifierArtifact>
              </configuration>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>

    <!-- ===================================================================== -->
    <!-- ===================================================================== -->
    <!-- BUILD PLUGINS                                                         -->
    <!-- ===================================================================== -->
    <!-- ===================================================================== -->
    <plugins>
      <!-- ===================================================================== -->
      <!-- Java Compiler                                                         -->
      <!-- ===================================================================== -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
      </plugin>

      <!-- ===================================================================== -->
      <!--QUERY DSL-->
      <!-- ===================================================================== -->
      <plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <version>${querydsl-apt-maven-plugin.version}</version>
        <executions>
          <execution>
            <goals>
              <goal>process</goal>
            </goals>
            <configuration>
              <outputDirectory>target/generated-sources/java</outputDirectory>
              <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
            </configuration>
          </execution>
        </executions>
      </plugin>

    </plugins>
  </build>

  <!-- ===================================================================== -->
  <!-- ===================================================================== -->
  <!-- PROFILES                                                              -->
  <!-- ===================================================================== -->
  <!-- ===================================================================== -->
  <profiles>

    <!-- ===================================================================== -->
    <!-- SOURCES                                                               -->
    <!-- ===================================================================== -->
    <profile>
      <id>sources</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-source-plugin</artifactId>
            <version>${maven-source-plugin.version}</version>
            <executions>
              <execution>
                <id>attach-sources</id>
                <goals>
                  <goal>jar-no-fork</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- JAVADOCS                                                              -->
    <!-- ===================================================================== -->
    <profile>
      <id>javadocs</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <executions>
              <execution>
                <id>attach-javadocs</id>
                <goals>
                  <goal>jar</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- CODE SIGN                                                             -->
    <!-- ===================================================================== -->
    <profile>
      <id>codesign</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-gpg-plugin</artifactId>
            <version>${maven-gpg-plugin.version}</version>
            <executions>
              <execution>
                <id>sign-artifacts</id>
                <phase>verify</phase>
                <goals>
                  <goal>sign</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- NEXUS STAGING                                                         -->
    <!-- ===================================================================== -->
    <profile>
      <id>nexus</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.sonatype.plugins</groupId>
            <artifactId>nexus-staging-maven-plugin</artifactId>
            <version>${nexus-staging-maven-plugin.version}</version>
            <extensions>true</extensions>
            <configuration>
</#noparse>
              <serverId>${ projectDescription.distributionProfile }</serverId>
<#noparse>
              <nexusUrl>https://oss.sonatype.org/</nexusUrl>
              <autoReleaseAfterClose>true</autoReleaseAfterClose>
            </configuration>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- VALIDATE LICENSE                                                      -->
    <!-- ===================================================================== -->
    <profile>
      <id>validate-license</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>license-maven-plugin</artifactId>
            <executions>
              <execution>
                <phase>validate</phase>
                <goals>
                  <goal>check-file-header</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- VALIDATE CODE FORMAT                                                  -->
    <!-- ===================================================================== -->
    <profile>
      <id>validate-code-format</id>
      <build>
        <plugins>
          <plugin>
            <groupId>com.coveo</groupId>
            <artifactId>fmt-maven-plugin</artifactId>
            <executions>
              <execution>
                <goals>
                  <goal>check</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- VALIDATE CODE STYLE                                                   -->
    <!-- ===================================================================== -->
    <profile>
      <id>validate-code-style</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-checkstyle-plugin</artifactId>
            <executions>
              <execution>
                <goals>
                  <goal>check</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- SCA: Findbugs                                                         -->
    <!-- ===================================================================== -->
    <profile>
      <id>validate-code-bugs</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>findbugs-maven-plugin</artifactId>
            <executions>
              <execution>
                <phase>validate</phase>
                <goals>
                  <goal>check</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

    <!-- ===================================================================== -->
    <!-- SCA: PMD                                                              -->
    <!-- ===================================================================== -->
    <profile>
      <id>validate-code</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-pmd-plugin</artifactId>
            <executions>
              <execution>
                <phase>validate</phase>
                <goals>
                  <goal>check</goal>
                  <goal>cpd-check</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
        </plugins>
      </build>
    </profile>

  </profiles>


  <!-- ===================================================================== -->
  <!-- ===================================================================== -->
  <!-- REPORTING                                                             -->
  <!-- ===================================================================== -->
  <!-- ===================================================================== -->


  <reporting>
    <plugins>
      <!-- ===================================================================== -->
      <!-- The JXR plugin provides cross-reference information for SCA plugins   -->
      <!-- ===================================================================== -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jxr-plugin</artifactId>
      </plugin>
      <!-- ===================================================================== -->
      <!-- SCA: checkstyle                                                       -->
      <!-- ===================================================================== -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-checkstyle-plugin</artifactId>
        <reportSets>
          <reportSet>
            <reports>
              <report>checkstyle</report>
            </reports>
          </reportSet>
        </reportSets>
      </plugin>
      <!-- ================================================================= -->
      <!-- SCA: Findbugs                                                     -->
      <!-- ================================================================= -->
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>findbugs-maven-plugin</artifactId>
      </plugin>
      <!-- ================================================================= -->
      <!-- SCA: PMD                                                          -->
      <!-- ================================================================= -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-pmd-plugin</artifactId>
      </plugin>
      <!-- ================================================================= -->
      <!-- SCA: Tag reporting                                                -->
      <!-- ================================================================= -->
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>taglist-maven-plugin</artifactId>
        <version>${taglist-maven-plugin.version}</version>
        <configuration>
          <tagListOptions>
            <tagClasses>
              <tagClass>
                <displayName>Work items</displayName>
                <tags>
                  <tag>
                    <matchString>todo</matchString>
                    <matchType>ignoreCase</matchType>
                  </tag>
                  <tag>
                    <matchString>@todo</matchString>
                    <matchType>ignoreCase</matchType>
                  </tag>
                  <tag>
                    <matchString>fix</matchString>
                    <matchType>ignoreCase</matchType>
                  </tag>
                  <tag>
                    <matchString>@fix</matchString>
                    <matchType>ignoreCase</matchType>
                  </tag>
                  <tag>
                    <matchString>@fixme</matchString>
                    <matchType>ignoreCase</matchType>
                  </tag>
                  <tag>
                    <matchString>fixme</matchString>
                    <matchType>ignoreCase</matchType>
                  </tag>
                </tags>
              </tagClass>
            </tagClasses>
          </tagListOptions>
        </configuration>
      </plugin>
      <!-- ================================================================= -->
      <!-- Configuration of Code Coverage analysis                           -->
      <!-- ================================================================= -->
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>cobertura-maven-plugin</artifactId>
        <version>${cobertura-maven-plugin.version}</version>
      </plugin>
      <!-- ================================================================= -->
      <!-- Configuration of Javadocs plugin                                  -->
      <!-- ================================================================= -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <reportSets>
          <reportSet>
            <id>default</id>
            <reports>
              <!-- Do not create test javadocs -->
              <report>javadoc</report>
            </reports>
          </reportSet>
        </reportSets>
      </plugin>
      <!-- ================================================================= -->
      <!-- Configuration of Project Reporting plugin                         -->
      <!-- ================================================================= -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-project-info-reports-plugin</artifactId>
        <version>${maven-project-info-reports-plugin.version}</version>
        <reportSets>
          <reportSet>
            <reports>
              <report>index</report>
              <report>modules</report>
              <report>dependency-info</report>
              <report>issue-tracking</report>
              <report>project-team</report>
              <!--<report>dependencies</report>-->
              <report>dependency-management</report>
              <report>dependency-convergence</report>
              <report>license</report>
              <report>scm</report>
              <report>plugins</report>
            </reports>
          </reportSet>
        </reportSets>
      </plugin>
    </plugins>
  </reporting>

</project>
</#noparse>

