package br.com.ogvieira.first

import br.com.ogvieira.first.exceptions.UnsupportedMathOperationException
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

    @RequestMapping("/subtract/{numberOne}/{numberTwo}")
    fun subtract(@PathVariable(value="numberOne") numberOne: String?
                ,@PathVariable(value="numberTwo") numberTwo: String?):Double
    {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo) )
            throw  UnsupportedMathOperationException("Por favor set um valor numérico");

        return  convertDouble(numberOne) - convertDouble(numberTwo) ;
    }

    @RequestMapping("/multiple/{numberOne}/{numberTwo}")
    fun multiple(@PathVariable(value="numberOne") numberOne: String?
                 ,@PathVariable(value="numberTwo") numberTwo: String?):Double
    {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo) )
            throw  UnsupportedMathOperationException("Por favor set um valor numérico");

        return  convertDouble(numberOne) * convertDouble(numberTwo) ;
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    fun division(@PathVariable(value="numberOne") numberOne: String?
                 ,@PathVariable(value="numberTwo") numberTwo: String?):Double
    {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo) )
            throw  UnsupportedMathOperationException("Por favor set um valor numérico");

        return  convertDouble(numberOne) / convertDouble(numberTwo) ;
    }


    @RequestMapping(value=["/sum/{numberOne}/{numberTwo}"])
    fun sum( @PathVariable(value="numberOne") numberOne: String?
            ,@PathVariable(value="numberTwo") numberTwo: String?
    ):Double
    {
           if (!isNumeric(numberOne) || !isNumeric(numberTwo) )
                 throw  UnsupportedMathOperationException("Por favor set um valor numérico");
          return  convertDouble(numberOne) + convertDouble(numberTwo) ;
    }

    private fun convertDouble(strNumber: String?): Double {
        if (strNumber.isNullOrBlank())
            return  0.0
        val number = strNumber.replace(",".toRegex(),".");
        if (isNumeric(strNumber))
            return number.toDouble();
        return  0.0
    }



    private fun isNumeric(strNumber: String?): Boolean {
        if (strNumber.isNullOrBlank()) return false
        return strNumber.matches("""[-+]?[0-9]+(\.[0-9]+)?""".toRegex())
    }
}