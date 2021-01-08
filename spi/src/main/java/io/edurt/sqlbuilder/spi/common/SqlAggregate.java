package io.edurt.sqlbuilder.spi.common;

/**
 * sql aggregate
 */
public enum SqlAggregate
{
    GROUP_BY("GROUP BY");
    private final String value;

    SqlAggregate(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
