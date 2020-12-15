package io.edurt.sqlbuilder.spi.common;

/**
 * sql operation, only support SELECT
 */
public enum SqlOperation
{
    SELECT("SELECT");

    private String value;

    SqlOperation(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
