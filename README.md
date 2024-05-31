About
Country-Details is an application to retrieve instant, accurate information for any country in the world in lightweight JSON format.
Its a simple application to demonstrate features like:
•	Consuming an API,
•	Pagination & Sorting API data, making it easier for clients to consume and interact with large datasets, 
•	Backend Level Security, Authentication & Authorization 
This Project aims to enhance the functionality of an existing external API by providing additional features 

Tools and Technologies
•	Java 21
•	Spring Boot - version 3.2.5 RELEASE
•	Spring Web - version 3.2.5 RELEASE
•	Spring Data JPA - version 3.2.5 RELEASE
•	Spring Security 6 - version 6.2.4 RELEASE
•	MySQL Database - version 8.0.30
•	Maven

Endpoints:

Name
Search by country name. 
http://localhost:6000/countries/get_country_details/{country_name}
http://localhost:6000/countries/get_country_details/america

Population
Search countries by Population
Population greater then N
http://localhost:6000/countries/population_greater_then/{population}
http://localhost:6000/countries/population_greater_then/10000000
Population less then N
http://localhost:6000/countries/population_less_then/{population}
http://localhost:6000/countries/population_less_then/10000000

Area
Search countries by area
Area Greater then N(Square Kilometer)
http://localhost:6000/countries/area_greater_then/{area}

Area less then N(Square Kilometer)
http://localhost:6000/countries/area_less_then/{area}

Language
Search countries by National language
http://localhost:6000/countries/get_countries_by_language/{language}
