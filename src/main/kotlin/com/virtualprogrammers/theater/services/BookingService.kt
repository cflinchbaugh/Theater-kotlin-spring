package com.virtualprogrammers.theater.services

import com.virtualprogrammers.theater.data.BookingRepository
import com.virtualprogrammers.theater.data.SeatRepository
import com.virtualprogrammers.theater.domain.Booking
import com.virtualprogrammers.theater.domain.Performance
import com.virtualprogrammers.theater.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var seatRepository: SeatRepository

    fun isSeatFree(seat: Seat, performance: Performance) : Boolean {
        val matchedBookings = findBooking(seat, performance)

        return matchedBookings == null
    }

    fun findSeat(seatRow: Char, seatNum: Int) : Seat? {
        val matchingSeat = seatRepository.findAll().filter {
            it.seatRow == seatRow && it.seatNum == seatNum
        }

        return matchingSeat.firstOrNull()
    }

    fun findBooking(seat: Seat, performance: Performance): Booking? {
        val matchingBooking = bookingRepository.findAll().filter {
            it.seat == seat && it.performance == performance
        }

        return matchingBooking.firstOrNull()
    }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val newBooking = Booking(0, customerName)
        newBooking.seat = seat
        newBooking.performance = performance

        bookingRepository.save(newBooking)

        return newBooking
    }

}