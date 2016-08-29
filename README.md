# simple-lottery

DESCRIPTION
This is a Simple Lottery Web Service which gives you ability to generate a random lottery Ticket.

Problem which this web service solves is described by the below:
    "LOTTERY RULES
     You have a series of lines on a ticket with 3 numbers, each of which has a value of 0, 1, or 2.
     For each ticket if the sum of the values is 2, the result is 10. Otherwise if they are all the same, the result is 5.
     Otherwise so long as both 2nd and 3rd numbers are different from the 1st, the result is 1. Otherwise the result is 0.

     IMPLEMENTATION
     Implement a REST interface to generate a ticket with n lines.
     Additionally we will need to have the ability to check the status of lines on a ticket.
     We would like the lines sorted into outcomes before being returned.
     It should be possible for a ticket to be amended with n additional lines.
     Once the status of a ticket has been checked it should not be possible to amend."


COMPILING AND RUNNING
This is a maven spring-boot application so to compile the sources you need maven installed
(version 3.3.3 has been used at the time of development).

To run the web service cd into the simple-lottery/ and run:
    mvn clean install
    mvn spring-boot:run

The first command builds the application and the second runs it. It starts an embedded web container which serves the
application on port 8080. If this port is already used on your system by something else then you can change the
simple-lotter default port by editing the 'server.port' property defined in:
    simple-lottery/src/main/resources/application.properties
default value:
    server.port=8080

Also, the 'application.properties' file allows you to define the default number of lines which will be generated on a new Ticket
if not provided by API caller.


API DOCUMENTATION
The application uses swagger-ui for API documentation. To access it go to:
    http://localhost:8080/swagger-ui.html

    (Please refresh the link if you don't get the 'ticket-controller : Ticket Controller'
    after opening the url).

Using that swagger-ui interface you can try all the endpoints. They are fully functional from there.

There is also a Postman collection attached for convenience:
    simple-lottery/Simple-Lottery.postman_collection

NOTE
The application does not use any persistence at the moment. All the data is kept in the memory as long as the service is running.
If you restart the service then you will lose all the Tickets previously generated.
