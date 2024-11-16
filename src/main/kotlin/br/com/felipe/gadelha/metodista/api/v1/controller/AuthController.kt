package br.com.felipe.gadelha.metodista.api.v1.controller

import br.com.felipe.gadelha.metodista.api.dto.request.TokenRq
import br.com.felipe.gadelha.metodista.api.dto.response.TokenRs
import br.com.felipe.gadelha.metodista.domain.model.Member
import br.com.felipe.gadelha.metodista.domain.service.AuthService
import br.com.felipe.gadelha.metodista.api.security.TokenService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authManager: AuthenticationManager,
    private val tokenService: TokenService,
    private val authService: AuthService
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)
    }

    @PostMapping
    @Transactional
    fun authenticate(@RequestBody @Valid tokenRq: TokenRq): ResponseEntity<*> {
        val loginData: UsernamePasswordAuthenticationToken = tokenRq.toAuth()
        try {
            val authentication = authManager.authenticate(loginData)
            logger.info("authenticate::isAuthenticated = ${authentication.isAuthenticated}")

            val user = authService.loadUserByUsername(authentication.name) as Member

            logger.info("authenticate::isAuthenticated = $user")
            val token = tokenService.generateToken(
                user,
                mapOf(
                    "id" to user.id,
                    "name" to user.username,
                    "roles" to authentication.authorities
                        .map { obj: GrantedAuthority -> obj.authority }
                ),
            )

            logger.info("authenticate::TOKEN = $token")
            return ResponseEntity.ok<Any>(TokenRs(token, "Bearer"))
        } catch (e: AuthenticationException) {
            logger.error("authenticate::USER = ${tokenRq.email}, MESSAGE = ${e.message}")
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mapOf("message" to e.message))
        }
    }
}
