package com.evbooking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Booking {
    @Id private String id;
    private String userEmail;
    private String stationId;
    private String stationName;
    private String stationLocation;
    private String connectorType;
    private LocalDate bookingDate;
    private String timeSlot;
    private int durationHours;
    private double amount;
    private String status;
    private LocalDateTime createdAt;

    public Booking() {}
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getUserEmail(){return userEmail;} public void setUserEmail(String userEmail){this.userEmail=userEmail;}
    public String getStationId(){return stationId;} public void setStationId(String stationId){this.stationId=stationId;}
    public String getStationName(){return stationName;} public void setStationName(String stationName){this.stationName=stationName;}
    public String getStationLocation(){return stationLocation;} public void setStationLocation(String stationLocation){this.stationLocation=stationLocation;}
    public String getConnectorType(){return connectorType;} public void setConnectorType(String connectorType){this.connectorType=connectorType;}
    public LocalDate getBookingDate(){return bookingDate;} public void setBookingDate(LocalDate bookingDate){this.bookingDate=bookingDate;}
    public String getTimeSlot(){return timeSlot;} public void setTimeSlot(String timeSlot){this.timeSlot=timeSlot;}
    public int getDurationHours(){return durationHours;} public void setDurationHours(int durationHours){this.durationHours=durationHours;}
    public double getAmount(){return amount;} public void setAmount(double amount){this.amount=amount;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
