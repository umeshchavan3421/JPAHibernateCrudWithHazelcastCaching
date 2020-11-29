Assignment - REST API using Spring Data JPA Hibernate with Hazelcast cache and Spring basic auth  Security Crud operations 
============== 
Feature Details:
-------------------
1. Spring based web application exposing the following 6 REST APIs performs CRUD operations to manage the User details.
	API			                | Description
	----------------------------| -----------
	GET /users	      	        | Perform operation to fetch all the User details
	GET /user/{username}	    | Perform operation to fetch User specific details based on name passed	 
	POST /add/user		        | Perform operation to add new User 
	PUT /update/user		    | Perform operation to update the existing User details
	DELETE /delete/user/{id}	| Perform operation to delete the existing User
	DELETE /delete/users    	| Perform operation to delete all User entries
2. All the 6 APIS secured with Basic Authentication using Spring security
3. Method level security provided for data manipulation operations like DELETE, ADD and UPDATE
4. UnitTest cases implemented for Controller and Service layer
5. Implemented cache feature using Hazelcast. 
6. Logs: logback configuration used to generate logs.
	* Location: logs/assignment.%d{yyyy-MM-dd}.log at web application root directory.
	
User entity details:
-----------------------
a. id (long) / primary key Auto Generate
b. username (String) / unique
c. password (String)
d. status (String) 

Prerequisites (Dependencies): 
---------------------------------
Java 8
Spring Boot
Spring Security
Spring Data JPA/Hibernate
Maven
Git

Usage: 
---------
1. Extract zip file into your file system to specific location.
2. Go to the Project root folder where POM file exist.
3. Run below commands to build and execute the application
	1. __mvn clean install__
	1. __mvn spring-boot:run__
4. You can validate all the APIs using below CURL commands
	A) GET /users
	curl --location --request GET 'http://localhost:9090/users' \
         --header 'Authorization: Basic dXNlcjE6cGFzc3dvcmQ=' \
         --header 'Cookie: JSESSIONID=19F3CD3B2E3168BF50E910F26DE819E3'	
	B) GET /user/{username}
	curl --location --request GET 'http://localhost:9090/user/test1' \
         --header 'Authorization: Basic dXNlcjE6cGFzc3dvcmQ=' \
         --header 'Cookie: JSESSIONID=19F3CD3B2E3168BF50E910F26DE819E3'
	C) POST /add/user
	curl --location --request POST 'http://localhost:9090/add/user' \
         --header 'Authorization: Basic dXNlcjE6cGFzc3dvcmQ=' \
         --header 'Content-Type: application/json' \
         --header 'Cookie: JSESSIONID=C47DD0CD67EF55CCBC285D45E3CA9B0E' \
         --data-raw '{
                      "userName":"test3",
                      "password":"pass",
                      "status":"active"
                     }'	
	D) DELETE /delete/user/{id}
	curl --location --request DELETE 'http://localhost:9090/delete/user/3' \
         --header 'Authorization: Basic YWRtaW46YWRtaW4=' \
         --header 'Cookie: JSESSIONID=19F3CD3B2E3168BF50E910F26DE819E3'
    E) PUT /update/user
    curl --location --request PUT 'http://localhost:9090/update/user' \
         --header 'Authorization: Basic YWRtaW46YWRtaW4=' \
         --header 'Content-Type: application/json' \
         --header 'Cookie: JSESSIONID=19F3CD3B2E3168BF50E910F26DE819E3' \
         --data-raw '{"id":1,"userName":"test1","password":"password","status":"deact"}'
    F) DELETE /delete/users
    curl --location --request DELETE 'http://localhost:9090/delete/users' \
         --header 'Authorization: Basic YWRtaW46YWRtaW4=' \
         --header 'Cookie: JSESSIONID=C47DD0CD67EF55CCBC285D45E3CA9B0E' \
         --data-raw ''         
5. OR you can validate all the APIs by any REST client like POSTman.
6. DB Credentials
	* __Username__: username
	* __Password__: password
7. REST API access credentials:
   A) For Data manipulation API's like DELETE user, Update user and ADD user  select Basic Auth and provide username: admin and password: admin
      or add header Authorization: Basic YWRtaW46YWRtaW4=
   B) For other API's like fetch all users, get specific user select Basic Auth and provide username: user1 and password: password
      or add header Authorization: Basic dXNlcjE6cGFzc3dvcmQ=
		
8. You can access the in memory DB using H2 console by url http://host:port/h2-console. (e.g. http://localhost:9090/h2-console)
