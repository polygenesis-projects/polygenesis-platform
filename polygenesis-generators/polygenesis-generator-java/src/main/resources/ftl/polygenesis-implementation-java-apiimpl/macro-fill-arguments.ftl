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
<#macro fillArguments properties persistenceVariable requestDto converterVariable multiTenant>
    <#list properties as property>
      <#switch property.propertyType>
        <#case 'AGGREGATE_ROOT_ID'>
          <#if multiTenant>
<#--        ${ persistenceVariable }.nextId(UUID.fromString(${ requestDto.dataGroup.objectName.text }.getTenantId()))<#sep>,</#sep>-->
        ${ persistenceVariable }.nextId()<#sep>,</#sep>
          <#else>
        ${ persistenceVariable }.nextId()<#sep>,</#sep>
          </#if>
        <#break>
        <#case 'PROJECTION_ID'>
          <#if multiTenant>
          <#--        ${ persistenceVariable }.nextId(UUID.fromString(${ requestDto.dataGroup.objectName.text }.getTenantId()))<#sep>,</#sep>-->
        ${ persistenceVariable }.nextId()<#sep>,</#sep>
          <#else>
        ${ persistenceVariable }.nextId()<#sep>,</#sep>
          </#if>
          <#break>
        <#case 'TENANT_ID'>
        new TenantId(UUID.fromString(${ requestDto.dataGroup.objectName.text }.getTenantId()))<#sep>,</#sep>
          <#break>
        <#case 'PRIMITIVE'>
        ${ requestDto.dataGroup.objectName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()<#sep>,</#sep>
          <#break>
        <#case 'VALUE_OBJECT'>
        ${ converterVariable }.convertToVo(${ requestDto.dataGroup.objectName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }())<#sep>,</#sep>
          <#break>
        <#default>
        // Property Type = ${ property.propertyType } is not supported
      </#switch>
    </#list>
</#macro>
