package userStuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import Demo.Demo;
import exceptions.InvalidDateException;
import exceptions.InvalidInformationException;
import places.Place;
import services.Event;
import services.Offer;
import services.Reservation;
import website.Website;

public abstract class UserAdministration {
	private static boolean isLogged = false;
	public static List<User> allUsers = new ArrayList<>();
	private static User currentUser;
	private static String adminPass = "admin";
	private static Admin admin;

	public static User register(String firstName, String lastName, String city, String emailAdress, String password,
			String phoneNumber, LocalDate birthday, boolean isAdmin, String adminPass)
			throws InvalidInformationException {
		if (checkForValidString(firstName)) {
			if (checkForValidString(lastName)) {
				if (city != null) {
					if (UserAdministration.checkForValidEMail(emailAdress)) {
						if (checkForValidPassword(password)) {
							if (checkForValidPhoneNumber(phoneNumber)) {
								if (birthday != null) {
									if (isAdmin) {
										if (UserAdministration.adminPass.equals(adminPass)) {
											System.out.println("Registration successful! Welcome our new admin!");
											Admin a = new Admin(firstName, lastName, city, emailAdress, password,
													phoneNumber, birthday);
											a.setIsAdmin(true);
											UserAdministration.allUsers.add(a);
											return a;
										} else
											throw new InvalidInformationException(
													"Registration unsuccessful. Wrong admin pass");
									} else {
										System.out.println("Registration successful!");
										User u = new User(firstName, lastName, city, emailAdress, password, phoneNumber,
												birthday);
										UserAdministration.allUsers.add(u);
										return u;
									}
								} else
									throw new InvalidInformationException(
											"Registration unsuccessful. Null for birthday.");
							}
						}
					}
				} else
					throw new InvalidInformationException("Registration unsuccessful. Null for city.");
			}
		}
		return null;
	}
	
	public static void makeRegistration(){
		String firstName = getFirstName();
		String lastName = getLastName();
		String city = getCity();
		String emailAdress = getEmail();
		String password = getPassword();
		String phoneNumber = getPhoneNumber();
		LocalDate birthday = getBirthday();
		boolean isAdmin = isAdmin();
		String adminPass = null;
		if (isAdmin)
			adminPass = getAdminPass();
		try {
			UserAdministration.register(firstName, lastName, city, emailAdress, password, phoneNumber, birthday,
					isAdmin, adminPass);
		} catch (InvalidInformationException e) {
			System.out.println("Registration unsuccessful! ");
			System.out.println(e.getMessage());
		}
	}
	
	public static void login(String email, String password) throws InvalidInformationException {
		if (isLogged == false) {
			if (password != null && email != null) {
				for (User user : UserAdministration.allUsers) {
					if (user.getEmailAdress().equals(email.trim())) {
						if (user.getPassword().equals(password.trim())) {
							System.out.println("Login uspeshen");
							isLogged = true;
							if(user instanceof Admin) {
								admin = (Admin) user;
							}
							else {
								UserAdministration.currentUser = user;
							}
							return;
						} else {
							throw new InvalidInformationException("Login neuspeshen! Nepravilna parola!");
						}
					}
				}
				throw new InvalidInformationException("Login neuspeshen! Nqma registriran potrebtiel na takyv email");
			} else {
				throw new InvalidInformationException("Login neuspeshen! Podavash mi null za email/parola!");
			}
		} else {
			throw new InvalidInformationException("Login neuspeshen! Veche ima vpisan potrebitel");
		}
	}

