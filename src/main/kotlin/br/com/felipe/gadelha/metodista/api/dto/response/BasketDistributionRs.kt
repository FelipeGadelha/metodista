package br.com.felipe.gadelha.metodista.api.dto.response

import br.com.felipe.gadelha.metodista.domain.model.BasketDistribution
import java.time.Instant
import java.util.UUID

class BasketDistributionRs private constructor(
    val id: UUID,
    val distributionDate: Instant,
    val basicBasketId: UUID,
    val familyId: UUID
) {

    constructor(basketDistribution: BasketDistribution) :
        this(
            id = basketDistribution.id!!,
            distributionDate = basketDistribution.distributionDate,
            basicBasketId = basketDistribution.getBasicBasketId()!!,
            familyId = basketDistribution.getFamilyId()!!,
        )
}
