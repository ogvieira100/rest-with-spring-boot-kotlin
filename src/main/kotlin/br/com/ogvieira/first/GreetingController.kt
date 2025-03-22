package br.com.ogvieira.first

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong


@RestController
class GreetingController {

    val counter:AtomicLong =  AtomicLong();

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value="name", defaultValue = "Hello Word") name:String?):Greeting{
        return  Greeting(counter.incrementAndGet(),"Hello Kotlin $name!");
    }


    @RequestMapping(value=["/sum/{numberOne}/{numberTwo}"])
    fun sum(@PathVariable(value="numberOne") numberOne: String?
            ,@PathVariable(value="numberTwo") numberTwo: String?
    ):Double{
          return  1.500;
    }
}