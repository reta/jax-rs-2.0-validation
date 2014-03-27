Apache CXF and Bean Validation 1.1
==============

- 200 OK: 
  curl http://localhost:8080/rest/api/people -i -X POST -d "email=a@b.com&firstName=Tom&lastName=Knocker"
  
- 200 OK: 
  curl http://localhost:8080/rest/api/people/a@b.com

- 500 Internal Server Error: 
  curl http://localhost:8080/rest/api/people -i -X POST -d "email=b@c.com"
  
- 400 Bad Request:           
  curl http://localhost:8080/rest/api/people -i -X POST -d "firstName=Tom&lastName=Knocker"
  
- 500 Internal Server Error: 
  curl http://localhost:8080/rest/api/people?count=10 -i