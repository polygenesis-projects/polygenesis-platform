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
<#include "./macro-fill-argument-value-object.ftl">
<#function isPropertyContainedInRequestDto property requestDto>
    <#list requestDto.dataObject.models as model>
        <#if model.variableName.text == property.data.variableName.text>
            <#return 'true'>
        </#if>
    </#list>
    <#return 'false'>
</#function>
<#macro fillArguments properties persistenceVariable requestDto multiTenant converterVariable>
    <#list properties as property>
<#if isPropertyContainedInRequestDto(property, requestDto) == 'true'>
      <#switch property.propertyType>
        <#case 'AGGREGATE_ROOT_ID'>
          <#if multiTenant>
<#--        ${ persistenceVariable }.nextId(UUID.fromString(${ requestDto.dataObject.variableName.text }.getTenantId()))<#sep>,</#sep>-->
        ${ persistenceVariable }.nextId()<#sep>,</#sep>
          <#else>
        ${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }() == null ? ${ persistenceVariable }.nextId() : new ${ textConverter.toUpperCamel(property.data.variableName.text) }(UUID.fromString(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()))<#sep>,</#sep>
          </#if>
        <#break>
        <#case 'PROJECTION_ID'>
          <#if multiTenant>
          <#--        ${ persistenceVariable }.nextId(UUID.fromString(${ requestDto.dataObject.variableName.text }.getTenantId()))<#sep>,</#sep>-->
        ${ persistenceVariable }.nextId()<#sep>,</#sep>
          <#else>
        ${ persistenceVariable }.nextId()<#sep>,</#sep>
          </#if>
          <#break>
        <#case 'TENANT_ID'>
        new TenantId(UUID.fromString(${ requestDto.dataObject.variableName.text }.getTenantId()))<#sep>,</#sep>
          <#break>
        <#case 'PRIMITIVE'>
        ${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()<#sep>,</#sep>
          <#break>
        <#case 'DECIMAL'>
        ${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()<#sep>,</#sep>
          <#break>
        <#case 'VALUE_OBJECT'>
        <@fillArgumentValueObject property persistenceVariable requestDto multiTenant converterVariable></@fillArgumentValueObject><#sep>,</#sep>
          <#break>
        <#case 'SINGLE_VALUE_OBJECT'>
        ${ converterVariable }.convertToVo(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }())<#sep>,</#sep>
          <#break>
        <#default>
        // Property Type = ${ property.propertyType } is not supported!
      </#switch>
<#else>
    <#if property.propertyType == 'TENANT_ID'>
        new TenantId(UUID.fromString(${ requestDto.dataObject.variableName.text }.getTenantId()))<#sep>,</#sep>
    <#else>
        <#if property.propertyType == 'REFERENCE_TO_AGGREGATE_ROOT'>
        <#else>
        null<#sep>,</#sep> // TODO
        </#if>
    </#if>
</#if>
    </#list>
</#macro>
