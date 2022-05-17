package com.alexanderhoang.foodtruck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.alexanderhoang.foodtruck.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    companion object {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, object : JsonDeserializer<LocalDateTime> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): LocalDateTime {
                    return LocalDateTime.parse(json!!.asString)
                }
            })
            .create()

        val foodTruckService: FoodTruckService =  Retrofit.Builder()
            .baseUrl("https://api.foodtruck.schedgo.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FoodTruckService::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("699423245763-insnbbp034ep600msiqfan5g0b2pau67.apps.googleusercontent.com")
            .requestEmail()
            .build()

        var db: AppDatabase? = null;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .allowMainThreadQueries().build();

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Food Trucks"

//        //should work but not sure why it doesnt
//        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
//        val viewPager2 = findViewById<ViewPager2>(R.id.pager)
//
//        val adapter = TabStateAdapter(supportFragmentManager, lifecycle)
//
//        viewPager2.adapter = adapter
//
//        TabLayoutMediator(tabLayout, viewPager2) {tab, position->
//            when (position) {
//                0->{
//                    tab.text="Menu"
//                }
//                1->{
//                    tab.text="Reviews"
//                }
//            }
//        }.attach()

    }
}