package br.com.ogvieira.first

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)

/*
	val encoders: MutableMap<String, PasswordEncoder> = HashMap()
	val pbkdf2Encoder = Pbkdf2PasswordEncoder(
	"",             // secret
	310000,         // iterationCount
	16,             // saltLength
	Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
	)

	val pbkdf2Encoder2 = Pbkdf2PasswordEncoder(
		"",             // secret
		310000,         // iterationCount
		16,             // saltLength
		Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
	)

	encoders["pbkdf2"] = pbkdf2Encoder
	val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)
	passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder2)

	val result = passwordEncoder.encode("admin123")
	println("My hash $result")

 */
	}
