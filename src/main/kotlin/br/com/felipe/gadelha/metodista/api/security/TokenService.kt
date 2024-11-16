package br.com.felipe.gadelha.metodista.api.security

import br.com.felipe.gadelha.metodista.domain.model.Member
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenService {

    @Value("\${metodista.jwt.expiration:864000}") //one day
    private val expiration: Long? = null

    @Value("\${metodista.jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private val secret: String? = null

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TokenService::class.java)
    }
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = this.extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun generateToken(userDetails: UserDetails): String? {
        return generateToken(userDetails, HashMap())
    }

    fun generateToken(userDetails: UserDetails, extraClaims: Map<String, Any?>): String {
        val user = userDetails as Member
        val now = Instant.now()

        val token = Jwts.builder()
            .issuer("metodista")
            .claims(extraClaims)
            .subject(user.id!!.toString()) //                .setIssuedAt(new Date(System.currentTimeMillis()))
            .signWith(getSignInKey())
            .issuedAt(Date.from(now))
            .expiration(this.expirationDate())
            .compact()

        return token
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        logger.info("isTokenValid::token")
        return (username == userDetails.username)
            && !isTokenExpired(token)
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun expirationDate(): Date {
        return Date.from(expiration?.let { Instant.now().plusSeconds(it) })
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}