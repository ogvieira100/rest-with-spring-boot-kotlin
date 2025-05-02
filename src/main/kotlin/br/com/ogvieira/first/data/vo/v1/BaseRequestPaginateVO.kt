package br.com.ogvieira.first.data.vo.v1

class BaseRequestVO (
        page: Int = 1,
        limit:Int = 10,
        active:Boolean = true,
        column:String = "",
        desc:Boolean = false
){

    var page: Int = page
        get() = field
        set(value) {
            field = if (value < 1) 1 else value
        }

    var limit: Int = limit
        get() = field
        set(value) {
            field = if (value < 1) 10 else value
        }

    var active: Boolean = active
        get() = field
        set(value) {
            field = value
        }

    var column: String = column
        get() = field
        set(value) {
            field = value.trim()
        }

    var desc: Boolean = desc
        get() = field
        set(value) {
            field = value
        }


}