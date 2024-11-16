package br.com.felipe.gadelha.metodista.api.dto.request

import br.com.felipe.gadelha.metodista.domain.model.Family
import com.fasterxml.jackson.annotation.JsonCreator

class FamilyRq
@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
constructor(
    private val headOfFamily: String
) {
    fun toModel(): Family {
        return Family(
            headOfFamily = headOfFamily,
        )
    }
}
