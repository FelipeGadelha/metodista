package br.com.felipe.gadelha.metodista.domain.service

import br.com.felipe.gadelha.metodista.domain.exception.EntityInUseException
import br.com.felipe.gadelha.metodista.domain.model.BasicBasket
import br.com.felipe.gadelha.metodista.domain.repository.BasicBasketRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BasicBasketService(
    private val basicBasketRepository: BasicBasketRepository,
) {
    companion object {
        const val MSG_BASIC_BASKET_NOT_FOUND = "Não existe cesta basica com o ID %d"
        const val MSG_BASIC_BASKET_IN_USE = "Cesta basica com o %d não pode ser removida, pois esta em uso"
    }

    @Transactional
    fun findAll(): MutableList<BasicBasket> = basicBasketRepository.findAll()

    @Transactional
    fun findById(id: UUID): BasicBasket = basicBasketRepository
        .findById(id)
        .orElseThrow{ EntityNotFoundException(String.format(MSG_BASIC_BASKET_NOT_FOUND, id))}

    @Transactional
    fun save(basic: BasicBasket): BasicBasket {
        return basicBasketRepository.save(basic)
    }

    @Transactional
    fun update(id: UUID, updated: BasicBasket): BasicBasket {
        var basic = this.findById(id)
        basic = basic.update(updated)
        return this.save(basic)
    }

    @Transactional
    fun deleteById(id: Long) {
        try {
            basicBasketRepository.run { deleteById(id); flush() }
        } catch (e: EmptyResultDataAccessException) {
            throw EntityNotFoundException(String.format(MSG_BASIC_BASKET_NOT_FOUND, id))
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_BASIC_BASKET_IN_USE, id))
        }
    }
}
