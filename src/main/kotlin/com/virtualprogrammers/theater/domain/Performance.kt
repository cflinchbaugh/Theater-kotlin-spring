package com.virtualprogrammers.theater.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Performance(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                val id: Long,
                val title: String) {
    override fun toString(): String = "Performance $id-$title"
}