package com.ftn.PrviMavenVebProjekat.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Price {
	private Long id;
	private Long destinationId;
	private Long putovanjeId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Double priceOfTravel;
	
	
	
	public Price(Long destinationId, Long putovanjeId, LocalDateTime startDate, LocalDateTime endDate, Double priceOfTravel) {
		super();
		this.destinationId = destinationId;
		this.putovanjeId = putovanjeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceOfTravel = priceOfTravel;
	}
	public Price(Long id, Long destinationId, Long putovanjeId, LocalDateTime startDate, LocalDateTime endDate, Double priceOfTravel) {
		super();
		this.id = id;
		this.destinationId = destinationId;
		this.putovanjeId = putovanjeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceOfTravel = priceOfTravel;
	}
	public Price(Long destinationId, Long putovanjeId, String formattedStartDate, String formattedEndDate, Double priceOfTravel) {
		super();
		this.destinationId = destinationId;
		this.putovanjeId = putovanjeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceOfTravel = priceOfTravel;
	}
	public Price(Long id, Long destinationId, Long putovanjeId, String formattedStartDate, String formattedEndDate, Double priceOfTravel) {
		super();
		this.id = id;
		this.destinationId = destinationId;
		this.putovanjeId = putovanjeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceOfTravel = priceOfTravel;
	}

	public Price(Long id, LocalDateTime startDate, LocalDateTime endDate, Double priceOfTravel) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceOfTravel = priceOfTravel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}
	public Long getPutovanjeId() {
		return putovanjeId;
	}
	public void setPutovanjeId(Long putovanjeId) {
		this.putovanjeId = putovanjeId;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public Double getPriceOfTravel() {
		return priceOfTravel;
	}
	public void setPriceOfTravel(Double priceOfTravel) {
		this.priceOfTravel = priceOfTravel;
	}
	

    public String getFormattedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return startDate.format(formatter);
    }


    // Setter for startDate with custom parsing
    public void setFormattedStartDate(String formattedStartDate) {
        this.startDate = LocalDateTime.parse(formattedStartDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    public String getFormattedEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return endDate.format(formatter);
    }

    // Setter for endDate with custom parsing
    public void setFormattedEndDate(String formattedEndDate) {
        this.endDate = LocalDateTime.parse(formattedEndDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
	
}
