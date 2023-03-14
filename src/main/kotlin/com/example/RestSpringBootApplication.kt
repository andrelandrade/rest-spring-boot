package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestSpringBootApplication

fun main(args: Array<String>) {
	runApplication<RestSpringBootApplication>(*args)

	/*
	val encoders: MutableMap<String, PasswordEncoder> = HashMap()
	encoders["pbkdf2"] = Pbkdf2PasswordEncoder()
	val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)
	passwordEncoder.setDefaultPasswordEncoderForMatches(Pbkdf2PasswordEncoder())

	val result = passwordEncoder.encode("foo-bar")
	println("My hash $result")
	*/

}
