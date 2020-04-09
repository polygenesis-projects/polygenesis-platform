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


<#include "*/author/author-html.ftl">

<mat-sidenav-container>
  <#if container.top??>

  <${ textConverter.toLowerHyphen(container.top.containerName.text) }></${ textConverter.toLowerHyphen(container.top.containerName.text) }>
  </#if>

  <mat-sidenav [opened]="true"
               mode="side">
    <app-side-navigation-default></app-side-navigation-default>
  </mat-sidenav>

  <router-outlet #o="outlet"></router-outlet>

<#if container.bottom??>
  <${ textConverter.toLowerHyphen(container.bottom.containerName.text) }></${ textConverter.toLowerHyphen(container.bottom.containerName.text) }>

</#if>
</mat-sidenav-container>


