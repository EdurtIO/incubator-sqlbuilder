package io.edurt.sqlbuilder.spi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.edurt.sqlbuilder.spi.common.SqlOperation;
import io.edurt.sqlbuilder.spi.common.SqlRelation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Sql action body, eg: SELECT *, SELECT id, name
 *
 * @see SqlOperation
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Action
{
    @JsonProperty(value = "table")
    private String table;
    @JsonProperty(value = "operation")
    private SqlOperation operation;
    @JsonProperty(value = "columns")
    private List<String> columns;
    @JsonProperty(value = "relation")
    private SqlRelation relation;
}
