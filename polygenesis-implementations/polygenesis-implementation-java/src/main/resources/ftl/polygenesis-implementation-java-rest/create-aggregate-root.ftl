<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

<#include "../polygenesis-implementation-java-shared/macro-assertions-for-parameters.ftl">
<#include "macro-set-tenantid-and-ipaddress.ftl">
<#include "macro-return-value.ftl">
<@assertionsForParameters representation.parameterRepresentations></@assertionsForParameters>

<@setTenantIdAndIpAddress requestDto></@setTenantIdAndIpAddress>

<@returnValue serviceName serviceMethodName requestDto></@returnValue>
