package com.virtualprogrammers.theater.control

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

    @RequestMapping("")
    fun homePage() : ModelAndView =
        ModelAndView("seatBooking", "bean", CheckAvailabilityBackingBean())

    @RequestMapping(value=["checkAvailability"], method=[RequestMethod.POST])
    fun checkAvailability(bean : CheckAvailabilityBackingBean) : ModelAndView {
        val selectedSeat = theaterService.find(bean.selectedSeatNum, bean.selectedSeatRow)
        val isAvailable = bookingService.isSeatFree(selectedSeat)
        bean.result = "Seat ${selectedSeat.row} - ${selectedSeat.num} (${selectedSeat.description}) is " + if (isAvailable) "available for $${selectedSeat.price}" else "unavailable"

        return ModelAndView("seatBooking", "bean", bean)
    }

}

class CheckAvailabilityBackingBean {
    val seatNums = 1..36
    val seatRows = 'A'..'O' //15 Rows

    var selectedSeatNum : Int = 1
    var selectedSeatRow : Char = 'A'
    var result : String = ""
}
