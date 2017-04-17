# Group-12

## CS5500 Project

The Project consists of 3 main components

- User Interface: Interface for User Interface and dispatching user requests to query engine
- Query Engine: Responsible for translating user queries to my sql queries to fetch relevant information
- Parser: Parses DBLP Data and Committee Information that is stored in Maria DB Hosted on RDS Instance (AWS)

## Project Structure
- User Interface : (src/main/java/view)
- Query Engine : (src/main/java/queryengine)
- Front End(Parser) : (src/main/java/frontend)
- Tests : (test/java/msd/group12)

## Steps to Build/Run Application:

- Run 'mvn clean package' in the project root directory to build the JAR.
- Navigate to target directory and run command 'java -jar' on the jar generated
- Also, the App can be started by running MainApp.java (src/main/java/application)
- Once the Application is launched, use test Credentials: mohit/mohit to use the application

## How to Test Application

- Run 'mvn test' or Run AllTestsV2 as JUnit (src/test/java/msd/group12/unit)

## Jenkins Integration Details:

- Jenkins CI Pipelines configured
- Jenkins Build runs for every branch pushed to Github
- Build status/results are sent to Team slack channel.
- Checkstyle Plugin Added for Static Code Analysis

## ASSUMPTIONS (Parsing DBLP Data)

### DBLP
    
- Proceedings : Records with empty Key or BookTitle are not processed
    
- InProceedings: Recors with empty Key or BookTitle are not processed
    
- Article: Records with empty Key or Title are not processed
    
- www: Records with empty Key are not processed
    

### Committee

 - It is assumed that the data files have name of the format "conference-acronym""Year"-"pc e.g. (ase2007-pc.txt)
    
 - The data contains "Author Name" on each line of file or "Author Postion Held Acronym": "Author Name"

## Please email Sudeep, Mohit, Rajdeep or Ira before testing as we need to upgrade the RDS DB instance type.Right now it is set to t2.micro which gives poor performance for the queries
