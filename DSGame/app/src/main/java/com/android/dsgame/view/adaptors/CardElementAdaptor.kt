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
import com.android.dsgame.view.CardElement

class CardElementAdaptor (private val dataSet: Array<TripleCard>) :
        RecyclerView.Adapter<CardElementAdaptor.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val bestCard1: CardElement
            val bestCard2: CardElement
            val bestCard3: CardElement

            init {
                bestCard1 = view.findViewById(R.id.hbCard1)
                bestCard2 = view.findViewById(R.id.hbCard2)
                bestCard3 = view.findViewById(R.id.hbCard3)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_card_row, viewGroup, false)
            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            val tripleCard = dataSet[position]

            holder.bestCard1.setCardView(tripleCard.card1)
            holder.bestCard1.setOnClickListener {
                GameManager.setCard(tripleCard.color, tripleCard.card1!!.id!!)
                it.context.startActivity(Intent(it.context, HomeActivity::class.java))
            }

            if (tripleCard.card2 != null) {
                holder.bestCard2.setCardView(tripleCard.card2)
                holder.bestCard2.setOnClickListener {
                    GameManager.setCard(tripleCard.color, tripleCard.card2!!.id!!)
                    it.context.startActivity(Intent(it.context, HomeActivity::class.java))
                }
            } else
                holder.bestCard2.visibility = View.GONE

            if (tripleCard.card3 != null) {
                holder.bestCard3.setCardView(tripleCard.card3)
                holder.bestCard3.setOnClickListener {
                    GameManager.setCard(tripleCard.color, tripleCard.card3!!.id!!)
                    it.context.startActivity(Intent(it.context, HomeActivity::class.java))
                }
            } else
                holder.bestCard3.visibility = View.GONE
        }

        override fun getItemCount() = dataSet.size
    }