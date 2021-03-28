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

<#macro fillFetchOneResponseDtoArguments responseDto aggregateRootVariable converterVariable>
<#list responseDto.dataObject.models as data >
  <#switch data.dataPrimaryType>
    <#case 'PRIMITIVE'>
        <#if data.dataObject??>
      ${ aggregateRootVariable }.get${ textConverter.toUpperCamel(data.variableName.text) }().getTypeId().toString()<#sep>,</#sep>
        <#else>
      ${ aggregateRootVariable }.get${ textConverter.toUpperCamel(data.variableName.text) }()<#sep>,</#sep>
      </#if>
    <#break>
    <#case 'OBJECT'>
      ${converterVariable}.convertTo${ textConverter.toUpperCamel(data.objectName.text) }Dto(${ aggregateRootVariable }.get${ textConverter.toUpperCamel(data.variableName.text) }())<#sep>,</#sep>
      <#break>
    <#default>
      // Data Primary Type Type = ${ data.dataPrimaryType } is not supported
  </#switch>
</#list>
</#macro>
