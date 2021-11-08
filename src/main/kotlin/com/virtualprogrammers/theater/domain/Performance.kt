package com.virtualprogrammers.theater.domain

import javax.persistence.*

@Entity
data class Performance(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                val id: Long,
                val title: String) {

    @OneToMany(mappedBy = "performance")
    lateinit var bookings: List<Booking> //A list should not be part of the constructor for performance reasons, so we lateinit here

    override fun toString(): String = "Performance $id-$title"
}