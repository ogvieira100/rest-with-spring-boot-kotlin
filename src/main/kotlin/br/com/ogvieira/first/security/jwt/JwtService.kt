package br.com.ogvieira.first.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date


@Service
class JwtService {
    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val expirationMillis = 60 * 60 * 1000 // 1 hora

    fun generateToken(extraClaims: Map<String, Any>, username: String): String {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expirationMillis))
            .signWith(key)
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun <T> extractClaim(token: String, claimsResolver: (io.jsonwebtoken.Claims) -> T): T {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        return claimsResolver(claims)
    }

    fun isTokenValid(token: String, username: String): Boolean {
        val tokenUsername = extractUsername(token)
        return tokenUsername == username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractClaim(token) { it.expiration }
        return expiration.before(Date())
    }

}