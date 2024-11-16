package br.com.felipe.gadelha.metodista.api.dto.response

import br.com.felipe.gadelha.metodista.domain.model.BasicBasket
import java.time.Instant
import java.util.*

class BasicBasketRs private constructor(
    val id: UUID,
    val description: String,
    val expirationDate: Instant
) {
    constructor(basicBasket: BasicBasket) :
        this(
            id = basicBasket.id!!,
            description = basicBasket.description,
            expirationDate = basicBasket.expirationDate
        )
}
