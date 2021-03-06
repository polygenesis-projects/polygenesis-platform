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
<#include "macro-restore-aggregate-root.ftl">
<#include "macro-store-aggregate-root.ftl">
<#include "../../../polygenesis-implementation-java-apiimpl/macro-fill-arguments.ftl">
<#include "./macro-fill-arguments-response-dto.ftl">
<@assertionsForParameters data.parameterRepresentations></@assertionsForParameters>

<@restoreAggregateRoot data.aggregateRootData data.persistenceVariable data.aggregateRootIdDataType data.aggregateRootDataType data.aggregateRootVariable data.requestDto data.thingIdentity data.multiTenant></@restoreAggregateRoot>

    ${data.aggregateRootVariable}.${data.stateMutationMethodName}(
<@fillArguments data.properties data.persistenceVariable data.requestDto data.multiTenant?c data.converterVariable!"No Converter"></@fillArguments>
    );

<@storeAggregateRoot data.aggregateRootData></@storeAggregateRoot>
<#if !data.responseDto.getVirtual()>

    return new ${ data.returnValue }(${ data.aggregateRootVariable }.getId().getTypeId().toString());</#if>