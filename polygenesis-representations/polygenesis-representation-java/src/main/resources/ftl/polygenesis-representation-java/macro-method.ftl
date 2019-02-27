<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2019 OREGOR LTD
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
<#macro printMethod method>
  /**
   * ${ method.description }
  <#if method.parameterRepresentations?size gt 0 || method.returnValue != "void">
   *
  </#if>
  <#list method.parameterRepresentations as parameter>
   * @param ${ textConverter.toLowerCamel(parameter.variableName) } the ${ textConverter.toLowerCamelSpaces(parameter.variableName) }
  </#list>
  <#if method.returnValue != "void">
   * @return ${ textConverter.toLowerCamelSpaces(method.returnValue) }
  </#if>
   */
<#list method.annotations as annotation>
  ${ annotation }
</#list>
  ${ method.modifiers }<#if method.modifiers != ""> </#if>${ method.returnValue} ${ method.methodName }(<@printParameters method.parameterRepresentations></@printParameters>) {
${ method.implementation }
  }
</#macro>
