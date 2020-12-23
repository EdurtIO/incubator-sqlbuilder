package io.edurt.sqlbuilder.spi.process;

import io.edurt.sqlbuilder.spi.exception.SqlConvertException;
import io.edurt.sqlbuilder.spi.model.Query;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;

import static java.lang.String.format;

/**
 * presto sql processor
 *
 * @see Processor
 */
public class PrestoProcessor
        implements Processor
{
    @Override
    public String generateQuery(Query body)
            throws SqlConvertException
    {
        if (ObjectUtils.isEmpty(body)) {
            LOGGER.warn("sql generate error, query body must not null, time {}", LocalDateTime.now());
            throw new SqlConvertException(format("sql generate error, query body must not null, time %s", LocalDateTime.now()));
        }
        StringBuilder builder = this.parseQuery(body)
                .parseRelation(body)
                .parseCondition(body)
                .getBuilder();
        return builder.toString();
    }
}
