package com.kava.manager;

public record ConnectionConfig(String driverClass,
                               String url,
                               String username,
                               String password) {
}
