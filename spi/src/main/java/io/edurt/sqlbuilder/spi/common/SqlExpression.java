package io.edurt.sqlbuilder.spi.common;

/**
 * sql expression
 */
public enum SqlExpression
{
    EQ("="),
    EQUAL("="),
    GT(">"),
    GREAT(">"),
    LESS("<"),
    LT("<");

    private String value;

    SqlExpression(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return this.value;
    }
}
