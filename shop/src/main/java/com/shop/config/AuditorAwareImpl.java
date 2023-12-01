package com.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> { // 인터페이스 구현하는  AuditorAwareImpl클래스 . 제네릭 타입<String>은 감사 정보로 사용될 사용자의 식별자(ID)를 나타낸다.
    @Override
    public Optional<String> getCurrentAuditor() {// AuditorAware 인터페이스의 getCurrentAuditor 메서드를 구현합니다. 이 메서드는 현재 감사 정보를 반환해야 합니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 사용자의 인증 정보를 얻기 위해 SecurityContextHolder를 사용합니다.
        String userId = ""; //사용자 ID를 저장할 변수를 초기화합니다.
        if(authentication != null){
            userId = authentication.getName();
        }
        return Optional.of(userId); // 최종적으로 Optional을 사용하여 사용자 ID를 감사 정보로 감싸서 반환합니다.
    }
}
