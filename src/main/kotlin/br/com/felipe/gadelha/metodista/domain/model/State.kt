package br.com.felipe.gadelha.metodista.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.util.Objects

@Entity
@Table(name = "states")
data class State(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @field:NotBlank val name: String = ""
) {
    fun update(updated: State) = State(
        id = if (Objects.isNull(id)) this.id else updated.id,
        name = updated.name
    )
}