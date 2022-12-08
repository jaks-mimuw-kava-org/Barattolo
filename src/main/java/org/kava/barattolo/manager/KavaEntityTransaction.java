package org.kava.barattolo.manager;

import jakarta.persistence.EntityTransaction;

// TODO
public class KavaEntityTransaction implements EntityTransaction {
    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void setRollbackOnly() {

    }

    @Override
    public boolean getRollbackOnly() {
        return false;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
