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
@Embeddable
public class ${ projection.objectNameWithOptionalExtendsImplements } {

<#list projection.variables as variable>
  private ${ variable.key } ${ variable.value };
</#list>

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * No-args constructor for persistence frameworks.
   */
  private ${ textConverter.toUpperCamel(projection.objectName) }() {
    super();
  }

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
  // GETTERS
  // ===============================================================================================
<#list projection.variables as variable>

  /**
   * Gets the ${ textConverter.toUpperCamelSpaces(variable.value) }.
   */
  public ${ variable.key } get${ textConverter.toUpperCamel(variable.value) }() {
    return this.${ variable.value };
  }
</#list>

  // ===============================================================================================
  // SETTERS
  // ===============================================================================================
<#list projection.variables as variable>

  /**
   * Sets the ${ textConverter.toUpperCamelSpaces(variable.value) }.
   *
   * @param ${ variable.value } The ${ textConverter.toUpperCamelSpaces(variable.value) }
   */
  public void set${ textConverter.toUpperCamel(variable.value) }(${ variable.key } ${ variable.value }) {
    this.${ variable.value } = ${ variable.value };
  }
</#list>
}
