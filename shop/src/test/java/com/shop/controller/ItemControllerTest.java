package com.shop.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations ="classpath:application-test.properties")
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN") // 1. 현재 회원의 이름이 admin이고, role이 ADMIN인 유저가 로그인된 상태로 테스트를 할 수 있도록 해주는 어노테이션입니다.
    public void itemFormTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) // 2. 상품 등록 페이지에 요청을 get 요청으로 보냅니다.
                .andDo(print()) // 3. 요청과 응답 메세지를 확인할 수 있도록 콘솔창에 출력해줍니다.
                .andExpect(status().isOk()); // 4. 응답 상태 코드가 정상인지 확인합니다.
    }

    @Test
    @DisplayName("상품 등록 페이지 일반 회원 접근 테스트")
    @WithMockUser(username = "user", roles = "USER")
    public  void itemFormNotAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
