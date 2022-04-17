package com.example.whatermelon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.whatermelon.databinding.FragmentContentBinding
import com.example.whatermelon.model.ActivityViewModel
import com.example.whatermelon.model.ActivityViewModelFactory
import com.example.whatermelon.network.ApiService
import com.example.whatermelon.repository.MainRepository

class ContentFragment : Fragment() {
    lateinit var dataViewModel: ActivityViewModel
    private lateinit var binding: FragmentContentBinding
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofitService = ApiService.getInstance()
        val mainRepository = MainRepository(retrofitService)

        binding.contentFragment = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        dataViewModel = ViewModelProvider(this, ActivityViewModelFactory(mainRepository)).get(ActivityViewModel::class.java)

        dataViewModel.activity.observe(viewLifecycleOwner, Observer {
            binding.activityText.text = viewModel.activity.value?.activity
            binding.typeText.text = viewModel.activity.value?.type
        })

        dataViewModel.getRandomActivity()
    }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
}