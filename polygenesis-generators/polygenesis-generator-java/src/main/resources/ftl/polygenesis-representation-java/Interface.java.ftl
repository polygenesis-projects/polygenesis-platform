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
<#include "macro-method-interface.ftl">
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
${ representation.modifiers }<#if representation.modifiers != ""> </#if>interface ${ representation.fullObjectName } {
<#if representation.getMethodRepresentations()?size gt 0>

  // ===============================================================================================
  // METHODS
  // ===============================================================================================

  <#list representation.getMethodRepresentations() as method>
    <@printMethod method>
    </@printMethod>
    <#sep>

    </#sep>
  </#list>
</#if>
}
