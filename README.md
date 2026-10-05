# EV Charging Station Booking System (IntelliJ IDEA + MongoDB Compass)

A beginner-friendly Spring Boot web project with account registration/login, station browsing, booking slots, demo payment, booking history, cancellation, and printable receipt.

## Requirements
- JDK 17 or later (JDK 17 recommended)
- IntelliJ IDEA (Community Edition is fine)
- MongoDB Community Server running locally
- MongoDB Compass (to view the database)
- Internet connection on the first Maven build (to download dependencies and web fonts/images)

## 1. Start MongoDB
1. Install MongoDB Community Server for Windows if it is not already installed. During installation, keep MongoDB running as a Windows service.
2. Open MongoDB Compass.
3. Connect using `mongodb://localhost:27017`.
4. The app uses database `ev_station_booking`. MongoDB creates it after the app first saves data. Collections are `users`, `stations`, and `bookings`.

If Compass cannot connect, check that the MongoDB Server service is running in Windows Services. Compass is a database viewer; the MongoDB Server must also be installed and running.

## 2. Open the project in IntelliJ
1. Extract this ZIP to a normal folder, for example `C:\Users\YourName\IdeaProjects\ev-charging-station-booking`.
2. Open IntelliJ IDEA → **File → Open** → select the extracted `ev-charging-station-booking` folder (the folder containing `pom.xml`).
3. If asked, choose **Trust Project** and **Load Maven Project**.
4. Open **File → Project Structure → Project** and set Project SDK to JDK 17 (or later).
5. Wait until Maven finishes downloading dependencies.
6. Open `src/main/resources/application.properties` and confirm the MongoDB URL is `mongodb://localhost:27017/ev_station_booking`.
7. Open `src/main/java/com/evbooking/EvChargingBookingApplication.java` and click the green Run arrow next to `main`, or right-click the file → **Run**.
8. Wait for a line similar to `Started EvChargingBookingApplication` in the Run window.

## 3. Open the application
Open a browser at: `http://localhost:8080`
- Choose **Create an account**.
- Register with your name, email, and a password of at least 6 characters.
- You should be redirected to the dashboard.
- Open Stations → choose a station → select date/time/duration → Continue to payment.
- Choose a demo payment method and confirm. A receipt appears; use **Print / Save as PDF** to save it.

## 4. View records in MongoDB Compass
1. Refresh the connection/database list in Compass.
2. Open `ev_station_booking`.
3. Open `users`, `stations`, or `bookings` to see records.
4. Station sample records are inserted automatically on the first application start when the `stations` collection is empty.

## Important notes
- This is an academic/demo project. Payment is simulated only; it does not connect to UPI, banks, or a payment gateway.
- Passwords are stored as BCrypt hashes.
- The sample station photos use remote image URLs, so they need an internet connection. The booking workflow works even if images fail to load.
- Slot availability is sample data and is not decremented when a booking is made. This is suitable for a mini-project demonstration, not production use.
- If port 8080 is already occupied, change `server.port=8080` in `application.properties` to `8081`, then open `http://localhost:8081`.
- If you see a MongoDB connection error, start the MongoDB Server service and restart the Spring Boot app.

## Project structure
```text
ev-charging-station-booking/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/evbooking/
    │   ├── EvChargingBookingApplication.java
    │   ├── config/ (password encoder and sample stations)
    │   ├── controller/ (login, registration, station booking, payment, receipt)
    │   ├── model/ (User, Station, Booking)
    │   └── repository/ (MongoDB repositories)
    └── resources/
        ├── application.properties
        ├── static/css/style.css
        └── templates/ (login, register, dashboard, stations, book, bookings, payment, receipt)
```
