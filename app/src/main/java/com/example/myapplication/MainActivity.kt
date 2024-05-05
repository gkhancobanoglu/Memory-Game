package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener{
            val intent = Intent(this@MainActivity,Encode::class.java)
            startActivity(intent)
        }
        // Buradan itibaren program kodları yazılır
        val database = FirebaseDatabase.getInstance().reference
        kaydoltext.setOnClickListener{
            adSoyad2.visibility = View.VISIBLE
            kaydolpass.visibility = View.VISIBLE
            idtext.visibility =View.VISIBLE
            kaydolbutton.visibility = View.VISIBLE
            passchange.visibility = View.INVISIBLE
            passchangebutton.visibility = View.INVISIBLE
            passchangeid.visibility = View.INVISIBLE
            passchangeusername.visibility = View.INVISIBLE
        }
        kaydolbutton.setOnClickListener{
            val userid = idtext.text.toString().toInt()
            val username = adSoyad2.text.toString()
            val password = kaydolpass.text.toString()
            //database.setValue(Users(userid, username, password))
            database.child("Users").child(userid.toString()).setValue(Users(userid,username,password))
        }
        sifretext.setOnClickListener{
            idtext.visibility = View.INVISIBLE
            adSoyad2.visibility = View.INVISIBLE
            kaydolpass.visibility = View.INVISIBLE
            kaydolbutton.visibility = View.INVISIBLE
            passchangeusername.visibility = View.VISIBLE
            passchange.visibility = View.VISIBLE
            passchangebutton.visibility = View.VISIBLE
            passchangeid.visibility = View.VISIBLE
        }
        passchangebutton.setOnClickListener{
            val userid = passchangeid.text.toString()
            val userpass = passchange.text.toString()
            val username = passchangeusername.text.toString()
            val editMap = mapOf(
                "username" to username,
                "password" to userpass,
                "userid" to userid
            )
            database.child("Users").child(userid).updateChildren(editMap)
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()

        }
        button6.setOnClickListener{
            val db = FirebaseDatabase.getInstance().getReference("Users")
            if(adSoyad.text.toString().equals("root") && sifre.text.toString().equals("root")){

            }else{
                val getdata = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {


                        for (i in snapshot.children) {
                            val username = i.child("username").getValue().toString()
                            val password = i.child("password").getValue().toString()

                            if (username.equals(adSoyad.text.toString()) && password.equals(sifre.text.toString())) {
                                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                                startActivity(intent)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                }
                db.addValueEventListener(getdata)
                db.addListenerForSingleValueEvent(getdata)
            }
        }
        }
    }
