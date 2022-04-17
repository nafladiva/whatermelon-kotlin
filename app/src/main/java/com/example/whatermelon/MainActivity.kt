package com.example.whatermelon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.whatermelon.databinding.ActivityMainBinding
import com.example.whatermelon.model.ActivityViewModel
import com.example.whatermelon.model.ActivityViewModelFactory
import com.example.whatermelon.network.ApiService
import com.example.whatermelon.repository.MainRepository

class MainActivity : AppCompatActivity() {

    lateinit var dataViewModel: ActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        val retrofitService = ApiService.getInstance()
        val mainRepository = MainRepository(retrofitService)

        dataViewModel = ViewModelProvider(this, ActivityViewModelFactory(mainRepository)).get(ActivityViewModel::class.java)

        dataViewModel.getRandomActivity()

        dataViewModel.activity.observe(this, Observer {
            binding.activityText.text = viewModel.activity.value?.activity
            binding.typeText.text = viewModel.activity.value?.type
        })

        binding.shuffleButton.setOnClickListener {
            dataViewModel.getRandomActivity()
        }
    }

}