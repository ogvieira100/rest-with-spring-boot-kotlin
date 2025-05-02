package br.com.ogvieira.first.data.vo.v1

abstract class BaseResponsePaginateVO<T> {

    private var _items: MutableList<T> = mutableListOf()
    var items: MutableList<T>
        get() = _items
        set(value) {
            _items = value
        }

    private var _page: Int = 0
    var page: Int
        get() = _page
        set(value) {
            _page = value
        }

    private var _pageSize: Int = 0
    var pageSize: Int
        get() = _pageSize
        set(value) {
            _pageSize = value
        }

    private var _totalItens: Int = 0
    var totalItens: Int
        get() = _totalItens
        set(value) {
            _totalItens = value
        }

    private var _totalPages: Int = 0
    var totalPages: Int
        get() = _totalPages
        set(value) {
            _totalPages = value
        }

    constructor()

    constructor(
        items: MutableList<T>,
        page: Int,
        pageSize: Int,
        totalItens: Int,
        totalPages: Int
    ) {
        this._items = items
        this._page = page
        this._pageSize = pageSize
        this._totalItens = totalItens
        this._totalPages = totalPages
    }
}