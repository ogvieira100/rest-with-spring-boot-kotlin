package br.com.ogvieira.first.config

import br.com.ogvieira.first.security.jwt.JwtAuthenticationFilter
import br.com.ogvieira.first.security.jwt.JwtTokenProvider
import br.com.ogvieira.first.security.jwt.JwtConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity

import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthenticationFilter
) {
    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers( "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"  ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthFilter,
                UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    /*
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val encoders: MutableMap<String, PasswordEncoder> = HashMap()
        val secret = ""

        val pbkdf2Encoder = Pbkdf2PasswordEncoder(
            secret,             // secret
            310000,         // iterationCount
            16,             // saltLength
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        )

        encoders["pbkdf2"] = pbkdf2Encoder

        val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder)
        return passwordEncoder
    }

 */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
