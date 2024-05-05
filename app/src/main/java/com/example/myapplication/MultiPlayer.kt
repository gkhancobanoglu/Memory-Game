package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.multiplayer.*
import kotlinx.android.synthetic.main.singleplayer.*
import kotlinx.android.synthetic.main.singleplayer.button7
import kotlinx.android.synthetic.main.singleplayer.imageButton10
import kotlinx.android.synthetic.main.singleplayer.imageButton7
import kotlinx.android.synthetic.main.singleplayer.imageButton8
import kotlinx.android.synthetic.main.singleplayer.imageButton9
import kotlinx.android.synthetic.main.singleplayer.player1score
import kotlinx.android.synthetic.main.singleplayer.textView5

class MultiPlayer : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    private var turn:Boolean = false
    var counter = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiplayer)

        button7.setOnClickListener{
            imageButton7.visibility = View.VISIBLE
            imageButton8.visibility = View.VISIBLE
            imageButton9.visibility = View.VISIBLE
            imageButton10.visibility = View.VISIBLE
            player1score.visibility = View.VISIBLE
            player2score.visibility = View.VISIBLE
            textView5.visibility = View.VISIBLE
            startTimerCounter()
        }
        val images = mutableListOf(R.drawable.ic_plane, R.drawable.ic_dollar)
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
        Toast.makeText(this,"Test 1",Toast.LENGTH_SHORT).show()

        if(cards[position1].identifier == cards[position2].identifier){
            Toast.makeText(this,"Match Found", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            if(turn == false){
                val str = player1score.text.toString().toInt() + 2
                player1score.setText(str.toString())
            }else{
                val str = player2score.text.toString().toInt() + 2
                player2score.setText(str.toString())
            }
        }else{
            if(turn == false){
                Toast.makeText(this,"Test 2",Toast.LENGTH_SHORT).show()
                if(player1score.text.toString().toInt() == 0){

                }else {
                    val str = player1score.text.toString().toInt() + -1
                    player1score.setText(str.toString())
                    Toast.makeText(this, "Test 3", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Test 4",Toast.LENGTH_SHORT).show()
                if(player2score.text.toString().toInt() == 0){

                }else {
                    val str = player2score.text.toString().toInt() + -1
                    player2score.setText(str.toString())
                    Toast.makeText(this,"Test 5",Toast.LENGTH_SHORT).show()
                }
            }


            turn = !turn
            Toast.makeText(this,"Test 6",Toast.LENGTH_SHORT).show()

        }

    }
}