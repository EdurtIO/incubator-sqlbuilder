package io.edurt.sqlbuilder.spi.process;

import io.edurt.sqlbuilder.spi.common.SqlOperation;
import io.edurt.sqlbuilder.spi.exception.SqlConvertException;
import io.edurt.sqlbuilder.spi.model.Action;
import io.edurt.sqlbuilder.spi.model.Query;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

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
     * @return sql body for string
     */
    String generateQuery();

    /**
     * parse query body, eg: SELECT *, SELECT id, SELECT id, name
     *
     * @param body query object
     * @return query body string
     * @throws SqlConvertException
     */
    default StringBuilder parseQuery(Query body)
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
        return builder;
    }

    /**
     * parse query relation, eg: FROM a, LEFT JOIN a
     *
     * @param body query object
     * @return query body string
     * @throws SqlConvertException
     */
    default StringBuilder parseRelation(Query body)
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
        return builder;
    }
}
