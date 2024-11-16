package br.com.felipe.gadelha.metodista.api.dto.request

import br.com.felipe.gadelha.metodista.domain.model.BasicBasket
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant

class BasicBasketRq(
    @NotBlank val description: String,
    @NotNull val expirationDate: Instant
) {
    fun toModel() = BasicBasket(
        description = description,
        expirationDate = expirationDate
    )
}