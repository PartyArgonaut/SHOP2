package com.shop.config;


import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email") // 로그인 시 사용할 파라미터 이름으로 email을 지정합니다.
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 URL을 설정합니다.
                .logoutSuccessUrl("/"); // 로그아웃 성공 시 이동할 URL을 설정합니다.

        http.authorizeRequests() //1. 시큐리티 처리에 HttpSercletRequest를 이용한다는 것을 의미합니다.
                .mvcMatchers("/", "/members/**", "/item/**", "/image/**").permitAll() // 2. permitAll을 통해 모든 사용자가 인증(로그인)없이 해당 경로에 접근할 수 있도록 설정합니다. 메인 페이지, 회원관련URL, 뒤에서 만들 상품 상세 페이지, 상품 이미지를 불러오는 경로가 이에 해당합니다.
                .mvcMatchers("/admin/**").hasRole("ADMIN") // 3. admin으로 시작하는 경로는 해당 계정이 ADMIN Role일 경우에만 접근 가능하도록 설정합니다.
                .anyRequest().authenticated(); // 4. 2,3에서 설정해준 경로를 제외한 나머지 경로들을 모두 인증을 요구하도록 설정합니다.

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); // 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록합니다.



    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**"); // static 디렉터리의 하위 파일은 인증을 무시하도록 설정합니다.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    } // Spring Security에서 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManagerBuilder가 AuthenticationManager를 생성합니다. userDetailService를 구현하고 있는 객체로 memberService를 지정해주며, 비밀번호 암호화를 위해 passwordEncoder를 지정해줍니다.



}