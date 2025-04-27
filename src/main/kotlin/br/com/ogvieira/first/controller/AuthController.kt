package br.com.ogvieira.first.controller


import br.com.ogvieira.first.security.jwt.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth/v1")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): String {


        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )


        return jwtService.generateToken(emptyMap(), request.username)
    }
}