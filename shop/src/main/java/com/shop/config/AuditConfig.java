package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // @Configuration은 Spring Framework에서 사용되는 어노테이션 중 하나로, 해당 클래스가 Bean 정의 및 설정 정보를 제공하는 클래스임을 나타냅니다. 이 어노테이션을 사용하면 Spring IoC 컨테이너가 해당 클래스를 감지하고, Bean으로 등록된 설정을 로드하게 됩니다. @Configuration 어노테이션이 클래스에 붙으면 해당 클래스 내에서 @Bean 어노테이션을 사용하여 Bean을 정의할 수 있습니다. Spring IoC 컨테이너는 이러한 Bean 정의를 읽고 관리합니다.
@EnableJpaAuditing // Spring Data JPA에서 제공하는 기능 중 하나로, JPA 엔터티의 감사(Audit) 정보를 자동으로 기록할 수 있게 해주는 어노테이션입니다. 이 어노테이션을 사용하면 엔터티에 대한 생성일자, 수정일자, 생성자, 수정자 등을 자동으로 관리할 수 있습니다.
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){ // 등록자와 수정자를 처리해주는 AuditorAware을 빈으로 등록합니다.
        return new AuditorAwareImpl();
    }
}
