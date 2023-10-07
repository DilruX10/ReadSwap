package com.example.readswap.activities
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.readswap.R
import com.example.readswap.models.BookModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity: AppCompatActivity() {

    private lateinit var etbookName: EditText
    private lateinit var etbookAuthor: EditText
    private lateinit var etbookPrice: EditText
    private lateinit var etbookDesc: EditText
    private lateinit var etimgurl: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etbookName = findViewById(R.id.etbookName)
        etbookAuthor = findViewById(R.id.etbookAuthor)
        etbookPrice = findViewById(R.id.etbookPrice)
        etbookDesc = findViewById(R.id.etbookDesc)
        etimgurl = findViewById(R.id.etimgurl)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Books")

        btnSaveData.setOnClickListener {
            saveBookData()
        }
    }

    private fun saveBookData() {

        //getting values
        val bookName = etbookName.text.toString()
        val bookAuthor = etbookAuthor.text.toString()
        val bookPrice = etbookPrice.text.toString()
        val bookDesc = etbookDesc.text.toString()
        val imgurl = etimgurl.text.toString()

        if (bookName.isEmpty()) {
            etbookName.error = "Please enter title"
        }
        if (bookAuthor.isEmpty()) {
            etbookAuthor.error = "Please enter author"
        }
        if (bookPrice.isEmpty()) {
            etbookPrice.error = "Please enter price"
        }
        if(bookDesc.isEmpty()){
            etbookDesc.error = "Please enter description"
        }
        if(imgurl.isEmpty()){
            etimgurl.error = "Please enter imgurl"
        }

        val bookId = dbRef.push().key!!

        val book = BookModel(bookId, bookName, bookAuthor, bookPrice, bookDesc, imgurl)

        dbRef.child(bookId).setValue(book)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etbookName.text.clear()
                etbookAuthor.text.clear()
                etbookPrice.text.clear()
                etbookDesc.text.clear()
                etimgurl.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}