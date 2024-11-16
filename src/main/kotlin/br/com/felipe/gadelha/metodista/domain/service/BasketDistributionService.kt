package br.com.felipe.gadelha.metodista.domain.service

import br.com.felipe.gadelha.metodista.domain.exception.EntityInUseException
import br.com.felipe.gadelha.metodista.domain.model.BasketDistribution
import br.com.felipe.gadelha.metodista.domain.repository.BasketDistributionRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class BasketDistributionService(
    private val basketDistributionRepository: BasketDistributionRepository,
    private val familyService: FamilyService,
    private val basketService: BasicBasketService,
) {
    companion object {
        const val MSG_DISTRIBUTION_BASIC_BASKET_NOT_FOUND = "Não existe distribuição de cesta basica com o ID %d"
        const val MSG_DISTRIBUTION_BASIC_BASKET_IN_USE = "distribuição de cesta basica com o %d não pode ser removida, pois esta em uso"
    }

    @Transactional
    fun findAll(): MutableList<BasketDistribution> = basketDistributionRepository.findAll()

    @Transactional
    fun findById(id: UUID): BasketDistribution = basketDistributionRepository
        .findById(id)
        .orElseThrow{ EntityNotFoundException(String.format(MSG_DISTRIBUTION_BASIC_BASKET_NOT_FOUND, id))}

    @Transactional
    fun save(basketDistribution: BasketDistribution): BasketDistribution {
        val familyId = basketDistribution.getFamilyId()
        val basicId = basketDistribution.getBasicBasketId()

        val family = familyService.findById(familyId!!)
        val basic = basketService.findById(basicId!!)

        val saved = basketDistribution
            .copy(family = family, basicBasket = basic)

        return basketDistributionRepository.save(saved)
    }

    @Transactional
    fun update(id: UUID, updated: BasketDistribution): BasketDistribution {
        var basketDistribution = this.findById(id)
        basketDistribution = basketDistribution.update(updated)
        return this.save(basketDistribution)
    }

    @Transactional
    fun deleteById(id: Long) {
        try {
            basketDistributionRepository.run { deleteById(id); flush() }
        } catch (e: EmptyResultDataAccessException) {
            throw EntityNotFoundException(String.format(MSG_DISTRIBUTION_BASIC_BASKET_NOT_FOUND, id))
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_DISTRIBUTION_BASIC_BASKET_IN_USE, id))
        }
    }
}
