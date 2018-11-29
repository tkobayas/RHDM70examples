### Build

~~~
$ mvn clean package
~~~

or

~~~
$ mvn clean install
~~~


### Deploy

Deploy to EAP 7/WildFly:

~~~
$ mvn clean package wildfly:deploy
~~~


### Undeploy

Undeploy to EAP 7/WildFly:

~~~
$ mvn clean wildfly:undeploy
~~~


### Access the application

The application will be running at <http://localhost:8080/drools-test/hello>

You can specify "user" as a query parameter like:

~~~
$ curl http://localhost:8080/drools-test/hello?user=drools
~~~


For example:

~~~
$ curl http://localhost:8080/drools-test/hello
<html>
 <head>
  <title>DroolsExampleServlet</title>
 </head>
 <body>
 <h1>
   message = Goodbye cruel world
 </h1>
 </body>
</html>
~~~
~~~
$ curl http://localhost:8080/drools-test/hello?user=drools
<html>
 <head>
  <title>DroolsExampleServlet</title>
 </head>
 <body>
 <h1>
   message = Goodbye cruel drools
 </h1>
 </body>
</html>
~~~

And you can see the log messages like the following in server.log:

~~~
INFO  [com.sample.DroolsExampleServlet] (default task-3) doGet() is invoked
INFO  [Sample.drl] (default task-3) KieSession[1] : Hello, world
INFO  [Sample.drl] (default task-3) KieSession[1] : Goodbye cruel world
INFO  [com.sample.DroolsExampleServlet] (default task-4) doGet() is invoked
INFO  [Sample.drl] (default task-4) KieSession[1] : Hello, drools
INFO  [Sample.drl] (default task-4) KieSession[1] : Goodbye cruel drools
~~~
