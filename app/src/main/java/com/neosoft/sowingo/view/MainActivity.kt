package com.neosoft.sowingo.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.neosoft.sowingo.adapter.ProductsAdapter
import com.neosoft.sowingo.databinding.ActivityMainBinding
import com.neosoft.sowingo.network.RetrofitService
import com.neosoft.sowingo.repository.ProductRepository
import com.neosoft.sowingo.viewmodel.MainViewModel
import com.neosoft.sowingo.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = ProductsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()
        callApi()
    }

    /**
     * Function to set the UI widgets
     * **/
    private fun setUI() {
        initViewModel()
        setUpListeners()

        binding.rvProducts.adapter = adapter

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length > 2) { // 2 as a threshold
                    adapter.addProducts(adapter.filter(s.toString()))
                } else if (s.isEmpty()) {
                    adapter.addProducts(viewModel.productList.value!!)
                }
            }
        })
    }

    /**
     * Function to initialize the ViewModel
     * **/
    private fun initViewModel() {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = ProductRepository(retrofitService)
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[MainViewModel::class.java]
    }

    /**
     * Function to fetch all the products via API
     * **/
    private fun callApi() {
        viewModel.getAllProducts()
    }

    /**
     * Function to set the Listeners/Observers
     * **/
    private fun setUpListeners() {
        viewModel.productList.observe(this) {
            binding.refreshLayout.isRefreshing = false
            adapter.addProducts(it)
        }
        binding.refreshLayout.setOnRefreshListener {
            resetTheFilter()
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressDialog.visibility = View.VISIBLE
            } else {
                binding.progressDialog.visibility = View.GONE
            }
        })

    }

    /**
     * Function to reset the filter and fetch the API again
     * **/
    private fun resetTheFilter() {
        binding.etSearch.setText("")
        callApi()
    }
}