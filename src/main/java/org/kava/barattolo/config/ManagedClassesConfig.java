package org.kava.barattolo.config;

import java.util.List;

public record ManagedClassesConfig(
        List<String> managedClassNames
) {
}
