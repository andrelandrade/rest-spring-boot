package com.example.services

import com.example.data.vo.v1.PersonVO
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.model.Person
import com.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")

        return DozerMapper.parseListObjects(repository.findAll(), PersonVO::class.java)
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person!")
        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun create(personVO: PersonVO): PersonVO {
        logger.info("Creating one person with name ${personVO.firstName}!")
        val entity = DozerMapper.parseObject(personVO, Person::class.java)

        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun update(person: PersonVO): PersonVO {
        logger.info("Updating one person with name ${person.firstName}!")
        val entity = DozerMapper.parseObject(findById(person.id), Person::class.java)

        entity.firstName = person.firstName
        entity.lastName  = person.lastName
        entity.address   = person.address
        entity.gender    = person.gender

        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting one person!")

        val entity = DozerMapper.parseObject(findById(id), Person::class.java)
        repository.delete(entity)
    }
}