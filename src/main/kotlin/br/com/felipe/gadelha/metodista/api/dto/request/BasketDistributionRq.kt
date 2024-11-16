package br.com.felipe.gadelha.metodista.api.dto.request

import br.com.felipe.gadelha.metodista.domain.model.BasicBasket
import br.com.felipe.gadelha.metodista.domain.model.BasketDistribution
import br.com.felipe.gadelha.metodista.domain.model.Family
import java.time.Instant
import java.util.UUID

class BasketDistributionRq(
    private val distributionDate: Instant,
    private val familyId: String,
    private val basicBasketId: String
) {

    fun toModel(): BasketDistribution = BasketDistribution(
        distributionDate = this.distributionDate,
        family = Family(UUID.fromString(familyId)),
        basicBasket = BasicBasket(UUID.fromString(basicBasketId))
    )
}
