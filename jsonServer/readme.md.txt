This will install a fake json server so you can test http requests.

- https://github.com/typicode/json-server
- Command: npm install -g json-server
- Check with command: json-server
- Create directory C:\Libs\json
- Create a db.json file with some data
- Link db.json file with server with this command in db.json folder: json-server db.json
- You can now access with http://localhost:3000/posts or http://localhost:3000/ for homepage or http://localhost:3000/posts/1 for first post

If you want to run using authorization
- Instead of 'json-server db.json' use 'npm install' then 'npm run start-auth'
- That will start the authorization code that has been created
