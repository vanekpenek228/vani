package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.myapplication.API.API_Connection
import com.example.myapplication.databinding.ActivityEmailAuthBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailAuth : AppCompatActivity() {
    private lateinit var binding: ActivityEmailAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editTextTextEmailAddress.addTextChangedListener(textWatcher)
        binding.button.setOnClickListener{
            API_Connection.getConnection().SendCode(binding.editTextTextEmailAddress.text.toString()).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.code() == 200){
                        API_Connection.email = binding.editTextTextEmailAddress.text.toString()
                        startActivity(Intent(this@EmailAuth, EmailCode::class.java))
                    }
                    else{
                        Log.println(Log.ERROR, "API","Error connection")
                        Toast.makeText(this@EmailAuth, "Error connection", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.println(Log.ERROR, "API", t.message.toString())
                    Toast.makeText(this@EmailAuth, "Error request", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private val textWatcher: TextWatcher = object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkEmail()
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }
    private fun checkEmail()
    {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editTextTextEmailAddress.text.toString()).matches()){
            binding.button.isEnabled = true
        }
        else{
            binding.button.isEnabled = false
        }
    }

}