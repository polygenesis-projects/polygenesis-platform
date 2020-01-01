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
<#include "../../../polygenesis-implementation-java-shared/macro-assertions-for-parameters.ftl">
<@assertionsForParameters data.parameterRepresentations></@assertionsForParameters>

    return new ${ textConverter.toUpperCamel(data.to.dataObject.dataType) }(
    <#list data.to.dataObject.models as dataModel>
      <#if dataModel.dataPurpose.text == 'THING_IDENTITY'>
        ${ data.from.data.variableName.text }.getId().getTypeId().toString()<#sep>,</#sep>
      <#else>
      <#switch dataModel.dataPrimaryType>
        <#case 'PRIMITIVE'>
          <#if dataModel.dataObject??>
        ${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }().getValue()<#sep>,</#sep>
          <#else>
        ${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }()<#sep>,</#sep>
          </#if>
        <#break>
        <#case 'VALUE_OBJECT'>
        ${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }().getValue()<#sep>,</#sep>
        <#break>
        <#case 'OBJECT'>
        convertTo${ textConverter.toUpperCamel(dataModel.objectName.text) }Dto(${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }())<#sep>,</#sep>
          <#break>
        <#default>
        // Data Primary Type = ${ dataModel.dataPrimaryType } is not supported
      </#switch>
      </#if>
    </#list>
<#--<#list data.from.properties as property>-->
  <#--<#switch property.propertyType>-->
    <#--<#case 'AGGREGATE_ROOT_ID'>-->
      <#--<#if multiTenant>-->
      <#--<#else>-->
      <#--</#if>-->
      <#--<#break>-->
    <#--<#case 'PRIMITIVE'>-->
        <#--${ data.from.objectName.text }.get${ textConverter.toUpperCamel(property.dataModel.variableName.text) }()<#sep>,</#sep>-->
      <#--<#break>-->
    <#--<#case 'VALUE_OBJECT'>-->
        <#--convertToDto(${ data.from.objectName.text }.get${ textConverter.toUpperCamel(property.dataModel.variableName.text) }())<#sep>,</#sep>-->
      <#--<#break>-->
    <#--<#default>-->
        <#--// Property Type = ${ property.propertyType } is not supported-->
  <#--</#switch>-->
<#--</#list>-->
    );