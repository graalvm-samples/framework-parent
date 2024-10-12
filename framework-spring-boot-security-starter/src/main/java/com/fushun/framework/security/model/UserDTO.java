package com.fushun.framework.security.model;

import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Administrator
 * 用户信息
 */
@Data
@ToString
public class UserDTO implements JsonGraalVMNativeBean {

    private Integer userId;


    private String userAccount;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 微信名称
     */
    private String nickName;

    /**
     * 公司id
     */
    private Integer companyId;

    private String companyName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 地址
     */
    private String userAddress;

    /**
     * 用户菜单权限信息
     */
    private Set<String> permissions;

    /**
     * 头像
     */
    private String headPortrait;


    /**
     * 角色对象
     */
    private Set<String> roles;


    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 登陆端口 校验权限
     * 对应了不同的 HttpSecurity
     */
    private Set<GrantedAuthority> authorities = new HashSet<>();

    /**
     * APP用户token前缀key 单点登录使用
     */
    private String appUserToken;

    /**
     * APP交互token前缀key
     */
    private String appTokenPre;

    /**
     * 是否是新用户,第一次登录
     */
    private Boolean firstLoginUser=false;

    /**
     * openId
     */
    private String openId;

    private String unionId;

}
