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
<@assertionsForParameters representation.parameterRepresentations></@assertionsForParameters>

    return new ${ textConverter.toUpperCamel(to.dataGroup.dataType) }(
    <#list to.dataGroup.models as data>
      <#switch data.dataPrimaryType>
        <#case 'PRIMITIVE'>
        ${ from.data.variableName.text }.get${ textConverter.toUpperCamel(data.variableName.text) }()<#sep>,</#sep>
        <#break>
        <#case 'OBJECT'>
        convertToDto(${ from.data.variableName.text }.get${ textConverter.toUpperCamel(data.variableName.text) }())<#sep>,</#sep>
          <#break>
        <#default>
        // Data Primary Type = ${ data.dataPrimaryType } is not supported
      </#switch>
    </#list>
<#--<#list from.properties as property>-->
  <#--<#switch property.propertyType>-->
    <#--<#case 'AGGREGATE_ROOT_ID'>-->
      <#--<#if multiTenant>-->
      <#--<#else>-->
      <#--</#if>-->
      <#--<#break>-->
    <#--<#case 'PRIMITIVE'>-->
        <#--${ from.objectName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()<#sep>,</#sep>-->
      <#--<#break>-->
    <#--<#case 'VALUE_OBJECT'>-->
        <#--convertToDto(${ from.objectName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }())<#sep>,</#sep>-->
      <#--<#break>-->
    <#--<#default>-->
        <#--// Property Type = ${ property.propertyType } is not supported-->
  <#--</#switch>-->
<#--</#list>-->
    );
