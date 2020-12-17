package io.edurt.sqlbuilder.common.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * builder a class tools
 *
 * @param <T> object class
 */
public class ObjectBuilder<T>
{
    private final Supplier<T> instantiate;
    private List<Consumer<T>> modifiers = new ArrayList<>();

    public ObjectBuilder(Supplier<T> instantiate)
    {
        this.instantiate = instantiate;
    }

    public static <T> ObjectBuilder<T> of(Supplier<T> instantiate)
    {
        return new ObjectBuilder<>(instantiate);
    }

    public <P1> ObjectBuilder<T> with(ConsumerWithOne<T, P1> consumer, P1 p1)
    {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        modifiers.add(c);
        return this;
    }

    public <P1, P2> ObjectBuilder<T> with(ConsumerWithTwo<T, P1, P2> consumer, P1 p1, P2 p2)
    {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
        modifiers.add(c);
        return this;
    }

    public <P1, P2, P3> ObjectBuilder<T> with(ConsumerWithThree<T, P1, P2, P3> consumer, P1 p1, P2 p2, P3 p3)
    {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3);
        modifiers.add(c);
        return this;
    }

    public T build()
    {
        T value = instantiate.get();
        modifiers.forEach(modifier -> modifier.accept(value));
        modifiers.clear();
        return value;
    }

    @FunctionalInterface
    public interface ConsumerWithOne<T, P1>
    {
        void accept(T t, P1 p1);
    }

    @FunctionalInterface
    public interface ConsumerWithTwo<T, P1, P2>
    {
        void accept(T t, P1 p1, P2 p2);
    }

    @FunctionalInterface
    public interface ConsumerWithThree<T, P1, P2, P3>
    {
        void accept(T t, P1 p1, P2 p2, P3 p3);
    }
}
