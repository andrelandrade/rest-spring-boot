package com.example.services

import com.example.controller.BookController
import com.example.data.vo.v1.BookVO
import com.example.exceptions.RequiredObjectIsNullException
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.model.Book
import com.example.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookVO> {
        logger.info("Finding all books!")
        val books = repository.findAll()
        val vos = DozerMapper.parseListObjects(books, BookVO::class.java)

        for (book in vos) {
            val withSelfRel = linkTo(BookController::class.java).slash(book.key).withSelfRel()
            book.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): BookVO {
        logger.info("Finding one book with ID $id!")
        val book = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID!") }

        val bookVO: BookVO = DozerMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()

        bookVO.add(withSelfRel)

        return bookVO
    }

    fun create(book: BookVO?): BookVO {
        if (book == null) throw RequiredObjectIsNullException()

        logger.info("Creating one book with title ${book.title}!")
        val entity = DozerMapper.parseObject(book, Book::class.java)

        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()

        bookVO.add(withSelfRel)

        return bookVO
    }

    fun update(book: BookVO?): BookVO {
        if (book == null) throw RequiredObjectIsNullException()

        logger.info("Updating one book with title ${book.title}!")
        val entity = DozerMapper.parseObject(findById(book.key), Book::class.java)

        entity.author = book.author
        entity.launchDate = book.launchDate
        entity.price = book.price
        entity.title = book.title

        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()

        bookVO.add(withSelfRel)

        return bookVO
    }

    fun delete(id: Long) {
        logger.info("Deleting one book!")

        val entity = DozerMapper.parseObject(findById(id), Book::class.java)
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