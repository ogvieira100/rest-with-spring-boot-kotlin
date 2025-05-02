package br.com.ogvieira.first.controller
import br.com.ogvieira.first.data.vo.v1.PersonRequestPaginateVO
import br.com.ogvieira.first.data.vo.v1.PersonResponsePaginated
import br.com.ogvieira.first.data.vo.v1.PersonVO as PersonVOv1
import br.com.ogvieira.first.data.vo.v2.PersonVO as PersonVOv2
import br.com.ogvieira.first.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import br.com.ogvieira.first.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.swing.text.AbstractDocument
//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for mapping filter")
class PersonController {

    @Autowired
    private lateinit var service: PersonService

    @RequestMapping(method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Find All People", description = "Find All People", tags = ["People"],
        responses = [
                        ApiResponse(
                            description = "Success",
                            responseCode = "200" ,
                            content = [Content(array = ArraySchema(schema = Schema(implementation= PersonVOv1::class)))]),
                            ApiResponse(description = "No Content", responseCode = "204", content = [
                                Content(schema = Schema(implementation = Unit::class))
                            ]),
                            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                                Content(schema = Schema(implementation = Unit::class))
                            ]),
                            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                                Content(schema = Schema(implementation = Unit::class))
                            ]),
                            ApiResponse(description = "Not Found", responseCode = "404", content = [
                                Content(schema = Schema(implementation = Unit::class))
                            ]),
                            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                                Content(schema = Schema(implementation = Unit::class))
                            ])
                    ])
    fun findAll(): List<PersonVOv1> {
        return service.findAll()
    }

    //@CrossOrigin(origins = ["http://localhost:8080"])
    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET],
        produces =  [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Finds a Person", description = "Finds a Person",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = PersonVOv1::class))
                ]
            ),
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun findById(@PathVariable(value="id") id: Long): PersonVOv1 {
        return service.findById(id)
    }

    @RequestMapping(method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Adds a new PersonV1", description = "Adds a new Person",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = PersonVOv1::class))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun create(@RequestBody person: PersonVOv1): PersonVOv1 {
        return service.createV1(person)
    }
    @RequestMapping(
        "/findAllPaged",
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML]
    )
    @Operation(summary = "Finds all People", description = "Finds all People",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(array = ArraySchema(schema = Schema(implementation = PersonVOv1::class)))
                ]
            ),
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun findAllPaged(@ModelAttribute personRequestPaginateVO:PersonRequestPaginateVO): PersonResponsePaginated {
                return service.findPaginateWithjdbcClient(personRequestPaginateVO)
    };



    @RequestMapping("/v2",method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces =  [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Adds a new PersonV2", description = "Adds a new Person",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = PersonVOv2::class))
                ]
            ),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun createv2(@RequestBody person: PersonVOv2): PersonVOv2 {
        return service.create(person)
    }

    @RequestMapping(method = [RequestMethod.PUT],
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        produces =  [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Updates a person's information", description = "Updates a person's information",
        tags = ["People"],
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [
                    Content(schema = Schema(implementation = PersonVOv1::class))
                ]
            ),
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun update(@RequestBody person: PersonVOv1): PersonVOv1 {
        return service.update(person)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE],
        produces =  [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Deletes a person", description = "Deletes a person",
        tags = ["People"],
        responses = [
            ApiResponse(description = "No Content", responseCode = "204", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Bad Request", responseCode = "400", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Not Found", responseCode = "404", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(description = "Internal Error", responseCode = "500", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
        ]
    )
    fun delete(@PathVariable(value="id") id: Long) {
        service.delete(id)
    }

}