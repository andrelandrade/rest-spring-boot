package com.example.controller

import com.example.data.vo.v1.PersonVO
import com.example.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var service: PersonService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): List<PersonVO> {
        return service.findAll()
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable(value="id") id: Long): PersonVO {
        return service.findById(id)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
                 produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody personVO: PersonVO): PersonVO {
        return service.create(personVO)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
                produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody personVO: PersonVO): PersonVO {
        return service.update(personVO)
    }

    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable(value="id") id: Long): ResponseEntity<*> {
        service.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }
}