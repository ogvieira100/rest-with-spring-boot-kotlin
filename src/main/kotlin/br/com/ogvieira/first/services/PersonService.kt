package br.com.ogvieira.first.services

import br.com.ogvieira.first.exceptions.ResourceNotFoundException
import br.com.ogvieira.first.model.Person
import br.com.ogvieira.first.repository.PersonRepository
import com.sun.source.tree.TryTree
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {


    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Finding all person!")
        return repository.findAll()
    }

    fun findById(id: Long): Person {
        logger.info("Finding one person!")

        return repository.findById(id).orElseThrow({ResourceNotFoundException("No records find exception")});
    }

    fun create(person: Person) : Person{

        logger.info("Creating one person with name ${person.firstName}!")
        return  try {
            logger.info("Creating one person with name ${person.firstName}!")
            repository.save(person)
        } catch (e: Exception) {
            logger.info("Erro ${e.message}!")
            throw RuntimeException("Erro ao salvar a pessoa", e)
        }
    }

    fun update(person: Person): Person {

        val entity = findById(person.id);

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        return repository.save(entity)

    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID $id!")
        val entity = findById(id)
        repository.delete(entity)
    }



}