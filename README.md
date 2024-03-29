# Web Semantica App
Web Application to find public parkings in Madrid by district

![alt text](https://github.com/SabrinaFZ/WebSemanticaApp/blob/master/App.png)

### Getting started
_If you want to run the app locally, follow the next steps:_

1. Install the following technologies
- [Maven](https://maven.apache.org/download.cgi)
- [Java 1.8+](https://www.java.com/es/download/)
- [Apache Tomcat 7](http://tomcat.apache.org/)

2. Clone the repository
``` 
git clone https://github.com/SabrinaFZ/WebSemanticaApp/
```

3. Run commands (run app locally)
```
mvn clean
mvn package
java -jar target/dependency/webapp-runner.jar target/app-0.0.1-SNAPSHOT.war
```

4. Open in web browser http://localhost:8080/

### Build with
- [Spring Boot](https://start.spring.io/)
- HTML5
- CSS3
- Javascript
- [Bootstrap](https://getbootstrap.com/)
- [Google Maps API](https://developers.google.com/maps/?hl=es-419)
- [Jena](https://jena.apache.org/download/index.cgi)
- [Heroku](https://dashboard.heroku.com/)
