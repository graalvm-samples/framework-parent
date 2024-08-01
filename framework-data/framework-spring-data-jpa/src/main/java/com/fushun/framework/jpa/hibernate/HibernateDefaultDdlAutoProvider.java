package com.fushun.framework.jpa.hibernate;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;

import javax.sql.DataSource;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class HibernateDefaultDdlAutoProvider implements SchemaManagementProvider {
    private final Iterable<SchemaManagementProvider> providers;

    HibernateDefaultDdlAutoProvider(Iterable<SchemaManagementProvider> providers) {
        this.providers = providers;
    }

    public String getDefaultDdlAuto(DataSource dataSource) {
        if (!EmbeddedDatabaseConnection.isEmbedded(dataSource)) {
            return "none";
        } else {
            SchemaManagement schemaManagement = this.getSchemaManagement(dataSource);
            return SchemaManagement.MANAGED.equals(schemaManagement) ? "none" : "create-drop";
        }
    }

    @Override
    public SchemaManagement getSchemaManagement(DataSource dataSource) {
        Stream var10000 = StreamSupport.stream(this.providers.spliterator(), false).map((provider) -> {
            return provider.getSchemaManagement(dataSource);
        });
        SchemaManagement var10001 = SchemaManagement.MANAGED;
        var10001.getClass();
        return (SchemaManagement)var10000.filter(var10001::equals).findFirst().orElse(SchemaManagement.UNMANAGED);
    }
}

