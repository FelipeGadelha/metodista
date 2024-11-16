package br.com.felipe.gadelha.metodista.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "basic_basket")
data class BasicBasket(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val description: String = "",
    val expirationDate: Instant = Instant.now()
) {
    fun update(updated: BasicBasket): BasicBasket = BasicBasket(
        id = if (Objects.isNull(id)) this.id else updated.id,
        expirationDate = updated.expirationDate,
        description = updated.description
    )
}