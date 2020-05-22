package com.example.levanmidterm1.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.levanmidterm1.R
import com.example.levanmidterm1.api.dto.Country
import kotlinx.android.synthetic.main.country_item.view.*

class CountryRecyclerViewAdapter(private var countries: List<Country> )
    : RecyclerView.Adapter<CountryRecyclerViewAdapter.CountryViewHolder>() {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var country: Country

        @SuppressLint("SetTextI18n")
        fun bind(country: Country) {
            this.country = country

            itemView.countryName.text = "Country: ${country.country}"
            itemView.newConfirmed.text = "New Confirmed: ${country.newConfirmed}"
            itemView.totalConfirmed.text = "Total Confirmed: ${country.totalConfirmed}"
            itemView.newDeaths.text = "New Deaths: ${country.newDeaths}"
            itemView.totalDeaths.text = "Total Deaths: ${country.totalDeaths}"
            itemView.newRecovered.text = "New Recovered: ${country.newRecovered}"
            itemView.totalRecovered.text = "Total Recovered: ${country.totalRecovered}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return CountryViewHolder(v)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    fun updateCountries(countries: List<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }
}