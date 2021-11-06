package com.virtualprogrammers.theater.services
import com.virtualprogrammers.theater.domain.Seat
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TheaterService {

    private val hiddenSeats = mutableListOf<Seat>()

    constructor() {

        fun getPrice(seatRow: Int, seatNum: Int) : BigDecimal {
            return when {
                seatRow >=14 -> BigDecimal(14.50)
                seatNum <=3 || seatNum >= 34 -> BigDecimal(16.50)
                seatRow == 1 -> BigDecimal(21)
                else -> BigDecimal(18)
            }

        }

        fun getDescription(seatRow: Int, seatNum: Int) : String {
            return when {
                seatRow == 15 -> "Back Row"
                seatRow == 14 -> "Cheaper Seat"
                seatNum <=3 || seatNum >= 34 -> "Restricted View"
                seatRow <=2 -> "Best View"
                else -> "Standard Seat"
            }
        }

        fun convertIntToChar(convertThisInt: Int) : Char =
            (convertThisInt + 64).toChar() //Magic number to get to cap letters in ASCII


        for (seatRow in 1..15) {
            for (seatNum in 1..36) {
                hiddenSeats.add(Seat(0, convertIntToChar(seatRow), seatNum, getPrice(seatRow,seatNum), getDescription(seatRow,seatNum) ))
            }
        }
    }

	val seats
    get() = hiddenSeats.toList()

    fun find(seatRow: Char, seatNum: Int) : Seat {
        return seats.first {
            it.seatRow == seatRow && it.seatNum == seatNum
        }

    }

}
