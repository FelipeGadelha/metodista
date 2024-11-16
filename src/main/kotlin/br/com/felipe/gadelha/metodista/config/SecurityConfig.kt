package br.com.felipe.gadelha.metodista.config

import br.com.felipe.gadelha.metodista.domain.service.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain


@Configuration
class SecurityConfig(
    private val authService: AuthService
) {

    @Bean
    fun configure(http: HttpSecurity): DefaultSecurityFilterChain? {
        return http.authorizeHttpRequests {
            h -> h.requestMatchers("/*/members/**").permitAll()
            .requestMatchers("/*/families/**").permitAll()
            .requestMatchers("/*/cities/**").permitAll()
            .requestMatchers("/*/states/**").permitAll()
            .requestMatchers("/*/auth/**").permitAll()
            .requestMatchers("/*/users/**").permitAll()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()
        }
            .sessionManagement { s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .csrf{ c -> c.disable() }
            .cors { c -> c.disable() }
            .build()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(authService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}