package com.android.dsgame.view.adaptors

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dsgame.R
import com.android.dsgame.activities.HomeActivity
import com.android.dsgame.model.TripleCard
import com.android.dsgame.managers.GameManager
import com.android.dsgame.model.Card
import com.android.dsgame.view.CardElement

class CardElementAdaptor (private val dataSet: Array<TripleCard>) :
        RecyclerView.Adapter<CardElementAdaptor.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val card1: CardElement
            val card2: CardElement
            val card3: CardElement

            init {
                card1 = view.findViewById(R.id.ceCard1)
                card2 = view.findViewById(R.id.ceCard2)
                card3 = view.findViewById(R.id.ceCard3)
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_card_row, viewGroup, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val tripleCard = dataSet[position]

            setCardAdaptor(holder.card1, tripleCard.card1, tripleCard.color)

            if (tripleCard.card2 != null)
                setCardAdaptor(holder.card2, tripleCard.card2, tripleCard.color)
            else
                holder.card2.visibility = View.GONE

            if (tripleCard.card3 != null)
                setCardAdaptor(holder.card3, tripleCard.card3, tripleCard.color)
            else
                holder.card3.visibility = View.GONE
        }


    private fun setCardAdaptor(cardElementAdaptor: CardElement, cardElement: Card?, color: String) {
        cardElementAdaptor.setCardElement(cardElement)
        cardElementAdaptor.setOnClickListener {
            GameManager.setCard(color, cardElement!!.id!!)
            val intent = Intent(it.context, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}