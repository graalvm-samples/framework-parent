package com.fushun.framework.security.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fushun.framework.bean.properties.config.IBeanCopyPropertiesBean;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import com.fushun.framework.util.util.UUIDUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public abstract class BaseLoginInfo  implements UserDetails, JsonGraalVMNativeBean, IBeanCopyPropertiesBean {

    //登录账号
    protected Integer userId;
    protected String userName;
    protected String password;
    protected String nickName;

    private String token;
    private Long loginTime;
    private Long expireTime;

    private String ipaddr;
    private String loginLocation;
    private String browser;
    private String os;

    /**
     * APP用户token前缀key 单点登录使用
     */
    protected String appUserToken;

    /** 用户头像 */
    private String avatar;
    
    /**
     * 权限列表
     */
    protected Set<String> permissions;

    /**
     * 用户角色列表
     */
    protected Set<String> roles;

    /**
     * 是否超级管理员
     * @return
     */
    public boolean isAdmin(){
        return false;
    };


    @Override
    public String getUsername() {
        return userName;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }



    /**
     * 用户账号是否过期
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户账号是否被锁定
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户密码是否过期
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * user 关联的 token
     * @return
     */
    @JsonIgnore
    public String getTokenKey() {
        if (StringUtils.isNotBlank(this.getAppUserToken())) {
            return this.getAppUserToken() + this.getUsername();
        }
        return this.getUsername();
    }

    @JsonIgnore
    public String generateToken() {
        return UUIDUtil.getUUID().toString().replace("-", "").toUpperCase();
    }
}
