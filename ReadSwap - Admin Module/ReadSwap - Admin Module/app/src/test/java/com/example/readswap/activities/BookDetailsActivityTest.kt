package com.example.readswap.activities
/*
import com.example.readswap.models.BookModel
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.junit.Assert.*
import org.junit.Test

class BookDetailsActivityTest{

    @Test
    fun testUpdateEmpData() {
        
        // Initialize the Firebase emulator suite
        val options = FirebaseOptions.Builder().setProjectId("test-project").setEmulatorHost("localhost:8080").build()
        
        FirebaseApp.initializeApp(options)

        // Create a mock book object
        val bookId = "book1"
        val bookName = "Test Book"
        val bookAuthor = "Test Author"
        val bookPrice = "10.00"
        val bookDesc = "Test description"
        val imgUrl = "https://example.com/image.jpg"
        val bookInfo = BookModel(bookId, bookName, bookAuthor, bookPrice, bookDesc, imgUrl)

        // Write the mock book object to the mock database
        val dbRef = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        dbRef.setValue(bookInfo)

        // Call the function to update the book object
        val newBookName = "New Test Book"
        val newBookAuthor = "New Test Author"
        val newBookPrice = "20.00"
        val newBookDesc = "New test description"
        val newImgUrl = "https://example.com/new-image.jpg"

        updateEmpData(bookId, newBookName, newBookAuthor, newBookPrice, newBookDesc, newImgUrl)

        // Check if the book object was updated correctly
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val updatedBookInfo = dataSnapshot.getValue(BookModel::class.java)
                assertEquals(updatedBookInfo?.bookName, newBookName)
                assertEquals(updatedBookInfo?.bookAuthor, newBookAuthor)
                assertEquals(updatedBookInfo?.bookPrice, newBookPrice)
                assertEquals(updatedBookInfo?.bookDesc, newBookDesc)
                assertEquals(updatedBookInfo?.imgurl, newImgUrl)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                fail("Database error: \${databaseError.message}")
            }
        })
    }

}
*/