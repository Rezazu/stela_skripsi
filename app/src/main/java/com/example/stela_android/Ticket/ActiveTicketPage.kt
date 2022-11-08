package com.example.stela_android.Ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.stela_android.R
import kotlinx.android.synthetic.main.activity_active_ticket_page.*;

class ActiveTicketPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_active_ticket_page, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideShowTicketsInformationSystem()
        hideShowTicketsInfrastructureJaringan()
        hideShowTicketsTataKelolaIT()
        hideShowTicketsLainnya()

        ticketClickHandler()
    }

    private fun hideShowTicketsInformationSystem() {
        dropdown_handler_1.setOnClickListener {
            if(tickets_section_1.isVisible) {
                tickets_section_1.visibility = View.GONE
                dropdown_handler_1.setImageResource(R.drawable.ic_chevron_down_is)
            } else  {
                tickets_section_1.visibility = View.VISIBLE
                dropdown_handler_1.setImageResource(R.drawable.ic_chevron_up_is)
            }
        }
    }

    private fun hideShowTicketsInfrastructureJaringan() {
        dropdown_handler2.setOnClickListener {
            if(tickets_section2.isVisible) {
                tickets_section2.visibility = View.GONE
                dropdown_handler2.setImageResource(R.drawable.ic_chevron_down_ij)
            } else  {
                tickets_section2.visibility = View.VISIBLE
                dropdown_handler2.setImageResource(R.drawable.ic_chevron_up_ij)
            }
        }
    }

    private fun hideShowTicketsTataKelolaIT() {
        dropdown_handler3.setOnClickListener {
            if(tickets_section3.isVisible) {
                tickets_section3.visibility = View.GONE
                dropdown_handler3.setImageResource(R.drawable.ic_chevron_down_tkt)
            } else  {
                tickets_section3.visibility = View.VISIBLE
                dropdown_handler3.setImageResource(R.drawable.ic_chevron_up_tkt)
            }
        }
    }

    private fun hideShowTicketsLainnya() {
        dropdown_handler4.setOnClickListener {
            if(tickets_section4.isVisible) {
                tickets_section4.visibility = View.GONE
                dropdown_handler4.setImageResource(R.drawable.ic_chevron_down_lainnya)
            } else  {
                tickets_section4.visibility = View.VISIBLE
                dropdown_handler4.setImageResource(R.drawable.ic_chevron_up_lainnya)
            }
        }
    }

    private fun ticketClickHandler() {
        ticket1.setOnClickListener{
            startActivity(Intent(activity, Ticket::class.java))
        }
    }

}