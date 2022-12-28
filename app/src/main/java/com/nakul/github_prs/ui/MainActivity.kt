package com.nakul.github_prs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.nakul.github_prs.R
import com.nakul.github_prs.databinding.ActivityMainBinding
import com.nakul.github_prs.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModels<MainViewModel>()
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initBinding()
        initObservers()
        viewModel.getAndAddItems()
    }

    private fun initBinding() {
        binding?.apply {
            lifecycleOwner = this@MainActivity
            viewmodel = this@MainActivity.viewModel
        }
        binding?.swipeRefresh?.setOnRefreshListener {
            binding?.swipeRefresh?.isRefreshing = false
            viewModel.resetData()
        }
    }

    private fun initObservers() {
        viewModel.errorMessage.observe(this) { message ->
            snackbar?.dismiss()
            if (message != null) {
                binding?.root?.let { view ->
                    snackbar = getNewSnackbar(view, message)
                    snackbar?.show()
                }
            }
        }
    }

    private fun getNewSnackbar(view: View, message: String) =
        Snackbar.make(this, view, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { viewModel.getAndAddItems() }

}