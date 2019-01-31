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
<settings xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xsi:schemalocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>${ projectDescription.distributionProfile }</id>
      <#noparse>
      <username>${env.SONATYPE_USERNAME}</username>
      <password>${env.SONATYPE_PASSWORD}</password>
      </#noparse>
    </server>
  </servers>

  <profiles>
    <profile>
      <id>${ projectDescription.distributionProfile }</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <#noparse>
        <gpg.passphrase>${env.GPG_PASSPHRASE}</gpg.passphrase>
        </#noparse>
      </properties>
    </profile>
  </profiles>
</settings>
