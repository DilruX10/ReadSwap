package com.example.readswap.models

data class BookModel(
    var bookId: String? = null,
    var bookName: String? = null,
    var bookAuthor: String? = null,
    var bookPrice: String? = null,
    var bookDesc: String? = null,
    var imgurl: String? = null
)