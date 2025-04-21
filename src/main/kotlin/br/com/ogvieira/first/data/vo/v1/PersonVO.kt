package br.com.ogvieira.first.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel


//@JsonPropertyOrder("id", "first_name", "lastName", "last_name", "gender")
data class PersonVO (
                    @field:JsonProperty("id")
                    var id: Long = 0,
                    @field:JsonProperty("firstName")
                    var firstName: String = "",
                    @field:JsonProperty("lastName")
                    var lastName: String = "",
                    @field:JsonProperty("address")
                    var address: String = "",
                    @field:JsonProperty("gender")
                    var gender: String = ""
                )