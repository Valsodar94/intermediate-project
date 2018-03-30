package services;

import java.time.LocalDateTime;
import java.util.UUID;

import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.Place;
import userStuff.User;

public class Reservation {
	private String reservationID;
	private LocalDateTime dateAndTimeOfReservation;
	private int numberOfPeople;
	private int numberOfChildren;
	private String locationPref;
	private int discount;
	private String extraOptions;
	private final User user;
	private final Place place;
	private Offer offer;
	private Event event;

	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, int discount, User user, Place place) throws InvalidInformationException, InvalidDateException {

		this.reservationID = generateReservationID();
		setDateAndTime(dateAndTimeOfReservation);
		setNumberOfPeople(numberOfPeople);
		setNumberOfChildren(numberOfChildren);
		setlocationPref(locationPref);
		setDiscount(discount);
		if (user != null) {
			this.user = user;
		} else {
			throw new InvalidInformationException("Invalid user!");
		}
		if (place != null) {
			this.place = place;
		} else {
			throw new InvalidInformationException("Invalid place!");
		}
	}

	public Reservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, int discount, String extraOptions, User user, Place place) throws Exception {
		this(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref, discount, user, place);
		setExtraOptions(extraOptions);
	}

	public String generateReservationID() {

		UUID uuid = UUID.randomUUID();
		String s = Long.toString(uuid.getMostSignificantBits(), 32);
		s = s.replace('-', 'R');

		return s;
	}

	public void listReservationInfo() {
		System.out.println("Reservation:");
		System.out.println("Date: " + this.dateAndTimeOfReservation.getDayOfMonth() + "/"
				+ this.dateAndTimeOfReservation.getMonthValue() + "/" + this.dateAndTimeOfReservation.getYear());
		System.out.println("Adults: " + this.numberOfPeople);
		System.out.println("Children: " + this.numberOfChildren);
		System.out.println("Time: " + this.dateAndTimeOfReservation.getHour()+":"+this.dateAndTimeOfReservation.getMinute());
		System.out.println("Discount: " + this.getDiscount()+"%");
		System.out.println("Reservation number: " + this.reservationID);
		System.out.println("Place: " + this.locationPref);
		if(this.extraOptions != null) {
			System.out.println("I would like to be " + this.extraOptions);
		}
		if(this.event != null) {
			System.out.println("Event: " + this.event.getTitle());
		}
		if(this.offer != null) {
			System.out.println("Offer: " + this.offer.getTitle());
		}
	}

	// getters and setters

	public LocalDateTime getDateAndTime() {
		return dateAndTimeOfReservation;
	}

	public void setDateAndTime(LocalDateTime dateAndTimeOfReservation) throws InvalidDateException {
		if (dateAndTimeOfReservation != null) {
			LocalDateTime currentDateTime = LocalDateTime.now();
			if (dateAndTimeOfReservation.isEqual(currentDateTime)
					|| dateAndTimeOfReservation.isAfter(currentDateTime)) {
				this.dateAndTimeOfReservation = dateAndTimeOfReservation;
			}
		} else {
			throw new InvalidDateException("Invalid date or time of reservation");
		}
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) throws InvalidInformationException {
		if (numberOfPeople > 0) {
			this.numberOfPeople = numberOfPeople;
		} else {
			throw new InvalidInformationException("Number of people must be positive!");
		}
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) throws InvalidInformationException {
		if (numberOfChildren > 0) {
			this.numberOfChildren = numberOfChildren;
		} else {
			throw new InvalidInformationException("Number of children must be positive!");
		}
	}

	public String getLocationPref() {
		return locationPref;
	}

	public void setlocationPref(String locationPref) throws InvalidInformationException {
		if (Place.isValidString(locationPref)) {
			this.locationPref = locationPref;
		} else {
			throw new InvalidInformationException("Please enter a valid location preference!");
		}
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) throws InvalidInformationException {
		if (discount > 0) {
			this.discount = discount;
		} else {
			throw new InvalidInformationException("Discount must be bigger than 0 %.");
		}
	}

	public String getExtraOptions() {
		return extraOptions;
	}

	public void setExtraOptions(String extraOptions) throws InvalidInformationException {
		if (Place.isValidString(extraOptions)) {
			this.extraOptions = extraOptions;
		} else {
			throw new InvalidInformationException("Please enter valid extra options!");
		}
	}

	public String getReservationID() {
		return reservationID;
	}

	public User getUser() {
		return user;
	}

	public Place getPlace() {
		return place;
	}

}
