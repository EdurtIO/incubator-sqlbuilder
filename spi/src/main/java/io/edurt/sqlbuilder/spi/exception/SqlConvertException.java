package io.edurt.sqlbuilder.spi.exception;

public class SqlConvertException
        extends Exception
{
    public SqlConvertException(String message)
    {
        super(message);
    }

    public SqlConvertException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
