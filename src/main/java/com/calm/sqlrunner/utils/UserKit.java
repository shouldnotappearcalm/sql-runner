package com.calm.sqlrunner.utils;

import com.calm.sqlrunner.bean.pojo.UserDetailsInfo;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

/**
 * 用户相关工具类.
 *
 * @author gaozhirong on 2020/02/06
 * @version 1.0.0
 */
@UtilityClass
public class UserKit {

    /**
     * 获取当前登陆用户信息.
     * @return 当前用户信息
     */
    public static Mono<UserDetailsInfo> getCurUserInfo() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(UserDetailsInfo.class);
    }

}
