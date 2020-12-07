# BA-LSD-CASE3

Backend team for Team C

- Claus Kramath - cph-ck83@cphbusiness.dk
- Kenneth Leo Hansen - cph-kh415@cphbusiness.dk
- Simon Bojesen - cph-sb339@cphbusiness.dk
- Martin Høigaard Rasmussen - cph-mr221@cphbusiness.dk
- Mads Wulff Nielsen - cph-mn492@cphbusiness.dk

Link to deployed frontend solution:
[https://lsd-case-3-fe.herokuapp.com/api/v1/booking](https://lsd-case-3-fe.herokuapp.com/api/v1/booking)

[![Build Status](https://travis-ci.com/SimonBojesen/BA-LSD-CASE3.svg?branch=main)](https://travis-ci.com/SimonBojesen/BA-LSD-CASE3)

## Handover Documentation

Our three separated projects are listed here:

- Contract: [https://github.com/moh682/LSD-Case3](https://github.com/moh682/LSD-Case3)
- Frontend: [https://github.com/grem848/FE-LSD-CASE3](https://github.com/grem848/FE-LSD-CASE3)
- Backend: [https://github.com/SimonBojesen/BA_LSD_CASE3](https://github.com/SimonBojesen/BA_LSD_CASE3)

The contract is what both teams have been using to follow the same structure of classes and methods. The contract is hosted on [packagecloud](https://packagecloud.io/lsdckmwn/lsdcontract) and is used as a dependency in both projects.

### General & How to use

We recommend using the hosted solution, which has a link above.

#### To request access to the Heroku app and view our deployed Heroku setup please contact us, remember you are required to have a Heroku account

If you want to look at the project locally, follow these simple steps:

- Clone the frontend project
- Open your preferred IDE and the project
- Build
- Run
- API is now hosted on localhost:8080

The frontend project is a REST API, that allows for a client to sent requests to it. It was built using Spring Boot, and takes in JSON requests. The backend project takes care of database and business logic, and has setup a RMI service that the frontend can communicate with.

[We have created a Postman Collection, that can be used to send requests and receive responses, join it by clicking here.](https://app.getpostman.com/join-team?invite_code=8d814a09b48d50ff68a6c40616dc665c)

### CI/CD

For continuous integration and continuous deployment, we have used a combination of Travis CI and Digital Ocean Droplet.

We have set it up so Travis builds our project and checks if the build is valid, then if the build is successful Travis will then deploy to our Droplet app.

Travis only deploys if the build is successful.

**[Here is our Travis config:](https://github.com/SimonBojesen/BA-LSD-CASE3/blob/main/.travis.yml)**

```yml
if: branch = main
language: java
addons:
  ssh_known_hosts: 207.154.224.203
jdk: openjdk15
sudo: false
script: mvn test
deploy:
  provider: script
  script: deploy.sh
  on:
    branch: main
before_install:
- openssl aes-256-cbc -K $encrypted_f28073d6fb03_key -iv $encrypted_f28073d6fb03_iv -in travis_rsa.enc -out travis_rsa -d
- chmod 600 travis_rsa
- mv travis_rsa ~/.ssh/id_rsa
```

This makes sure that our main branch builds and then deploys, making it so we only have to worry about coding.

### Branching Strategy

We have used feature branch strategy.

Merging of complete features into main branch was done by pull requests.

### Monitoring & Logging

TBD

### SLA proposition

[View Digital Oceans's Terms of Service Agreement here](https://www.digitalocean.com/legal/terms-of-service-agreement/)

#### Service Description

The service must provide the functionality to see and book cars and manage bookings.
We will make sure of availability, and take responsibility for the service functionality.

#### Uptime/availability (percentage of all time) - 90%

Travis takes care of the deployment of our application, so any issues with their services is not our responsibility, and will require you to contact Travis.  
Digital Ocean hosts the application, so any issues with their services is not our responsibility, and will require you to contact Digital Ocean.

#### Mean response time (average time to serve answer) - up to 7 business days

If you have questions or need answers on anything concerning the application, contact us and we will return to you within a 7 business days.

#### Mean time to recover ( time to recover after outage) - up to 7 business days

During an outage or an error with the application, you are welcome to contact us, we will respond within 7 business days. For Digital Ocean or Travis issues contact them.

#### Failure frequency (number of failures/ timeouts over time) - 1-5 failures/week

If you experience a higher failure frequency, please contact us and we will return to you within 7 business days. For Digital Ocean or Travis issues contact them.

<br>

### Contact us

If you have any questions feel free to contact us at cph-sb339@cphbusiness.dk, and we will return to you within 7 business days.

###### Fixes, changes and any reports or documentation will ONLY be provided upon contact with our support center.