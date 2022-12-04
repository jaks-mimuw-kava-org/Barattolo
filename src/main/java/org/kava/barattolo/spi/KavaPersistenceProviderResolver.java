package org.kava.barattolo.spi;

import jakarta.persistence.spi.PersistenceProvider;
import jakarta.persistence.spi.PersistenceProviderResolver;

import java.util.List;

public class KavaPersistenceProviderResolver implements PersistenceProviderResolver {
    @Override
    public List<PersistenceProvider> getPersistenceProviders() {
        return List.of(
                new KavaPersistenceProvider()
        );
    }

    @Override
    public void clearCachedProviders() {
        throw new UnsupportedOperationException();
    }
}
