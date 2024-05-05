package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_encode.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.EventListener

class Encode : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encode)
        button4.setOnClickListener{
            Toast.makeText(this,"Test 1", Toast.LENGTH_SHORT).show()

            Toast.makeText(this,"Test 2", Toast.LENGTH_SHORT).show()
        val dbref = FirebaseDatabase.getInstance().getReference("Cards")
            var x = 0
            val getdata = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val images = listOf<ImageView>()

                    Toast.makeText(this@Encode,"Test 3", Toast.LENGTH_SHORT).show()
                    for(i in snapshot.children){
                        Toast.makeText(this@Encode,"Test 4", Toast.LENGTH_SHORT).show()
                        val base64 = i.child("base64").getValue().toString()
                        val name = i.child("Name").getValue().toString()
                        Toast.makeText(this@Encode,name, Toast.LENGTH_SHORT).show()


                        val imageBytes = Base64.decode(base64.toByteArray(), Base64.DEFAULT)
                        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        images[x].setImageBitmap(decodedImage)
                        Toast.makeText(this@Encode,"Test 5", Toast.LENGTH_SHORT).show()
                        x += 1
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
            dbref.addValueEventListener(getdata)
            dbref.addListenerForSingleValueEvent(getdata)
        /*
            val imageBytes = Base64.decode(stringofimage, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageView.setImageBitmap(decodedImage)
        */
        }

    }
}