# Academia - a service architecture application
This project required me to understand and apply backend development techniques using SpringBoot with Java, fastAPI with Python and gRPC.
## The usage of each technology
### SpringBoot with Java

In this project I have used Spring mainly for developing services tied to a SQL Database. The main purpose of this service were to apply all CRUD operations that were tied to the database structure.  
These SQL databases contained information about students (like their year, their study cycle - master, bachelor etc. - email and other relevant information), about teachers and about the course enrollment (as in a teacher can teach multiple courses, and a student can attend multiple lectures).  
The routes and their purpose can also be viewed in the API documentation.  
  
### FastApi with Python

FastApi was used for handling the MongoDB schemas from this project, again applying all the CRUD operation on a database. This database was smaller but it's structure was more variable, sometimes fields were missing or were very different from other documents in the lectures collections.  
MongoDB stored information and documents tied to each course, for instance, each course had an unique id, had a method of evaluation and some "weights" giving a much importance on a grade than on other grades (for instance, you could have a 0.80 weight on a project and 0.20 weight on a written test, of course the project would be more important).

### gRPC with Python

The only purpose I used gRPC in this project was for security purposes. The main functions of this gRPC server are authentication - the user inputs an email and a password and this function takes these, check for a role in the database, and return a JWS token unique to that user and that request, validation check - the token will be checked if it is still a valid token and return the appropriate message (valid, invalid, blacklisted or expired), blacklisting - this request will not invalidate the given token, but it will also add it to a blacklist so if someone tries to reuse the blacklisted token the response message will display that the token has been blacklisted.
