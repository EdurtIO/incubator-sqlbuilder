package io.edurt.sqlbuilder.spi.process;

import io.edurt.sqlbuilder.common.jdk.ObjectBuilder;
import io.edurt.sqlbuilder.spi.common.SqlAggregate;
import io.edurt.sqlbuilder.spi.common.SqlExpression;
import io.edurt.sqlbuilder.spi.common.SqlOperation;
import io.edurt.sqlbuilder.spi.common.SqlRelation;
import io.edurt.sqlbuilder.spi.exception.SqlConvertException;
import io.edurt.sqlbuilder.spi.model.Action;
import io.edurt.sqlbuilder.spi.model.Aggregate;
import io.edurt.sqlbuilder.spi.model.Condition;
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
        System.out.println("-------- query test case --------");
        Processor processor = body -> null;
        System.out.println(processor.parseQuery(query).getBuilder().toString());
    }

    @Test
    public void test_parseRelation()
            throws SqlConvertException
    {
        System.out.println("-------- relation test case --------");
        Processor processor = body -> null;
        System.out.println(processor.parseRelation(query));
    }

    @Test
    public void test_parseCondition_one()
    {
        System.out.println("-------- one condition test case --------");
        Condition condition = ObjectBuilder.of(Condition::new)
                .with(Condition::setColumn, "name")
                .with(Condition::setExpression, SqlExpression.EQ)
                .with(Condition::setValues, Arrays.asList("sqlbuilder"))
                .build();
        query.setCondition(Arrays.asList(condition));
        Processor processor = body -> null;
        System.out.println(processor.parseCondition(query));
    }

    @Test
    public void test_parseCondition_multiple()
    {
        System.out.println("-------- multiple condition test case --------");
        Condition condition = ObjectBuilder.of(Condition::new)
                .with(Condition::setColumn, "id")
                .with(Condition::setExpression, SqlExpression.EQ)
                .with(Condition::setValues, Arrays.asList("1", "2"))
                .build();
        query.setCondition(Arrays.asList(condition));
        Processor processor = body -> null;
        System.out.println(processor.parseCondition(query));
    }

    @Test
    public void test_parseCondition_gt()
    {
        System.out.println("-------- gt condition test case --------");
        Condition condition = ObjectBuilder.of(Condition::new)
                .with(Condition::setColumn, "id")
                .with(Condition::setExpression, SqlExpression.GREAT)
                .with(Condition::setValues, Arrays.asList("1"))
                .build();
        query.setCondition(Arrays.asList(condition));
        Processor processor = body -> null;
        System.out.println(processor.parseCondition(query));
    }

    @Test
    public void test_parseCondition_lt()
    {
        System.out.println("-------- lt condition test case --------");
        Condition condition = ObjectBuilder.of(Condition::new)
                .with(Condition::setColumn, "id")
                .with(Condition::setExpression, SqlExpression.LESS)
                .with(Condition::setValues, Arrays.asList("1"))
                .build();
        query.setCondition(Arrays.asList(condition));
        Processor processor = body -> null;
        System.out.println(processor.parseCondition(query));
    }

    @Test
    public void test_parseAggregate()
    {
        System.out.println("-------- aggregate test case --------");
        query.setAggregates(Arrays.asList(ObjectBuilder.of(Aggregate::new)
                .with(Aggregate::setAggregate, SqlAggregate.GROUP_BY)
                .with(Aggregate::setValues, Arrays.asList("name", "id"))
                .build()));
        Processor processor = body -> null;
        System.out.println(processor.parseAggregate(query).getBuilder().toString());
    }
}
