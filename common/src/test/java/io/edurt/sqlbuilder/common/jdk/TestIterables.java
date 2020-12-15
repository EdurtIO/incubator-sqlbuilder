package io.edurt.sqlbuilder.common.jdk;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestIterables
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestIterables.class);
    private List<String> list;

    @Before
    public void before()
    {
        list = new ArrayList<>();
        list.add("Sql");
        list.add("Builder");
        list.add("Incubator");
    }

    @Test
    public void test_forEach()
    {
        Iterables.forEach(list, (index, element) -> System.out.println(String.format("element index <%s>, element is <%s>", index, element)));
    }
}
