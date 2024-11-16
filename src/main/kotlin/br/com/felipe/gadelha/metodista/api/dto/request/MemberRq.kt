package br.com.felipe.gadelha.metodista.api.dto.request

import br.com.felipe.gadelha.metodista.domain.model.Family
import br.com.felipe.gadelha.metodista.domain.model.Gender
import br.com.felipe.gadelha.metodista.domain.model.Member
import br.com.felipe.gadelha.metodista.domain.model.Occupation
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import java.time.Instant
import java.util.*

@Validated
class MemberRq(
    @NotBlank val name: String = "",
    @NotBlank val email: String = "",
    @NotBlank val password: String = "",
    @NotBlank val phone: String = "",
    @NotBlank val familyId: String? = null,
    @NotBlank val gender: Gender = Gender.UNDEFINED,
    @NotBlank val identificationNumber: String = "",
    @NotBlank val receivesBenefits: Boolean = false,
    @Valid @NotNull val address: AddressRq,
    @NotBlank val birthDate: Instant,
    @NotBlank val maritalStatus: String = "",
    @NotBlank val occupation: Occupation = Occupation.UNEMPLOYED,
    @NotBlank val profession: String = "",
    @NotBlank val educationLevel: String = ""
) {

    fun toModel(): Member {
        return Member(
            id = null,
            name = name,
            email = email,
            phone = phone,
            family = if (Objects.isNull(familyId)) null
                    else Family(UUID.fromString(this.familyId)),
            gender = gender,
            password = password,
            identificationNumber = identificationNumber,
            receivesBenefits = receivesBenefits,
            address = address.toModel(),
            birthDate = birthDate,
            maritalStatus = maritalStatus,
            occupation = occupation,
            profession = profession,
            educationLevel = educationLevel
        )
    }
}
