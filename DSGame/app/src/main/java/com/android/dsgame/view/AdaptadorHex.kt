package layout

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dsgame.R
import com.android.dsgame.activities.MainActivity
import com.android.dsgame.model.TripleCard
import com.android.dsgame.utils.GameDelegate
import com.android.dsgame.view.HexButton

class AdaptadorHex (private val dataSet: Array<TripleCard>) :
        RecyclerView.Adapter<AdaptadorHex.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val bestCard1: HexButton
            val bestCard2: HexButton
            val bestCard3: HexButton
            val context: Context

            init {
                context = view.context
                bestCard1 = view.findViewById(R.id.hbCard1)
                bestCard2 = view.findViewById(R.id.hbCard2)
                bestCard3 = view.findViewById(R.id.hbCard3)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_multiple, viewGroup, false)
            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            val tripleCard = dataSet[position]

            viewHolder.bestCard1.setCardView(tripleCard.card1)
            viewHolder.bestCard1.setOnClickListener {
                GameDelegate.setCard(tripleCard.color, tripleCard.card1!!.id)
                viewHolder.context.startActivity(Intent(viewHolder.context, MainActivity::class.java))
            }

            if (tripleCard.card2 != null) {
                viewHolder.bestCard2.setCardView(tripleCard.card2)
                viewHolder.bestCard2.setOnClickListener {
                    GameDelegate.setCard(tripleCard.color, tripleCard.card2!!.id)
                    viewHolder.context.startActivity(Intent(viewHolder.context, MainActivity::class.java))
                }
            } else
                viewHolder.bestCard2.visibility = View.GONE

            if (tripleCard.card3 != null) {
                viewHolder.bestCard3.setCardView(tripleCard.card3)
                viewHolder.bestCard3.setOnClickListener {
                    GameDelegate.setCard(tripleCard.color, tripleCard.card3!!.id)
                    viewHolder.context.startActivity(Intent(viewHolder.context, MainActivity::class.java))
                }
            } else
                viewHolder.bestCard3.visibility = View.GONE
        }

        override fun getItemCount() = dataSet.size
    }

