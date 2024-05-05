package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.mainmenu.*
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainmenu)
        button2.setOnClickListener {
            // Tekli oyuncu için layout
            kolayseviyebutton2.visibility = View.VISIBLE
            ortaseviyebutton2.visibility = View.VISIBLE
            zorseviyebutton2.visibility = View.VISIBLE
            kolayseviyebutton.visibility = View.INVISIBLE
            ortaseviyebutton.visibility = View.INVISIBLE
            zorseviyebutton.visibility = View.INVISIBLE
        }
        button3.setOnClickListener {
            // Tekli oyuncu için layout
            kolayseviyebutton.visibility = View.VISIBLE
            ortaseviyebutton.visibility = View.VISIBLE
            zorseviyebutton.visibility = View.VISIBLE
            kolayseviyebutton2.visibility = View.INVISIBLE
            ortaseviyebutton2.visibility = View.INVISIBLE
            zorseviyebutton2.visibility = View.INVISIBLE
        }
        //Singleplayer zorluk butonları
        kolayseviyebutton2.setOnClickListener{
            val intent = Intent(this@MainActivity2,SinglePlayer::class.java)
            startActivity(intent)
        }
        ortaseviyebutton2.setOnClickListener{
            val intent = Intent(this@MainActivity2,SinglePlayer2::class.java)
            startActivity(intent)       }
        zorseviyebutton2.setOnClickListener{
            val intent = Intent(this@MainActivity2,SinglePlayer3::class.java)
            startActivity(intent)
        }
        // Multiplayer Zorluk butonları
        kolayseviyebutton.setOnClickListener{
            val intent = Intent(this@MainActivity2,MultiPlayer::class.java)
            startActivity(intent)
        }
        ortaseviyebutton.setOnClickListener{
            val intent = Intent(this@MainActivity2,Multiplayer2::class.java)
            startActivity(intent)
        }
        zorseviyebutton.setOnClickListener{
            val intent = Intent(this@MainActivity2,Multiplayer3::class.java)
            startActivity(intent)
        }
    }
}