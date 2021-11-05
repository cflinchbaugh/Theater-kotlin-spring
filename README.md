Theater Booking System Architecture 

(v1.0)
    Model
        TheaterService //Simulates DB
            Seats //list
                Seat

        BookingService
            // Queries TheaterService to determine isSeatAvailable


    Controller
        TheaterController
            //Check availability of seat

    View
        seatBooking.html //UI Form