package com.virtualprogrammers.theater.domain

import javax.persistence.*

@Entity
data class Booking(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                        val id: Long,
                        val customerName: String) {

    //ManyToOne data should not be in the constructor for performance related reasons
    @ManyToOne
    lateinit var seat: Seat
    @ManyToOne
    lateinit var performance: Performance

    override fun toString(): String = "Booking id: $id, performance: $performance, seat: $seat for $customerName"
}