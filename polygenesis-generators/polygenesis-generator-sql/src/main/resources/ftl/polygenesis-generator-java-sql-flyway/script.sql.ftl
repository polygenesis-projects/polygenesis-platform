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

<#list representation.items as table >
-- -------------------------------------------------------------------------------------------------
-- ${ textConverter.toUpperUnderscore(table.tableName.text) }
-- -------------------------------------------------------------------------------------------------

CREATE TABLE `${ tablePrefix }${ textConverter.toLowerUnderscore(table.tableName.text) }` (
<#list table.columns as column >
  `${ textConverter.toLowerUnderscore(column.name) }` ${ column.columnDataType }<#if column.length gt 0>(${ column.length })</#if><#if column.requiredType == "REQUIRED"> NOT NULL</#if><#sep>, </#sep><#if !column?has_next><#if table.hasPrimaryKeys() >,</#if></#if>
</#list>
<#if table.hasPrimaryKeys() >
  PRIMARY KEY (<#list table.getPrimaryKeys() as primaryKey>`${ textConverter.toLowerUnderscore(primaryKey.name) }`<#sep>, </#sep></#list>)
</#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

</#list>
