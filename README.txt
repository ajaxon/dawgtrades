DawgTrades - Team 2 
Manager - Allen




doc:
	includes UML class diagrams

db:
	includes the schema SQL file

src:
    include the sources in the following packages

    edu/uga/dawgtrades/model  	 — the interfaces of the Object Model module’s API
    edu/uga/dawgtrades/model/impl    - the implementation of the object model layer

    edu/uga/dawgtrades/persistence      - the interface of the Persitence module’s API
    edu/uga/dawgtrades/persistence/impl - the implementation of the Persistence module’s API

classes
	the compiled Java class files included here

Ant build file (build.xml)

To install and use the dawgtrades system:
1. Copy entire dawgtrades directory to your system
2. Install Mysql database 
3. Create team2 database using mysql CREATE DATABASE team2;
4. Create a database with user ‘team2’ and password ‘interface’ to accesss the db
5. Load the db schema into the db:
    mysql -u team2 -p team2 < db/4050.sql
6. compile the dawgtrades object model
 	ant compile
	
	java - class path classes:/opt/classes/mysql-connector-java-5.1.26-bin.jar edu.uga.dawgtrades.test.ObjectModelWrite

	Other tests are ObjectModelRead , ObjectModelUpdate and ObjectModelDelete
	
	Tests use the JUnit framework and all methods have applicable tests associated with them.

