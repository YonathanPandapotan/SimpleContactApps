package com.local.asus.simplecontactapps.Variable

import retrofit2.Call
import retrofit2.http.*

interface ApiCall {
    @GET("/api/download")
    fun downloadAll(): Call<BodyResponse>

    @POST("/api/upload")
    fun uploadAll(@Body kontakArr: ArrayList<Kontak>): Call<BodyResponse>
}