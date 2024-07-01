*************** Complete Framework Folder Structure *****************
1. Created a MAVEN Project
2. Created a Package as 'com.nagarro.utils' which have following classes
3. a. Helper: have functions to read config file, get Json file path, setRestAssuredLogStream(this is add request and response to Log4j), etc
4. b. Payloads: have all payloads
5. c. Report: for initializing and finalizing extent report
6. d. Resource: have all API resources
7. e. RestClientWrapper: have all common functions for request type
8. f. TestDataReader: have methods to read test data from xml file i.e.'testData.xml' and store it in map. It uses XMLParser class to generate map from testData.xml or any other xml
   g. XMLParser: Used to parse xml data into Map
9. Created a Package as 'payloads' which have Pojo classes for request and response
10.Created a package as 'src/main/resources/jsonSchemas' which have json schema for response to validate
10. 'src/main/resources/testsuites' have test suite
11. resource folder have config.properties, log4j2.xml and testData.xml
12. Path 'com/nagarro/tests' have all tests