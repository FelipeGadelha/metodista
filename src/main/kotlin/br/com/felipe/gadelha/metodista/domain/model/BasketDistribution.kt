package br.com.felipe.gadelha.metodista.domain.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.Instant
import java.util.*

@Entity
@Table(name = "basket_distribution")
data class BasketDistribution(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @NotNull @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "family_id", nullable = false)
    val family: Family = Family(),

    @ManyToOne @JoinColumn(name = "basic_basket_id", nullable = false)
    val basicBasket: BasicBasket,

    val distributionDate: Instant = Instant.now()
) {
    fun getFamilyId() = this.family.id

    fun update(updated: BasketDistribution): BasketDistribution = BasketDistribution(
        id = if (Objects.isNull(id)) this.id else updated.id,
        family = updated.family,
        distributionDate = updated.distributionDate,
        basicBasket = updated.basicBasket
    )

    fun getBasicBasketId() = this.basicBasket.id
}