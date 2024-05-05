package com.example.myapplication

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore.Audio
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.singleplayer.*
import com.example.myapplication.R.drawable.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class SinglePlayer : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    var counter = 45
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.singleplayer)

        button7.setOnClickListener{
            imageButton7.visibility = View.VISIBLE
            imageButton8.visibility = View.VISIBLE
            imageButton9.visibility = View.VISIBLE
            imageButton10.visibility = View.VISIBLE
            player1score.visibility = View.VISIBLE
            textView5.visibility = View.VISIBLE
            startTimerCounter()
        }

        val images = mutableListOf(ic_plane, ic_dollar)
        //  Add each image twice so we can create pairs
        images.addAll(images)
        // Randomize the order of images
        images.shuffle()
        buttons = listOf(imageButton7,imageButton8,imageButton9,imageButton10)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }
        buttons.forEachIndexed { index, button ->
                button.setOnClickListener{
                    // Update Models
                    updateModels(index)
                    // Update Views
                    updateViews()

                }
        }
        }


        fun startTimerCounter(){
            val countTime : TextView = findViewById(R.id.textView5)
            object : CountDownTimer(45000,1000){
                override fun onTick(p0: Long) {
                    countTime.text = counter.toString()
                    counter--
                }
                override fun onFinish() {
                    countTime.text = "Finished"
                }
            }.start()
        }
        private fun updateModels(position:Int){
            val card = cards[position]
            // Error checking
            if(card.isFaceUp){
                Toast.makeText(this,"Invalid move!",Toast.LENGTH_SHORT).show()
                return
            }
            // Three cases
            // 0 cards previously flipped over => restore cards + flip over the selected card
            // 1 cards previously flipped over => flip over the selected card + check if the images match
            // 2 cards previously flipped over => restore cards + flip over the selected card
            if(indexOfSingleSelectedCard == null){
                // 0 or 2 selected cards previously
                restoreCards()
                indexOfSingleSelectedCard = position
            }else{
                //exactly 1 card was selected previously
                checkForMatch(indexOfSingleSelectedCard!!,position)
                indexOfSingleSelectedCard = null
            }
            card.isFaceUp = !card.isFaceUp
        }

    private fun restoreCards() {
        for(card in cards){
            if(!card.isMatched){
                card.isFaceUp = false

            }
        }
    }

    private fun updateViews(){
            cards.forEachIndexed { index, card ->
                val button = buttons[index]
                if(card.isMatched){
                    button.alpha = 0.1f
                }
                button.setImageResource(if (card.isFaceUp) card.identifier else R.mipmap.ic_hog_foreground)
            }
        }
    private fun checkForMatch(position1:Int, position2:Int){
        Toast.makeText(this,"Test 1",Toast.LENGTH_SHORT).show()
        if(cards[position1].identifier == cards[position2].identifier){
            val str = player1score.text.toString().toInt()+2
            player1score.setText(str.toString())
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }else{
            if(player1score.text.toString().toInt() == 0){

            }else {
                val str = player1score.text.toString().toInt() - 1
                player1score.setText(str.toString())
            }
        }
    }
    }