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
<#macro fillArgumentValueObject property persistenceVariable requestDto multiTenant converterVariable>
<#compress>
  <#if property.data.getAsDataObject().models?size == 1 >
    <#if property.data.getAsDataObject().models[0].primitiveType == 'STRING'
        || property.data.getAsDataObject().models[0].primitiveType == 'DECIMAL'>
      new ${ textConverter.toUpperCamel(property.data.objectName.text) }(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }())
    <#elseif property.data.getAsDataObject().models[0].primitiveType == 'UUID'>
      new ${ textConverter.toUpperCamel(property.data.objectName.text) }(UUID.fromString(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()))
    <#elseif property.data.getAsDataObject().models[0].primitiveType == 'URL'>
      new ${ textConverter.toUpperCamel(property.data.objectName.text) }(new URL(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()))
    <#elseif property.data.getAsDataObject().models[0].primitiveType == 'URI'>
      new ${ textConverter.toUpperCamel(property.data.objectName.text) }(new URI(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }()))
    <#else>
      null // TODO: ${property.data.getAsDataObject().models[0].primitiveType} is not supported yet
    </#if>
  <#else>
      ${converterVariable}.convertTo${ textConverter.toUpperCamel(property.data.objectName.text) }Vo(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel(property.data.variableName.text) }())
  </#if>
</#compress>
</#macro>
