package com.reihan.suitmediatask.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.reihan.suitmediatask.R
import com.reihan.suitmediatask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val textPalindrome = activityMainBinding.editTextPalindrome.text
        val nama = activityMainBinding.editTextName.text

        activityMainBinding.buttonCheck.setOnClickListener {
            var isPalindrome : Boolean
            isPalindrome = true
            if(textPalindrome.isEmpty()){
                activityMainBinding.editTextPalindrome.error = getString(R.string.main_activity_error_message)
                isPalindrome = false
            }

            val textLength = textPalindrome.length
            var i = 0
            while(i<textLength/2){
                i++
                if(textPalindrome[i] != textPalindrome[textLength-1-i]){
                    isPalindrome = false
                    break
                }
            }
            if(isPalindrome){
                Toast.makeText(this@MainActivity, palindrome, Toast.LENGTH_SHORT).show()
            }else if(!isPalindrome && textPalindrome.isNotEmpty()) {
                Toast.makeText(this@MainActivity, notPalindrome, Toast.LENGTH_SHORT).show()
            }
        }

        activityMainBinding.buttonNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            val transfer = intent.putExtra(SecondActivity.EXTRA_NAME,nama.toString())
            Log.d(TAG, "$transfer")
            if(nama.isEmpty()){
                activityMainBinding.editTextName.error = getString(R.string.main_activity_error_message)
            }else startActivity(intent)

        }
    }

    companion object {
        const val TAG = "MainActivity"
        var palindrome = "Palindrome"
        var notPalindrome = "Not Palindrome"
    }
}