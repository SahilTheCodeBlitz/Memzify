package com.example.memesharez
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()

    }
    var currenturl : String?=null

    private fun loadMeme(){
        val imageview = findViewById<ImageView>(R.id.memeimg)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility=View.VISIBLE
        // Instantiate the RequestQueue.
        //val queue = Volley.newRequestQueue(this)//initialising the request queue
        val url = "https://meme-api.com/gimme" // url of the meme api



        // Request a string response from the provided URL.

        //val stringRequest = StringRequest(
        //Request.Method.GET, url,
        //    Response.Listener<String> { response ->

                //if no error arises in creation of string request


        //    },
        //    Response.ErrorListener {
                //error arises
        //  })

        // we want json object request as the memes are in json format

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
               // successfull
               // Log.d("success",response.toString())
                currenturl = response.getString("url")
                Glide.with(this).load(currenturl).listener(object :RequestListener<Drawable>{

                    override fun onLoadFailed( // LOAD FAILED
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        // LOAD SUCCESSFULL
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false

                    }

                }).into(imageview)
            },
            Response.ErrorListener { error ->

                Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
                //Glide.with(this).load(url).into(imageview)

                //Log.d("error",error.toString())

            }
        )


// Add the request to the RequestQueue.
        MySingelton.getInstance(this).addToRequestQueue(jsonObjectRequest) // adding the stringrequest to the request queue

    }

    fun share(view: View){

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"heyy i found this meme very interesting hope you will also find same $currenturl ")
        intent.type="text/plain"
        Intent.createChooser(intent,"share this message using ....")
        startActivity(intent)

    }

    fun next(view: View){
        loadMeme()
    }
}


