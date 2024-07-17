package com.example.randomquotegenerator

import android.os.Bundle
import android.view.View
import android.widget.Toast // Import for Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.randomquotegenerator.databinding.ActivityMainBinding
import kotlinx.coroutines.* // Import for coroutines

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val ioScope = CoroutineScope(Dispatchers.IO) // Separate IO coroutine scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getQuote()
        binding.nextBtn.setOnClickListener { // Corrected typo
            getQuote()
        }
    }

    private fun getQuote() {
        setInProgress(true)
        ioScope.launch { // Launch coroutine in IO dispatcher
            try {
                val response = RetrofitInstanse.quoteApi.getRandomQuote()
                withContext(Dispatchers.Main) { // Switch to Main dispatcher for UI updates
                    setInProgress(false)
                    response.body()?.first()?.let {
                        setUi(it)
                    }
                }
            } catch (e: Exception) {
                // Handle network exceptions here
                runOnUiThread{
                    setInProgress(false)
                    Toast.makeText(applicationContext,"Something went wrong !!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUi(quote: QuoteModel) {
        binding.quoteTv.text = quote.q
        binding.authorTv.text = quote.a
    }

    private fun setInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.nextBtn.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.nextBtn.visibility = View.VISIBLE
        }
    }
}
