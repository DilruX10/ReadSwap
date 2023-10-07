package com.example.readswap.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.readswap.R
import com.example.readswap.adapters.BookAdapter
import com.example.readswap.models.BookModel
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class FetchingActivity : AppCompatActivity() {

    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var tvLoadingData: TextView
    private lateinit var bookList: ArrayList<BookModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        bookRecyclerView = findViewById(R.id.rvBook)
        searchView = findViewById(R.id.searchView)
        bookRecyclerView.layoutManager = LinearLayoutManager(this)
        bookRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        bookList = arrayListOf<BookModel>()

        getBookData()

        searchView.setOnQueryTextListener(object: OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return true
            }
        })
    }

    private fun filterList(query: String) {
        if(query != null){
            val filteredList = ArrayList<BookModel>()
            for(i in bookList){
                if(i.bookName?.lowercase(Locale.ROOT)?.contains(query) == true){
                    filteredList.add(i)
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(this, "No Match Found", Toast.LENGTH_SHORT).show()
            } else {

                val mAdapter = BookAdapter(filteredList)
                bookRecyclerView.adapter = mAdapter
            }
        }
    }

    private fun getBookData() {

        bookRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Books")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookList.clear()
                if (snapshot.exists()){
                    for (bookSnap in snapshot.children){
                        val bookData = bookSnap.getValue(BookModel::class.java)
                        bookList.add(bookData!!)
                    }
                    val mAdapter = BookAdapter(bookList)
                    bookRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : BookAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, BookDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("bookId", bookList[position].bookId)
                            intent.putExtra("bookName", bookList[position].bookName)
                            intent.putExtra("bookAuthor", bookList[position].bookAuthor)
                            intent.putExtra("bookPrice", bookList[position].bookPrice)
                            intent.putExtra("bookDesc", bookList[position].bookDesc)
                            intent.putExtra("imgurl", bookList[position].imgurl)
                            startActivity(intent)
                        }

                    })

                    bookRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}