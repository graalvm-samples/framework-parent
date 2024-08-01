package com.hsh.platform.security.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import com.fushun.framework.security.model.BaseLoginInfo;
import com.fushun.framework.security.model.SimpleGrantedAuthority;
import com.fushun.framework.security.model.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * <pre>
 * <b>LoginInfo</b>
 * <b>Description:</b>
 * <b>@author:zenghuapei@360humi.com</b>
 * <b>Date:</b> 2020/4/16 14:44
 * <b>Copyright:</b> Copyright 2017-2019 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver          Date                    Author                  Detail
 *   ----------------------------------------------------------------------
 *   1.0   2020/4/16 14:44    zenghuapei@360humi.com     new file.
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class AdminLoginInfo extends BaseLoginInfo implements JsonGraalVMNativeBean {

    private String headPortrait;

    /**
     * 登陆端口 校验权限
     * 对应了不同的 HttpSecurity
     */
    private Set<GrantedAuthority> authorities;


    /**
     * APP交互token前缀key
     */
    private String appTokenPre;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 公司id
     */
    private Integer companyId;



    public AdminLoginInfo() {
    }

    public AdminLoginInfo(UserDTO user) {
        this.userName=user.getUserAccount();
        this.password = user.getUserPassword();
        this.nickName = user.getNickName();
        this.userId = user.getUserId();
        this.roles = user.getRoles();
        this.headPortrait = user.getHeadPortrait();
        this.permissions = user.getPermissions();
        this.authorities = user.getAuthorities();
        this.appUserToken = user.getAppUserToken();
        this.appTokenPre = user.getAppTokenPre();
        this.companyId = user.getCompanyId();
    }

    /**
     * 设置校验需要的权限列表 暂时听过写死的方式 为了解决方序列化回来 丢失的问题
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAdmin() {
        return userId != null && 1L == userId;
    }

}
