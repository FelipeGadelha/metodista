package br.com.felipe.gadelha.metodista.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Embeddable
data class Address(
    @Column(name = "address_cep") val cep: String = "",
    @Column(name = "address_place") val place: String = "",
    @Column(name = "address_number") val number: String = "",
    @Column(name = "address_complement") val complement: String? = null,
    @Column(name = "address_district") val district: String = "",
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(*["hibernateLazyInitializer"]) //, "handler"})
    @JoinColumn(name = "address_city_id") val city: City = City()
)