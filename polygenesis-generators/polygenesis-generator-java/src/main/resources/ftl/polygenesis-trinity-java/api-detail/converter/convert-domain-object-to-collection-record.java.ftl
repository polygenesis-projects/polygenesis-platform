<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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
<#include "../../../polygenesis-implementation-java-shared/macro-domain-assertions-for-parameters.ftl">
<@domainAssertionsForParameters data.parameterRepresentations></@domainAssertionsForParameters>

    return new ${ textConverter.toUpperCamel(data.to.dataObject.dataType) }(
    <#list data.to.dataObject.models as dataModel>
      <#if dataModel.dataPurpose.text == 'THING_IDENTITY'>
        ${ data.from.data.variableName.text }.getId().getTypeId().toString()<#sep>,</#sep>
      <#else>
      <#switch dataModel.dataPrimaryType>
        <#case 'PRIMITIVE'>
          <#assign xxx>
        ${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }().getValue()
          </#assign>
          <#if dataModel.dataObject??>
<#--        // dataModel has dataObject-->
<#--        // ${dataModel.dataType}-->
<#--        // ${dataModel.dataObject.objectName.text}-->
            <#list dataModel.dataObject.models as model>
<#--        // In List 1-->
<#--        // ${model.dataType}-->
<#--        // ${model.variableName.text}-->
<#--        // String.valueOf(${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }().get${ textConverter.toUpperCamel(model.variableName.text) }())<#sep>,</#sep>-->
        <#assign xxx>
          String.valueOf(${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }().get${ textConverter.toUpperCamel(model.variableName.text) }())
        </#assign>
        </#list>
        ${ xxx }<#sep>,</#sep>
          <#else>
        ${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }()<#sep>,</#sep>
          </#if>
        <#break>
        <#case 'VALUE_OBJECT'>
        ${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }().getValue()<#sep>,</#sep>
        <#break>
        <#case 'ENUMERATION'>
        ${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }().name()<#sep>,</#sep>
        <#break>
        <#case 'OBJECT'>
        convertTo${ textConverter.toUpperCamel(dataModel.objectName.text) }Dto(${ data.from.data.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }())<#sep>,</#sep>
        <#break>
        <#default>
        // Data Primary Type = ${ dataModel.dataPrimaryType } is not supported
      </#switch>
      </#if>
    </#list>
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
