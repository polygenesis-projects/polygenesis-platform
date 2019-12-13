/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2019 Christos Tsakostas, OREGOR LTD
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

package io.polygenesis.abstraction.data;

import io.polygenesis.commons.assertion.Assertion;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Data repository.
 *
 * @author Christos Tsakostas
 */
public class DataRepository {

  // ===============================================================================================
  // STATE
  // ===============================================================================================

  private Set<Data> data;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /** Instantiates a new Data repository. */
  public DataRepository() {
    this.data = new LinkedHashSet<>();
  }

  /**
   * Instantiates a new Data repository.
   *
   * @param data the data
   */
  public DataRepository(Data data) {
    this.data = new LinkedHashSet<>();
    addData(data);
  }

  /**
   * Instantiates a new Data repository.
   *
   * @param data the data
   */
  public DataRepository(Set<Data> data) {
    this.data = new LinkedHashSet<>();
    addSetOfData(data);
  }

  // ===============================================================================================
  // STATE MUTATION
  // ===============================================================================================

  /**
   * Add data.
   *
   * @param data the data
   */
  public void addData(Data data) {
    Assertion.isNotNull(data, "data is required");
    getData().add(data);
  }

  /**
   * Add data.
   *
   * @param data the data
   */
  public void addSetOfData(Set<Data> data) {
    Assertion.isNotNull(data, "data is required");
    getData().addAll(data);
  }

  // ===============================================================================================
  // QUERIES
  // ===============================================================================================

  /**
   * Find by data purpose set.
   *
   * @param dataPurpose the data purpose
   * @return the set
   */
  public Set<Data> findByDataPurpose(DataPurpose... dataPurpose) {
    PredicateDataPurpose predicateDataPurpose =
        new PredicateDataPurpose(Arrays.stream(dataPurpose));

    return getData().stream().filter(predicateDataPurpose::contains).collect(Collectors.toSet());
  }

  /**
   * Find data excluding identities and paging set.
   *
   * @return the set
   */
  public Set<Data> findDataExcludingIdentitiesAndPaging() {
    return getData()
        .stream()
        .filter(
            data ->
                !data.getDataPurpose().equals(DataPurpose.thingIdentity())
                    && !data.getDataPurpose().equals(DataPurpose.parentThingIdentity())
                    && !data.getDataPurpose().equals(DataPurpose.tenantIdentity())
                    && !data.getDataPurpose().equals(DataPurpose.pageNumber())
                    && !data.getDataPurpose().equals(DataPurpose.pageSize()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  /**
   * Find data excluding paging set.
   *
   * @return the set
   */
  public Set<Data> findDataExcludingPaging() {
    return getData()
        .stream()
        .filter(
            data ->
                !data.getDataPurpose().equals(DataPurpose.pageNumber())
                    && !data.getDataPurpose().equals(DataPurpose.pageSize()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  // ===============================================================================================
  // GETTERS
  // ===============================================================================================

  /**
   * Gets data.
   *
   * @return the data
   */
  public Set<Data> getData() {
    return data;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataRepository that = (DataRepository) o;
    return Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }

  // ===============================================================================================
  // PREDICATES
  // ===============================================================================================

  private static class PredicateDataPurpose {

    private Set<DataPurpose> dataPurposes;

    /**
     * Instantiates a new Predicate data purpose.
     *
     * @param dataPurposesStream the data purposes stream
     */
    public PredicateDataPurpose(Stream<DataPurpose> dataPurposesStream) {
      this.dataPurposes = dataPurposesStream.collect(Collectors.toSet());
    }

    /**
     * Contains boolean.
     *
     * @param data the data
     * @return the boolean
     */
    public boolean contains(Data data) {
      return dataPurposes.contains(data.getDataPurpose());
    }
  }
}
