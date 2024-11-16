package br.com.felipe.gadelha.metodista.domain.service

import br.com.felipe.gadelha.metodista.domain.exception.EntityNotFoundException
import br.com.felipe.gadelha.metodista.domain.model.Family
import br.com.felipe.gadelha.metodista.domain.model.Member
import br.com.felipe.gadelha.metodista.domain.repository.MemberRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val familyService: FamilyService,
    private val cityService: CityService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(MemberService::class.java)
        const val MSG_MEMBER_NOT_FOUND = "Não existe membro com o ID %d"
        const val MSG_MEMBER_IN_USE = "Membro com o %d não pode ser removido, pois esta em uso"
    }

    fun findAll(): MutableList<Member> = memberRepository.findAll()

    fun save(member: Member): Member {
        val city = cityService.findById(member.getCityId()!!)
        var family: Family? = null

        val familyId = member.getFamilyId()

        familyId?.let {
            runCatching { familyService.findById(it) }
                .onSuccess { f -> logger.info("MemberService::save family found: ${f.id}"); family = f }
                .onFailure { ex -> logger.error("MemberService::save unexpected error while fetching family", ex) }
        }
        val saved = member.copy(
            family = family ?: member.family,
            address = member.address.copy(city = city)
        )
        return memberRepository.save(saved)
    }
}