package org.kava.barattolo.spi;

import jakarta.persistence.spi.PersistenceProvider;
import jakarta.persistence.spi.PersistenceProviderResolver;

import java.util.List;

public class KavaPersistenceProviderResolver implements PersistenceProviderResolver {
    @Override
    public List<PersistenceProvider> getPersistenceProviders() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearCachedProviders() {
        throw new UnsupportedOperationException();
    }
}
