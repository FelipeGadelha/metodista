package br.com.felipe.gadelha.metodista.api.dto.request

import br.com.felipe.gadelha.metodista.domain.model.State
import jakarta.validation.constraints.NotBlank

class StateRq(
    @NotBlank val name: String
) {
    fun toModel() = State(name = name)
}