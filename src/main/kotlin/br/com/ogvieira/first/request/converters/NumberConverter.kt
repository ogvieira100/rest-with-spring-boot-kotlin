package br.com.ogvieira.first.request.converters

object NumberConverter {

    public fun convertDouble(strNumber: String?): Double {
        if (strNumber.isNullOrBlank())
            return  0.0
        val number = strNumber.replace(",".toRegex(),".");
        if (isNumeric(strNumber))
            return number.toDouble();
        return  0.0
    }



    public fun isNumeric(strNumber: String?): Boolean {
        if (strNumber.isNullOrBlank()) return false
        return strNumber.matches("""[-+]?[0-9]+(\.[0-9]+)?""".toRegex())
    }
}