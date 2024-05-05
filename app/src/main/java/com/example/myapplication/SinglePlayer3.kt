package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.single_player2.*
import kotlinx.android.synthetic.main.single_player3.*
import kotlinx.android.synthetic.main.singleplayer.*

class SinglePlayer3 : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    var counter = 45
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_player3)
        button8.setOnClickListener{
            ConstLayout.visibility = View.VISIBLE
        }
        Toast.makeText(this,"Test 1",Toast.LENGTH_SHORT).show()
        button8.setOnClickListener{
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
        buttons = listOf(imageButton3,imageButton11,imageButton12,imageButton13,imageButton14,imageButton27,imageButton28,imageButton29,imageButton30,imageButton31,imageButton32,imageButton33,imageButton34,imageButton35,imageButton36,imageButton37,imageButton38,imageButton39,imageButton40,imageButton41,imageButton42,imageButton43,imageButton44,imageButton45,imageButton46,imageButton47,imageButton48,imageButton49,imageButton50,imageButton51,imageButton52,imageButton53,imageButton54,imageButton55,imageButton56,imageButton57)
        Toast.makeText(this,"Test 3",Toast.LENGTH_SHORT).show()

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }
        Toast.makeText(this,"Test 4",Toast.LENGTH_SHORT).show()

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
            val str = textView7.text.toString().toInt()+2
            textView7.setText(str.toString())
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }else{
            if(textView7.text.toString().toInt() == 0){

            }else {
                val str = textView7.text.toString().toInt() - 1
                textView7.setText(str.toString())
            }
        }
    }
}