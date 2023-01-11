package org.kava.barattolo.entity.proxy;

import jakarta.persistence.EntityManager;
import org.kava.lungo.Level;
import org.kava.lungo.Logger;
import org.kava.lungo.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LazyLoadingInvocationHandler implements InvocationHandler {
    private final Logger logger = LoggerFactory.getLogger(LazyLoadingInvocationHandler.class, Level.DEBUG);
    private final Class<?> objectClass;
    private final Object primaryKey;
    private final EntityManager entityManager; // TODO: it probably shouldn't know about entity manager.
    private Object loadedObject;

    public LazyLoadingInvocationHandler(Object primaryKey, EntityManager entityManager, Class<?> objectClass) {
        this.primaryKey = primaryKey;
        this.loadedObject = null;
        this.entityManager = entityManager;
        this.objectClass = objectClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO: should it be thread safe?
        if (loadedObject == null) {
            logger.debug("Loaded object is null, retrieving from database...");
            loadedObject = loadObject();
        }

        return method.invoke(loadedObject, args);
    }

    private Object loadObject() {
        return entityManager.find(objectClass, primaryKey);
    }
}
