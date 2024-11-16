package br.com.felipe.gadelha.metodista.domain.service

import br.com.felipe.gadelha.metodista.domain.repository.MemberRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberRepository: MemberRepository
): UserDetailsService  {
    private val logger: Logger = LoggerFactory.getLogger(AuthService::class.java)

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        logger.info("loadUserByUsername::USERNAME = $email")
        return memberRepository.findByEmail(email)?.also {
            logger.info("loadUserByUsername::USERNAME = $it")
        } ?: throw BadCredentialsException("Dados inválidos!")
    }
}