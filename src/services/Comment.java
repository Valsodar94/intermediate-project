package services;

import java.time.LocalDate;
import java.time.LocalTime;

import exceptions.InvalidInformationException;
import places.City;
import places.Place;
import userStuff.User;

public class Comment {
	private final LocalDate date;
	private final LocalTime hour;
	private City city;
	private String description;
	private int rating;
	private final User user;
	private final Place place;

	public Comment(String description, int rating, User user, Place place) throws InvalidInformationException {
		this.date = LocalDate.now();
		this.hour = LocalTime.now();
		setDescription(description);
		setRating(rating);
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

	// getters and setters
	public void setDescription(String description) {
		if (description != null) {
			this.description = description;
		}
	}

	public void setRating(int rating) {
		if (rating > 0 && rating < 6) {
			this.rating = rating;
		}
	}
	

	public City getCity() {
		return city;
	}

	public int getRating() {
		return rating;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getHour() {
		return hour;
	}

	public User getUser() {
		return user;
	}

	public Place getPlace() {
		return place;
	}

}
