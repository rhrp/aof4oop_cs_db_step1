This project intends to evaluate the work effort when the schema of our case study evolves from version A to B using the native DB4o mechanisms

Testing steps

1 - Using the project aof4oop_cs_step1

1.1 - Run CreateDB for creating a new database

1.2 - Run ShowDB for checking the existing object in the DB

1.3 - Run MigrateDBStep1 for copying data to temporary classes

1.4 - Copy the database file to project aof4oop_cs_step2

2 - Using the projects aof4oop_cs_step2

2.1 - Run ShowDB for checking the existing object in the DB

2.2 - Run MigrateDBStep2 for copying data from temporary classes to the definitive ones

2.3 - Run again the ShowDB app for checking the existing object in the DB
