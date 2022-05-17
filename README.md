# Company Test Project
- Project developed to test the e-core company.

### Approched
- The project domain consists only of Role management.
The list of users and teams is originated by an external service that is accessed 
via FeignClient at project startup time. Then, all existing relationships between members and teams necessary for role management are created.

### Running documentation locally
- Open terminal and execute "./run.sh" in root folder. Waiting for tomcat starting in console. 

### Future Improvements
- Implement contract to update users and teams from extenal service
- Swagger Integration
- JPA Validations

### Future Improvements
- Implement contract to update users and teams from extenal service

### Postman
- Import Company_POSTMAN.json contained in the resources folder into the application

## Endpoints
### Users
- GET "http://localhost:8080/v1/company/users
### Teams
- GET "http://localhost:8080/v1/company/teams
### Roles
- GET "http://localhost:8080/v1/company/roles
### Memberships
- GET "http://localhost:8080/v1/company/memberships
### Create Role
- PUT "http://localhost:8080/v1/company/role/{roleName}
### Assign Role
- PUT "http://localhost:8080/v1/company/role/assign/{roleId}?userId={userId}&teamId={teamId}
### Role For Membership
- GET "http://localhost:8080/v1/company/role?userId={userId}&teamId={teamId}
### Memberships for Role
- GET "http://localhost:8080/v1/company/memberships/{roleId}

