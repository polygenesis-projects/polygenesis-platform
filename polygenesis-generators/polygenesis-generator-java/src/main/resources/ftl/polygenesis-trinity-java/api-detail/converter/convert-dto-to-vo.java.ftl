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
<#include "../../../polygenesis-implementation-java-shared/macro-assertions-for-parameters.ftl">
<@assertionsForParameters data.parameterRepresentations></@assertionsForParameters>

    return new ${ textConverter.toUpperCamel(data.to.data.dataType) }(
<#list data.from.dataObject.models as dataModel >
    <#switch dataModel.dataPrimaryType>
        <#case 'PRIMITIVE'>
      ${ data.from.dataObject.variableName.text }.get${ textConverter.toUpperCamel(dataModel.variableName.text) }()<#sep>,</#sep>
            <#break>
            <#break>
        <#default>
      // Data Primary Type Type = ${ dataModel.dataPrimaryType } is not supported
    </#switch>
</#list>
    );
