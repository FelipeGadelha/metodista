package br.com.felipe.gadelha.metodista.api.dto.request

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class TokenRq(
    val email: String,
    private val password: String
) {
    fun toAuth(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, password)
    }
}