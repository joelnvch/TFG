package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dsgame.databinding.ActivitySelectionBinding
import com.android.dsgame.managers.GameManager
import com.android.dsgame.model.TripleCard
import com.android.dsgame.view.adaptors.CardElementAdaptor


class SelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        var color = ""
        if (bundle != null)
            color = bundle.getString("color").toString()


        setBestCards(color)
        setAllCards(color)
    }


    private fun setAllCards(color: String) {
        val allCards = GameManager.getCardsByColor(color)

        var tripleCard = TripleCard(color)
        val tripleCardList = mutableListOf<TripleCard>()
        for (i in allCards.indices) {
            if ((i + 1) % 3 == 1) {
                tripleCard = TripleCard(color)
                tripleCard.card1 = allCards[i]
            } else if ((i + 1) % 3 == 2)
                tripleCard.card2 = allCards[i]
            else if ((i + 1) % 3 == 0) {
                tripleCard.card3 = allCards[i]
                tripleCardList.add(tripleCard)
            }
            if (i == (allCards.size - 1) && tripleCard.card1 != null)
                tripleCardList.add(tripleCard)
        }

        binding.cardList.layoutManager = LinearLayoutManager(this)
        binding.cardList.adapter = CardElementAdaptor(tripleCardList.toTypedArray())
    }

    private fun setBestCards(color: String) {
        val bestCards = GameManager.getBestCards(color)

        binding.ceCard1.setCardElement(bestCards[0])
        binding.ceCard1.setOnClickListener {
            GameManager.setCard(color, bestCards[0].id!!)

            val i = Intent(this, HomeActivity::class.java)
            i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(i)
        }

        binding.ceCard2.setCardElement(bestCards[1])
        binding.ceCard2.setOnClickListener {
            GameManager.setCard(color, bestCards[1].id!!)

            val i = Intent(this, HomeActivity::class.java)
            i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(i)
        }

        binding.ceCard3.setCardElement(bestCards[2])
        binding.ceCard3.setOnClickListener {
            GameManager.setCard(color, bestCards[2].id!!)

            val i = Intent(it.context, HomeActivity::class.java)
            i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(i)
        }
    }
}