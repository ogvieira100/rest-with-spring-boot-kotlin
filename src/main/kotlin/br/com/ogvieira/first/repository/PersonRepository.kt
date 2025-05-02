package br.com.ogvieira.first.repository
import br.com.ogvieira.first.data.vo.v1.PersonRequestPaginateVO
import br.com.ogvieira.first.data.vo.v1.PersonResponsePaginated
import br.com.ogvieira.first.data.vo.v1.PersonVO
import br.com.ogvieira.first.data.vo.v1.PersonVO as PersonVOv1
import br.com.ogvieira.first.data.vo.v2.PersonVO as PersonVOv2
import br.com.ogvieira.first.model.Person
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
interface PersonRepository : JpaRepository<Person, Long?>, PersonRepositoryCustom

interface PersonRepositoryCustom {
    fun findPaginateWithjdbcClient(personRequestPaginateVO: PersonRequestPaginateVO): PersonResponsePaginated
    fun findPaginatedWithJPQL(personRequestPaginateVO: PersonRequestPaginateVO): PersonResponsePaginated
    fun findPaginatedWithCriteria(personRequestPaginateVO: PersonRequestPaginateVO): PersonResponsePaginated
}

class PersonRepositoryImpl(
    @PersistenceContext private val entityManager: EntityManager,
    private val jdbcClient: JdbcClient

) : PersonRepositoryCustom {



/*
   override fun findPersonsByFirstName(firstName: String, pageable: Pageable): Page<Person> {
        val sql = """
            SELECT id, firstName, lastName, address, gender 
            FROM Person 
            WHERE firstName LIKE ? 
            ORDER BY id 
            LIMIT ? OFFSET ?
        """.trimIndent()

        val persons = jdbcClient!!.sql(sql).query(PersonVOv1::class.java).list()

        val total = jdbcClient!!.sql("SELECT COUNT(*) FROM Person WHERE firstName LIKE ?").query()


        return PageImpl(persons, pageable, total)
    }

 */

    override fun findPaginateWithjdbcClient(personRequestPaginateVO: PersonRequestPaginateVO): PersonResponsePaginated {
        var listPerson:MutableList<PersonVO>  = mutableListOf() ;
        val params = mutableMapOf<String, Any>()
        var page: Int = personRequestPaginateVO.page
        var pageSize:Int = 10;
        var totalItens: Int = 0;
        var totalPages: Int = 0;
        val limit = personRequestPaginateVO.limit
        val offset = (page - 1) * limit
        val sql = buildString {
            append("SELECT id, first_name, last_name, address, gender, enabled FROM person WHERE 1=1  ")

            getconsult(personRequestPaginateVO, params)
            append(" ORDER BY id LIMIT $limit OFFSET $offset")
            params["limit"] = limit
            params["offset"] = offset
       }
       val sqlTotalItens =  buildString{
           append("SELECT count(*) FROM person WHERE 1=1  ")
           getconsult(personRequestPaginateVO, params)
       }

        totalItens = jdbcClient!!.sql(sqlTotalItens)
            .params(params)
            .query(Int::class.java)
            .single()

        val results = jdbcClient!!.sql(sql)
            .params(params)
            .query(PersonVO::class.java)
            .list()

        totalPages = (totalItens + pageSize - 1) / pageSize

        return  PersonResponsePaginated(items = results,
                                        page = personRequestPaginateVO.page,
                                        pageSize = personRequestPaginateVO.limit,
                                        totalItens = totalItens,
                                        totalPages = totalPages)
    }

    private fun StringBuilder.getconsult(
        personRequestPaginateVO: PersonRequestPaginateVO,
        params: MutableMap<String, Any>
    ) {
        if (personRequestPaginateVO.id != null) {
            append(" AND id = :id")
            val idPar = personRequestPaginateVO.id
            params["id"] = idPar!!
        }

        if (!personRequestPaginateVO.first_name.isNullOrBlank()) {
            append(" AND firstName LIKE :firstName")
            params["firstName"] = "%${personRequestPaginateVO.first_name}%"
        }

        if (!personRequestPaginateVO.last_name.isNullOrBlank()) {
            append(" AND lastName LIKE :lastName ")
            params["lastName"] = "%${personRequestPaginateVO.last_name}%"
        }

        if (!personRequestPaginateVO.gender.isNullOrBlank()) {
            append(" AND gender LIKE :gender ")
            params["gender"] = "%${personRequestPaginateVO.gender}%"
        }
    }

    override fun findPaginatedWithJPQL(personRequestPaginateVO: PersonRequestPaginateVO): PersonResponsePaginated {
        TODO("Not yet implemented")
    }

    override fun findPaginatedWithCriteria(personRequestPaginateVO: PersonRequestPaginateVO): PersonResponsePaginated {
        TODO("Not yet implemented")
    }
    /*
        override fun findPaginatedWithJPQL(pageable: Pageable): Page<Person> {
            val query = entityManager.createQuery(
                "SELECT p FROM Person p ORDER BY p.firstName ASC",
                Person::class.java
            )
            query.firstResult = pageable.offset.toInt()
            query.maxResults = pageable.pageSize

            val resultList = query.resultList

            val countQuery = entityManager.createQuery(
                "SELECT COUNT(p) FROM Person p",
                Long::class.javaObjectType
            )
            val total = countQuery.singleResult

            return PageImpl(resultList, pageable, total)
        }

     */
/*
    override fun findPaginatedWithCriteria(pageable: Pageable): Page<Person> {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery: CriteriaQuery<Person> = builder.createQuery(Person::class.java)
        val root: Root<Person> = criteriaQuery.from(Person::class.java)

        criteriaQuery.select(root)
        criteriaQuery.orderBy(builder.asc(root.get<String>("firstName")))

        val query = entityManager.createQuery(criteriaQuery)
        query.firstResult = pageable.offset.toInt()
        query.maxResults = pageable.pageSize

        val resultList = query.resultList

        // Count
        val countCriteriaQuery: CriteriaQuery<Long> = builder.createQuery(Long::class.javaObjectType)
        val countRoot: Root<Person> = countCriteriaQuery.from(Person::class.java)
        countCriteriaQuery.select(builder.count(countRoot))

        val total = entityManager.createQuery(countCriteriaQuery).singleResult

        return PageImpl(resultList, pageable, total)
    }

 */
}