package com.example.memeshare

import android.app.Notification
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {
    var currentImgUrl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
                loadMeme()
    }
    private fun loadMeme() {

        progressBar.visibility = View.VISIBLE
        val url = "https://meme-api.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            {response->
                currentImgUrl = response.getString("url")
                Glide.with(this).load(currentImgUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(memeImageView)

            },
            {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show()
            })
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type= "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey! Checkout this new meme I got from reddit! $currentImgUrl")
        val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }

}




