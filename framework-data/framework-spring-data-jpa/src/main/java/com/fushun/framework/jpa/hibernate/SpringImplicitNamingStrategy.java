package com.fushun.framework.jpa.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;

public class SpringImplicitNamingStrategy extends org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy{

    @Override
    public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {
        Identifier identifier=  super.determineBasicColumnName(source);
        return new MyIdentifier(identifier.getText(),identifier.isQuoted());
    }
}
