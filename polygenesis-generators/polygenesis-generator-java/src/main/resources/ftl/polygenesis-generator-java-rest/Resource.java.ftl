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
package ${ projection.packageName };
<#if projection.imports?size gt 0>

</#if>
<#list projection.imports as import>
import ${ import };
</#list>

/**
 * ${ projection.description }
 *
 * @author ${ authorService.getAuthor() }
 */
@RestController
@RequestMapping(RestConstants.CONTEXT_REQUEST_MAPPING)
public class ${ projection.objectNameWithOptionalExtendsImplements } {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

<#list projection.variables as variable>
  private final ${ variable.key } ${ variable.value };
</#list>

  // ===============================================================================================
  // CONSTRUCTOR
  // ===============================================================================================

<#list projection.constructors as constructor>
  /**
   * Instantiates a new ${ textConverter.toUpperCamelSpaces(projection.objectName) }.
   *
  <#list constructor.parameters as parameter>
   * @param ${ textConverter.toLowerCamel(parameter.value) } the ${ textConverter.toLowerCamelSpaces(parameter.value) }
  </#list>
   */
  public ${ textConverter.toUpperCamel(projection.objectName) }(<#list constructor.parameters as parameter>${ textConverter.toUpperCamel(parameter.key) } ${ textConverter.toLowerCamel(parameter.value) }<#sep>, </#sep><#if !(parameter?has_next)>) {</#if></#list>
  <#list constructor.parameters as parameter>
    this.${ textConverter.toLowerCamel(parameter.value) } = ${ textConverter.toLowerCamel(parameter.value) };
  </#list>
  }
</#list>

  // ===============================================================================================
  // ENDPOINTS
  // ===============================================================================================

<#list projection.functionProjections as functionProjection>
  /**
   * ${ functionProjection.description }
  <#if functionProjection.arguments?size gt 0 || functionProjection.returnValue??>
   *
  </#if>
  <#list functionProjection.arguments as argument>
   * @param ${ argument.value } the ${ textConverter.toUpperCamelSpaces(argument.value) }
  </#list>
  <#if functionProjection.returnValue??>
   * @return the ${ textConverter.toUpperCamelSpaces(functionProjection.returnValue) }
  </#if>
   */
  <#list functionProjection.annotations as annotation>
  ${ annotation }
  </#list>
  public ${ functionProjection.returnValue} ${ functionProjection.name }(${ functionProjection.argumentsCommaSeparated }) {
    return ${ functionProjection.serviceName }.${ functionProjection.name }(<#list functionProjection.arguments as argument>${ argument.value }<#sep>, </#sep></#list>);
  }

</#list>
}
