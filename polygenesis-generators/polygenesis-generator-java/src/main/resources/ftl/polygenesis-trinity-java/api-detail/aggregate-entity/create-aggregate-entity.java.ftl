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
<#include "../aggregate-root/macro-restore-aggregate-root.ftl">
<#include "../aggregate-root/macro-store-aggregate-root.ftl">
<#include "../../../polygenesis-implementation-java-apiimpl/macro-fill-arguments.ftl">
<@assertionsForParameters data.parameterRepresentations></@assertionsForParameters>

<@restoreAggregateRoot data.aggregateRootData data.aggregateRootData.aggregateRootRepositoryVariable data.aggregateRootData.aggregateRootIdDataType data.aggregateRootData.aggregateRootDataType data.aggregateRootData.aggregateRootVariable data.requestDto data.parentThingIdentity data.aggregateRootData.multiTenant></@restoreAggregateRoot>

    ${ data.aggregateEntityData.dataType } ${ data.aggregateEntityData.variable } = ${ data.aggregateRootData.aggregateRootVariable }.${data.aggregateEntityData.parentMethodName}(
<@fillArguments data.aggregateEntityData.properties data.aggregateRootData.aggregateRootRepositoryVariable data.requestDto data.multiTenant data.converterVariable!"No Converter"></@fillArguments>
    );

<@storeAggregateRoot data.aggregateRootData></@storeAggregateRoot>

    return null;