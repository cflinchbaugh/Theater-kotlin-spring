package com.virtualprogrammers.theater.control

import com.virtualprogrammers.theater.data.PerformanceRepository
import com.virtualprogrammers.theater.data.SeatRepository
import com.virtualprogrammers.theater.domain.Booking
import com.virtualprogrammers.theater.domain.Performance
import com.virtualprogrammers.theater.domain.Seat
import com.virtualprogrammers.theater.services.BookingService
import com.virtualprogrammers.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {

    @Autowired
    lateinit var theaterService: TheaterService //lateinit is required for dependency injection

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage() : ModelAndView {
        val model = mapOf("bean" to CheckAvailabilityBackingBean(),
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O'
        )

        return ModelAndView("seatBooking", model)
    }

    @RequestMapping(value=["checkAvailability"], method=[RequestMethod.POST])
    fun checkAvailability(bean : CheckAvailabilityBackingBean) : ModelAndView {
        val selectedSeat = theaterService.find(bean.selectedSeatRow, bean.selectedSeatNum)
        val isAvailable = bookingService.isSeatFree(selectedSeat)
       // bean.result = "Seat ${selectedSeat.seatRow} - ${selectedSeat.seatNum} (${selectedSeat.description}) is " + if (isAvailable) "available for $${selectedSeat.price}" else "unavailable"

        return ModelAndView("seatBooking", "bean", bean)
    }

//    Should only be run once to populate DB, preserved here purely for reference
//    @RequestMapping("bootstrap")
//    fun createInitialData() : ModelAndView {
//        val seats = theaterService.seats
//        for (seat in seats) {
//            println("${seat.id} ${seat.seatRow}-${seat.seatNum} ${seat.description} ${seat.price}")
//        }
//
//        seatRepository.saveAll(seats)
//
//        return homePage()
//    }

}

//Data filled in from the form itself
class CheckAvailabilityBackingBean {
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var selectedPerformance: Long? = null
    var customerName: String = ""
    var available: Boolean? = null
    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}
