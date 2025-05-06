//package com.fushun.framework.security.utils;
//
//
//import com.fushun.framework.base.SpringContextUtil;
//import com.fushun.framework.redis.utils.RedisUtil;
//import com.fushun.framework.security.config.BasicTokenConfig;
//import com.fushun.framework.security.constant.SecurityConstant;
//import com.fushun.framework.security.model.BaseLoginInfo;
//import com.fushun.framework.security.service.TokenService;
//import com.fushun.framework.util.ip.AddressUtils;
//import com.fushun.framework.util.ip.IpUtils;
//import com.fushun.framework.util.util.ServletUtils;
//import com.fushun.framework.util.util.StringUtils;
//import eu.bitwalker.useragentutils.UserAgent;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author Exrickx
// */
//public class SecurityUtils {
//
//    /**
//     * token 配置
//     */
//    private static BasicTokenConfig basicTokenConfig;
//    private static RedisUtil redisUtil;
//    private static TokenService tokenService;
//
//    static  {
//        SecurityUtils.basicTokenConfig = SpringContextUtil.getBean(BasicTokenConfig.class);
//        SecurityUtils.redisUtil = SpringContextUtil.getBean(RedisUtil.class);
//        tokenService= SpringContextUtil.getBean(TokenService.class);
//    }
//
//
//    /**
//     * 设置用户代理信息
//     *
//     * @param loginUser 登录信息
//     */
//    public static  <T extends BaseLoginInfo> void setUserAgent(T loginUser) {
//        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
//        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
//        loginUser.setIpaddr(ip);
//        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
//        loginUser.setBrowser(userAgent.getBrowser().getName());
//        loginUser.setOs(userAgent.getOperatingSystem().getName());
//    }
//
//    /**
//     * 生成BCryptPasswordEncoder密码
//     *
//     * @param password 密码
//     * @return 加密字符串
//     */
//    public static String encryptPassword(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.encode(password);
//    }
//
//    /**
//     * 判断密码是否相同
//     *
//     * @param rawPassword     真实密码
//     * @param encodedPassword 加密后字符
//     * @return 结果
//     */
//    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
//
//    /**
//     * 获取请求token
//     *
//     * @param request
//     * @return token
//     */
//    public static String getToken(HttpServletRequest request) {
//
//        String token = request.getHeader(SecurityConstant.ACCESS_TOKEN);
//        if (StringUtils.isEmpty(token)) {
//            token = request.getParameter(SecurityConstant.ACCESS_TOKEN);
//        }
//        return token;
//    }
//
//    /**
//     * 通过用户名获取用户拥有权限
//     *
//     * @param username
//     */
//    public static List<GrantedAuthority> getCurrUserPerms(String username) {
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
////        UserService userService = (UserService) SpringContextUtil.getBean("userServiceImpl");
////        UserEntity userEntity = userService.selectUserByUserName(username);
////        PermissionService permissionService = (PermissionService) SpringContextUtil.getBean("permissionServiceImpl");
////        Set<String> permissions = permissionService.getMenuPermission(userEntity);
////        // 添加请求权限
////        if(permissions!=null&&permissions.size()>0){
////            for (String permission : permissions) {
////                authorities.add(new SimpleGrantedAuthority(permission));
////            }
////        }
////        // 添加角色
////        Set<String> roles = userEntity.getRoles();
////        if(roles!=null&&roles.size()>0){
////            // lambda表达式
////            roles.forEach(item -> {
////                if(StrUtil.isNotBlank(item)){
////                    authorities.add(new SimpleGrantedAuthority(item));
////                }
////            });
////        }
//
//        return authorities;
//    }
//
//    /**
//     * 获取Authentication
//     */
//    public static Authentication getAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//
//
//    /**
//     * 获取当前用户数据权限 null代表具有所有权限 包含值为-1的数据代表无任何权限
//     */
////    public List<String> getDeparmentIds(){
////
////        List<String> deparmentIds = new ArrayList<>();
////        User u = getCurrUser();
////        // 读取缓存
////        String key = "userRole::depIds:" + u.getId();
////        String v = redisTemplate.opsForValue().get(key);
////        if(StrUtil.isNotBlank(v)){
////            deparmentIds = new Gson().fromJson(v, new TypeToken<List<String>>(){}.getType());
////            return deparmentIds;
////        }
////        // 当前用户拥有角色
////        List<Role> roles = iUserRoleService.findByUserId(u.getId());
////        // 判断有无全部数据的角色
////        Boolean flagAll = false;
////        for(Role r : roles){
////            if(r.getDataType()==null||r.getDataType().equals(CommonConstant.DATA_TYPE_ALL)){
////                flagAll = true;
////                break;
////            }
////        }
////        // 包含全部权限返回null
////        if(flagAll){
////            return null;
////        }
////        // 每个角色判断 求并集
////        for(Role r : roles) {
////            if (r.getDataType().equals(CommonConstant.DATA_TYPE_UNDER)) {
////                // 本部门及以下
////                if (StrUtil.isBlank(u.getDepartmentId())) {
////                    // 用户无部门
////                    deparmentIds.add("-1");
////                } else {
////                    // 递归获取自己与子级
////                    List<String> ids = new ArrayList<>();
////                    getRecursion(u.getDepartmentId(), ids);
////                    deparmentIds.addAll(ids);
////                }
////            } else if (r.getDataType().equals(CommonConstant.DATA_TYPE_SAME)) {
////                // 本部门
////                if (StrUtil.isBlank(u.getDepartmentId())) {
////                    // 用户无部门
////                    deparmentIds.add("-1");
////                } else {
////                    deparmentIds.add(u.getDepartmentId());
////                }
////            } else if (r.getDataType().equals(CommonConstant.DATA_TYPE_CUSTOM)) {
////                // 自定义
////                List<String> depIds = iUserRoleService.findDepIdsByUserId(u.getId());
////                if (depIds == null || depIds.size() == 0) {
////                    deparmentIds.add("-1");
////                } else {
////                    deparmentIds.addAll(depIds);
////                }
////            }
////        }
////        // 去重
////        LinkedHashSet<String> set = new LinkedHashSet<>(deparmentIds.size());
////        set.addAll(deparmentIds);
////        deparmentIds.clear();
////        deparmentIds.addAll(set);
////        // 缓存
////        redisTemplate.opsForValue().set(key, new Gson().toJson(deparmentIds));
////        return deparmentIds;
////    }
//
////    private void getRecursion(String departmentId, List<String> ids){
////
////        Department department = departmentService.get(departmentId);
////        ids.add(department.getId());
////        if(department.getIsParent()!=null&&department.getIsParent()){
////            // 获取其下级
////            List<Department> departments = departmentService.findByParentIdAndStatusOrderBySortOrder(departmentId, CommonConstant.STATUS_NORMAL);
////            departments.forEach(d->{
////                getRecursion(d.getId(), ids);
////            });
////        }
////    }
//
//    /**
//     * 是否为管理员
//     *
//     * @param userId 用户userId
//     * @return 结果
//     */
//    public static boolean isAdmin(Long userId) {
//        return userId != null && userId == 1;
//    }
//}
