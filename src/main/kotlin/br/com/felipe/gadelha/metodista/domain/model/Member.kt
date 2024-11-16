package br.com.felipe.gadelha.metodista.domain.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import java.util.Objects
import java.util.UUID

@Entity
@Table(name = "members")
data class Member(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(unique = true) @NotBlank
    val name: String = "",

    @Column(unique = true) @NotBlank @JvmField
    val email: String = "",

    @NotBlank @JvmField
    val password: String = "",

    @NotBlank val birthDate: Instant = Instant.now(),
    @NotBlank val identificationNumber: String = "",

    @Enumerated(EnumType.STRING)
    val gender: Gender = Gender.UNDEFINED,

    @NotBlank val educationLevel: String = "",
    @NotBlank val maritalStatus: String = "",
    @NotBlank val profession: String = "",
    @Embedded val address: Address = Address(),
    @NotBlank val phone: String = "",

    @Column(name = "receives_benefits", nullable = false)
    @NotNull val receivesBenefits: Boolean = false,

    @Enumerated(EnumType.STRING)
    val occupation: Occupation = Occupation.UNEMPLOYED,

    @Column(name = "is_system_user", nullable = false)
    val isSystemUser: Boolean = false,

    @ManyToOne(optional = true)
    @JoinColumn(name = "family_id", nullable = true)
    @JsonBackReference
    val family: Family? = null,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "members_roles",
        joinColumns = [JoinColumn(name = "member_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<Role> = HashSet()
): UserDetails {

    fun update(updated: Member) = Member(
        id = if (Objects.isNull(id)) this.id else updated.id,
        name = updated.name,
        email = updated.email,
        password = updated.password,
        birthDate = updated.birthDate,
        identificationNumber = updated.identificationNumber,
        gender = updated.gender,
        educationLevel = updated.educationLevel,
        maritalStatus = updated.maritalStatus,
        profession = updated.profession,
        address = updated.address,
        phone = updated.phone,
        receivesBenefits = updated.receivesBenefits,
        occupation = updated.occupation,
        isSystemUser = updated.isSystemUser,
        family = if (Objects.isNull(updated.family)) this.family else updated.family,
        roles = if (Objects.isNull(updated.roles)) this.roles else updated.roles
    )

    fun getCityId(): Long? = this.address.city.id
    fun getFamilyId(): UUID? = this.family?.id
    fun addRole(role: Role) { roles.add(role) }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = this.roles

    override fun getPassword(): String = this.password
    override fun getUsername(): String = this.email
    override fun isAccountNonExpired(): Boolean = isSystemUser
    override fun isAccountNonLocked(): Boolean = isSystemUser
    override fun isCredentialsNonExpired(): Boolean = isSystemUser
    override fun isEnabled(): Boolean = isSystemUser
}