package br.com.ogvieira.first.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import br.com.ogvieira.first.model.Permission

@Entity
@Table(name = "users")
class User : UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "user_name", unique = true)
    var userName: String? = null

    @Column(name = "full_name")
    var fullName: String? = null

    @Column(name = "password")
    private var password: String? = null

    @Column(name = "account_non_expired")
    var accountNonExpired: Boolean? = null

    @Column(name = "account_non_locked")
    var accountNonLocked: Boolean? = null

    @Column(name = "credentials_non_expired")
    var credentialsNonExpired: Boolean? = null

    @Column(name = "enabled")
    var enabled: Boolean? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_permission",
        joinColumns = [JoinColumn(name = "id_user")],
        inverseJoinColumns = [JoinColumn(name = "id_permission")]
    )
    var permissions: MutableList<Permission> = mutableListOf()


    val roles: List<String?>
        get(){
            val roles: MutableList<String?> = ArrayList()
            for (permission in permissions!!) {
                roles.add(permission.description)
            }
            return roles
        }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return permissions!!
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return userName!!
    }

    override fun isAccountNonExpired() = accountNonExpired ?: true
    override fun isAccountNonLocked() = accountNonLocked ?: true
    override fun isCredentialsNonExpired() = credentialsNonExpired ?: true
    override fun isEnabled() = enabled ?: true
}