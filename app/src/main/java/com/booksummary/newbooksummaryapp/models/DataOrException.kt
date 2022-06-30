package com.booksummary.newbooksummaryapp.models


data class DataOrException<T, E : Exception>(
    var data : T? = null,
    var exception : Exception? = null
)