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
<#if data.superClassParameterRepresentations?size gt 0>
    super(<#list data.superClassParameterRepresentations as superClassParameterRepresentation>${superClassParameterRepresentation.variableName}<#sep>, </#sep></#list>);
</#if>
<#list data.thisClassParameterRepresentations as thisClassParameterRepresentation>
    set${ textConverter.toUpperCamel(thisClassParameterRepresentation.variableName) }(${ thisClassParameterRepresentation.variableName });
</#list>
<#if data.domainEvent??>

    registerDomainMessage(new ${ textConverter.toUpperCamel(data.domainEvent.objectName.text) }(
<#list data.domainEventParameterRepresentations as domainEventParameterRepresentation>
      get${ textConverter.toUpperCamel(domainEventParameterRepresentation.variableName) }()<#sep>, </#sep>
</#list>
    ));</#if>