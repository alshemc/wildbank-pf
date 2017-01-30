Spring + JSF + JPA DemoProject Quickstart
=============================

- Version 1.0.0.

- A demo project of the Bank of the wild West

- What it is: a demo project containing initial structure to:
  - ZERO CONFIGURATIONS to run the project! Checkout and run!
  - Spring Context 4.x as JEE context provider;
  - Jetty 9.2.x as Webserver;
  - Mojarra JavaServer Faces 2.2.x as JSF implementation;
  - Primefaces 5.x as JSF components library;
  - Built-in H2, PostgreSQL and MySQL Server connection for JDBC/JPA (extensible to ANY database - add specific JDBC drivers to POM if needed);
    - Oracle;
    - PostgreSQL;
    - Microsoft SQL Server;
    - Informix;
    - H2;
    - Derby;
    - and many more...
  - Hibernate 4.x as JPA 2.1 provider (switchable to EclipseLink if convenient - see web.xml);
  - Full-featured CRUD operations over an entity;
  - Internationalization;
  - Bean utilities;
  - Primefaces Twitter Bootstrap Theme;

- To run:
  - Checkout using Git or SVN;
  - Run using Maven 3.x `mvn clean jetty:run` or start.bat (Maven need to install http://maven.apache.org/download.cgi);
  - The project is available at	http://localhost:8080/wildbank-pf/

- Optional
  - Customize database (optional):
    - Create a database over your preferred SGBD;
    - Add the desired JAR to POM, if needed: built-in H2, PostgreSQL and MySQL drivers;
    - Configure database connection settings over `src/main/webapp/WEB-INF/database`;
  - Customize JPA provider:
    - Change `spring.profiles.active` context-param, between `eclipselink` and `hibernate` over `src/main/webapp/WEB-INF/web.xml`;
  - Customize Primefaces themes:
    - Change `primefaces.THEME` context-param over `src/main/webapp/WEB-INF/web.xml`;
