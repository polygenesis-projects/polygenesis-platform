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
    try {
      JsonNode jsonNode = objectMapper.readTree(jsonMessage);

      ${ textConverter.toUpperCamel(commandServiceMethod.requestDto.dataObject.objectName.text) } request = new ${ textConverter.toUpperCamel(commandServiceMethod.requestDto.dataObject.objectName.text) }();

<#list jsonData as data>
  <#switch data.dataPrimaryType>
    <#case 'PRIMITIVE'>
      if (jsonNode.hasNonNull("${ textConverter.toLowerCamel(data.variableName.text) }")) {
      <#switch data.primitiveType>
        <#case 'STRING'>
        request.set${ textConverter.toUpperCamel(data.variableName.text) }(jsonNode.get("${ textConverter.toLowerCamel(data.variableName.text) }").asText());
        <#break>
        <#default>
        // request.set${ textConverter.toUpperCamel(data.variableName.text) }(jsonNode.get("${ textConverter.toLowerCamel(data.variableName.text) }").asText());
        <#break>
      </#switch>
      }
      <#break>
    <#case 'OBJECT'>
      if (jsonNode.hasNonNull("${ textConverter.toLowerCamel(data.variableName.text) }")) {
        // Data Primary Type Type = ${ data.dataPrimaryType } is not supported
      }
      <#break>
    <#default>
      // Data Primary Type Type = ${ data.dataPrimaryType } is not supported
    <#break>
  </#switch>
</#list>

      ${ textConverter.toLowerCamel(commandServiceMethod.service.serviceName.text) }.${ textConverter.toLowerCamel(commandServiceMethod.function.name.text) }(request);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage(), e);
    }