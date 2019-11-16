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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.BaseDomainEntity;
import io.polygenesis.models.domain.BaseDomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.domain.DomainObjectType;
import io.polygenesis.models.domain.InstantiationType;
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

  private static final String CANNOT_CONVERT_TO_SQL_COLUMN =
      "Cannot convert to SQL column property=%s with type=%s";

  private static final String DOUBLE_S = "%s_%s";

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
   * @param domainEntity the aggregate root
   * @return the set
   */
  public Set<Table> deduce(BaseDomainEntity domainEntity) {
    Set<Table> allDomainEntityRelatedTables = new LinkedHashSet<>();

    if (domainEntity.getDomainObjectType().equals(DomainObjectType.ABSTRACT_AGGREGATE_ROOT)
        || domainEntity.getInstantiationType().equals(InstantiationType.ABSTRACT)) {
      return allDomainEntityRelatedTables;
    }

    Set<Column> domainEntityColumns = new LinkedHashSet<>();

    if (domainEntity.getDomainObjectType().equals(DomainObjectType.AGGREGATE_ROOT)) {
      addAggregateRootIdInColumnSetAsPrimaryKey(domainEntityColumns, domainEntity);
    }

    if (domainEntity.getDomainObjectType().equals(DomainObjectType.PROJECTION)) {
      addProjectionIdInColumnSet(domainEntityColumns);
    }

    domainEntity
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case AGGREGATE_ROOT_ID:
                case ABSTRACT_AGGREGATE_ROOT_ID:
                case PROJECTION_ID:
                case TENANT_ID:
                  break;
                case PRIMITIVE:
                  domainEntityColumns.add(getColumnForPrimitive(property.getData(), ""));
                  break;
                case PRIMITIVE_COLLECTION:
                  allDomainEntityRelatedTables.add(
                      getTableForPrimitiveCollection(domainEntity, property));
                  break;
                case VALUE_OBJECT:
                  domainEntityColumns.addAll(
                      getColumnsForValueObject(property.getData().getAsDataObject()));
                  break;
                case VALUE_OBJECT_COLLECTION:
                  allDomainEntityRelatedTables.add(
                      getTableForValueObjectCollection(domainEntity, property));
                  break;
                case AGGREGATE_ENTITY:
                  domainEntityColumns.addAll(
                      getColumnsForValueObject(property.getData().getAsDataObject()));
                  break;
                case AGGREGATE_ENTITY_COLLECTION:
                  AggregateEntityCollection aggregateEntityCollection =
                      (AggregateEntityCollection) property;
                  allDomainEntityRelatedTables.add(
                      getTableForAggregateEntityCollection(
                          domainEntity, aggregateEntityCollection.getAggregateEntity(), property));
                  break;
                case MAP:
                  domainEntityColumns.addAll(getColumnsForMap());
                  break;
                default:
                  throw new IllegalArgumentException(
                      String.format(
                          CANNOT_CONVERT_TO_SQL_COLUMN,
                          property.getData().getVariableName().getText(),
                          property.getPropertyType().name()));
              }
            });

    // Add version
    domainEntityColumns.add(
        new Column("version", ColumnDataType.INTEGER, 11, RequiredType.REQUIRED));

    Table domainEntityTable =
        new Table(
            new TableName(domainEntity.getObjectName().getText()),
            domainEntityColumns,
            domainEntity.getMultiTenant());

    allDomainEntityRelatedTables.add(domainEntityTable);

    return allDomainEntityRelatedTables;
  }

  /**
   * Gets columns by properties.
   *
   * @param properties the properties
   * @return the columns by properties
   */
  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================
  protected Set<Column> getColumnsByProperties(Set<DomainObjectProperty<?>> properties) {
    Set<Column> columns = new LinkedHashSet<>();

    properties.forEach(
        property -> {
          switch (property.getPropertyType()) {
            case AGGREGATE_ROOT_ID:
            case AGGREGATE_ENTITY_ID:
            case REFERENCE_TO_AGGREGATE_ROOT:
              break;
            case PRIMITIVE:
              columns.add(getColumnForPrimitive(property.getData(), ""));
              break;
            case REFERENCE:
              columns.add(getColumnForReferenceToThing(""));
              break;
            case PRIMITIVE_COLLECTION:
              break;
            case VALUE_OBJECT:
              columns.addAll(getColumnsForValueObject(property.getData().getAsDataObject()));
              break;
            case VALUE_OBJECT_COLLECTION:
              break;
            case AGGREGATE_ENTITY:
              columns.addAll(getColumnsForValueObject(property.getData().getAsDataObject()));
              break;
            case AGGREGATE_ENTITY_COLLECTION:
              break;
            default:
              throw new IllegalArgumentException(
                  String.format(
                      CANNOT_CONVERT_TO_SQL_COLUMN,
                      property.getData().getVariableName().getText(),
                      property.getPropertyType().name()));
          }
        });

    return columns;
  }

  /**
   * Gets tables by properties.
   *
   * @param baseDomainObject the base domain object
   * @return the tables by properties
   */
  protected Set<Table> getTablesByProperties(BaseDomainObject baseDomainObject) {
    Set<Table> tables = new LinkedHashSet<>();

    baseDomainObject
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case AGGREGATE_ROOT_ID:
                  break;
                case PRIMITIVE:
                  break;
                case PRIMITIVE_COLLECTION:
                  tables.add(getTableForPrimitiveCollection(baseDomainObject, property));
                  break;
                case VALUE_OBJECT:
                  break;
                case VALUE_OBJECT_COLLECTION:
                  tables.add(getTableForValueObjectCollection(baseDomainObject, property));
                  break;
                case AGGREGATE_ENTITY:
                  break;
                case AGGREGATE_ENTITY_COLLECTION:
                  AggregateEntityCollection aggregateEntityCollection =
                      (AggregateEntityCollection) property;
                  tables.add(
                      getTableForAggregateEntityCollection(
                          baseDomainObject,
                          aggregateEntityCollection.getAggregateEntity(),
                          property));
                  break;
                default:
                  throw new IllegalArgumentException(
                      String.format(
                          CANNOT_CONVERT_TO_SQL_COLUMN,
                          property.getData().getVariableName().getText(),
                          property.getPropertyType().name()));
              }
            });

    return tables;
  }

  // ===============================================================================================
  // PRIVATE
  // ===============================================================================================

  /**
   * Gets column for reference to thing.
   *
   * @param columnPrefix the column prefix
   * @return the column for reference to thing
   */
  private Column getColumnForReferenceToThing(String columnPrefix) {
    // TODO
    DataPrimitive dataPrimitive = DataPrimitive.of(PrimitiveType.STRING, new VariableName("ref"));
    return getColumnForPrimitive(dataPrimitive, columnPrefix);
  }

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
    } else if (columnDataType.equals(ColumnDataType.BINARY)) {
      length = 16;
    } else if (columnDataType.equals(ColumnDataType.DECIMAL)) {
      return new Column(
          String.format("%s%s", columnPrefix, data.getVariableName().getText()),
          columnDataType,
          19,
          2,
          RequiredType.OPTIONAL);
    } else if (columnDataType.equals(ColumnDataType.DATE)
        || columnDataType.equals(ColumnDataType.DATETIME)) {
      length = 0;
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
   * @param dataObject the data group
   * @return the columns for value object
   */
  private Set<Column> getColumnsForValueObject(DataObject dataObject) {
    Set<Column> columns = new LinkedHashSet<>();

    dataObject
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
                                dataObject.getVariableName().getText()))));
              } else {
                throw new IllegalStateException();
              }
            });

    return columns;
  }

  /**
   * Gets columns for map.
   *
   * @return the columns for map
   */
  private Set<Column> getColumnsForMap() {
    Set<Column> columns = new LinkedHashSet<>();

    // TODO

    return columns;
  }

  /**
   * Gets table for primitive collection.
   *
   * @param baseDomainObject the aggregate root
   * @param property the property
   * @return the table for primitive collection
   */
  private Table getTableForPrimitiveCollection(
      BaseDomainObject baseDomainObject, DomainObjectProperty<?> property) {
    Set<Column> columns = new LinkedHashSet<>();

    addAggregateRootIdInColumnSetWithoutPrimaryKey(columns, baseDomainObject);

    columns.add(getColumnForPrimitive(property.getTypeParameterData(), ""));

    return new Table(
        new TableName(
            String.format(
                DOUBLE_S,
                TextConverter.toLowerUnderscore(baseDomainObject.getObjectName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        baseDomainObject.getMultiTenant());
  }

  /**
   * Gets table for value object collection.
   *
   * @param baseDomainObject the aggregate root
   * @param property the property
   * @return the table for value object collection
   */
  private Table getTableForValueObjectCollection(
      BaseDomainObject baseDomainObject, DomainObjectProperty<?> property) {
    Set<Column> columns = new LinkedHashSet<>();

    return new Table(
        new TableName(
            String.format(
                DOUBLE_S,
                TextConverter.toLowerUnderscore(baseDomainObject.getObjectName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        baseDomainObject.getMultiTenant());
  }

  /**
   * Gets table for aggregate entity collection.
   *
   * @param baseDomainObjectChild the aggregate root
   * @param property the property
   * @return the table for aggregate entity collection
   */
  private Table getTableForAggregateEntityCollection(
      BaseDomainObject baseDomainObjectParent,
      BaseDomainObject baseDomainObjectChild,
      DomainObjectProperty<?> property) {
    Set<Column> columns = new LinkedHashSet<>();

    addAggregateRootIdInColumnSetAsPrimaryKey(columns, baseDomainObjectParent);
    addAggregateEntityIdInColumnSet(columns);

    columns.addAll(getColumnsByProperties(baseDomainObjectChild.getProperties()));

    // Add version
    columns.add(new Column("version", ColumnDataType.INTEGER, 11, RequiredType.REQUIRED));

    return new Table(
        new TableName(
            String.format(
                DOUBLE_S,
                TextConverter.toLowerUnderscore(baseDomainObjectParent.getObjectName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        baseDomainObjectChild.getMultiTenant());
  }

  /**
   * Add aggregate root id in column set as primary key.
   *
   * @param columns the columns
   * @param baseDomainObject the aggregate root
   */
  private void addAggregateRootIdInColumnSetAsPrimaryKey(
      Set<Column> columns, BaseDomainObject baseDomainObject) {
    addAggregateRootIdInColumnSet(columns, baseDomainObject, true);
  }

  /**
   * Add aggregate root id in column set without primary key.
   *
   * @param columns the columns
   * @param baseDomainObject the aggregate root
   */
  private void addAggregateRootIdInColumnSetWithoutPrimaryKey(
      Set<Column> columns, BaseDomainObject baseDomainObject) {
    addAggregateRootIdInColumnSet(columns, baseDomainObject, false);
  }

  /**
   * Add aggregate root id in column set.
   *
   * @param columns the columns
   * @param baseDomainObject the aggregate root
   * @param addAsPrimaryKey the add as primary key
   */
  private void addAggregateRootIdInColumnSet(
      Set<Column> columns, BaseDomainObject baseDomainObject, boolean addAsPrimaryKey) {
    // Add Object Id
    columns.add(
        new Column(
            "root_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, addAsPrimaryKey));

    if (baseDomainObject.getMultiTenant()) {
      // Add Tenant Id
      columns.add(
          new Column(
              "tenant_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, addAsPrimaryKey));
    }
  }

  /**
   * Add aggregate enity id in column set.
   *
   * @param columns the columns
   */
  private void addAggregateEntityIdInColumnSet(Set<Column> columns) {
    columns.add(new Column("entity_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, true));
  }

  private void addProjectionIdInColumnSet(Set<Column> columns) {
    columns.add(
        new Column("projection_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, true));
  }
}
