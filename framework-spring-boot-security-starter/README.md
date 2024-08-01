登陆成功：com.fushun.framework.security.handler.CustomAuthenticationSuccessHandler
登陆失败：com.fushun.framework.security.handler.CustomAuthenticationFailHandler
校验是否登陆：com.fushun.framework.security.filter.JWTAuthorizationFilter
权限校验 com.fushun.framework.security.service.CheckPermissionService

流程：
1、获取用户信息：com.fushun.framework.security.service.AdminUserDetailsService.loadUserByUsername
1.1、用户用户信息（接口，应用层单独实现）：com.fushun.framework.security.service.LoginAdminUserService.findLoginByUserName
1.2、组装UserDetails（用户的信息）：com.fushun.framework.security.model.AdminLoginInfo
2、根据设置密钥方式校验密码：com.fushun.framework.security.WebSecurityConfig.passwordEncoder
3、校验结果：
3.1、登陆成功：com.fushun.framework.security.handler.CustomAuthenticationSuccessHandler
3.2、登陆失败：com.fushun.framework.security.handler.CustomAuthenticationFailHandler
4、接口登陆验证：com.fushun.framework.security.filter.JWTAuthorizationFilter
5、用户权限校验：
5.1、用户接口权限：com.fushun.framework.security.service.CheckPermissionService.hasPermission
5.2、用户接口权限（角色）校验：com.fushun.framework.security.service.CheckPermissionService.hasRole
6、退出处理：com.fushun.framework.security.handler.LogoutSuccessHandlerImpl

配置：
不需要登陆的url：com.fushun.framework.security.config.IgnoredUrlsConfig.urls

其他：
前端登陆token: 
1、通过header传人
2、header名称：com.fushun.framework.security.constant.SecurityConstant.HEADER



登陆接口路径

后台管理：
1、basic/login 登陆接口
2、basic/needLogin 登陆异常接口

app端：
1、api/login 登陆接口
2、api/needLogin 登陆异常接口


# TODO

1、后台管理和App端登陆，存储redis key相同
需要对token的生成和获取方式做修改
改造 com.fushun.framework.security.service.TokenService 支持不通的端口，相同的账号同时登陆


2、现有的方式。实现一种新的登陆方式，需要重写的代码较多。将变化的和不变的代码，分开管理
2.1、com.fushun.framework.security.WebSecurityConfig （复制）
2.1、com.fushun.framework.security.controller.AppLoginController
2.2、com.fushun.framework.security.service.AdminUserDetailsService
2.3、com.fushun.framework.security.service.AppLoginUserService

3、对象没有无参构造函数，jackson无法序列化问题

