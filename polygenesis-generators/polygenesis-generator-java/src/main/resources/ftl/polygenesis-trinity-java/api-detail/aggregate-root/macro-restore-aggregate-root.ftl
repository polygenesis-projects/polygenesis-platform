<#--
 ==========================LICENSE_START=================================
 PolyGenesis Platform
 ========================================================================
 Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

<#macro restoreAggregateRoot aggregateRootData persistenceVariable aggregateRootIdDataType aggregateRootDataType aggregateRootVariable requestDto thingIdentity multiTenant>
  <#if multiTenant>
    ${ aggregateRootData.aggregateRootDataType } ${ aggregateRootData.aggregateRootVariable } = ${ aggregateRootData.aggregateRootRepositoryVariable }.restore(new ${ aggregateRootData.aggregateRootIdDataType }(UUID.fromString(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel( aggregateRootData.aggregateRootIdVariable ) }()))).orElseThrow(() -> new IllegalArgumentException("Cannot restore ${ aggregateRootVariable }"));
  <#else>
    ${ aggregateRootData.aggregateRootDataType } ${ aggregateRootData.aggregateRootVariable } = ${ aggregateRootData.aggregateRootRepositoryVariable }.restore(new ${ aggregateRootData.aggregateRootIdDataType }(UUID.fromString(${ requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel( aggregateRootData.aggregateRootIdVariable ) }()))).orElseThrow(() -> new IllegalArgumentException("Cannot restore ${ aggregateRootVariable }"));
  </#if>
</#macro>