	// without extraOptions
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					user, place);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// with extraOptions
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					extraOptions, user, place);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// without extraOptions, with offer
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					user, place, offer);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// without extraOptions, with event
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					user, place, event);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// with extraOptions and offer
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Offer offer) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					extraOptions, user, place, offer);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}

	// with extraOptions and event
	public static void makeReservation(LocalDateTime dateAndTimeOfReservation, int numberOfPeople, int numberOfChildren,
			String locationPref, String extraOptions, User user, Place place, Event event) {
		try {
			Reservation r = new Reservation(dateAndTimeOfReservation, numberOfPeople, numberOfChildren, locationPref,
					extraOptions, user, place, event);
			// Adding reservation to the user's reservations
			r.getUser().addReservation(r);
			// Adding reservation to the place's reservations
			r.getPlace().addReservation(r);
		} catch (InvalidInformationException | InvalidDateException e) {
			e.printStackTrace();
		}
	}
	
	public static void getReservationDetailsAndMakeReservation() {
		Place place = getPlaceIfExists();
		if (place == null)
			return;
		int nmbOfChildren = 0;
		if (place.isRestaurant())
			System.out.println("Enter the number of children for the reservation");
		nmbOfChildren = getNumberOfPeople();
		LocalDateTime dateAndTimeOfReservation = getDateTime();
		System.out.println("Enter the number of people for the reservation");
		int nmbOfPeople = getNumberOfPeople();
		String locationPref = pickLocationPref(place);
		makeReservation(place, nmbOfChildren, nmbOfPeople, dateAndTimeOfReservation, locationPref);
	}

	private static void makeReservation(Place place, int nmbOfChildren, int nmbOfPeople,
			LocalDateTime dateAndTimeOfReservation, String locationPref) {
		UserAdministration.makeReservation(dateAndTimeOfReservation, nmbOfPeople, nmbOfChildren, locationPref,
				UserAdministration.getU(), place);

	}

	public static void usersToJson() {
		File users = new File("JsonFiles" + File.separator + "users.json");
		Website.writeToJson(UserAdministration.allUsers, users);
	}

	public static void usersFromJson() {
		File file = new File("JsonFiles" + File.separator + "users.json");
		System.out.println("Loading user data...");

		try (Reader reader = new FileReader(file);) {
			Gson gson = new Gson();
			JsonElement json = gson.fromJson(reader, JsonElement.class);
			String result = gson.toJson(json);

			Type setType = new TypeToken<ArrayList<User>>() {
			}.getType();
			if (result != null && result.trim().length() > 0) {
				UserAdministration.allUsers = gson.fromJson(result, setType);
			} else {
				System.out.println("Tuka e problema!!!");
			}

		} catch (FileNotFoundException e) {
			System.out.println("This file does not exist!");
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static void logIn(){
		System.out.println("Enter your email: ");
		String email = Demo.sc.next();
		System.out.println("Enter your password: ");
		String password = Demo.sc.next();
		try {
			UserAdministration.login(email, password);
		} catch (InvalidInformationException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void logout() {

		if (currentUser != null) {
			System.out.println("Logout successful.");
			currentUser = null;
			isLogged = false;
		} else {
			System.out.println("You are not logged in!");
		}
	}

	public static void deleteUser(String password) {
		if (currentUser != null) {
			if (password.equals(currentUser.getPassword())) {
				UserAdministration.allUsers.remove(currentUser);
				logout();

			}
		}
	}
	
	public static void deleteProfile() {
		String password = getPassword();
		deleteUser(password);
	}
	public static void getAllUsers() {
		System.out.println(UserAdministration.allUsers);
	}

	public static boolean checkForValidEMail(String eMail) throws InvalidInformationException {
		if (eMail != null && Place.isValidEmailAddress(eMail)) {
			if (!allUsers.isEmpty()) {
				for (User u : allUsers) {
					if (u.getEmailAdress().equals(eMail)) {
						throw new InvalidInformationException("You already have a registration with this e-mail!");
					}
				}
				return true;
			} else {
				return true;
			}
		} else
			throw new InvalidInformationException("Missing input...");
	}

	public static boolean checkForValidPhoneNumber(String phoneNumber) throws InvalidInformationException {
		if (phoneNumber != null) {
			if (phoneNumber.trim().length() == 10) {
				for (int i = 0; i < phoneNumber.length(); i++) {
					if (!(Character.isDigit(phoneNumber.charAt(i))))
						throw new InvalidInformationException("Nomera trqbva da se systoi samo ot cifri!");
					else
						return true;
				}
			} else
				throw new InvalidInformationException("Nomera trqbva da sydyrza tochno 10 cifri!");
		} else
			throw new InvalidInformationException("Podavash null za phonenumber");
		return false;
	}

	public static boolean checkForValidString(String str) throws InvalidInformationException {
		if ((str != null) && (str.trim().length() > 0))
			return true;
		else
			throw new InvalidInformationException("Podavash null za String ili imash po-malko ot 1 znak");
	}

	protected static boolean checkForPasswordMatch(String password) throws InvalidInformationException {
		if (password != null) {
			if (password.equals(currentUser.getPassword()))
				return true;
			else
				throw new InvalidInformationException("Parolite ne syvpadat!!");
		} else {
			throw new InvalidInformationException("Podavash null za parola..");
		}
	}

	public static boolean checkForValidPassword(String password) throws InvalidInformationException {
		if (password != null) {
			if (password.trim().length() > 5)
				return true;
			else
				throw new InvalidInformationException("Dylzhinata na parolata trqbva da e pone 5 znaka");
		} else
			throw new InvalidInformationException("Podavash null za parola..");
	}

	public static boolean isLogged() {
		return isLogged;
	}

	public static User getU() {
		return currentUser;
	}
	public static String getPhoneNumber() {
		System.out.println("Type your phoneNumber");
		String phoneNumber = Demo.sc.next();
		return phoneNumber;
	}

	public static String getPassword() {
		System.out.println("Type your password");
		String password = Demo.sc.next();
		return password;
	}

	public static String getEmail() {
		System.out.println("Type your email");
		String email = Demo.sc.next();
		return email;
	}

	public static String getCity() {
		String city = null;
		while(city==null || city.trim().length()==0) {
			System.out.println("Type your city");
			city = Demo.sc.next();
		}
		return city;
	}

	public static String getLastName() {
		String lastName = null;
		while(lastName==null || lastName.trim().length()==0) {
			System.out.println("Type your last name");
			lastName = Demo.sc.next();
		}
		return lastName;
	}

	public static String getFirstName() {
		String firstName = null;
		while(firstName==null || firstName.trim().length()==0) {
			System.out.println("Type your first name");
			firstName = Demo.sc.next();
		}
		return firstName;
	}
	


	private static String getAdminPass() {
		System.out.println("Type the admin password:");
		String adminPassword = Demo.sc.next();
		return adminPassword;
	}

	private static boolean isAdmin() {
		System.out.println("Are you going to be an admin(Required admin password for this)");
		System.out.println("Type yes or no");
		String answer = Demo.sc.next();
		if (answer.equalsIgnoreCase("yes"))
			return true;
		else
			return false;
	}

	public static LocalDate getBirthday() {
		System.out.println("Enter you birth date.");
		int day = 0;
		while(day <1||day>31) {
			System.out.println("Day: ");
			day = Demo.sc.nextInt();
		}
		int month = 0;
		while(month<1||month>12) {
			System.out.println("Month: ");
			month = Demo.sc.nextInt();
		}
		int year = -1;
		while(year < 0) {
			System.out.println("Year: ");
			year = Demo.sc.nextInt();
		}
		return LocalDate.of(year, month, day);
	}
	private static int getNumberOfPeople() {
		return Demo.sc.nextInt();
	}
	private static Place getPlaceIfExists() {
		String placeName = getPlaceName();
		for (Place p : Website.getWebsite().getAllRestaurants(UserAdministration.getU())) {
			if (p.getName().equals(placeName)) {
				return p;
			}
		}
		for (Place p : Website.getWebsite().getAllClubs(UserAdministration.getU())) {
			if (p.getName().equals(placeName)) {
				return p;
			}
		}
		System.out.println("There is no place with such name in our system. Please check the name and try again!");
		return null;
	}

	private static String getPlaceName() {
		System.out.println("Please enter the name of the restaurant or club you want to reserve in: ");
		return Demo.sc.next();
	}
	private static LocalDateTime getDateTime() {
		System.out.println("Enter the month of the reservation: ");
		int month = Demo.sc.nextInt();
		System.out.println("Enter the day of the reservation: ");
		int day = Demo.sc.nextInt();
		System.out.println("Enter hour of reservation: ");
		int hour = Demo.sc.nextInt();
		return LocalDateTime.of(2018, month, day, hour, 0);

	}
	private static String pickLocationPref(Place p) {
		showLocationPrefOptions(p);
		System.out.println("Type the name of the option you want");
		String locationPref = Demo.sc.next();
		for (String s : p.getLocationPrefs()) {
			if (locationPref.equals(s))
				return locationPref;
		}
		System.out.println("This option for location pref doesn't exist. A random location pref is chosen for you.");
		return p.getLocationPrefs().get((int) (Math.random() * p.getLocationPrefs().size()));
	}
	private static void showLocationPrefOptions(Place p) {
		System.out.println("This are the location options you have for this place: ");
		for (String s : p.getLocationPrefs()) {
			System.out.println(s);
		}
	}
}
