package br.com.felipe.gadelha.metodista.api.dto.request

import br.com.felipe.gadelha.metodista.domain.model.Address
import br.com.felipe.gadelha.metodista.domain.model.City
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


class AddressRq(
    @field:NotBlank val cep: String,
    @field:NotBlank val place: String,
    @field:NotBlank val number: String,
    val complement: String?,
    @field:NotBlank val district: String,
    @field:NotNull val cityId: Long
) {
    fun toModel(): Address {
        return Address(
            cep = cep,
            place = place,
            number = number,
            complement = complement,
            district = district,
            city = City(cityId)
        )
    }
}