package com.example.levanmidterm1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.levanmidterm1.adapters.CountryRecyclerViewAdapter
import com.example.levanmidterm1.api.RetrofitClient
import com.example.levanmidterm1.api.dto.Country
import com.example.levanmidterm1.api.dto.Covid19Data
import kotlinx.android.synthetic.main.activity_covid19.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Covid19Activity : AppCompatActivity() {

    private lateinit var countryAdapter: CountryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid19)

        this.initRecycler()

        this.getAllCountries()
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this)
        countriesRecyclerView.layoutManager = layoutManager
        countryAdapter = CountryRecyclerViewAdapter(ArrayList())
        countriesRecyclerView.adapter = countryAdapter
    }

    private fun getAllCountries() {
        RetrofitClient.covid19Api.getInfo("summary")
            .enqueue(object : Callback<Covid19Data<List<Country>>> {
                override fun onFailure(call: Call<Covid19Data<List<Country>>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<Covid19Data<List<Country>>>,
                    response: Response<Covid19Data<List<Country>>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val list = response.body()!!.countries.sortedByDescending { it.totalConfirmed }
                        countryAdapter.updateCountries(list)
                    }
                }

            })
    }



}
