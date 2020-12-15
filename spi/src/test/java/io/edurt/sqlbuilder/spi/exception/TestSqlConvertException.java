package io.edurt.sqlbuilder.spi.exception;

import org.junit.Test;

public class TestSqlConvertException
{
    @Test
    public void test_1()
    {
        try {
            throw new SqlConvertException("test exception case");
        }
        catch (SqlConvertException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_2()
    {
        try {
            throw new SqlConvertException("test exception case", new RuntimeException());
        }
        catch (SqlConvertException e) {
            e.printStackTrace();
        }
    }
}
