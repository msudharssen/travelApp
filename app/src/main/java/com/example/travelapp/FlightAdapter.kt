package com.example.travelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FlightAdapter(private var flights: MutableList<Info>) :
    RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    // ViewHolder class
    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flightId: TextView = itemView.findViewById(R.id.flightId)
        val origin: TextView = itemView.findViewById(R.id.origin)
        val destination: TextView = itemView.findViewById(R.id.destination)
        val depart: TextView = itemView.findViewById(R.id.depart)
        val land: TextView = itemView.findViewById(R.id.land)
        val status: TextView = itemView.findViewById(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flight, parent, false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flights[position]
        holder.flightId.text = flight.id
        holder.origin.text = flight.origin
        holder.destination.text = flight.destination
        holder.depart.text = flight.departTime ?: "N/A"
        holder.land.text = flight.arriveTime ?: "N/A"
        holder.status.text = flight.status ?: "N/A"
    }

    override fun getItemCount(): Int = flights.size

    // Method to update data
    fun updateFlights(newFlights: List<Info>) {
        flights.clear()
        flights.addAll(newFlights)
        notifyDataSetChanged()
    }
}

