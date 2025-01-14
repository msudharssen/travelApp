package com.example.travelapp

data class StoreData(
        val flights: List<Flight>,
        val links: Any?, // or replace with the actual type if known
        val num_pages: Int
)

data class Flight(
        val ident: String,
        val ident_icao: String,
        val ident_iata: String,
        val actual_runway_off: String?,
        val actual_runway_on: String?,
        val fa_flight_id: String,
        val operator: String,
        val operator_icao: String,
        val operator_iata: String,
        val flight_number: String,
        val registration: String?,
        val atc_ident: String?,
        val inbound_fa_flight_id: String?,
        val codeshares: List<String>,
        val codeshares_iata: List<String>,
        val blocked: Boolean,
        val diverted: Boolean,
        val cancelled: Boolean,
        val position_only: Boolean,
        val origin: Airport,
        val destination: Airport,
        val departure_delay: Int,
        val arrival_delay: Int,
        val filed_ete: Int?,
        val foresight_predictions_available: Boolean,
        val scheduled_out: String,
        val estimated_out: String?,
        val actual_out: String?,
        val scheduled_off: String,
        val estimated_off: String?,
        val actual_off: String?,
        val scheduled_on: String,
        val estimated_on: String?,
        val actual_on: String?,
        val scheduled_in: String,
        val estimated_in: String?,
        val actual_in: String?,
        val progress_percent: Int,
        val status: String,
        val aircraft_type: String?,
        val route_distance: Int?,
        val filed_airspeed: Int?,
        val filed_altitude: Int?,
        val route: String?,
        val baggage_claim: String?,
        val seats_cabin_business: Int?,
        val seats_cabin_coach: Int?,
        val seats_cabin_first: Int?,
        val gate_origin: String?,
        val gate_destination: String?,
        val terminal_origin: String?,
        val terminal_destination: String?,
        val type: String
)

data class Airport(
        val code: String,
        val code_icao: String,
        val code_iata: String,
        val code_lid: String?,
        val timezone: String,
        val name: String,
        val city: String,
        val airport_info_url: String
)

data class Info(
        val id: String,
        val origin: String,
        val destination: String,
        val departTime: String?,
        val arriveTime: String?,
        val status: String,
)

