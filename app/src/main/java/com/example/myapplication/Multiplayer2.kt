package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.multiplayer2.*
import kotlinx.android.synthetic.main.multiplayer3.*

class Multiplayer2 : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    private var turn:Boolean = false
    var counter = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiplayer2)
        button10.setOnClickListener{
            startTimerCounter()
        }
        val images = mutableListOf(
            R.drawable.ic_plane,
            R.drawable.ic_hand,
            R.drawable.ic_dollar,
            R.drawable.ic_forward,
            R.drawable.ic_info,
            R.drawable.ic_star,
            R.drawable.ic_mic,
            R.drawable.ic_surf
        )
        //  Add each image twice so we can create pairs
        images.addAll(images)

        // Randomize the order of images
        images.shuffle()
        buttons = listOf(imageButton,imageButton99,imageButton98,imageButton6,imageButton100,imageButton15,imageButton16,imageButton17,imageButton18,imageButton19,imageButton20,imageButton21,imageButton22,imageButton23,imageButton24,imageButton25)

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
        val countTime : TextView = findViewById(R.id.textView3)
        object : CountDownTimer(60000,1000){
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
            Toast.makeText(this,"Invalid move!", Toast.LENGTH_SHORT).show()
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
        if(cards[position1].identifier == cards[position2].identifier){
            Toast.makeText(this,"Match Found", Toast.LENGTH_SHORT).show()

            cards[position1].isMatched = true
            cards[position2].isMatched = true
            if(turn == false){
                val str = textView6.text.toString().toInt() + 2
                textView6.setText(str.toString())
            }else{
                val str = textView4.text.toString().toInt() + 2
                textView4.setText(str.toString())
            }
        }else{
            if(turn == false){
                if(textView6.text.toString().toInt() == 0){

                }else {
                    val str = textView6.text.toString().toInt() + -1
                    textView6.setText(str.toString())
                }
            }else{
                if(textView4.text.toString().toInt() == 0){

                }else {
                    val str = textView4.text.toString().toInt() + -1
                    textView4.setText(str.toString())
                }
            }
            turn = !turn
        }
    }
}