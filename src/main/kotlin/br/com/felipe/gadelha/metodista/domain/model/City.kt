package br.com.felipe.gadelha.metodista.domain.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import java.util.Objects

@Entity
@Table(name = "cities")
data class City(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @NotBlank val name: String = "",
    @NotNull
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "state_id", nullable = false)
    val state: State = State()
) {
    fun update(updated: City) = City(
        id = if (Objects.isNull(id)) this.id else updated.id,
        name = updated.name,
        state = updated.state
    )
}