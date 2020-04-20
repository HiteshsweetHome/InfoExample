package com.example.infoexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.infoexample.R
import com.example.infoexample.databinding.ActivityMainBinding
import com.example.infoexample.extra.InternetCheckManager
import com.example.infoexample.model.AboutModel
import com.example.infoexample.view.adapter.FactsAdapter
import com.example.infoexample.viewmodels.FactsViewModel

class MainActivity : AppCompatActivity() {
    lateinit var factsAdapter : FactsAdapter
    lateinit var mBinding: ActivityMainBinding
    lateinit var viewModel: FactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(FactsViewModel::class.java)

        factsAdapter = FactsAdapter()
        mBinding.rvfacts.adapter = factsAdapter

        InternetCheckManager.bindInternetListenerIn(this, onInternetStateChange, applicationContext)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData(){
        mBinding.isLoading = true
        viewModel.getFacts()
        observeViewModel()
    }

    private fun observeViewModel() {
        // Update the list when the data changes
        viewModel.getEarningObservable().observe(this, object :
            Observer<AboutModel> {
            override fun onChanged(@Nullable response: AboutModel) {
                mBinding.isLoading = false
                if (response != null) {
                    factsAdapter?.setProductList(response.rows)
                }
            }
        })
    }

    var onInternetStateChange = object : InternetCheckManager.OnInternetStateChange{
        override fun onInternetStateChange(isConnected: Boolean) {
            if(!isConnected){
                Toast.makeText(this@MainActivity, "Please Connect Internet", Toast.LENGTH_LONG).show()
            }
        }
    }
}
