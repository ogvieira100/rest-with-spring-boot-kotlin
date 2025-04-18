package br.com.ogvieira.first.model

import jakarta.persistence.*

@Entity
@Table(name = "Person")
data class Person (

                    @Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    var id: Long = 0,
                    @Column(name = "firstName", nullable = false, length = 200)
                    var firstName: String = "",
                    @Column(name = "lastName", nullable = false, length = 200)
                    var lastName: String = "",
                    @Column(nullable = false, length = 200)
                    var address: String = "",
                    @Column(nullable = false, length = 100)
                    var gender: String = ""
                )