package com.local.asus.simplecontactapps.Variable

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConnection {
    companion object{
        var retrofit: Retrofit? = null
        var baseUrl: String = "http://192.168.43.161:8000"

        fun getConnection(): Retrofit{
            if(retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit as Retrofit
        }
    }
}