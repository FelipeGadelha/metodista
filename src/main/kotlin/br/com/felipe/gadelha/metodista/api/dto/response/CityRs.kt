package br.com.felipe.gadelha.metodista.api.dto.response

import br.com.felipe.gadelha.metodista.api.dto.View
import br.com.felipe.gadelha.metodista.domain.model.City
import com.fasterxml.jackson.annotation.JsonView

class CityRs private constructor(
    @JsonView(View.Basic::class) val id: Long,
    @JsonView(View.Basic::class) val name: String,
    val state: StateRs
) {
    constructor(city: City) :
            this(
                id = city.id!!,
                name = city.name,
                state = StateRs(city.state)
            )
}