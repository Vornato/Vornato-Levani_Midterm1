package com.example.levanmidterm1.api


import com.example.levanmidterm1.api.dto.Country
import com.example.levanmidterm1.api.dto.Covid19Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Covid19Service {

     @GET("{param}")
     fun getInfo(@Path("param") param: String) : Call<Covid19Data<List<Country>>>



}