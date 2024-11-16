package com.example.lab2app.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2app.R
import com.example.lab2app.adapter.AnimalAdapter
import com.example.lab2app.model.Animal
import com.example.lab2app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: AnimalAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)

        adapter = AnimalAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Initially, set RecyclerView visibility to GONE
        recyclerView.visibility = RecyclerView.GONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    // Fetch data when a query is submitted
                    fetchAnimalData(query)
                } else {
                    // Hide the RecyclerView if the query is empty
                    recyclerView.visibility = RecyclerView.GONE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun fetchAnimalData(name: String) {
        val apiKey = "DEn3rdh+oWG6/7ECnGf74g==u3qp1Evr5PGNuOl3"  // Retrieve the API key from strings.xml
        val call = RetrofitClient.instance.getAnimals(name, apiKey)

        call.enqueue(object : Callback<List<Animal>> {
            override fun onResponse(
                call: Call<List<Animal>>,
                response: Response<List<Animal>>
            ) {
                if (response.isSuccessful) {
                    val animals = response.body()
                    if (animals != null && animals.isNotEmpty()) {
                        // Show the RecyclerView and update the adapter with data
                        recyclerView.visibility = RecyclerView.VISIBLE
                        adapter.setAnimals(animals)
                        for (animal in animals) {
                            Log.d("Animal", "Name: ${animal.name}")
                            Log.d("Animal", "Locations: ${animal.locations}")
                            Log.d("Animal", "Lifespan: ${animal.lifespan}")
                        }
                    } else {
                        Log.d("API_RESPONSE", "No animals found")
                        recyclerView.visibility = RecyclerView.GONE
                    }
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                    recyclerView.visibility = RecyclerView.GONE
                }
            }

            override fun onFailure(call: Call<List<Animal>>, t: Throwable) {
                Log.e("API_ERROR", "Failed to fetch data: ${t.message}")
                recyclerView.visibility = RecyclerView.GONE
            }
        })
    }
}

