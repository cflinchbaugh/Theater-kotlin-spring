package com.virtualprogrammers.theater.control

import com.virtualprogrammers.theater.data.SeatRepository
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

    @RequestMapping("")
    fun homePage() : ModelAndView =
        ModelAndView("seatBooking", "bean", CheckAvailabilityBackingBean())

    @RequestMapping(value=["checkAvailability"], method=[RequestMethod.POST])
    fun checkAvailability(bean : CheckAvailabilityBackingBean) : ModelAndView {
        val selectedSeat = theaterService.find(bean.selectedSeatRow, bean.selectedSeatNum)
        val isAvailable = bookingService.isSeatFree(selectedSeat)
        bean.result = "Seat ${selectedSeat.seatRow} - ${selectedSeat.seatNum} (${selectedSeat.description}) is " + if (isAvailable) "available for $${selectedSeat.price}" else "unavailable"

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

class CheckAvailabilityBackingBean {
    val seatNums = 1..36
    val seatRows = 'A'..'O' //15 Rows

    var selectedSeatNum : Int = 1
    var selectedSeatRow : Char = 'A'
    var result : String = ""
}
