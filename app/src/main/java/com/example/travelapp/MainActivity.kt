package com.example.travelapp

import FlightApiService
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpRequest
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request



class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var buttonLogout: Button
    private lateinit var textView: TextView
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var date: String
    private lateinit var search: String
    private lateinit var flightAdapter: FlightAdapter
    private lateinit var searchFlightEditText: EditText
    private lateinit var searchFlightButton: Button
    private lateinit var searchResultTextView: TextView
    private lateinit var saveResultButton: Button
    private val myTrips = mutableListOf<StoreData>()
    private val key = "FppKtToRGYjqOMPoi3GenlX01L24wIMl"




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        flightAdapter = FlightAdapter(mutableListOf())
        recyclerView.adapter = flightAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        auth = FirebaseAuth.getInstance()
//        buttonLogout = findViewById(R.id.logout)
        textView = findViewById(R.id.welcome_message)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        searchFlightEditText = findViewById(R.id.searchFlightEditText)
        searchFlightButton = findViewById(R.id.searchFlightButton)



        actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        navigate()
        info()

        searchFlightButton.setOnClickListener{
            val flightNumber = searchFlightEditText.text.toString().trim()
            if (flightNumber.isNotEmpty()) {
                flightInfo(key, flightNumber, recyclerView)
            } else {
                Toast.makeText(this, "Please enter a flight number", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun flightInfo(apiKey: String, flightNumber: String, r: RecyclerView) {
        val apiUrl = "https://aeroapi.flightaware.com/aeroapi/"
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FlightApiService::class.java)
        val call = service.getFlightData(apiKey, flightNumber)

        call.enqueue(object : Callback<StoreData> {
            override fun onResponse(call: Call<StoreData>, response: Response<StoreData>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        Toast.makeText(this@MainActivity, "Success: ${data.flights[2].origin}", Toast.LENGTH_SHORT).show()
                        Log.d("Flight Information", "Going From: ${data.flights[2].origin.city}, To: ${data.flights[2].destination.city}")
                        val mappedFlights = Info(
                            id = data.flights[2].ident_iata ?: "N/A",
                            origin = data.flights[2].origin.city,
                            destination = data.flights[2].destination.city,
                            departTime = data.flights[2].actual_off,
                            arriveTime = data.flights[2].estimated_on,
                            status = data.flights[2].status
                        )
                        runOnUiThread {
                            flightAdapter.updateFlights(listOf(mappedFlights))
                            r.visibility = View.VISIBLE
                        }
                    }



                    else {
                        Log.e("Data Not Found", "Response body is null")
                    }
                } else {
                    Log.e("Error Recieved", "Error: ${response.code()} - ${response.message()}")
                    Toast.makeText(
                        this@MainActivity,
                        "Request failed: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<StoreData>, t: Throwable) {
                Log.e("FlightInfo", "Error: ${t.message}")
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }





    private fun navigate(){
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    auth.signOut()
                    Toast.makeText(this@MainActivity, "Logout Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            false;
        }
    }

    private fun info(){
        val headerView = navigationView.getHeaderView(0)
        val usernameTextView = headerView.findViewById<TextView>(R.id.usernameTextView)
        val user = auth.currentUser
        if (user != null) {
            usernameTextView.text = user.email
        }
        else{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

//    private fun signOut() {
//        auth.signOut()
//        Toast.makeText(this@MainActivity, "Logout Successful", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, Login::class.java)
//        startActivity(intent)
//        finish()
//    }


}
