package io.edurt.sqlbuilder.spi.common;

/**
 * sql condition
 */
public enum SqlCondition
{
    WHERE("WHERE"),
    AND("AND"),
    IN("IN");
    private String value;

    SqlCondition(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
