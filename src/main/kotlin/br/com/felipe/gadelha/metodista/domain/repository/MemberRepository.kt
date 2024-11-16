package br.com.felipe.gadelha.metodista.domain.repository

import br.com.felipe.gadelha.metodista.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MemberRepository: JpaRepository<Member, UUID> {
    fun findByEmail(name: String): Member?
}
