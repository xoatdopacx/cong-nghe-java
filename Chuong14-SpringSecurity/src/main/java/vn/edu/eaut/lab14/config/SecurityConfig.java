package vn.edu.eaut.lab14.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vn.edu.eaut.lab14.service.AppUserDetailsService;

/**
 * Bài 2, 3, 6, 7, 9, 10: Cấu hình Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserDetailsService appUserDetailsService;

    public SecurityConfig(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Bài 2: Phân quyền URL
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/about", "/css/**", "/h2-console/**").permitAll()
                .requestMatchers("/students/create", "/students/save", "/students/edit/**", "/students/delete/**").hasRole("ADMIN") // Bài 9: chỉ ADMIN xóa/thêm/sửa
                .requestMatchers("/students/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/courses/**").hasRole("ADMIN")  // Bài 6: chỉ ADMIN truy cập courses
                .anyRequest().authenticated()
            )
            // Bài 4: Form đăng nhập tùy chỉnh
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/students", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            // Bài 7: Trang lỗi 403
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error/403")
            )
            // Cho phép H2 Console (dùng frame)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }

    /**
     * Bài 10: DaoAuthenticationProvider sử dụng AppUserDetailsService (user từ CSDL).
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
