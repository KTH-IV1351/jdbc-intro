# Introdction to JDBC

A short Java program giving an overview of JDBC concepts.

## How to execute

1. Clone this git repository.
1. Change to the newly created directory `cd jdbc-intro`
1. Make sure there is a database which can be reached with the url on lines
   43-44 or the url on lines 47-48. There are two ways to do this.
   1. Create a database that can be reached with one of the existing urls. If
      postgres is used, that is a database called simplejdbc, wich can be
      reached on port 5432 at localhost, by the user 'postgres' with the
      password 'postgres'. If MySQL is used, that is a database called
      simplejdbc, which can be reached on port 3306 at localhost, by the user
      'root' with the password 'javajava'.
   1. Change the url to match your database.
1. Build the project with the command `mvn install`
1. Run the program with the command `mvn exec:java`
