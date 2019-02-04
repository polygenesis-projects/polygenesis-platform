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
 * Test ${ projection.description }
 *
 * @author PolyGenesis
 */
public class ${ projection.objectNameWithOptionalExtendsImplements } {

	// ===============================================================================================
	// DEPENDENCIES
	// ===============================================================================================

<#list projection.variables as variable>
	private ${ variable.key } ${ variable.value };
</#list>
	private ${ textConverter.toUpperCamel(serviceInterface) } ${ textConverter.toLowerCamel(serviceInterface) };

	// ===============================================================================================
	// SETUP
	// ===============================================================================================

	/**
	 * Sets up.
	 */
	@Before
	public void setUp() {
<#list projection.variables as variable>
		${ variable.value } = mock(${ textConverter.toUpperCamel(variable.key) }.class);
</#list>
		${ textConverter.toLowerCamel(serviceInterface) } = new ${ textConverter.toUpperCamel(serviceInterfaceImpl) }(
<#list projection.variables as variable>
				${ variable.value }<#sep>,</#sep>
</#list>
		);
	}

	// ===============================================================================================
	// TESTS
	// ===============================================================================================

<#list projection.methodProjections as methodProjection>
	/**
	 * Should succeed to ${ textConverter.toUpperCamelSpaces(methodProjection.name) }.
	 */
	@Test
	public void shouldSucceedTo${ textConverter.toUpperCamel(methodProjection.name) }() {
		// TODO[PolyGenesis]: write test here

		// GIVEN
	<#list methodProjection.arguments as argument>
		// ${ argument.key } ${ argument.value } = new ${ argument.key }();
		// ${ argument.value }.set(...);
	</#list>

		// WHEN
		// ${ methodProjection.returnValue } ${ textConverter.toLowerCamel(methodProjection.returnValue) } = ${ textConverter.toLowerCamel(serviceInterface) }.${ methodProjection.name }(<#list methodProjection.arguments as argument>${ argument.value }<#sep>, </#sep></#list>);

		// THEN
		// assertThat(${ textConverter.toLowerCamel(methodProjection.returnValue) }).isNotNull();

		assertThat(${ textConverter.toLowerCamel(serviceInterface) }).isNotNull();
	}

	/**
	 * Should fail to ${ textConverter.toUpperCamelSpaces(methodProjection.name) }.
	 */
	@Test
	public void shouldFailTo${ textConverter.toUpperCamel(methodProjection.name) }() {
		// TODO[PolyGenesis]: write test here

		// GIVEN
	<#list methodProjection.arguments as argument>
		// ${ argument.key } ${ argument.value } = null;
	</#list>

		// WHEN - THEN
		assertThatThrownBy(() -> ${ textConverter.toLowerCamel(serviceInterface) }.${ textConverter.toLowerCamel(methodProjection.name) }(<#list methodProjection.arguments as argument>null<#sep>, </#sep></#list>))
				.isInstanceOf(IllegalArgumentException.class);
	}

</#list>
	// ===============================================================================================
	// PRIVATE
	// ===============================================================================================

}
