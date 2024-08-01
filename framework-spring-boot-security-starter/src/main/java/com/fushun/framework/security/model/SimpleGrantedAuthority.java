package com.fushun.framework.security.model;

import com.fushun.framework.bean.properties.config.IBeanCopyPropertiesBean;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

@Data
public class SimpleGrantedAuthority implements GrantedAuthority, JsonGraalVMNativeBean, IBeanCopyPropertiesBean {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String authority;

    public SimpleGrantedAuthority() {

    }

    public SimpleGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.authority = role;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SimpleGrantedAuthority) {
            return authority.equals(((SimpleGrantedAuthority) obj).authority);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }

    @Override
    public String toString() {
        return this.authority;
    }
}
