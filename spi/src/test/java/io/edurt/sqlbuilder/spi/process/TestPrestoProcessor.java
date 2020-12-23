package io.edurt.sqlbuilder.spi.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.edurt.sqlbuilder.common.jdk.ObjectBuilder;
import io.edurt.sqlbuilder.spi.common.SqlExpression;
import io.edurt.sqlbuilder.spi.common.SqlOperation;
import io.edurt.sqlbuilder.spi.common.SqlRelation;
import io.edurt.sqlbuilder.spi.model.Action;
import io.edurt.sqlbuilder.spi.model.Condition;
import io.edurt.sqlbuilder.spi.model.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestPrestoProcessor
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
    public void test_generateQuery()
            throws Exception
    {
        System.out.println("-------- query generate case --------");
        System.out.println(new ObjectMapper().writeValueAsString(query));
        PrestoProcessor processor = ObjectBuilder.of(PrestoProcessor::new).build();
        System.out.println(processor.generateQuery(query));
    }

    @Test
    public void test_generateQuery_condition()
            throws Exception
    {
        System.out.println("-------- query generate and condition case --------");
        Condition condition = ObjectBuilder.of(Condition::new)
                .with(Condition::setColumn, "name")
                .with(Condition::setExpression, SqlExpression.EQ)
                .with(Condition::setValues, Arrays.asList("sqlbuilder"))
                .build();
        query.setCondition(Arrays.asList(condition));
        System.out.println(new ObjectMapper().writeValueAsString(query));
        PrestoProcessor processor = ObjectBuilder.of(PrestoProcessor::new).build();
        System.out.println(processor.generateQuery(query));
    }
}
