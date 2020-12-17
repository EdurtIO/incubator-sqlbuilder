package io.edurt.sqlbuilder.spi.common;

/**
 * sql relation
 */
public enum SqlRelation
{
    FROM("FROM");
    private String value;

    SqlRelation(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
