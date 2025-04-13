package com.alef.souqleader

import android.util.Log
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class WebSocketClient(
    private val onMessage: (String) -> Unit
) {

    private val client = OkHttpClient.Builder()
        .readTimeout(3, TimeUnit.SECONDS)
        .build()

    private var webSocket: WebSocket? = null

    fun connect() {
        val request = Request.Builder()
            .url("wss://ws-eu.pusher.com/app/850a45c0ee5d326e0322?protocol=7&client=js&version=4.4.0&cluster=eu")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // Subscribe to the channel
                val subscribeMessage = """
                    {
                      "event": "pusher:subscribe",
                      "data": {
                        "channel": "souqleader"
                      }
                    }
                """.trimIndent()
              //  webSocket.send(subscribeMessage)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessage(text)
                Log.e("sssssss",text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                onMessage(bytes.utf8())
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                onMessage("Error: ${t.message}")
            }
        })
    }

    fun close() {
        webSocket?.close(1000, "User closed connection")
    }

    // Send data to WebSocket (this will trigger from UI)
    fun sendData(leadId:String,send:String,salesId:String) {

        val data = JSONObject().apply {
            put("channel", "souqleader")
            put("lead_id", leadId)
            put("send", send)
            put("sales_id", salesId)
        }

        val message = JSONObject().apply {
            put("event", "pusher:subscribe")
            put("data", data)
        }


        webSocket?.send(message.toString())
    }
}