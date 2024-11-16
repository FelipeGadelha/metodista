package br.com.felipe.gadelha.metodista.domain.service

import br.com.felipe.gadelha.metodista.domain.exception.EntityInUseException
import br.com.felipe.gadelha.metodista.domain.exception.EntityNotFoundException
import br.com.felipe.gadelha.metodista.domain.model.Family
import br.com.felipe.gadelha.metodista.domain.repository.FamilyRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FamilyService(
    private val familyRepository: FamilyRepository
) {

    companion object {
        private const val MSG_FAMILY_NOT_FOUND = "Familia com o ID %d não existe"
        private const val MSG_FAMILY_IN_USE = "Familia com o ID %d não pode ser removido, pois esta em uso"
    }


    fun findAll(): MutableList<Family> {
        return familyRepository.findAll()
    }

    fun findById(id: UUID): Family {
        return familyRepository.findById(id)
            .orElseThrow { throw EntityNotFoundException(String.format(MSG_FAMILY_NOT_FOUND, id))}
    }

    fun save(family: Family): Family {
        return familyRepository.save(family)
    }
    @Transactional
    fun deleteById(id: Long) {
        try {
            familyRepository.run { deleteById(id); flush() }
        } catch (e: EmptyResultDataAccessException) {
            throw EntityNotFoundException(String.format(MSG_FAMILY_NOT_FOUND, id))
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_FAMILY_IN_USE, id))
        }
    }
}
