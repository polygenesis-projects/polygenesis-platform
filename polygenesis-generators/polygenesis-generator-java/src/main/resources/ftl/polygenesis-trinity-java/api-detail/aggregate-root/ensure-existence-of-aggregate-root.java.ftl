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
    <@assertionsForParameters data.parameterRepresentations></@assertionsForParameters>

    // Assertion.isNotNull(request, "request is required");

    ${ textConverter.toUpperCamel(data.aggregateRootIdDataType) } ${ textConverter.toLowerCamel(data.aggregateRootIdDataType) } = new ${ textConverter.toUpperCamel(data.aggregateRootIdDataType) }(UUID.fromString(${ data.requestDto.dataObject.variableName.text }.get${ textConverter.toUpperCamel( data.thingIdentity.variableName.text) }()));

    Optional<${ textConverter.toUpperCamel(data.aggregateRootDataType) }> optional${ textConverter.toUpperCamel(data.aggregateRootDataType) } = ${ data.persistenceVariable }.restore(${ textConverter.toLowerCamel(data.aggregateRootIdDataType) });

    if (!optional${ textConverter.toUpperCamel(data.aggregateRootDataType) }.isPresent()) {
      ${ textConverter.toUpperCamel(data.aggregateRootDataType) } ${ textConverter.toLowerCamel(data.aggregateRootDataType) } = new ${ textConverter.toUpperCamel(data.aggregateRootDataType) }(${ textConverter.toLowerCamel(data.aggregateRootIdDataType) });
      ${ data.persistenceVariable }.store(${ textConverter.toLowerCamel(data.aggregateRootDataType) });
    }