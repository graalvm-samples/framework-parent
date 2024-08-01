package com.fushun.framework.security.model;

import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
public class AppUserDTO  implements JsonGraalVMNativeBean {
    /**
     * 姓名
     */
    private String name;

    /**
     * 密码，默认身份证后6位
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 工号
     */
    private String userNumber;

    /**
     * 状态 on 在职 quit 离职 stop 停薪留职
     */
    private String state;

    /**
     * 账号状态 normal 正常  stop停用  freeze 冻结
     */
    private String accountStatus;

    /**
     * 是否填写过承诺
     */
    private Boolean isPromise;

    /**
     * 登陆端口 校验权限
     * 对应了不同的 HttpSecurity
     */
    private Set<GrantedAuthority> authorities = new HashSet<>();
}
