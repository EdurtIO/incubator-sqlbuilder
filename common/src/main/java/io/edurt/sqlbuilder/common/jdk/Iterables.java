package io.edurt.sqlbuilder.common.jdk;

import java.util.function.BiConsumer;

/**
 * java iterable foreach(support index)
 *
 * @see java.util.Iterator
 */
public class Iterables
{
    private Iterables()
    {}

    /**
     * foreach enhanced version
     *
     * @param elements element list
     * @param action element structure (index, element)
     * @param <E> element object
     */
    public static <E> void forEach(Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action)
    {
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
