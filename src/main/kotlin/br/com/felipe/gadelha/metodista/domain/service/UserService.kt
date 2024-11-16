package br.com.felipe.gadelha.metodista.domain.service

import br.com.felipe.gadelha.metodista.domain.exception.EntityNotFoundException
import br.com.felipe.gadelha.metodista.domain.model.Role
import br.com.felipe.gadelha.metodista.domain.repository.MemberRepository
import jakarta.persistence.EntityManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
        const val MSG_USER_NOT_FOUND = "Não existe usuário com o ID %d"
        const val MSG_MEMBER_NOT_FOUND = "Não existe membro com o ID %d"
        const val MSG_MEMBER_IN_USE = "Membro com o %d não pode ser removido, pois esta em uso"
    }

    @Transactional
    fun activeUser(id: UUID) {
        logger.info("activeUser - id $id")
        val member = memberRepository.findById(id)
            .orElseThrow{ throw EntityNotFoundException(String.format(MSG_USER_NOT_FOUND, id)) }
        val pass = getSaltString()
        logger.info("activeUser - pass $pass")
        val saved = member.copy(
            isSystemUser = true,
            password = passwordEncoder.encode(pass)
        )

        saved.addRole(Role(1L))
        logger.info("activeUser - member $saved")

        memberRepository.save(saved)
    }

    protected fun getSaltString(): String {
        val SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val salt = StringBuilder()
        val rnd: Random = Random()
        while (salt.length < 5) { // length of the random string.
            salt.append(SALTCHARS[((rnd.nextFloat() * SALTCHARS.length).toInt())])
        }
        return salt.toString()
    }
}