# apod
## This program tests the REST api provided by APOD project of NASA [Astronomy Picture of the Day](https://apod.nasa.gov/apod/astropix.html)

### Instructions to set up the project locally 
1. Please use code editor like Eclipse 
2. Import the project from git using url - [https://github.com/rashmipatt/apodsubmission]
3. Code editor need to have maven plug in 
4. Right click on pom.xml and update the project
5. Go to testng.xml and create run configurations, 
   5.1 test class to Test_APOD_GET 
   5.2  create an environment variable named APOD_KEY and set the value of your key to your key (get developer key from [https://api.nasa.gov/] ) 
4. run testng.xml 


### Scenarios 

1. Successful scenarios
2. Boundary conditions 
3. Invalid Headers

### Detaild Scenarios 

1. 	200passing date and assigned api_key 	
	
2.	400 passing date and start_date params  with assigned api_key
	
3. 	400 passing date and end date params with assigned api_key
	
4. 	200 passing start_date and end_date range	with assigned api_key
	
	
5. 	200 0k passing date and start_date params  with assigned api_key
	
6.  200 ok passing date and end date params with assigned api_key
		
7. 	200  okpassing start_date and end_date range	with assigned api_key
		
8.	200 pass count with api_key
	
9.	pass count with date

10. Passing wrong date format

