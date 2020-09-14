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

<#include "macro-constructor.ftl">
<#include "macro-method.ftl">
<#list data.imports as import>
import '${ import }';
</#list>
<#if data.imports?size gt 0>

</#if>
/// ${ data.description }
///
/// @author ${ authorService.getAuthor() }
<#list data.annotations as annotation>
${ annotation }
</#list>
${ data.modifiers }<#if data.modifiers != ""> </#if>class ${ data.fullObjectName } {
<#if data.fieldRepresentations?size gt 0>

  // ===============================================================================================
  // STATE / DEPENDENCIES
  // ===============================================================================================

</#if>
<#list data.fieldRepresentations as fieldRepresentation>
  <#list fieldRepresentation.annotations as annotation>
  ${ annotation }
  </#list>
  ${ fieldRepresentation.dataType } ${ fieldRepresentation.variableName };
</#list>
<#if data.getMethodRepresentationsBy('CONSTRUCTOR')?size gt 0>

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  <#list data.getMethodRepresentationsBy('CONSTRUCTOR') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
<#else>
  <#if data.constructorRepresentations?size gt 0>

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  <#list data.constructorRepresentations as constructor>
    <@printConstructor constructor>
    </@printConstructor>
    <#sep>

    </#sep>
  </#list>
  </#if>
</#if>
<#if data.getMethodRepresentationsBy('MODIFY')?size gt 0>

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  <#list data.getMethodRepresentationsBy('MODIFY') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if data.getMethodRepresentationsBy('GETTER')?size gt 0>

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  <#list data.getMethodRepresentationsBy('GETTER') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if data.getMethodRepresentationsBy('SETTER')?size gt 0>

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================

  <#list data.getMethodRepresentationsBy('SETTER') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if data.getMethodRepresentationsBy('GUARD')?size gt 0>

  // ===============================================================================================
  // GUARDS
  // ===============================================================================================

  <#list data.getMethodRepresentationsBy('GUARD') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if data.getMethodRepresentationsBy('ANY')?size gt 0>

  // ===============================================================================================
  // OVERRIDES / FUNCTIONALITY
  // ===============================================================================================

  <#list data.getMethodRepresentationsBy('ANY') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
<#if data.getMethodRepresentationsBy('TEST')?size gt 0>

  // ===============================================================================================
  // TEST CASES
  // ===============================================================================================

  <#list data.getMethodRepresentationsBy('TEST') as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
}
