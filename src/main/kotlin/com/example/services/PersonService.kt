package com.example.services

import com.example.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {

    private val counter: AtomicLong = AtomicLong()

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Finding all people!")

        val persons: MutableList<Person> = mutableListOf()

        for (i in 0..7) {
            persons.add(mockPerson(i))
        }

        return persons
    }

    fun findById(id: Long): Person {
        logger.info("Finding one person!")

        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "John"
        person.lastName = "Wick"
        person.address = "Street XPTO"
        person.gender = "Male"
        return person
    }

    fun create(person: Person) = person

    fun update(person: Person) = person

    fun delete(id: Long) {}

    private fun mockPerson(i: Int): Person {
        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "Person Name $i"
        person.lastName = "Last name $i"
        person.address = "Some address in Brazil"
        person.gender = "Male"
        return person
    }
}