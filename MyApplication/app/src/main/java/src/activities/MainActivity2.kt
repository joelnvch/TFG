package src.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main2.*
import layout.AdaptadorHex
import src.R
import src.utils.GameDelegate
import src.model.TripleCard

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var bundle = intent.extras
        var color = ""
        if (bundle != null)
            color = bundle.getString("color").toString()


        // BEST CARDS SECTION
        var bestCards = GameDelegate.getBestCards(color)

        hbCard1.setCardView(bestCards[0])
        hbCard2.setCardView(bestCards[1])
        hbCard3.setCardView(bestCards[2])

        hbCard1.setOnClickListener {
            GameDelegate.setCard(color, bestCards[0].id)
            startActivity(Intent(this, MainActivity::class.java))
        }
        hbCard2.setOnClickListener {
            GameDelegate.setCard(color, bestCards[1].id)
            startActivity(Intent(this, MainActivity::class.java))
        }
        hbCard3.setOnClickListener {
            GameDelegate.setCard(color, bestCards[2].id)
            startActivity(Intent(this, MainActivity::class.java))
        }


        // ALL CARDS SECTION
        var allCards = GameDelegate.getAllCards(color)

        var tripleCard = TripleCard(color)
        var tripleCardList = mutableListOf<TripleCard>()
        for (i in allCards.indices) {
            if ((i+1) % 3 == 1){
                tripleCard = TripleCard(color)
                tripleCard.card1 = allCards[i]
            } else if ((i+1) % 3 == 2)
                tripleCard.card2 = allCards[i]
              else if ((i+1) % 3 == 0) {
                tripleCard.card3 = allCards[i]
                tripleCardList.add(tripleCard)
            }
            if (i == (allCards.size - 1) && tripleCard.card1 != null)
                tripleCardList.add(tripleCard)
        }

        listaBotones.layoutManager = LinearLayoutManager(this)
        listaBotones.adapter = AdaptadorHex(tripleCardList.toTypedArray())
    }
}