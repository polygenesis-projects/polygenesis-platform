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
<#include "../polygenesis-implementation-java-shared/macro-assertions-for-parameters.ftl">
<#include "../polygenesis-trinity-java/api-detail/aggregate-root/macro-restore-aggregate-root.ftl">
<@assertionsForParameters data.parameterRepresentations></@assertionsForParameters>

<#--    Paginated<${ aggregateRootDataType }> paginated = ${ persistenceVariable }.findPaginated(<#if multiTenant>UUID.fromString(${ requestDto.dataObject.variableName.text }.getTenantId()), </#if>${ requestDto.dataObject.objectName.text }.getPageNumber(), ${ requestDto.dataObject.objectName.text }.getPageSize());-->
    Paginated<${ data.aggregateRootDataType }> paginated = ${ data.persistenceVariable }.findPaginated(<#if data.multiTenant>new TenantId(UUID.fromString(${ data.requestDto.dataObject.variableName.text }.getTenantId())), </#if>${ data.requestDto.dataObject.variableName.text }.getPageNumber(), ${ data.requestDto.dataObject.variableName.text }.getPageSize());

    return new ${ data.returnValue }(
        StreamSupport
            .stream(paginated.getItems().spliterator(), false)
            .map(${ data.converterVariable }::convertTo${ data.aggregateRootDataType }CollectionRecord)
            .collect(Collectors.toList()),
        paginated.getSeekMethodLeftOffValue(),
        paginated.getTotalPages(),
        paginated.getTotalElements(),
        ${ data.requestDto.dataObject.variableName.text }.getPageNumber(),
        ${ data.requestDto.dataObject.variableName.text }.getPageSize()
    );