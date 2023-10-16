package board.BulletinBoard.config;

import board.BulletinBoard.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig{
    private MemberService memberService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/user/myInfo")).hasRole("MEMBER")
                .anyRequest().permitAll());
        http.formLogin((form) -> form
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/login/result")
                .permitAll());
        http.logout((logout) -> logout
                .logoutUrl("/user/logout/result")
                        .logoutSuccessUrl("/user/logout/result")
                        .invalidateHttpSession(true));
        http.exceptionHandling((exception) -> exception
                .accessDeniedPage("/user/denied"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

}
