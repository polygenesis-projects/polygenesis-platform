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
    ${ data.aggregateEntityData.dataType } ${ data.aggregateEntityData.variable } = new ${ data.aggregateEntityData.dataType }(
      <#list data.properties as property>
          <#if property.propertyType == 'AGGREGATE_ENTITY_ID'>
        new ${ data.aggregateEntityData.idDataType }(UuidGenerator.timeBasedUuid())<#sep>, </#sep>
          <#elseif property.propertyType == 'REFERENCE_TO_AGGREGATE_ROOT'>
        this<#sep>, </#sep>
          <#else>
        ${ property.data.variableName.text }<#sep>, </#sep>
          </#if>
      </#list>
    );

    get${ textConverter.toUpperCamel(data.aggregateEntityData.variablePlural) }().add(${ data.aggregateEntityData.variable });

    return ${ data.aggregateEntityData.variable };