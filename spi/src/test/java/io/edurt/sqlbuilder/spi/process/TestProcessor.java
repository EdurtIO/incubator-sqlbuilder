package io.edurt.sqlbuilder.spi.process;

import io.edurt.sqlbuilder.common.jdk.ObjectBuilder;
import io.edurt.sqlbuilder.spi.common.SqlOperation;
import io.edurt.sqlbuilder.spi.common.SqlRelation;
import io.edurt.sqlbuilder.spi.exception.SqlConvertException;
import io.edurt.sqlbuilder.spi.model.Action;
import io.edurt.sqlbuilder.spi.model.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestProcessor
{
    private Query query;

    @Before
    public void before()
    {
        Action action = ObjectBuilder.of(Action::new)
                .with(Action::setTable, "test")
                .with(Action::setOperation, SqlOperation.SELECT)
                .with(Action::setColumns, Arrays.asList("id, name"))
                .with(Action::setRelation, SqlRelation.FROM)
                .build();
        query = ObjectBuilder.of(Query::new)
                .with(Query::setAction, action)
                .build();
    }

    @Test
    public void test_parseQuery()
            throws SqlConvertException
    {
        Processor processor = () -> null;
        System.out.println(processor.parseQuery(query));
    }

    @Test
    public void test_parseRelation()
            throws SqlConvertException
    {
        Processor processor = () -> null;
        System.out.println(processor.parseRelation(query));
    }
}
