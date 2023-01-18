package org.kava.barattolo.entity.proxy;

import org.kava.lungo.Level;
import org.kava.lungo.Logger;
import org.kava.lungo.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class LazyLoadingInvocationHandler implements InvocationHandler {
    private final Logger logger = LoggerFactory.getLogger(LazyLoadingInvocationHandler.class, Level.DEBUG);
    private final Supplier<Object> objectSupplier;
    private Object loadedObject;

    public LazyLoadingInvocationHandler(Supplier<Object> objectSupplier) {
        this.objectSupplier = objectSupplier;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO: Thread safety.
        if (loadedObject == null) {
            logger.debug("Loaded object is null, retrieving from database...");
            loadedObject = objectSupplier.get();
        }

        return method.invoke(loadedObject, args);
    }
}
