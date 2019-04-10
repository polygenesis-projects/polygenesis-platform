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
  <artifactId>${ projectDescription.modulePrefix }-service</artifactId>

  <dependencies>
    <!-- ======================================================================================= -->
    <!-- SPRING BOOT                                                                             -->
    <!-- ======================================================================================= -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

  <#list projectDescription.layers as layer>
    <#if layer == 'API_IMPL'>
    <!--API DETAIL-->
    <dependency>
      <groupId>${ projectDescription.groupId }</groupId>
      <artifactId>${ projectDescription.modulePrefix }-api-detail</artifactId>
    </dependency>
    </#if>
  </#list>

    <!-- ======================================================================================= -->
    <!-- API CLIENTS                                                                             -->
    <!-- ======================================================================================= -->

  <#list projectDescription.layers as layer>
    <#if layer == 'PRIMARY_ADAPTER_REST_SPRING'>
    <!-- REST SPRING -->
    <dependency>
      <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-api-clients</groupId>
      <artifactId>${ projectDescription.modulePrefix }-api-rest</artifactId>
    </dependency>
    </#if>
  </#list>

  <#list projectDescription.layers as layer>
    <#if layer == 'PRIMARY_ADAPTER_SUBSCRIBER_ACTIVEMQ'>
    <!-- SUBSCRIBER ACTIVEMQ -->
    <dependency>
      <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-api-clients</groupId>
      <artifactId>${ projectDescription.modulePrefix }-api-subscriber-activemq</artifactId>
    </dependency>
    </#if>
  </#list>

    <!-- ======================================================================================= -->
    <!-- DOMAIN DETAILS                                                                          -->
    <!-- ======================================================================================= -->

  <#list projectDescription.layers as layer>
    <#if layer == 'SECONDARY_ADAPTER_PERSISTENCE_SPRING_DATA_JPA'>
    <!-- PERSISTENCE RDBMS -->
    <dependency>
      <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-domain-details</groupId>
      <artifactId>${ projectDescription.modulePrefix }-domain-persistence-rdbms</artifactId>
    </dependency>
    </#if>
  </#list>

    <!-- ======================================================================================= -->
    <!-- AUXILIARY DETAILS                                                                       -->
    <!-- ======================================================================================= -->

    <#list projectDescription.layers as layer>
      <#if layer == 'SECONDARY_ADAPTER_PUBLISHER_ACTIVEMQ'>
    <!-- PUBLISHER ACTIVEMQ -->
    <dependency>
      <groupId>${ projectDescription.groupId }.${ projectDescription.modulePrefix }-auxiliary-details</groupId>
      <artifactId>${ projectDescription.modulePrefix }-publisher-activemq</artifactId>
    </dependency>
      </#if>
    </#list>

    <!-- ======================================================================================= -->
    <!-- DATABASE -->
    <!-- ======================================================================================= -->
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>

    <!-- ======================================================================================= -->
    <!-- TEST -->
    <!-- ======================================================================================= -->
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
