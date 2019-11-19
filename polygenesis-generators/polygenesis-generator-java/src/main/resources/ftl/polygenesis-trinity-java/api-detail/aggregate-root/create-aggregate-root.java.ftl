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
<#include "../../../polygenesis-implementation-java-apiimpl/macro-restore-aggregate-root.ftl">
<#include "../../../polygenesis-implementation-java-apiimpl/macro-store-aggregate-root.ftl">
<#include "../../../polygenesis-implementation-java-apiimpl/macro-fill-arguments.ftl">
    <@assertionsForParameters data.parameterRepresentations></@assertionsForParameters>

    ${ data.aggregateRootDataType } ${ data.aggregateRootVariable } = new ${ data.aggregateRootDataType }(
<@fillArguments data.properties data.persistenceVariable data.requestDto data.multiTenant data.converterVariable></@fillArguments>
    );

<@storeAggregateRoot data.persistenceVariable data.aggregateRootVariable></@storeAggregateRoot>
<#if !data.responseDto.getVirtual()>

    return new ${ data.returnValue }(${ data.aggregateRootVariable }.getId().getTypeId().toString());
</#if>