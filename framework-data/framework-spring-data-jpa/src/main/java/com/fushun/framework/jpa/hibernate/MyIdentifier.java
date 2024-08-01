package com.fushun.framework.jpa.hibernate;

import org.hibernate.boot.model.naming.Identifier;

public class MyIdentifier extends Identifier {

    public MyIdentifier(String text, boolean quoted) {
        super(text, quoted);
    }

    protected MyIdentifier(String text) {
        super(text);
    }
}
