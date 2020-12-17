package io.edurt.sqlbuilder.common.jdk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestObjectBuilder
{
    private TestClass clazz;

    @Before
    public void before()
    {
        clazz = ObjectBuilder.of(TestClass::new)
                .build();
    }

    @Test
    public void test_notNull()
    {
        Assert.assertNotNull(clazz);
    }

    @Test
    public void test_withParameter()
    {
        clazz = ObjectBuilder.of(TestClass::new)
                .with(TestClass::setId, 1)
                .build();
        Assert.assertTrue(clazz.getId() != null);
        Assert.assertTrue(clazz.getId() > 0);
    }
}
