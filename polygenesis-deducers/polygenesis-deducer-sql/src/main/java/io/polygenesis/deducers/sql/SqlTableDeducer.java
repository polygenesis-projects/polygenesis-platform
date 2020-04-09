/*-
 * ==========================LICENSE_START=================================
 * PolyGenesis Platform
 * ========================================================================
 * Copyright (C) 2015 - 2020 Christos Tsakostas, OREGOR LP
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

package io.polygenesis.deducers.sql;

import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.domain.DomainObjectMetamodelRepository;
import io.polygenesis.models.domain.ProjectionMetamodelRepository;
import io.polygenesis.models.sql.Column;
import io.polygenesis.models.sql.ColumnDataType;
import io.polygenesis.models.sql.RequiredType;
import io.polygenesis.models.sql.SqlTableMetamodelRepository;
import io.polygenesis.models.sql.Table;
import io.polygenesis.models.sql.TableName;
import java.util.LinkedHashSet;
import java.util.Set;

public class SqlTableDeducer implements Deducer<SqlTableMetamodelRepository> {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================
  private final TableDeducer tableDeducer;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  public SqlTableDeducer(TableDeducer tableDeducer) {
    this.tableDeducer = tableDeducer;
  }

  // ===============================================================================================
  // OVERRIDES
  // ===============================================================================================

  @Override
  public SqlTableMetamodelRepository deduce(
      Set<AbstractionRepository<?>> abstractionRepositories,
      Set<MetamodelRepository<?>> metamodelRepositories) {
    Set<Table> tables = new LinkedHashSet<>();

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(metamodelRepositories, DomainObjectMetamodelRepository.class)
        .getItems()
        .forEach(aggregateRoot -> tables.addAll(tableDeducer.deduce(aggregateRoot)));

    ProjectionMetamodelRepository projectionMetamodelRepository =
        CoreRegistry.getMetamodelRepositoryResolver()
            .resolve(metamodelRepositories, ProjectionMetamodelRepository.class);

    projectionMetamodelRepository
        .getItems()
        .forEach(projection -> tables.addAll(tableDeducer.deduce(projection)));

    if (projectionMetamodelRepository.getItems().isEmpty()) {
      tables.add(createDomainMessageTable());
      tables.add(createPublishedDomainMessageTable());
    }

    return new SqlTableMetamodelRepository(tables);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Table createDomainMessageTable() {
    Set<Column> columns = columnsForDomainMessageData();

    return new Table(new TableName("domain_message_data"), columns, false);
  }

  private Table createPublishedDomainMessageTable() {
    Set<Column> columns = columnsForDomainMessageData();

    // Add sent_on
    columns.add(new Column("sent_on", ColumnDataType.DATETIME, 0, RequiredType.OPTIONAL));

    return new Table(new TableName("domain_message_published_data"), columns, false);
  }

  private Set<Column> columnsForDomainMessageData() {
    Set<Column> columns = new LinkedHashSet<>();

    // Add Message Id
    columns.add(
        new Column("message_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, true));

    // Add occurred_on
    columns.add(new Column("occurred_on", ColumnDataType.DATETIME, 0, RequiredType.REQUIRED));

    // Add root_id
    columns.add(new Column("root_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED));

    // Add root_version
    columns.add(new Column("root_version", ColumnDataType.INTEGER, 11, 0, RequiredType.REQUIRED));

    // Add message_type
    columns.add(new Column("message_type", ColumnDataType.VARCHAR, 10, 0, RequiredType.REQUIRED));

    // Add message_name
    columns.add(new Column("message_name", ColumnDataType.VARCHAR, 512, 0, RequiredType.REQUIRED));

    // Add message_version
    columns.add(
        new Column("message_version", ColumnDataType.INTEGER, 11, 0, RequiredType.REQUIRED));

    // Add message
    columns.add(new Column("message_body", ColumnDataType.LONGTEXT, 0, RequiredType.REQUIRED));

    // Add user Id
    columns.add(new Column("user_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED));

    // Add ip_address
    columns.add(new Column("ip_address", ColumnDataType.VARCHAR, 100, 0, RequiredType.OPTIONAL));

    return columns;
  }
}
