# DataX
DataX is an open-source, local database for java servers.
## Introduction
1. To get started, download the latest version. For now, there is no official release, as it is still in beta.
2. Add the jar file to your project's dependencies
## Usage
### Databases
Databases are folders, or packages. For example, if you have a folder inside your project in a folder called src, and the folder is called database, then this is how you would declare it in your java program:
```java
Database database = new Database("src/database");
```
### Tables
Inside of the databases are tables. You can create a table by doing the following:
```java
Database database = new Database("src/database");
Table table = database.createTable("data");
```
To return an already existing table, do:
```java
Database database = new Database("src/database");
Table table = database.getTable("data");
```
There is also a method that creates a table if it doesn't already exist, and if it does exist, then it returns it:
```java
Database database = new Database("src/database");
Table table = database.returnTable("data");
```
### Creating Columns
To create a column inside of a database, you can do this:
```java
Database database = new Database("src/database");
Table table = database.returnTable("data");
table.addColumn("username", DataType.STRING);
```
### Deleting Columns
To delete a column inside of a database, you can do this:
```java
Database database = new Database("src/database");
Table table = database.returnTable("data");
table.removeColumn("username");
```
### Deleting Tables
To delete a table, you can provide either the table name, or the table directly:
```java
Database database = new Database("src/database");
database.deleteTable("data");
```
or
```java
Database database = new Database("src/database");
Table table = database.returnTable("data");
database.deleteTable(table);
```
### Deleting Databases
You can delete a database by doing the following:
```java
Database database = new Database("src/database");
database.selfDestruct();
```
or
```java
Database database = new Database("src/database");
database.selfDestructOnExit();
```
### Executing Queries
To execute a query, you can do the following:
```java
Database database = new Database("src/database");
Table table = database.returnTable("data");
table.execute(new Execution() {
  @Override
  public void Execution(Query query) {
    query.setQuery("INSERT (username) VALUES (user1)");
    query.executeQuery();
  }
});
```
This syntax is very similar to SQL syntax. The executeQuery() method returns a ResultSet which you can work with;
