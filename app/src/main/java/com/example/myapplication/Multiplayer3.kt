package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.multiplayer3.*


class Multiplayer3 : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    private var turn:Boolean = false
    var counter = 60
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiplayer3)
        Toast.makeText(this,"Test 1",Toast.LENGTH_SHORT).show()
        button5.setOnClickListener{
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
            R.drawable.ic_surf,
            R.drawable.ic_monitor,
            R.drawable.ic_network,
            R.drawable.ic_hive,
            R.drawable.ic_pause,
            R.drawable.ic_phone,
            R.drawable.ic_plane,
            R.drawable.ic_shield,
            R.drawable.ic_camera,
            R.drawable.ic_headset,
            R.drawable.ic_remote
        )
        //  Add each image twice so we can create pairs
        images.addAll(images)
        Toast.makeText(this,"Test 2",Toast.LENGTH_SHORT).show()
        // Randomize the order of images
        images.shuffle()
        buttons = listOf(imageButton2,imageButton4,imageButton5,imageButton26,imageButton58,imageButton59,imageButton83,imageButton84,imageButton85,imageButton86,imageButton87,imageButton88,imageButton89,imageButton60,imageButton61,imageButton62,imageButton63,imageButton64,imageButton65,imageButton66,imageButton67,imageButton68,imageButton69,imageButton70,imageButton71,imageButton72,imageButton73,imageButton74,imageButton75,imageButton76,imageButton77,imageButton78,imageButton79,imageButton80,imageButton81,imageButton82)
        Toast.makeText(this,"Test 3",Toast.LENGTH_SHORT).show()
        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }
        Toast.makeText(this,"Test 3",Toast.LENGTH_SHORT).show()
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
        val countTime : TextView = findViewById(R.id.textView8)
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
                val str = textView7.text.toString().toInt() + 2
                textView7.setText(str.toString())
            }else{
                val str = textView9.text.toString().toInt() + 2
                textView9.setText(str.toString())
            }
        }else{
            if(turn == false){
                if(textView7.text.toString().toInt() == 0){

                }else {
                    val str = textView7.text.toString().toInt() + -1
                    textView7.setText(str.toString())
                }
            }else{
                if(textView9.text.toString().toInt() == 0){

                }else {
                    val str = textView9.text.toString().toInt() + -1
                    textView9.setText(str.toString())
                }
            }
            turn = !turn
        }
    }
}