 package com.example.news

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

 class MainActivity : AppCompatActivity(), newsOnClick {

    private lateinit var madapter : newsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycleView.layoutManager = LinearLayoutManager(this)
        fetchData()
        madapter = newsAdapter(this)
        recycleView.adapter = madapter
    }

     fun fetchData(){
         val url ="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
         val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,
             Response.Listener{
                 val jsonArray = it.getJSONArray("articles")
                 val newsArray= ArrayList<News>()
                 for(i in 0 until jsonArray.length())
                 {
                     val newsJsonArray = jsonArray.getJSONObject(i)
                     val new = News(
                         newsJsonArray.getString("title"),
                         newsJsonArray.getString("author"),
                         newsJsonArray.getString("url"),
                         newsJsonArray.getString(("urlToImage"))
                     )
                     newsArray.add(new)
                 }
                 madapter.newsUpdate(newsArray)
             },

            Response.ErrorListener{}

         )

         MySingelton.getInstance(this).addToRequestQueue(jsonObjectRequest)
     }

     override fun onItemClick(item: News) {
         val builder = CustomTabsIntent.Builder()
         val customTabs = builder.build()
         customTabs.launchUrl(this, Uri.parse(item.url))
     }
 }