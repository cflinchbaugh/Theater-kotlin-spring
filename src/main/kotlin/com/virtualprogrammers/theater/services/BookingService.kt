package com.virtualprogrammers.theater.services

import com.virtualprogrammers.theater.domain.Seat
import org.springframework.stereotype.Service

@Service
class BookingService {

    fun isSeatFree(seat: Seat) : Boolean {
        return true
    }

}