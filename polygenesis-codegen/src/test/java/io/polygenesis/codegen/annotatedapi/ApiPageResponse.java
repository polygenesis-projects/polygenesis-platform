/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 OREGOR LTD
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===========================LICENSE_END==================================
 */

package io.polygenesis.codegen.annotatedapi;

import java.util.List;

/** @author Christos Tsakostas */
public class ApiPageResponse<T> extends ApiResponse {

  List<T> items;

  int totalPages;

  long totalElements;

  int page;

  int size;

  // The following is for Spring compatibility in PolyGenesis
  int number;

  public ApiPageResponse() {}

  public ApiPageResponse(List<T> items, int totalPages, long totalElements, int page, int size) {
    this.items = items;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.page = page;
    this.size = size;
    this.number = page;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }

  public int getNumber() {
    return number;
  }
}
