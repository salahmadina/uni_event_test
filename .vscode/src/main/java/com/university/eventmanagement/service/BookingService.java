package com.university.eventmanagement.service;

import com.university.eventmanagement.model.Booking;
import com.university.eventmanagement.model.Event;
import com.university.eventmanagement.model.User;
import com.university.eventmanagement.repository.BookingRepository;
import com.university.eventmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public String bookEvent(User user, Long eventId) {

        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getStatus() == Event.Status.CANCELLED) {
            return "This event has been cancelled and cannot be booked.";
        }

        if (bookingRepository.existsByUserAndEvent(user, event)) {
            return "You have already booked this event.";
        }

        if (!event.hasAvailableSeats()) {
            return "Sorry, this event is fully booked.";
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setEventNameSnapshot(event.getName()); // ← snapshot

        if (event.isFree()) {
            booking.setPaymentStatus(Booking.PaymentStatus.FREE);
        } else {
            booking.setPaymentStatus(Booking.PaymentStatus.PENDING);
        }

        bookingRepository.save(booking);

        event.setBookedSeats(event.getBookedSeats() + 1);
        eventRepository.save(event);

        return "SUCCESS";
    }

    public List<Booking> getBookingsForUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public boolean hasUserBookedEvent(User user, Event event) {
        return bookingRepository.existsByUserAndEvent(user, event);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByBookedAtDesc();
    }

    @Transactional
    public String confirmPayment(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isEmpty()) {
            return "Booking not found.";
        }

        Booking booking = bookingOpt.get();

        if (booking.getPaymentStatus() != Booking.PaymentStatus.PENDING) {
            return "Booking is not in PENDING status.";
        }

        booking.setPaymentStatus(Booking.PaymentStatus.PAID);
        bookingRepository.save(booking);
        return "SUCCESS";
    }
}
