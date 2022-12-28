package com.nakul.github_prs.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nakul.github_prs.databinding.ActivityMainBinding
import com.nakul.github_prs.util.AppUtils
import com.nakul.github_prs.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModels<MainViewModel>()
    private var snackbar: Snackbar? = null
    var state: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initBinding()
        initObservers()
        if (state != null)
            viewModel.model.fetchData()
    }

    override fun onPause() {
        super.onPause()
        state = binding?.recyclerView?.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        binding?.recyclerView?.layoutManager?.onRestoreInstanceState(state)
    }

    private fun initBinding() {
        binding?.apply {
            lifecycleOwner = this@MainActivity
            viewmodel = this@MainActivity.viewModel
        }
        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.resetData()
        }
    }

    private fun initObservers() {
        viewModel.errorMessage.observe(this) { message ->
            snackbar?.dismiss()
            if (message != null) {
                binding?.root?.let { view ->
                    snackbar =
                        AppUtils.getNewSnackbar(view, message) { viewModel.model.fetchData() }
                    snackbar?.show()
                }
            }
        }
    }

}