<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2019 OREGOR LTD
 ========================================================================
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ===========================LICENSE_END==================================
-->
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <parent>
    <groupId>${ projectDescription.groupId }</groupId>
    <artifactId>${ projectDescription.artifactId }</artifactId>
    <version>${ projectDescription.version }</version>
  </parent>
  <modelVersion>4.0.0</modelVersion>

  <groupId>${ projectDescription.groupId }</groupId>
  <artifactId>${ projectDescription.modulePrefix }-app</artifactId>

  <dependencies>
    <!-- ===================================================================================== -->
    <!-- SPRING BOOT WEB -->
    <!-- ===================================================================================== -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- API IMPL -->
    <dependency>
      <groupId>com.oregor.microservices.${ projectDescription.modulePrefix }</groupId>
      <artifactId>${ projectDescription.modulePrefix }-api-impl</artifactId>
    </dependency>

    <!-- ===================================================================================== -->
    <!-- PRIMARY ADAPTERS                                                                      -->
    <!-- ===================================================================================== -->

    <!-- REST SPRING -->
    <dependency>
      <groupId>com.oregor.microservices.${ projectDescription.modulePrefix }.${ projectDescription.modulePrefix }-primary-adapters</groupId>
      <artifactId>${ projectDescription.modulePrefix }-rest-spring</artifactId>
    </dependency>

    <!-- SUBSCRIBER ACTIVEMQ -->
    <dependency>
      <groupId>com.oregor.microservices.${ projectDescription.modulePrefix }.${ projectDescription.modulePrefix }-primary-adapters</groupId>
      <artifactId>${ projectDescription.modulePrefix }-subscriber-activemq</artifactId>
    </dependency>

    <!-- ===================================================================================== -->
    <!-- SECONDARY ADAPTERS                                                                    -->
    <!-- ===================================================================================== -->

    <!-- PERSISTENCE RDBMS -->
    <dependency>
      <groupId>com.oregor.microservices.${ projectDescription.modulePrefix }.${ projectDescription.modulePrefix }-secondary-adapters</groupId>
      <artifactId>${ projectDescription.modulePrefix }-persistence-rdbms</artifactId>
    </dependency>

    <!-- PUBLISHER ACTIVEMQ -->
    <dependency>
      <groupId>com.oregor.microservices.${ projectDescription.modulePrefix }.${ projectDescription.modulePrefix }-secondary-adapters</groupId>
      <artifactId>${ projectDescription.modulePrefix }-publisher-activemq</artifactId>
    </dependency>

    <!-- ===================================================================================== -->
    <!-- DATABASE -->
    <!-- ===================================================================================== -->
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>

    <!-- ===================================================================================== -->
    <!-- TEST -->
    <!-- ===================================================================================== -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

</project>
