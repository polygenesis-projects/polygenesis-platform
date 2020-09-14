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
<#include "macro-parameters.ftl">
<#macro printConstructor constructor>
  /**
   * ${ constructor.description }
  <#if constructor.parameterRepresentations?size gt 0>
   *
  </#if>
  <#list constructor.parameterRepresentations as parameter>
   * @param ${ textConverter.toLowerCamel(parameter.variableName) } the ${ textConverter.toLowerCamelSpaces(parameter.variableName) }
  </#list>
   */
  <#list constructor.annotations as annotation>
  ${ annotation }
  </#list>
  ${ constructor.modifiers }<#if constructor.modifiers != ""> </#if>${ textConverter.toUpperCamel(representation.simpleObjectName) }(<#list constructor.parameterRepresentations as parameter>${ textConverter.toUpperCamel(parameter.dataType) } ${ textConverter.toLowerCamel(parameter.variableName) }<#sep>, </#sep><#if !(parameter?has_next)>) {</#if></#list><#if constructor.parameterRepresentations?size == 0>) {</#if>
${ constructor.implementation }
  }
</#macro>
