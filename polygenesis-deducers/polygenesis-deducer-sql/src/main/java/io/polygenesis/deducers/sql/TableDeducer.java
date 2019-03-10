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

package io.polygenesis.deducers.sql;

import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.core.data.Data;
import io.polygenesis.core.data.DataGroup;
import io.polygenesis.models.domain.AbstractProperty;
import io.polygenesis.models.domain.AggregateRoot;
import io.polygenesis.models.sql.Column;
import io.polygenesis.models.sql.ColumnDataType;
import io.polygenesis.models.sql.RequiredType;
import io.polygenesis.models.sql.Table;
import io.polygenesis.models.sql.TableName;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Table deducer.
 *
 * @author Christos Tsakostas
 */
public class TableDeducer {

  // ===============================================================================================
  // DEPENDENCIES
  // ===============================================================================================

  private final FromDataTypeToSqlColumnConverter fromDataTypeToSqlColumnConverter;

  // ===============================================================================================
  // CONSTRUCTOR(S)
  // ===============================================================================================

  /**
   * Instantiates a new Table deducer.
   *
   * @param fromDataTypeToSqlColumnConverter the from data type to sql column converter
   */
  public TableDeducer(FromDataTypeToSqlColumnConverter fromDataTypeToSqlColumnConverter) {
    this.fromDataTypeToSqlColumnConverter = fromDataTypeToSqlColumnConverter;
  }

  // ===============================================================================================
  // FUNCTIONALITY
  // ===============================================================================================

  /**
   * Deduce set.
   *
   * @param aggregateRoot the aggregate root
   * @return the set
   */
  public Set<Table> deduce(AggregateRoot aggregateRoot) {
    Set<Table> allAggregateRootRelatedTables = new LinkedHashSet<>();

    Set<Column> aggregateRootColumns = new LinkedHashSet<>();

    addAggregateRootIdInColumnSetAsPrimaryKey(aggregateRootColumns, aggregateRoot);

    aggregateRoot
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case AGGREGATE_ROOT_ID:
                  break;
                case PRIMITIVE:
                  aggregateRootColumns.add(getColumnForPrimitive(property.getData(), ""));
                  break;
                case PRIMITIVE_COLLECTION:
                  allAggregateRootRelatedTables.add(
                      getTableForPrimitiveCollection(aggregateRoot, property));
                  break;
                case VALUE_OBJECT:
                  aggregateRootColumns.addAll(
                      getColumnsForValueObject(property.getData().getAsDataGroup()));
                  break;
                case VALUE_OBJECT_COLLECTION:
                  allAggregateRootRelatedTables.add(
                      getTableForValueObjectCollection(aggregateRoot, property));
                  break;
                default:
                  throw new IllegalArgumentException(
                      String.format(
                          "Cannot convert to SQL column property=%s with type=%s",
                          property.getVariableName().getText(), property.getPropertyType().name()));
              }
            });

    // Add version
    aggregateRootColumns.add(
        new Column("version", ColumnDataType.INTEGER, 11, RequiredType.REQUIRED));

    Table mainAggregateRootTable =
        new Table(
            new TableName(aggregateRoot.getName().getText()),
            aggregateRootColumns,
            aggregateRoot.getMultiTenant());

    allAggregateRootRelatedTables.add(mainAggregateRootTable);

    return allAggregateRootRelatedTables;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Gets column for primitive.
   *
   * @param data the data
   * @return the column for primitive
   */
  private Column getColumnForPrimitive(Data data, String columnPrefix) {
    ColumnDataType columnDataType = fromDataTypeToSqlColumnConverter.getColumnDataTypeBy(data);

    int length = 100;

    if (columnDataType.equals(ColumnDataType.BIT)) {
      length = 1;
    }

    return new Column(
        String.format("%s%s", columnPrefix, data.getVariableName().getText()),
        columnDataType,
        length,
        RequiredType.OPTIONAL);
  }

  /**
   * Gets columns for value object.
   *
   * @param dataGroup the data group
   * @return the columns for value object
   */
  private Set<Column> getColumnsForValueObject(DataGroup dataGroup) {
    Set<Column> columns = new LinkedHashSet<>();

    dataGroup
        .getModels()
        .forEach(
            model -> {
              if (model.isDataPrimitive()) {
                columns.add(
                    getColumnForPrimitive(
                        model,
                        String.format(
                            "%s_",
                            TextConverter.toLowerUnderscore(
                                dataGroup.getVariableName().getText()))));
              } else {
                throw new IllegalStateException();
              }
            });

    return columns;
  }

  /**
   * Gets table for primitive collection.
   *
   * @param aggregateRoot the aggregate root
   * @param property the property
   * @return the table for primitive collection
   */
  private Table getTableForPrimitiveCollection(
      AggregateRoot aggregateRoot, AbstractProperty property) {
    Set<Column> columns = new LinkedHashSet<>();

    addAggregateRootIdInColumnSetWithoutPrimaryKey(columns, aggregateRoot);

    columns.add(getColumnForPrimitive(property.getTypeParameterData(), ""));

    return new Table(
        new TableName(
            String.format(
                "%s_%s",
                TextConverter.toLowerUnderscore(aggregateRoot.getName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        aggregateRoot.getMultiTenant());
  }

  /**
   * Gets table for value object collection.
   *
   * @param aggregateRoot the aggregate root
   * @param property the property
   * @return the table for value object collection
   */
  private Table getTableForValueObjectCollection(
      AggregateRoot aggregateRoot, AbstractProperty property) {
    Set<Column> columns = new LinkedHashSet<>();

    return new Table(
        new TableName(
            String.format(
                "%s_%s",
                TextConverter.toLowerUnderscore(aggregateRoot.getName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        aggregateRoot.getMultiTenant());
  }

  /**
   * Add aggregate root id in column set as primary key.
   *
   * @param columns the columns
   * @param aggregateRoot the aggregate root
   */
  private void addAggregateRootIdInColumnSetAsPrimaryKey(
      Set<Column> columns, AggregateRoot aggregateRoot) {
    addAggregateRootIdInColumnSet(columns, aggregateRoot, true);
  }

  /**
   * Add aggregate root id in column set without primary key.
   *
   * @param columns the columns
   * @param aggregateRoot the aggregate root
   */
  private void addAggregateRootIdInColumnSetWithoutPrimaryKey(
      Set<Column> columns, AggregateRoot aggregateRoot) {
    addAggregateRootIdInColumnSet(columns, aggregateRoot, false);
  }

  /**
   * Add aggregate root id in column set.
   *
   * @param columns the columns
   * @param aggregateRoot the aggregate root
   * @param addAsPrimaryKey the add as primary key
   */
  private void addAggregateRootIdInColumnSet(
      Set<Column> columns, AggregateRoot aggregateRoot, boolean addAsPrimaryKey) {
    // Add Object Id
    columns.add(
        new Column("root_id", ColumnDataType.BINARY, 16, RequiredType.REQUIRED, addAsPrimaryKey));

    if (aggregateRoot.getMultiTenant()) {
      // Add Tenant Id
      columns.add(
          new Column(
              "tenant_id", ColumnDataType.BINARY, 16, RequiredType.REQUIRED, addAsPrimaryKey));
    }
  }
}
