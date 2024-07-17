package com.example.randomquotegenerator
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanse {
    private const val BASE_URL = "https://zenquotes.io/api/"
    private fun getInstanse() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val quoteApi : QuoteApi = getInstanse().create(QuoteApi::class.java )
}