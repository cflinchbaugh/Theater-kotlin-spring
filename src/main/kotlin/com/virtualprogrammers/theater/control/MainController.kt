package com.virtualprogrammers.theater.control

import com.virtualprogrammers.theater.data.PerformanceRepository
import com.virtualprogrammers.theater.data.SeatRepository
import com.virtualprogrammers.theater.domain.Booking
import com.virtualprogrammers.theater.domain.Performance
import com.virtualprogrammers.theater.domain.Seat
import com.virtualprogrammers.theater.services.BookingService
import com.virtualprogrammers.theater.services.TheaterService
import org.hibernate.annotations.Check
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
        val bean = CheckAvailabilityBackingBean() //instantiate the bean here
        val model = generateModel(bean)

        return ModelAndView("seatBooking", model)
    }

    @RequestMapping(value=["checkAvailability"], method=[RequestMethod.POST])
    fun checkAvailability(bean : CheckAvailabilityBackingBean) : ModelAndView {
        val selectedSeat = bookingService.findSeat(bean.selectedSeatRow, bean.selectedSeatNum)!! // !! because fields are selected via dropdown
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get() // !! and .get() because fields are selected via dropdown
        bean.seat = selectedSeat
        bean.performance = selectedPerformance

        val isAvailable = bookingService.isSeatFree(selectedSeat, selectedPerformance)

        if (!isAvailable) {
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }
        bean.available = isAvailable

        val model = generateModel(bean) //Use the existing bean to persist dropdown options

        return ModelAndView("seatBooking", model)
    }

    fun generateModel(bean: CheckAvailabilityBackingBean ): Map<String, Any> {
        return mapOf(
            "bean" to bean,
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O'
        )
    }

    @RequestMapping(value=["booking"], method=[RequestMethod.POST])
    fun bookSeat(bean: CheckAvailabilityBackingBean) : ModelAndView {
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)

        return ModelAndView("bookingConfirmed", "booking", booking)

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
