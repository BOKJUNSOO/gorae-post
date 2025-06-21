package com.gorae.gorae_post.common;

import com.gorae.gorae_post.common.exception.NotFound;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GatewayRequestHeaderUtils {

    public static String getRequestHeaderParamAsString(String key) {
        // Header 에 포함된 Param 파싱하는 함수
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getHeader(key);
    }

    public static String getUserId() {
        // 헤더에 포함된 X-Auth-UserId 키를 이용하여 userId 확인
        String userId = getRequestHeaderParamAsString("X-Auth-UserId");
        if (userId == null) {
            throw new NotFound("헤더에 userId 정보가 없습니다.");
        }
        return userId;
    }
}
