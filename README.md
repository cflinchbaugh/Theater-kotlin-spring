Theater Booking System Architecture 



Instructions
    Run application via IntelliJ
    Navigate to localhost:8080

To access h2 DB navigate to localhost/8080/h2-console
    JDBC URL: jdbc:h2:~/theater

To manually create the corresponding DB Table:
    `CREATE TABLE SEAT (
        ID BIGINT NOT NULL,
        DESCRIPTION VARCHAR(255),
        PRICE DECIMAL(19,2),
        SEAT_NUM INTEGER NOT NULL,
        SEAT_ROW CHAR(255) NOT NULL,
        PRIMARY KEY (ID)
    )`

Routes:
    / -> Check seat availability (Part1)
    /checkAvailability -> If available, book w/ Customer Name
    /performances -> Add new performance to DB
    /reports -> See bookings stored in DB

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

