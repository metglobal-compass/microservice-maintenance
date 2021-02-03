# spring-maintenance
A maintenance project written in Spring Boot.

### Summary

It provides maintenance mode to the applications, if any app is in maintenance mode,
incoming requests will be caught in the maintenance filter, and it will throw an 
exception with message "Server is under maintenance."

It only requires a running Redis server, each project's maintenance keys and their
boolean values will be written there as a key-value pair.

### Usage

It can be used by just adding one dependency to project's pom.xml. Needed components
already exist in the package.

pom.xml code (replace "latest version" with project version (e.g. 0.1.0)):

 ```
 <dependencies>
     ..............
     <!-- maintenance switcher !-->
     <dependency>
 	<groupId>com.github.metglobal-compass</groupId>
 	<artifactId>spring-maintenance</artifactId>
 	<version>"latest version"</version>
     </dependency>
     ..............
 </dependencies>
 ```

And application.properties file should have:

```
# Basic authorization credentials
maintenance.username=foo
maintenance.password=123

# Redis host and port (optional, these are default values)
maintenance.host=localhost
maintenance.port=6379

# Redis ttl (optional)
maintenance.ttl=3600 (this is default)
```

Used routes (Basic Authorization should be used) (without body):

```
(PATCH) /maintenance/lock -> Locks the application (maintenance mode on)
(PATCH) /maintenance/unlock -> Unlocks the application (maintenance mode off)
```

If request is successful, we should get this response (e.g. if maintenance became off):

```
{
   "success": true,
   "message": "Server is under maintenance."
}
```

After locking the application, if we send a usual request, it will throw
a 503 Service unavailable error with message `Server is under maintenance.` until we
unlock it again.