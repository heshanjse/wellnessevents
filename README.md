# Wellness Event Booking Application

This web application facilitates the online booking of wellness events, such as health talks and onsite screenings. It offers two distinct dashboards. The HR dashboard to create and view events. On the other hand, the vendor dashboard serves as a centralized hub for approving or rejecting the events created by HR personnel, ensuring a seamless and efficient workflow for event management.

### Application Architecture
* The backend application is developed using Spring Boot 3.2.0 and Java 17.
* Spring Security is utilized to secure web endpoints through JWT tokens.
* Spring Data JPA is employed to implement Object-Relational Mapping (ORM).
* Log4j is utilized to generate logs, with Lombok's inbuilt log4j2 support.
* JUnit 5 is applied for unit testing.
* Mockito is used for mocking during the testing.
* Swagger is used for API documentation.
* Lombok is incorporated to minimize boilerplate code.
* Maven is employed as the build tool.
* The frontend application is constructed using React version 18.2.0 and Node version 18.12.0.
* Axios is employed to make web API calls to endpoints.
* React-Bootstrap is utilized for designing the user interface.
* npm is employed as the build tool.
* Git is used for version control.
* MySQL serves as the database for the application.
* The frontend is deployed using an AWS S3 bucket.
* The backend is deployed using AWS EC2.


### Backend API facilitates the following endpoints:

1. User registration:
1.1 HR admin registration:
```bash
curl --location 'http://localhost:8080/api/v1/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "username":"hradmin",
    "password":"1234hradmin",
    "companyName":"hradmin",
    "role":"HR_ADMIN"
}'
```
1.2 HR User registration
```bash
curl --location 'http://localhost:8080/api/v1/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "username":"hrauser1",
    "password":"1234hruser1",
    "companyName":"XYZ",
    "role":"HR_USER"
}'
```
 1.3 Vendor admin registration
```bash
curl --location 'http://localhost:8080/api/v1/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "username":"vadmin",
    "password":"1234vadmin",
    "companyName":"vadmin",
    "role":"VENDOR_ADMIN"
}'
```
 1.4 Vendor user registration
```bash
curl --location 'http://localhost:8080/api/v1/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "username":"vuser1",
    "password":"1234vuser1",
    "companyName":"XYZ-VENDOR",
    "role":"VENDOR_USER"
}'
```
2. User authentication:
```bash
curl --location 'http://localhost:8080/api/v1/auth/authenticate' \
--header 'Content-Type: application/json' \
--data '{
    "username":"hrauser1",
    "password":"1234hruser1"
}'
```
3. Create event booking:
```bash
curl --location 'http://localhost:8080/api/v1/human-resource/event' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxx' \
--data '{
    "eventTypeId":"1",
    "eventName":"health talks",
    "confirmedDate":"2023-12-20,2023-12-21,2023-12-22",
    "location":"MBS Hall B",
    "status":"Pending",
    "remarks":"",
    "createdDate":"2023-12-02",
    "humanResourceId":"10",
    "humanResourceName":"hrauser1",
    "vendorId":"13",
    "vendorName":"vuser1"
}'
```
3. update event booking (Accept or Reject):
```bash
curl --location 'http://localhost:8080/api/v1/vendor/events' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxx' \
--data '{
    "eventId": 1,
    "eventTypeId": 1,
    "eventName": "health talks",
    "confirmedDate": "2023-12-20,2023-12-21,2023-12-22",
    "location": "MBS Hall A",
    "status": "Rejected",
    "remarks": "",
    "createdDate": "2023-12-02T00:00",
    "humanResourceId": 8,
    "humanResourceName": "hradmin",
    "vendorId": 9,
    "vendorName": "vadmin"
}'
```
4. Read bookings as a HR:
```bash
curl --location 'http://localhost:8080/api/v1/human-resource/event/HR_USER/XYZ-HR' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxx'
```
5. update event as a Vendor:
```bash
curl --location 'http://localhost:8080/api/v1/human-resource/event/VENDOR_USER/XYZ-VENDOR' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer xxx'
```


### Relational database design:

