##Using Postman
- Select Post method with address http://localhost:8080/create-short
- Select field Authorization:
  - Username: user 
  - Password: spring generates itself and outputs to the console, just copy and paste
- Select field Body:
    - Choose raw and JSON
    - Enter view code:
    ````
   {
    "originalName":"https://amo.to/F/PV5I5Q/DZPE8A"
    }
- Select Get method with address http://localhost:8080/original-url-redirect

