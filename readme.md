Project Details
==============

Json Fake server
---------------

- https://github.com/typicode/json-server
- Command: npm install -g json-server
- Check with command: json-server
- Create directory C:\Libs\json
- Create a db.json file with some data
- Link db.json file with server with this command in db.json folder: json-server db.json
- You can now access with http://localhost:3000/posts or http://localhost:3000/ for homepage or http://localhost:3000/posts/1 for first post

Authorization with Bearer Token
-------------------------------
Within the jsonServer folder there are some package.json and javascript files that creates authorization within the JSON
fake server, with the purpose of testing token authentication within this project.
- Instead of 'json-server db.json' use 'npm install' the first time and then 'npm run start-auth'
each time you want to run it. 
- That will start the authorization code that has been created

Deserializing JSON to POJO
--------------------------
We have some pojo classes called Address.java, Location.java, Post.java that takes Json
responses and transform it into classes, then we can use it as:
 var posts = response.getBody().as(Posts.class);
 
 Json Schema matcher
 -------------------
 There is also a schema matcher and the idea is to validate the json response against
 the one expected. 
 - You crete the post.json in the target/classes folder as this will be read from there in
 execution. 
 - You need the dependency called 'json-schema-validator'
 - In method iShouldSeeTheAuthorNameAsWithJSONValidation
    - Add the library: import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
    - Then you call the static method matchesJsonSchemaInClasspath
 - Now it will assert the schema against the one expected when you run this scenario.
 
 Changing libraries to generic ones
 ----------------------------------
 The work will be done over 'RestAssuredExtension.java' file
 - Create a new 'RestAssuredExtensionV2.java' file
 - Create a new 'APIConstants.java' file