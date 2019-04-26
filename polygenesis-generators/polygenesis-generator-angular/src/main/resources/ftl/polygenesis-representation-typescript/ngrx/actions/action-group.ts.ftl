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

/**
 * Description here.
 *
 * @author PolyGenesis
 */

<#list representation.importObjects as key, value>
import { <#list key as object>${ object }<#sep>, </#sep></#list> } from '${ value }';
</#list>

export enum ${ representation.actionEnumeration.name } {
<#list representation.actionEnumeration.keyValues as keyValue>
  ${ keyValue.key } = '${ keyValue.value }'<#sep>,</#sep>
</#list>
}

<#list representation.actionClasses as actionClass>
export class ${ actionClass.name } implements Action {
  readonly type = ${ actionClass.enumerationKey };

  constructor(public payload: ${ actionClass.payload }) {
  }
}

</#list>
export type ${ representation.actionUnion.name } = <#list representation.actionClasses as actionClass>${ actionClass.name }<#sep> | </#sep></#list>;
