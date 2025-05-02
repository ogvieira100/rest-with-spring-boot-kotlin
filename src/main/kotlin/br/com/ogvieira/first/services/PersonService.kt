package br.com.ogvieira.first.services

import br.com.ogvieira.first.controller.PersonController
import br.com.ogvieira.first.data.vo.v1.PersonRequestPaginateVO
import br.com.ogvieira.first.data.vo.v1.PersonResponsePaginated

import br.com.ogvieira.first.data.vo.v1.PersonVO
import br.com.ogvieira.first.exceptions.RequiredObjectIsNullException
import br.com.ogvieira.first.exceptions.ResourceNotFoundException
import br.com.ogvieira.first.mapper.DozerMapper
import br.com.ogvieira.first.mapper.custom.PersonMapper
import br.com.ogvieira.first.model.Person
import br.com.ogvieira.first.repository.PersonRepository
import org.springframework.data.domain.Pageable
import com.sun.source.tree.TryTree
import org.hibernate.query.results.Builders.entity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService( private val repository: PersonRepository) {



    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<PersonVO>

    private val logger = Logger.getLogger(PersonService::class.java.name)


    fun findPaginateWithjdbcClient(personRequestPaginateVO: PersonRequestPaginateVO): PersonResponsePaginated {
        return repository.findPaginateWithjdbcClient(personRequestPaginateVO)
    }
    fun findAll(): List<PersonVO> {

        logger.info("Finding all person!")
        val persons = repository.findAll()
        return DozerMapper.parseListObject(persons, PersonVO::class.java)
    }





    fun findById(id: Long): PersonVO {
        logger.info("Finding one person!")

        var person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun createV1(person: PersonVO?) : PersonVO{
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating one person with name ${person.first_name}!")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)
        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun update(person: PersonVO?): PersonVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Updating one person with ID ${person.id}!")
        val entity = repository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        entity.firstName = person.first_name
        entity.lastName = person.last_name
        entity.address = person.address
        entity.gender = person.gender
        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)

    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id!")
        val entity = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }
        repository.delete(entity)
    }

    fun create(person: br.com.ogvieira.first.data.vo.v2.PersonVO?): br.com.ogvieira.first.data.vo.v2.PersonVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating one person with name ${person.firstName}!")
        val personMapper =  PersonMapper()
        var entity: Person = personMapper.mapVOToEntity(person)
        return personMapper.mapEntityToVO(repository.save(entity))
    }
}