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
 * @author PolyGenesis
 */
public interface ${ projection.objectNameWithOptionalExtendsImplements } {
<#list projection.methodProjections as methodProjection>

	/**
	 * ${ methodProjection.description }
<#if methodProjection.arguments?size gt 0 || methodProjection.returnValue??>
	 *
</#if>
<#list methodProjection.arguments as argument>
	 * @param ${ argument.value } the ${ textConverter.toUpperCamelSpaces(argument.value) }
</#list>
<#if methodProjection.returnValue??>
	 * @return the ${ textConverter.toUpperCamelSpaces(methodProjection.returnValue) }
</#if>
	 */
	${ methodProjection.returnValue} ${ methodProjection.name }(${ methodProjection.argumentsCommaSeparated });
</#list>
}