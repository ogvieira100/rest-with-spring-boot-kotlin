package br.com.ogvieira.first.data.vo.v1

class PersonResponsePaginated(
    items: MutableList<PersonVO>,
    page: Int,
    pageSize: Int,
    totalItens: Int,
    totalPages: Int
) : BaseResponsePaginateVO<PersonVO>(items, page, pageSize, totalItens, totalPages) {

}