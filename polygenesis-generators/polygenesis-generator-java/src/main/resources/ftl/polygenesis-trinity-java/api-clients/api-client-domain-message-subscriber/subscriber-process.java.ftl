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
    ${ textConverter.toLowerCamel(data.ensureExistenceServiceMethod.service.serviceName.text) }.${ textConverter.toLowerCamel(data.ensureExistenceServiceMethod.function.name.text) }(new ${ textConverter.toUpperCamel(data.ensureExistenceServiceMethod.requestDto.dataObject.objectName.text) }(incomingDomainMessage.getRootId()));

    ${ textConverter.toUpperCamel(data.commandServiceMethod.requestDto.dataObject.objectName.text) } request = new ${ textConverter.toUpperCamel(data.commandServiceMethod.requestDto.dataObject.objectName.text) }();

<#list data.messageData as item>
  <#switch item.dataPrimaryType>
    <#case 'PRIMITIVE'>
    if (jsonNodeBody.hasNonNull("${ textConverter.toLowerCamel(item.variableName.text) }")) {
      <#switch item.primitiveType>
        <#case 'STRING'>
      request.set${ textConverter.toUpperCamel(item.variableName.text) }(jsonNodeBody.get("${ textConverter.toLowerCamel(item.variableName.text) }").asText());
        <#break>
        <#default>
      // request.set${ textConverter.toUpperCamel(item.variableName.text) }(jsonNodeBody.get("${ textConverter.toLowerCamel(item.variableName.text) }").asText());
        <#break>
      </#switch>
    }
      <#break>
    <#case 'OBJECT'>
    if (jsonNodeBody.hasNonNull("${ textConverter.toLowerCamel(item.variableName.text) }")) {
      // Data Primary Type Type = ${ item.dataPrimaryType } is not supported
    }
      <#break>
    <#default>
    // Data Primary Type Type = ${ item.dataPrimaryType } is not supported
    <#break>
  </#switch>
</#list>

    ${ textConverter.toLowerCamel(data.commandServiceMethod.service.serviceName.text) }.${ textConverter.toLowerCamel(data.commandServiceMethod.function.name.text) }(request);