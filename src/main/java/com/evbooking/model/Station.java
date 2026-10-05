package com.evbooking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stations")
public class Station {
    @Id private String id;
    private String name;
    private String location;
    private String connectorType;
    private double pricePerHour;
    private int availableSlots;
    private String imageUrl;

    public Station() {}
    public Station(String name, String location, String connectorType, double pricePerHour, int availableSlots, String imageUrl) {
        this.name=name; this.location=location; this.connectorType=connectorType; this.pricePerHour=pricePerHour; this.availableSlots=availableSlots; this.imageUrl=imageUrl;
    }
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getLocation(){return location;} public void setLocation(String location){this.location=location;}
    public String getConnectorType(){return connectorType;} public void setConnectorType(String connectorType){this.connectorType=connectorType;}
    public double getPricePerHour(){return pricePerHour;} public void setPricePerHour(double pricePerHour){this.pricePerHour=pricePerHour;}
    public int getAvailableSlots(){return availableSlots;} public void setAvailableSlots(int availableSlots){this.availableSlots=availableSlots;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
}
