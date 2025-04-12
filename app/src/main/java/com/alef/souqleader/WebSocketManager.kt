package com.alef.souqleader

import android.util.Log

import kotlinx.coroutines.*
import okhttp3.*
import okio.ByteString


import org.json.JSONObject

class WebSocketManager(private val url: String) {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()
    private val scope = CoroutineScope(Dispatchers.IO)

    // Connects to WebSocket and subscribes to the 'souqleader' channel
    fun connect(onMessageReceived: (String) -> Unit) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("WebSocket", "Connected")
                // Send subscription message to the channel
                webSocket.send("{ \"event\": \"pusher:subscribe\", \"data\": { \"channel\": \"souqleader\" } }")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WebSocket", "Message received: $text")
                onMessageReceived(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                onMessageReceived(bytes.utf8())
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("WebSocket", "Error: ${t.message}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WebSocket", "Closed: $reason")
            }
        })
    }

    // Sends a custom message to the WebSocket connection
    fun sendMessage(event: String, data: Map<String, Any>) {
        val jsonObject = JSONObject()
        jsonObject.put("event", event) // Add event type
        jsonObject.put("data", JSONObject(data)) // Add dynamic data

        val message = jsonObject.toString()

        // Send message to WebSocket
        scope.launch {
            webSocket?.send(message)
            Log.d("WebSocket", "Message sent: $message")
        }
    }

    // Closes the WebSocket connection
    fun close() {
        webSocket?.close(1000, "Closing WebSocket")
        webSocket = null
    }
}