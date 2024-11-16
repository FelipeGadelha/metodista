package br.com.felipe.gadelha.metodista.domain.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "families")
data class Family(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    val headOfFamily: String = "",
    @OneToMany(mappedBy = "family")
    @JsonManagedReference
    val members: List<Member> = emptyList(),
)
