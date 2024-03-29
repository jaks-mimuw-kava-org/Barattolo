package org.kava.barattolo.entity.proxy;

import jakarta.persistence.EntityManager;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import org.kava.barattolo.entity.object.ObjectFieldDefinition;
import org.kava.lungo.Level;
import org.kava.lungo.Logger;
import org.kava.lungo.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class LazyLoadingProxyBuilder {
    private final Logger logger = LoggerFactory.getLogger(LazyLoadingProxyBuilder.class, Level.DEBUG);

    public Object getProxy(ObjectFieldDefinition fieldDefinition,
                           Object databaseValue,
                           EntityManager entityManager) { // TODO: it probably shouldn't know about entity manager.
        logger.debug("Getting proxy for type %s with value %s", fieldDefinition.getFieldClass().toString(), databaseValue.toString());

        Supplier<Object> objectSupplier = () -> entityManager.find(fieldDefinition.getFieldClass(), databaseValue);
        LazyLoadingInvocationHandler invocationHandler = new LazyLoadingInvocationHandler(objectSupplier);

        // TODO: Once a subclass is generated it doesn't have to be re-generated.
        Class<?> proxyType = new ByteBuddy()
                .subclass(fieldDefinition.getFieldClass())
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(invocationHandler))
                .make()
                .load(fieldDefinition.getFieldClass().getClassLoader())
                .getLoaded();

        try {
            return proxyType.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
