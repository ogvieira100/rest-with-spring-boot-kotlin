package br.com.ogvieira.first.exceptions
import org.springframework.http.HttpStatus
import  java.lang.*
import  org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
class ResourceNotFoundException(exception: String?): RuntimeException(exception)