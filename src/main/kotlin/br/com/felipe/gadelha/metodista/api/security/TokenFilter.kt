package br.com.felipe.gadelha.metodista.api.security

import br.com.felipe.gadelha.metodista.domain.repository.MemberRepository
import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*


@Component
class TokenFilter(
    private val tokenService: TokenService,
    private val repository: MemberRepository
) : OncePerRequestFilter() {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TokenFilter::class.java)
    }

    override fun doFilterInternal(
        @NotNull request: HttpServletRequest,
        @NotNull response: HttpServletResponse,
        @NotNull filterChain: FilterChain
    ) {
        logger.info("preHandle::RequestURI::${request.servletPath}")
        val token = retrieveToken(request)
        if (Objects.isNull(token)) {
            filterChain.doFilter(request, response)
            return
        }
        logger.info("doFilterInternal::TOKEN = $token")
        val user = getUserDetails(token!!)

        val valid: Boolean = tokenService.isTokenValid(token, user)
        logger.info("doFilterInternal::valid = $valid")

        if (valid) authenticate(user, request)

        filterChain.doFilter(request, response)
    }

    private fun authenticate(user: UserDetails, request: HttpServletRequest) {
        logger.info("authenticate::Username = ${user.username}")
        val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        logger.info("authenticate::authentication = ${authentication.isAuthenticated}")
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
        logger.info("authenticate::context = ${SecurityContextHolder.getContext().authentication.authorities}")
    }

    private fun getUserDetails(token: String): UserDetails {
        val email: String = tokenService.extractClaim(token)
            { obj: Claims -> obj.subject }
        return repository.findByEmail(email)
            ?.also { logger.info("getUserDetails::USERNAME = ${it.email}") }
            ?: throw UsernameNotFoundException("Dados inválidos!")
    }

    private fun retrieveToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        if (token == null || token.contains("undefined") || !token.startsWith("Bearer ")) {
            return null
        }
        //        return token.substring(7, token.length());
        return token.substring(7)
    }

}