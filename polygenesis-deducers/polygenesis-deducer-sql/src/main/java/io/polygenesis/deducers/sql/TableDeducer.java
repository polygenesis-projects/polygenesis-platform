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

import io.polygenesis.abstraction.data.Data;
import io.polygenesis.abstraction.data.DataObject;
import io.polygenesis.abstraction.data.DataPrimitive;
import io.polygenesis.abstraction.data.PrimitiveType;
import io.polygenesis.commons.text.TextConverter;
import io.polygenesis.commons.valueobjects.VariableName;
import io.polygenesis.models.domain.AggregateEntityCollection;
import io.polygenesis.models.domain.DomainObject;
import io.polygenesis.models.domain.DomainObjectProperty;
import io.polygenesis.models.sql.Column;
import io.polygenesis.models.sql.ColumnDataType;
import io.polygenesis.models.sql.RequiredType;
import io.polygenesis.models.sql.Table;
import io.polygenesis.models.sql.TableName;
import java.util.LinkedHashSet;
import java.util.Set;

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
   * @param domainObject the aggregate root
   * @return the set
   */
  public Set<Table> deduce(DomainObject domainObject) {
    Set<Table> allDomainObjectRelatedTables = new LinkedHashSet<>();

    if (domainObject.isAbstract()) {
      return allDomainObjectRelatedTables;
    }

    Set<Column> domainObjectColumns = new LinkedHashSet<>();

    fillDomainObjectColumns(allDomainObjectRelatedTables, domainObjectColumns, domainObject);

    // Add version
    domainObjectColumns.add(
        new Column("version", ColumnDataType.INTEGER, 11, RequiredType.REQUIRED));

    String tableName = domainObject.getObjectName().getText();

    if (domainObject.isAggregateEntity()) {
      tableName =
          TextConverter.toLowerUnderscore(domainObject.getParent().getObjectName().getText())
              + "_"
              + TextConverter.toLowerUnderscore(
                  TextConverter.toPlural(domainObject.getObjectName().getText()));
    }

    Table domainObjectTable =
        new Table(new TableName(tableName), domainObjectColumns, domainObject.getMultiTenant());

    allDomainObjectRelatedTables.add(domainObjectTable);

    return allDomainObjectRelatedTables;
  }

  private void fillDomainObjectColumns(
      Set<Table> allDomainObjectRelatedTables,
      Set<Column> domainObjectColumns,
      DomainObject domainObject) {
    domainObject
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case ABSTRACT_AGGREGATE_ROOT_ID:
                  break;

                case PROJECTION_ID:
                  domainObjectColumns.add(
                      new Column(
                          "projection_id",
                          ColumnDataType.BINARY,
                          16,
                          0,
                          RequiredType.REQUIRED,
                          true));
                  break;

                case REFERENCE_BY_ID:
                  domainObjectColumns.addAll(
                      getColumnsForValueObject(property.getData().getAsDataObject()));
                  break;

                case REFERENCE_TO_AGGREGATE_ROOT:
                case AGGREGATE_ROOT_ID:
                  domainObjectColumns.add(
                      new Column(
                          "root_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, true));
                  break;
                case TENANT_ID:
                  domainObjectColumns.add(
                      new Column(
                          "tenant_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, true));
                  break;
                case AGGREGATE_ENTITY_ID:
                  domainObjectColumns.add(
                      new Column(
                          "entity_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, true));
                  break;
                case PRIMITIVE:
                  domainObjectColumns.add(getColumnForPrimitive(property.getData(), ""));
                  break;
                case PRIMITIVE_COLLECTION:
                  allDomainObjectRelatedTables.add(
                      getTableForPrimitiveCollection(domainObject, property));
                  break;
                case VALUE_OBJECT:
                  Set<Column> columns = new LinkedHashSet<>();
                  getColumnsForValueObject(columns, property.getData().getAsDataObject());
                  domainObjectColumns.addAll(columns);
                  break;
                case VALUE_OBJECT_COLLECTION:
                  allDomainObjectRelatedTables.add(
                      getTableForValueObjectCollection(domainObject, property));
                  break;
                case AGGREGATE_ENTITY:
                  domainObjectColumns.addAll(
                      getColumnsForValueObject(property.getData().getAsDataObject()));
                  break;
                case AGGREGATE_ENTITY_COLLECTION:
                  // TODO
                  //                  AggregateEntityCollection aggregateEntityCollection =
                  //                      (AggregateEntityCollection) property;
                  //                  allDomainObjectRelatedTables.add(
                  //                      getTableForAggregateEntityCollection(
                  //                          domainObject,
                  // aggregateEntityCollection.getAggregateEntity(), property));
                  break;
                case MAP:
                  domainObjectColumns.addAll(getColumnsForMap());
                  break;
                case ENUMERATION:
                  domainObjectColumns.add(getColumnForEnumeration(property.getData(), ""));
                  break;
                default:
                  throw new IllegalArgumentException(
                      String.format(
                          CANNOT_CONVERT_TO_SQL_COLUMN,
                          property.getData().getVariableName().getText(),
                          property.getPropertyType().name()));
              }
            });

    if (domainObject.getSuperClass() != null) {
      DomainObject superClass = domainObject.getSuperClass();

      fillDomainObjectColumns(allDomainObjectRelatedTables, domainObjectColumns, superClass);
    }
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
            case REFERENCE_TO_ABSTRACT_AGGREGATE_ROOT:
              break;
            case PRIMITIVE:
              columns.add(getColumnForPrimitive(property.getData(), ""));
              break;
            case REFERENCE_BY_ID:
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
   * @param domainObject the base domain object
   * @return the tables by properties
   */
  protected Set<Table> getTablesByProperties(DomainObject domainObject) {
    Set<Table> tables = new LinkedHashSet<>();

    domainObject
        .getProperties()
        .forEach(
            property -> {
              switch (property.getPropertyType()) {
                case AGGREGATE_ROOT_ID:
                  break;
                case PRIMITIVE:
                  break;
                case PRIMITIVE_COLLECTION:
                  tables.add(getTableForPrimitiveCollection(domainObject, property));
                  break;
                case VALUE_OBJECT:
                  break;
                case VALUE_OBJECT_COLLECTION:
                  tables.add(getTableForValueObjectCollection(domainObject, property));
                  break;
                case AGGREGATE_ENTITY:
                  break;
                case AGGREGATE_ENTITY_COLLECTION:
                  AggregateEntityCollection aggregateEntityCollection =
                      (AggregateEntityCollection) property;
                  tables.add(
                      getTableForAggregateEntityCollection(
                          domainObject, aggregateEntityCollection.getAggregateEntity(), property));
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
              // TODO vo
              //              else if (model.isDataGroup()) {
              //                ; // getColumnsForValueObject(model.getAsDataObject());
              //              }

            });

    return columns;
  }

  private void getColumnsForValueObject(Set<Column> columns, DataObject dataObject) {
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
              } else if (model.isDataGroup()) {
                getColumnsForValueObject(columns, model.getAsDataObject());
              } else {
                throw new IllegalStateException();
              }
              // TODO vo
              //              else if (model.isDataGroup()) {
              //                ; // getColumnsForValueObject(model.getAsDataObject());
              //              }

            });
  }

  private Column getColumnForEnumeration(Data data, String columnPrefix) {
    ColumnDataType columnDataType = ColumnDataType.VARCHAR;

    int length = 50;

    return new Column(
        String.format("%s%s", columnPrefix, data.getVariableName().getText()),
        columnDataType,
        length,
        RequiredType.OPTIONAL);
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
   * @param domainObject the aggregate root
   * @param property the property
   * @return the table for primitive collection
   */
  private Table getTableForPrimitiveCollection(
      DomainObject domainObject, DomainObjectProperty<?> property) {
    Set<Column> columns = new LinkedHashSet<>();

    addAggregateRootIdInColumnSetWithoutPrimaryKey(columns, domainObject);

    columns.add(getColumnForPrimitive(property.getTypeParameterData(), ""));

    return new Table(
        new TableName(
            String.format(
                DOUBLE_S,
                TextConverter.toLowerUnderscore(domainObject.getObjectName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        domainObject.getMultiTenant());
  }

  /**
   * Gets table for value object collection.
   *
   * @param domainObject the aggregate root
   * @param property the property
   * @return the table for value object collection
   */
  private Table getTableForValueObjectCollection(
      DomainObject domainObject, DomainObjectProperty<?> property) {
    Set<Column> columns = new LinkedHashSet<>();

    return new Table(
        new TableName(
            String.format(
                DOUBLE_S,
                TextConverter.toLowerUnderscore(domainObject.getObjectName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        domainObject.getMultiTenant());
  }

  /**
   * Gets table for aggregate entity collection.
   *
   * @param domainObjectChild the aggregate root
   * @param property the property
   * @return the table for aggregate entity collection
   */
  private Table getTableForAggregateEntityCollection(
      DomainObject domainObjectParent,
      DomainObject domainObjectChild,
      DomainObjectProperty<?> property) {
    Set<Column> columns = new LinkedHashSet<>();

    addAggregateRootIdInColumnSetAsPrimaryKeyForAggregateEntity(columns, domainObjectParent);
    addAggregateEntityIdInColumnSet(columns);

    columns.addAll(getColumnsByProperties(domainObjectChild.getProperties()));

    // Add version
    columns.add(new Column("version", ColumnDataType.INTEGER, 11, RequiredType.REQUIRED));

    return new Table(
        new TableName(
            String.format(
                DOUBLE_S,
                TextConverter.toLowerUnderscore(domainObjectParent.getObjectName().getText()),
                TextConverter.toLowerUnderscore(property.getData().getVariableName().getText()))),
        columns,
        domainObjectChild.getMultiTenant());
  }

  private void addAggregateRootIdInColumnSetAsPrimaryKeyForAggregateEntity(
      Set<Column> columns, DomainObject domainObject) {
    addAggregateRootIdInColumnSet(columns, domainObject, true, false);
  }

  private void addAggregateRootIdInColumnSetWithoutPrimaryKey(
      Set<Column> columns, DomainObject domainObject) {
    addAggregateRootIdInColumnSet(columns, domainObject, false, false);
  }

  private void addAggregateRootIdInColumnSet(
      Set<Column> columns,
      DomainObject domainObject,
      boolean addAsPrimaryKey,
      boolean addTenantId) {
    // Add Object Id
    columns.add(
        new Column(
            "root_id", ColumnDataType.BINARY, 16, 0, RequiredType.REQUIRED, addAsPrimaryKey));

    if (domainObject.getMultiTenant() && addTenantId) {
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
}
