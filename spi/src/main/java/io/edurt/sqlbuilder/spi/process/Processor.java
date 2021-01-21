package io.edurt.sqlbuilder.spi.process;

import io.edurt.sqlbuilder.spi.common.SqlCondition;
import io.edurt.sqlbuilder.spi.common.SqlExpression;
import io.edurt.sqlbuilder.spi.common.SqlOperation;
import io.edurt.sqlbuilder.spi.exception.SqlConvertException;
import io.edurt.sqlbuilder.spi.model.Action;
import io.edurt.sqlbuilder.spi.model.Aggregate;
import io.edurt.sqlbuilder.spi.model.Condition;
import io.edurt.sqlbuilder.spi.model.Query;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;

/**
 * sql generate processor
 */
public interface Processor
{
    Logger LOGGER = LoggerFactory.getLogger(Processor.class);
    StringBuilder builder = new StringBuilder();

    /**
     * generate sql body
     *
     * @param body query object
     * @return sql body for string
     */
    String generateQuery(Query body)
            throws SqlConvertException;

    /**
     * get sql string container
     *
     * @return StringBuilder container
     */
    default StringBuilder getBuilder()
    {
        return builder;
    }

    /**
     * parse query body, eg: SELECT *, SELECT id, SELECT id, name
     *
     * @param body query object
     * @return this instance
     * @throws SqlConvertException
     */
    default Processor parseQuery(Query body)
            throws SqlConvertException
    {
        if (ObjectUtils.isNotEmpty(body) && ObjectUtils.isNotEmpty(body.getAction())) {
            Action action = body.getAction();
            if (EnumUtils.isValidEnum(SqlOperation.class, action.getOperation().name())) {
                switch (action.getOperation()) {
                    case SELECT:
                        // fix VA_FORMAT_STRING_USES_NEWLINE for findbugs
                        builder.append(format("%s%n", action.getOperation().getValue()));
                        // not contains column or column is *, set *
                        if (ObjectUtils.isEmpty(action.getColumns()) ||
                                (action.getColumns().size() == 1 && action.getColumns().get(0).equals("*"))) {
                            builder.append("*\n");
                        }
                        // multiple column
                        else if (action.getColumns().size() > 1) {
                            builder.append(format("%s%n", String.join(", ", action.getColumns())));
                        }
                        // has a column and not is *
                        else {
                            builder.append(format("%s%n", action.getColumns().get(0)));
                        }
                        break;
                }
            }
            else {
                LOGGER.warn("sql convert error, not support action " + body.getAction().getOperation());
                throw new SqlConvertException("sql convert error, not support action " + body.getAction().getOperation());
            }
        }
        else {
            LOGGER.warn("sql convert error, body or action must not null!");
            throw new SqlConvertException("sql convert error, body or action must not null!");
        }
        return this;
    }

    /**
     * parse query relation, eg: FROM a, LEFT JOIN a
     *
     * @param body query object
     * @return this instance
     * @throws SqlConvertException
     */
    default Processor parseRelation(Query body)
            throws SqlConvertException
    {
        Action action = body.getAction();
        if (ObjectUtils.isNotEmpty(action) && ObjectUtils.isNotEmpty(action.getRelation())) {
            switch (action.getRelation()) {
                case FROM:
                    builder.append(String.format("%s%n%s", action.getRelation(), action.getTable()));
                    break;
            }
        }
        else {
            LOGGER.warn("sql convert error, sql action or relation must not null!");
            throw new SqlConvertException("sql convert error, sql action or relation must not null!");
        }
        return this;
    }

    /**
     * parse query condition, eg: WHERE a = 1
     *
     * @param body query object
     * @return this instance
     */
    default Processor parseCondition(Query body)
    {
        List<Condition> conditions = body.getCondition();
        builder.append(format("%n%s%n1 = 1 ", SqlCondition.WHERE));
        if (ObjectUtils.isNotEmpty(conditions) && conditions.size() > 0) {
            conditions.forEach(v -> {
                switch (v.getExpression()) {
                    case EQ:
                    case EQUAL:
                        if (v.getValues().size() == 1) {
                            builder.append(format("%s %s = %s%n", SqlCondition.AND, v.getColumn(), v.getValues().get(0)));
                        }
                        else {
                            builder.append(format("%s %s %s (%s)%n", SqlCondition.AND,
                                    v.getColumn(),
                                    SqlCondition.IN,
                                    join(", ", v.getValues())));
                        }
                        break;
                    case GT:
                    case GREAT:
                        builder.append(format("%s %s %s %s%n", SqlCondition.AND, v.getColumn(), SqlExpression.GT.getValue(), v.getValues().get(0)));
                        break;
                    case LT:
                    case LESS:
                        builder.append(format("%s %s %s %s%n", SqlCondition.AND, v.getColumn(), SqlExpression.LT.getValue(), v.getValues().get(0)));
                        break;
                }
            });
        }
        else {
            LOGGER.warn("sql condition is null, skip it!");
        }
        return this;
    }

    /**
     * arse query aggregate, eg: GROUP BY name
     *
     * @param body query object
     * @return this instance
     */
    default Processor parseAggregate(Query body)
    {
        List<Aggregate> aggregates = body.getAggregates();
        if (ObjectUtils.isNotEmpty(aggregates) && aggregates.size() > 0) {
            aggregates.forEach(v -> {
                switch (v.getAggregate()) {
                    case GROUP_BY:
                        LOGGER.info("builder sql {} aggregate from sql query", v.getAggregate());
                        if (v.getValues().size() == 1) {
                            builder.append(format("%n%s %s", v.getAggregate().getValue(), v.getValues().get(0)));
                        }
                        else {
                            builder.append(format("%n%s %s", v.getAggregate().getValue(), join(", ", v.getValues())));
                        }
                        break;
                }
            });
        }
        else {
            LOGGER.warn("sql aggregate is null, skip it!");
        }
        return this;
    }
}
