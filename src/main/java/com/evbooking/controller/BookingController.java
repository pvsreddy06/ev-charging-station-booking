package com.evbooking.controller;

import com.evbooking.model.Booking;
import com.evbooking.model.Station;
import com.evbooking.repository.BookingRepository;
import com.evbooking.repository.StationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BookingController {
    private final StationRepository stations; private final BookingRepository bookings;
    public BookingController(StationRepository stations, BookingRepository bookings) { this.stations=stations; this.bookings=bookings; }
    private String userEmail(HttpSession session) { return (String) session.getAttribute("userEmail"); }
    private String requireLogin(HttpSession session) { return userEmail(session)==null ? "redirect:/login" : null; }

    @GetMapping("/") public String home(HttpSession session) { return userEmail(session)==null ? "redirect:/login" : "redirect:/dashboard"; }
    @GetMapping("/dashboard") public String dashboard(HttpSession session, Model model) { String guard=requireLogin(session); if(guard!=null)return guard; model.addAttribute("stations", stations.findAll()); model.addAttribute("bookings", bookings.findByUserEmailOrderByCreatedAtDesc(userEmail(session))); return "dashboard"; }
    @GetMapping("/stations") public String stationList(@RequestParam(required=false, defaultValue="") String city, HttpSession session, Model model) {
        String guard=requireLogin(session); if(guard!=null)return guard;
        String searchCity = city == null ? "" : city.trim();
        List<Station> result = searchCity.isEmpty() ? stations.findAll() : stations.findByLocationContainingIgnoreCase(searchCity);
        model.addAttribute("stations", result);
        model.addAttribute("city", searchCity);
        return "stations";
    }
    @GetMapping("/book/{stationId}") public String bookingForm(@PathVariable String stationId, HttpSession session, Model model) { String guard=requireLogin(session); if(guard!=null)return guard; Station station=stations.findById(stationId).orElse(null); if(station==null)return "redirect:/stations"; model.addAttribute("station", station); model.addAttribute("minDate", LocalDate.now()); return "book"; }
    @PostMapping("/book/{stationId}") public String createBooking(@PathVariable String stationId, @RequestParam LocalDate bookingDate, @RequestParam String timeSlot, @RequestParam int durationHours, @RequestParam String connectorType, HttpSession session, Model model) {
        String guard=requireLogin(session); if(guard!=null)return guard;
        Station station=stations.findById(stationId).orElse(null); if(station==null)return "redirect:/stations";
        if(bookingDate.isBefore(LocalDate.now()) || durationHours<1 || durationHours>8) { model.addAttribute("station",station); model.addAttribute("minDate",LocalDate.now()); model.addAttribute("error","Choose a valid date and duration (1–8 hours)."); return "book"; }
        Booking b=new Booking(); b.setUserEmail(userEmail(session)); b.setStationId(station.getId()); b.setStationName(station.getName()); b.setStationLocation(station.getLocation()); b.setConnectorType(connectorType); b.setBookingDate(bookingDate); b.setTimeSlot(timeSlot); b.setDurationHours(durationHours); b.setAmount(station.getPricePerHour()*durationHours); b.setStatus("Pending Payment"); b.setCreatedAt(LocalDateTime.now()); bookings.save(b);
        return "redirect:/payment/"+b.getId();
    }
    @GetMapping("/bookings") public String myBookings(HttpSession session, Model model) { String guard=requireLogin(session); if(guard!=null)return guard; model.addAttribute("bookings",bookings.findByUserEmailOrderByCreatedAtDesc(userEmail(session))); return "bookings"; }
    @GetMapping("/payment/{id}") public String payment(@PathVariable String id, HttpSession session, Model model) { String guard=requireLogin(session); if(guard!=null)return guard; Booking b=bookings.findByIdAndUserEmail(id,userEmail(session)).orElse(null); if(b==null)return "redirect:/bookings"; model.addAttribute("booking",b); return "payment"; }
    @PostMapping("/payment/{id}") public String confirmPayment(@PathVariable String id, @RequestParam String paymentMethod, HttpSession session) { String guard=requireLogin(session); if(guard!=null)return guard; Booking b=bookings.findByIdAndUserEmail(id,userEmail(session)).orElse(null); if(b==null)return "redirect:/bookings"; b.setStatus("Confirmed"); bookings.save(b); return "redirect:/receipt/"+b.getId()+"?method="+paymentMethod; }
    @GetMapping("/receipt/{id}") public String receipt(@PathVariable String id, @RequestParam(defaultValue="UPI") String method, HttpSession session, Model model) { String guard=requireLogin(session); if(guard!=null)return guard; Booking b=bookings.findByIdAndUserEmail(id,userEmail(session)).orElse(null); if(b==null)return "redirect:/bookings"; model.addAttribute("booking",b); model.addAttribute("paymentMethod",method); return "receipt"; }
    @PostMapping("/cancel/{id}") public String cancel(@PathVariable String id, HttpSession session) { String guard=requireLogin(session); if(guard!=null)return guard; Booking b=bookings.findByIdAndUserEmail(id,userEmail(session)).orElse(null); if(b!=null && !"Cancelled".equals(b.getStatus())) { b.setStatus("Cancelled"); bookings.save(b); } return "redirect:/bookings"; }
}
