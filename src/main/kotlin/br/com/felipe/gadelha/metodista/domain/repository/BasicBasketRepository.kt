package br.com.felipe.gadelha.metodista.domain.repository

import br.com.felipe.gadelha.metodista.domain.model.BasicBasket
import br.com.felipe.gadelha.metodista.domain.model.BasketDistribution
import br.com.felipe.gadelha.metodista.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BasicBasketRepository: JpaRepository<BasicBasket, UUID> {
}
