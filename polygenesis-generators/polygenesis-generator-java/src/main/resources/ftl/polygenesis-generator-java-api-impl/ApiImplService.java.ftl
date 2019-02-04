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
@Service
@Transactional
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
	// IMPLEMENTATION(S)
	// ===============================================================================================

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
	@Override
	public ${ methodProjection.returnValue} ${ methodProjection.name }(${ methodProjection.argumentsCommaSeparated }) {
	<#list methodProjection.arguments as argument>
		Assertion.isNotNull(${ argument.value }, "${ textConverter.toUpperCamelSpaces(argument.value) } cannot be null");
	</#list>

		// TODO
	<#list aggregateRootProjection.constructors as constructor>
		${ textConverter.toUpperCamel(aggregateRootProjection.objectName) } ${ textConverter.toLowerCamel(aggregateRootProjection.objectName) } = new ${ textConverter.toUpperCamel(aggregateRootProjection.objectName) }(
		<#list constructor.parameters as parameter>
			set${ textConverter.toUpperCamel(parameter.value) }(${ textConverter.toLowerCamel(parameter.value) })<#sep>,</#sep>
		</#list>
		);
	</#list>

		${ textConverter.toLowerCamel(aggregateRootProjection.objectName) }Persistence.store(${ textConverter.toLowerCamel(aggregateRootProjection.objectName) });

		return new ${ methodProjection.returnValue}(${ textConverter.toLowerCamel(aggregateRootProjection.objectName) }.getId());
	}
</#list>
}
