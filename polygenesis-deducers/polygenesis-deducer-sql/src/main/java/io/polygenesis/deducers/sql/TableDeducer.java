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
import io.polygenesis.core.data.IoModel;
import io.polygenesis.core.data.IoModelGroup;
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

    // Add Object Id
    aggregateRootColumns.add(
        new Column("root_id", ColumnDataType.BINARY, 16, RequiredType.REQUIRED, true));

    if (aggregateRoot.getMultiTenant()) {
      // Add Tenant Id
      aggregateRootColumns.add(
          new Column("tenant_id", ColumnDataType.BINARY, 16, RequiredType.REQUIRED, true));
    }

    aggregateRoot
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case AGGREGATE_ROOT_ID:
                  break;
                case PRIMITIVE:
                  aggregateRootColumns.add(getColumnForPrimitive(property.getIoModel(), ""));
                  break;
                case PRIMITIVE_COLLECTION:
                  allAggregateRootRelatedTables.add(
                      getTableForPrimitiveCollection(aggregateRoot, property));
                  break;
                case VALUE_OBJECT:
                  aggregateRootColumns.addAll(
                      getColumnsForValueObject(
                          property
                              .getIoModelGroupAsOptional()
                              .orElseThrow(IllegalArgumentException::new)));
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
   * @param ioModel the io model
   * @return the column for primitive
   */
  private Column getColumnForPrimitive(IoModel ioModel, String columnPrefix) {
    ColumnDataType columnDataType = fromDataTypeToSqlColumnConverter.getColumnDataTypeBy(ioModel);

    int length = 100;

    if (columnDataType.equals(ColumnDataType.BIT)) {
      length = 1;
    }

    return new Column(
        String.format("%s%s", columnPrefix, ioModel.getVariableName().getText()),
        columnDataType,
        length,
        RequiredType.OPTIONAL);
  }

  /**
   * Gets columns for value object.
   *
   * @param ioModelGroup the io model group
   * @return the columns for value object
   */
  private Set<Column> getColumnsForValueObject(IoModelGroup ioModelGroup) {
    Set<Column> columns = new LinkedHashSet<>();

    ioModelGroup
        .getModels()
        .forEach(
            model -> {
              if (model.isPrimitive()) {
                columns.add(
                    getColumnForPrimitive(
                        model,
                        String.format(
                            "%s_",
                            TextConverter.toLowerUnderscore(
                                ioModelGroup.getVariableName().getText()))));
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

    return new Table(
        new TableName(
            String.format(
                "%s_%s",
                aggregateRoot.getName().getText(),
                property.getIoModel().getVariableName().getText())),
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
                aggregateRoot.getName().getText(),
                property.getIoModel().getVariableName().getText())),
        columns,
        aggregateRoot.getMultiTenant());
  }
}
