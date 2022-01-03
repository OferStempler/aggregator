**To build:**

mvn clean install

**to run:**
java  -jar aggregator-1.0.0.jar

**GET request example to referesh:**
curl --location --request GET 'http://localhost:8080/refresh'

**GET request example for getting crm data:**
curl --location --request GET 'http://localhost:8080/getCrmData?provider=10001121&createdErrorCode=101&status=Closed'
