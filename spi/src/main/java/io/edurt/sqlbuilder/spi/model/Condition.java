package io.edurt.sqlbuilder.spi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.edurt.sqlbuilder.spi.common.SqlExpression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Sql condition body, eg: id = 12
 *
 * @see SqlExpression
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Condition
{
    @JsonProperty(value = "column")
    private String column; // column of table
    @JsonProperty(value = "expression")
    private SqlExpression expression; // expression of column
    @JsonProperty(value = "values")
    private List<String> values; // values of column
}
