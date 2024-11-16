package br.com.felipe.gadelha.metodista.api.dto.response

import br.com.felipe.gadelha.metodista.domain.model.State

class StateRs(
    val id: Long,
    val name: String
) {
    constructor(state: State) :
        this(
            id = state.id!!,
            name = state.name,
        )
}