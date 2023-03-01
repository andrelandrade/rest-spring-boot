package com.example.services

import com.example.controller.PersonController
import com.example.data.vo.v1.PersonVO
import com.example.exceptions.RequiredObjectIsNullException
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.model.Person
import com.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all people!")
        val people = repository.findAll()
        val vos = DozerMapper.parseListObjects(people, PersonVO::class.java)

        for (person in vos) {
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id!")
        val person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        val personVO: PersonVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()

        personVO.add(withSelfRel)

        return personVO
    }

    fun create(person: PersonVO?): PersonVO {
        if (person == null) throw RequiredObjectIsNullException()

        logger.info("Creating one person with name ${person.firstName}!")
        val entity = DozerMapper.parseObject(person, Person::class.java)

        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()

        personVO.add(withSelfRel)

        return personVO
    }

    fun update(person: PersonVO?): PersonVO {
        if (person == null) throw RequiredObjectIsNullException()

        logger.info("Updating one person with name ${person.firstName}!")
        val entity = DozerMapper.parseObject(findById(person.key), Person::class.java)

        entity.firstName = person.firstName
        entity.lastName  = person.lastName
        entity.address   = person.address
        entity.gender    = person.gender

        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()

        personVO.add(withSelfRel)

        return personVO
    }

    fun delete(id: Long) {
        logger.info("Deleting one person!")

        val entity = DozerMapper.parseObject(findById(id), Person::class.java)
        repository.delete(entity)
    }

    //@Autowired
    //private lateinit var mapper: PersonMapper

    //fun createV2(personVO: PersonVOV2): PersonVOV2 {
    //    logger.info("Creating one person with name ${personVO.firstName}!")
    //    val entity = mapper.mapVOToEntity(personVO)
    //
    //    return mapper.mapEntityToVO(repository.save(entity))
    //}
}