![ER Diagram](/images/erd.png "Optional title")

### database scripts:
* The migration scripts are located in the following folder: `backend/src/main/resources/sqls/`
* You will find 2 scripts inside the `sqls` folder
  1. `ddl.sql` : Creates the database and its tables
  2. `dml.sql` : Inserts dummy values into the database tables

### Assumptions made while login to the app:
* users, event type and event already created. (need to execute DML sqls before login (dml.sql)) 

### Future Improvements:
* Enhance HR and Vendor 'GET' events rest controllers by extracting User role and and user company from the JWT.
* move user companies into separate table and map with user table 
* move event date suggestions now keep in one field as a comma separated string (not use best normalisation pactice) need to move that in to seperate table and link with event table.
* change event status to enum
* Optimize database performance by creating an index
* remove unwanted fields in event dto.
* Implement a forward proxy, such as Nginx, to enhance security by blocking unwanted requests.
* Integrate Actuator to monitor the backend application
* improve location field.
* Develop a user registration feature and implement an event creation page.
* Address unauthorized access errors by implementing proper error handling and redirecting users to the login page
* Improve the frontend by incorporating a refresh token mechanism. Implement a logout feature if refresh fails.
* Enhance security by storing JWT securely in cookies.
* Add integration test.

### User guide:

1. Log in to the application through the login page.

![HR login page](/images/hrloginpage.png "Optional title")

2. If the user is an HR user or HR admin, redirect to the HR page.
   
   ![HR page](/images/hrdashboard.png "Optional title")
   
3. HR users/HR admins can access event details by clicking the "View" button.

   ![HR popup](/images/hrpopup.png "Optional title")   

4. If the user is a vendor user or vendor admin, the system redirects them to the vendor page.

![Vendor login page](/images/vendorloginpage.png "Optional title")

![Vendor page](/images/venderdashboard.png "Optional title")

5. Vendor users/vendor admins can view events and take actions such as approving or rejecting events by clicking on each event row. Note that  if an event has already been approved or rejected, the corresponding popup cannot be opened.

![Vendor page](/images/vendorpopup.png"Optional title")

6. Vendors have the option to select a date from the dropdown and approve the event.

![Vendor  ](/images/vendorapprove.png "Optional title")

7. Vendors can also provide reasons for rejecting an event.

![Vendor page](/images/vendorreject.png "Optional title")

8. Users can log out at any time by clicking the logout button.

9. admin accounts can view all the records.

![hr admin login](/images/hradminlogin.png "Optional title")

![hr admin dashboard](/images/hradmindashboard.png "Optional title")

![vendor admin login](/images/vendorlogin.png "Optional title")

![hr admin login](/images/vendoradmindashboard.png "Optional title")

### User logins:

User type         | username    | password
------------- | ------------- | ------------- 
HR Admin   | hradmin   | 1234hradmin 
HR User   | hrauser1   | 1234hruser1 
HR User   | hrauser2   | 1234hruser2 
Vendor Admin   | vadmin   | 1234vadmin 
Vendor User   | vuser1   | 1234vuser1 
Vendor User   | vuser2   | 1234vuser2 

### local setup 

1. Create a database schema named `wellnessevents` in the MySQL database.
2. Execute the Data Definition Language (DDL) and Data Manipulation Language (DML) files located in `backend/src/main/resources/sqls/`.
3. Clone the front end and back end repositories from Bitbucket using the command: `git clone https://Heshanjay@bitbucket.org/bookingevents/wellnessevents.git`.
4. Open the backend folder in your Integrated Development Environment (IDE).
5. Execute the Maven install command and start the application.
6. Navigate to the frontend folder using your terminal.
7. Run the commands npm install and npm start. Note: Ensure that Node.js is installed on your computer before executing these commands.
8. Open your browser and visit `localhost:3000` to view the application.

### Deployment
This project is deployed to AWS, you can access all of the API below:
 
  [Wellness Event AWS S3]( http://wellness-events.s3-website-ap-southeast-2.amazonaws.com)


### _Thanks for reading this far. I hope you enjoyed it :)_