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
<#include "macro-constructor.ftl">
<#include "macro-method.ftl">
package ${ representation.packageName };
<#if representation.imports?size gt 0>

</#if>
<#list representation.imports as import>
import ${ import };
</#list>

/**
 * ${ representation.description }
 *
 * @author ${ authorService.getAuthor() }
 */
<#list representation.annotations as annotation>
${ annotation }
</#list>
${ representation.modifiers }<#if representation.modifiers != ""> </#if>class ${ representation.fullObjectName } {
<#if representation.fieldRepresentations?size gt 0>

</#if>
<#list representation.fieldRepresentations as fieldRepresentation>
  <#list fieldRepresentation.annotations as annotation>
  ${ annotation }
  </#list>
  private ${ fieldRepresentation.dataType } ${ fieldRepresentation.variableName };
</#list>
<#if representation.getMethodRepresentationsBy('CONSTRUCTOR')?size gt 0>

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  <#list representation.getMethodRepresentationsBy('CONSTRUCTOR') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
<#else>
  <#if representation.constructorRepresentations?size gt 0>

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

    <#list representation.constructorRepresentations as constructor>
      <@printConstructor constructor>
      </@printConstructor>
      <#sep>

      </#sep>
    </#list>
  </#if>
</#if>
<#if representation.getMethodRepresentationsBy('MODIFY')?size gt 0>

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  <#list representation.getMethodRepresentationsBy('MODIFY') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if representation.getMethodRepresentationsBy('GETTER')?size gt 0>

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  <#list representation.getMethodRepresentationsBy('GETTER') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if representation.getMethodRepresentationsBy('SETTER')?size gt 0>

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  <#list representation.getMethodRepresentationsBy('SETTER') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if representation.getMethodRepresentationsBy('GUARD')?size gt 0>

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  <#list representation.getMethodRepresentationsBy('GUARD') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if representation.getMethodRepresentationsBy('ANY')?size gt 0>

  // ===============================================================================================
  // ANY
  // ===============================================================================================

  <#list representation.getMethodRepresentationsBy('ANY') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if representation.getMethodRepresentationsBy('TEST')?size gt 0>

  // ===============================================================================================
  // TEST CASES
  // ===============================================================================================

  <#list representation.getMethodRepresentationsBy('TEST') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
}
