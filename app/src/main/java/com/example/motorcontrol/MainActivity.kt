package com.example.motorcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val URL = "http://192.168.0.24:5000/motor" // 라즈베리파이 서버 주소

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Forward 버튼 설정
        val buttonForward: Button = findViewById(R.id.buttonForward)
        buttonForward.setOnClickListener {
            sendCommand("forward", 100)
        }

        // Backward 버튼 설정
        val buttonBackward: Button = findViewById(R.id.buttonBackward)
        buttonBackward.setOnClickListener {
            sendCommand("backward", 100)
        }

        // Stop 버튼 설정
        val buttonStop: Button = findViewById(R.id.buttonStop)
        buttonStop.setOnClickListener {
            sendCommand("stop", 0)
        }
    }

    private fun sendCommand(command: String, speed: Int) {
        val JSON = "application/json; charset=utf-8".toMediaType()
        val json = "{\"command\":\"$command\", \"speed\":$speed}"
        val body = json.toRequestBody(JSON)
        val request = Request.Builder()
            .url(URL)
            .post(body)
            .build()

        Thread {
            try {
                val response: Response = client.newCall(request).execute()
                // 응답 처리 로직 (필요한 경우)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}