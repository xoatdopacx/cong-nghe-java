package vn.edu.eaut.lab15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vn.edu.eaut.lab15.service.AppUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserDetailsService appUserDetailsService;

    public SecurityConfig(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                // Trang công khai
                .requestMatchers("/", "/about", "/css/**", "/js/**", "/images/**").permitAll()
                // H2 Console (cho phát triển)
                .requestMatchers("/h2-console/**").permitAll()
                // ADMIN mới được thêm/sửa/xóa sinh viên
                .requestMatchers("/students/create", "/students/save", "/students/edit/**", "/students/delete/**").hasRole("ADMIN")
                // ADMIN mới được quản lý khóa học
                .requestMatchers("/courses/create", "/courses/save", "/courses/edit/**", "/courses/delete/**").hasRole("ADMIN")
                // Tất cả người dùng đã đăng nhập mới được xem
                .requestMatchers("/students/**", "/courses/**", "/enrollments/**", "/dashboard").authenticated()
                // Tất cả request còn lại yêu cầu đăng nhập
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error/403")
            )
            // Cho phép H2 Console iframe
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
