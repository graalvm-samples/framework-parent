package com.fushun.framework.jpa.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SpringPhysicalNamingStrategy extends org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
{
    @Override
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
//    public Identifier determineJoinTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//        if(name instanceof  MyIdentifier){
//            return super.toPhysicalColumnName(name, jdbcEnvironment);
//        }
//        return name;
        String var10000 = source.getOwningPhysicalTableName();
        String name = var10000 + "_" + source.getAssociationOwningAttributePath().getProperty();
        return this.toIdentifier(name, source.getBuildingContext());
    }
}
