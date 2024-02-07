package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myapplication.API.API_Connection
import com.example.myapplication.databinding.ActivityEmailCodeBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailCode : AppCompatActivity() {
    private lateinit var binding: ActivityEmailCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editTextNumber.addTextChangedListener(textWatcher)
        binding.imageButton.setOnClickListener {
            startActivity(Intent(this@EmailCode, EmailAuth::class.java))
        }
        timer.start()
    }

    private val timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.textView3.text =
                "Отправить код повторно можно будет через " + (millisUntilFinished / 1000) + "секунд"
        }

        override fun onFinish() {
            binding.imageButton.visibility = View.VISIBLE
            binding.textView3.visibility = View.GONE
        }

    }
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.editTextNumber.text.length == 4) {
                API_Connection.getConnection()
                    .SignIn(API_Connection.email.toString(), binding.editTextNumber.text.toString())
                    .enqueue(object :
                        Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.code() == 200) {
                                startActivity(Intent(this@EmailCode, MainActivity::class.java))
                            } else {
                                Log.println(Log.ERROR, "API", "Error connection")
                                Toast.makeText(
                                    this@EmailCode,
                                    "Error connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.println(Log.ERROR, "API", t.message.toString())
                            Toast.makeText(this@EmailCode, "Error request", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            }

        }

        override fun afterTextChanged(s: Editable?) {
        }

    }
}