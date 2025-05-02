package br.com.ogvieira.first.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty

class PersonRequestPaginateVO(  page: Int = 1,
                                limit:Int = 10,
                                active:Boolean = true,
                                column:String = "",
                                desc:Boolean = false) : BaseRequestPaginateVO( page,
                                limit,
                                active,
                                column,
                                desc)
{

        @field:JsonProperty("id")
        var id: Long? = null;
        @field:JsonProperty("first_name")
        var first_name: String? = null;
        @field:JsonProperty("last_name")
        var last_name: String? = null;
        @field:JsonProperty("address")
        var address: String? = null;
        @field:JsonProperty("gender")
        var gender: String? = null

}