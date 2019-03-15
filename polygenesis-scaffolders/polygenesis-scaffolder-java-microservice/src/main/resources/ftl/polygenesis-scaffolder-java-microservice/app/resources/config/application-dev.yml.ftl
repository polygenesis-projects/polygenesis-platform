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
# CREATE SCHEMA `${ projectDescription.context }-dev`;
# CREATE USER '${ projectDescription.context }-dev'@'%' IDENTIFIED BY '${ projectDescription.context }-dev';
# GRANT ALL ON *.* TO '${ projectDescription.context }-dev'@'%';

spring-config:
  activemq:
    broker-url: tcp://localhost:61616
    user: admin
    password: admin
    in-memory: false
    pool:
      enabled: true
      max-connections: 10
  datasource:
    url: jdbc:mysql://localhost:3306/${ projectDescription.context }-dev?useLegacyDatetimeCode=false&characterEncoding=utf8
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: ${ projectDescription.context }-dev
    password: ${ projectDescription.context }-dev
  jpa:
    hibernate.ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
        jdbc:
          time_zone: UTC
