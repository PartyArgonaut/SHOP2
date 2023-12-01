package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    @Value("${uploadPath}") // application.properties에 설정한 "uploadPath" 프로퍼티 값을 읽어옵니다.
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**") // 웹 브라우저에 입력하는 url에 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어오도록 설정합니다. **는 앤트 스타일 패턴(Ant-style pattern)으로, 패턴 매칭을 수행하는데 사용됨. /images/로 시작하는 모든 URL을 포함합니다. **는 임의의 하위 경로를 나타내며, /images/ 다음에 어떤 경로가 오더라도 매칭됩니다.
                .addResourceLocations(uploadPath); // 로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 설정합니다.
    }
}
