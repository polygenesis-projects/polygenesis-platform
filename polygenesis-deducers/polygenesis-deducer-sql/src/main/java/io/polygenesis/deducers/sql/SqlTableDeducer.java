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

package io.polygenesis.deducers.sql;

import io.polygenesis.core.AbstractionRepository;
import io.polygenesis.core.CoreRegistry;
import io.polygenesis.core.Deducer;
import io.polygenesis.core.MetamodelRepository;
import io.polygenesis.models.domain.DomainMetamodelRepository;
import io.polygenesis.models.sql.Column;
import io.polygenesis.models.sql.ColumnDataType;
import io.polygenesis.models.sql.RequiredType;
import io.polygenesis.models.sql.SqlTableMetamodelRepository;
import io.polygenesis.models.sql.Table;
import io.polygenesis.models.sql.TableName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Sql deducer.
 *
 * @author Christos Tsakostas
 */
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

  @SuppressWarnings("rawtypes")
  @Override
  public SqlTableMetamodelRepository deduce(
      Set<AbstractionRepository> abstractionRepositories,
      Set<MetamodelRepository> modelRepositories) {

    Set<Table> tables = new LinkedHashSet<>();

    CoreRegistry.getMetamodelRepositoryResolver()
        .resolve(modelRepositories, DomainMetamodelRepository.class)
        .getItems()
        .forEach(aggregateRoot -> tables.addAll(tableDeducer.deduce(aggregateRoot)));

    tables.add(createDomainMessageTable());

    return new SqlTableMetamodelRepository(tables);
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  private Table createDomainMessageTable() {
    Set<Column> columns = new LinkedHashSet<>();

    // Add Message Id
    columns.add(new Column("id", ColumnDataType.BINARY, 16, RequiredType.REQUIRED, true));

    // Add root_id
    columns.add(new Column("root_id", ColumnDataType.BINARY, 16, RequiredType.REQUIRED));

    // Add tenant_id
    columns.add(new Column("tenant_id", ColumnDataType.BINARY, 16, RequiredType.REQUIRED));

    // Add stream_version
    columns.add(new Column("stream_version", ColumnDataType.INTEGER, 11, RequiredType.OPTIONAL));

    // Add message_name
    columns.add(new Column("message_name", ColumnDataType.VARCHAR, 512, RequiredType.OPTIONAL));

    // Add message_version
    columns.add(new Column("message_version", ColumnDataType.INTEGER, 11, RequiredType.REQUIRED));

    // Add message
    columns.add(new Column("message", ColumnDataType.LONGTEXT, RequiredType.REQUIRED));

    // Add principal
    columns.add(new Column("principal", ColumnDataType.VARCHAR, 100, RequiredType.OPTIONAL));

    // Add ip_address
    columns.add(new Column("ip_address", ColumnDataType.VARCHAR, 100, RequiredType.OPTIONAL));

    // Add occurred_on
    columns.add(new Column("occurred_on", ColumnDataType.DATETIME, RequiredType.OPTIONAL));

    return new Table(new TableName("domain_message"), columns, false);
  }
}
