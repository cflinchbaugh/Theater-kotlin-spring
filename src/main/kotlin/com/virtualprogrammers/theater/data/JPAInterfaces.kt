package com.virtualprogrammers.theater.data

import com.virtualprogrammers.theater.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>