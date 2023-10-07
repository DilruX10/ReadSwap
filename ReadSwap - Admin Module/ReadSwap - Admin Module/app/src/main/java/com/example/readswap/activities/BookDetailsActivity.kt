package com.example.readswap.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.readswap.R
import com.example.readswap.models.BookModel
import com.google.firebase.database.FirebaseDatabase

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var tvbookId: TextView
    private lateinit var tvbookName: TextView
    private lateinit var tvbookAuthor: TextView
    private lateinit var tvbookPrice: TextView
    private lateinit var tvbookDesc: TextView
    private lateinit var tvimgurl: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    private fun initView() {

        tvbookId = findViewById(R.id.tvbookId)
        tvbookName = findViewById(R.id.tvbookName)
        tvbookAuthor = findViewById(R.id.tvbookAuthor)
        tvbookPrice = findViewById(R.id.tvbookPrice)
        tvbookDesc = findViewById(R.id.tvbookDesc)
        tvimgurl = findViewById(R.id.tvimgurl)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {

        tvbookId.text = intent.getStringExtra("bookId")
        tvbookName.text = intent.getStringExtra("bookName")
        tvbookAuthor.text = intent.getStringExtra("bookAuthor")
        tvbookPrice.text = intent.getStringExtra("bookPrice")
        tvbookDesc.text = intent.getStringExtra("bookDesc")
        tvimgurl.text = intent.getStringExtra("imgurl")

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("bookId").toString(),
                intent.getStringExtra("bookName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("bookId").toString()
            )
        }

    }

    private fun openUpdateDialog(
        bookId: String,
        bookName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etbookName = mDialogView.findViewById<EditText>(R.id.etbookName)
        val etbookAuthor = mDialogView.findViewById<EditText>(R.id.etbookAuthor)
        val etbookPrice = mDialogView.findViewById<EditText>(R.id.etbookPrice)
        val etbookDesc = mDialogView.findViewById<EditText>(R.id.etbookDesc)
        val etimgurl = mDialogView.findViewById<EditText>(R.id.etimgurl)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etbookName.setText(intent.getStringExtra("bookName").toString())
        etbookAuthor.setText(intent.getStringExtra("bookAuthor").toString())
        etbookPrice.setText(intent.getStringExtra("bookPrice").toString())
        etbookDesc.setText(intent.getStringExtra("bookDesc").toString())
        etimgurl.setText(intent.getStringExtra("imgurl").toString())

        mDialog.setTitle("Updating $bookName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                bookId,
                etbookName.text.toString(),
                etbookAuthor.text.toString(),
                etbookPrice.text.toString(),
                etbookDesc.text.toString(),
                etimgurl.text.toString()
            )

            Toast.makeText(applicationContext, "Item Data Updated", Toast.LENGTH_LONG).show()

            // setting updated data to textviews
            tvbookName.text = etbookName.text.toString()
            tvbookAuthor.text = etbookAuthor.text.toString()
            tvbookPrice.text = etbookPrice.text.toString()
            tvbookDesc.text = etbookDesc.text.toString()
            tvimgurl.text = etimgurl.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        bookId: String,
        bookName: String,
        bookAuthor: String,
        bookPrice: String,
        bookDesc: String,
        imgurl: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val bookInfo = BookModel(bookId, bookName, bookAuthor, bookPrice, bookDesc, imgurl)
        dbRef.setValue(bookInfo)
    }

    private fun deleteRecord(
        bookId: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Item data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Error: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}