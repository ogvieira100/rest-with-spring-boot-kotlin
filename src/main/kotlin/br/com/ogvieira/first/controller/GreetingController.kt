package br.com.ogvieira.first.controller


import br.com.ogvieira.first.Greeting
import br.com.ogvieira.first.exceptions.UnsupportedMathOperationException


import br.com.ogvieira.first.math.SimpleMath
import br.com.ogvieira.first.request.converters.NumberConverter
import br.com.ogvieira.first.request.converters.NumberConverter.isNumeric
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong


@RestController
@RequestMapping("/api/greeting/v1")
@Tag(name = "Greeting", description = "Endpoints for mapping filter")
class GreetingController {

    val counter:AtomicLong =  AtomicLong();
    private val math = SimpleMath()

    @RequestMapping("/greeting")
    fun greeting(@RequestParam(value="name", defaultValue = "Hello Word") name:String?): Greeting {
        return  Greeting(counter.incrementAndGet(),"Hello Kotlin $name!");
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    fun subtract(@PathVariable(value="numberOne") numberOne: String?
                ,@PathVariable(value="numberTwo") numberTwo: String?):Double
    {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo) )
            throw  UnsupportedMathOperationException("Por favor set um valor numérico");

        return math.subtraction(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    fun multiple(@PathVariable(value="numberOne") numberOne: String?
                 ,@PathVariable(value="numberTwo") numberTwo: String?):Double
    {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo) )
            throw  UnsupportedMathOperationException("Por favor set um valor numérico");
        return math.multiplication(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    fun division(@PathVariable(value="numberOne") numberOne: String?
                 ,@PathVariable(value="numberTwo") numberTwo: String?):Double
    {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo) )
            throw  UnsupportedMathOperationException("Por favor set um valor numérico");
        return math.division(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }


    @RequestMapping(value=["/sum/{numberOne}/{numberTwo}"])
    fun sum( @PathVariable(value="numberOne") numberOne: String?
            ,@PathVariable(value="numberTwo") numberTwo: String?
    ):Double
    {
        if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo) )
            throw  UnsupportedMathOperationException("Por favor set um valor numérico");
        return math.sum(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo));
    }

    @RequestMapping(value = ["/mean/{numberOne}/{numberTwo}"])
    fun mean(@PathVariable("numberOne") numberOne: String?, @PathVariable("numberTwo") numberTwo: String?): Double {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.mean(NumberConverter.convertDouble(numberOne), NumberConverter.convertDouble(numberTwo))
    }

    @RequestMapping(value = ["/squareRoot/{number}"])
    fun squareRoot(@PathVariable("number") number: String?): Double {
        if (!isNumeric(number)) {
            throw UnsupportedMathOperationException("Please set a numeric value!")
        }
        return math.squareRoot(NumberConverter.convertDouble(number))
    }


}