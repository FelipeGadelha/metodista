package br.com.felipe.gadelha.metodista.api.dto.request

import br.com.felipe.gadelha.metodista.domain.model.City
import br.com.felipe.gadelha.metodista.domain.model.State
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class CityRq(
    @NotBlank val name: String,
    @NotNull val stateId: Long
) {
    fun toModel() = City(name = name, state = State(id = stateId))
}