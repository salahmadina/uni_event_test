package com.university.eventmanagement.controller;

import com.university.eventmanagement.model.Booking;
import com.university.eventmanagement.model.Event;
import com.university.eventmanagement.model.User;
import com.university.eventmanagement.service.BookingService;
import com.university.eventmanagement.service.EventService;
import com.university.eventmanagement.service.RatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = getLoggedInStudent(session);
        if (user == null) return "redirect:/login";

        List<Event> events = eventService.getActiveEvents();

        Map<Long, Boolean> bookedMap = new HashMap<>();
        Map<Long, Boolean> ratedMap  = new HashMap<>();

        for (Event event : events) {
            bookedMap.put(event.getId(), bookingService.hasUserBookedEvent(user, event));
            ratedMap.put(event.getId(),  ratingService.hasUserRatedEvent(user, event));
        }

        model.addAttribute("user", user);
        model.addAttribute("events", events);
        model.addAttribute("bookedMap", bookedMap);
        model.addAttribute("ratedMap", ratedMap);

        return "student/dashboard";
    }

    @PostMapping("/book/{eventId}")
    public String bookEvent(@PathVariable Long eventId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        User user = getLoggedInStudent(session);
        if (user == null) return "redirect:/login";

        String result = bookingService.bookEvent(user, eventId);

        if (!result.equals("SUCCESS")) {
            redirectAttributes.addFlashAttribute("error", result);
        } else {
            redirectAttributes.addFlashAttribute("success", "Event booked successfully!");
        }

        return "redirect:/student/dashboard";
    }

    @GetMapping("/my-bookings")
    public String myBookings(HttpSession session, Model model) {

        User user = getLoggedInStudent(session);
        if (user == null) return "redirect:/login";

        List<Booking> bookings = bookingService.getBookingsForUser(user);

        Map<Long, Boolean> ratedMap = new HashMap<>();
        for (Booking booking : bookings) {
            ratedMap.put(booking.getEvent().getId(),
                         ratingService.hasUserRatedEvent(user, booking.getEvent()));
        }

        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);
        model.addAttribute("ratedMap", ratedMap);

        return "student/my-bookings";
    }

    @PostMapping("/rate/{eventId}")
    public String rateEvent(@PathVariable Long eventId,
                            @RequestParam int stars,
                            @RequestParam(required = false) String comment,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        User user = getLoggedInStudent(session);
        if (user == null) return "redirect:/login";

        String result = ratingService.rateEvent(user, eventId, stars, comment);

        if (!result.equals("SUCCESS")) {
            redirectAttributes.addFlashAttribute("error", result);
        } else {
            redirectAttributes.addFlashAttribute("success", "Thank you for your rating!");
        }

        return "redirect:/student/my-bookings";
    }

    private User getLoggedInStudent(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || user.getRole() != User.Role.STUDENT) return null;
        return user;
    }
}
