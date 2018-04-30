# Gitlab Adapter Microservice

This projects is part of the CME environment and aims at delivering of files from secured gitlab domains.
It uses specific users to connect with gitlab hosts.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to run the project

```
* IDE (Eclipse / Intellij IDEA etc.)
* Maven
* Java 8 SDK
* .env file (src/main/resources) which contains your environment variables
```

### Environment parameters

For successful running of the microservice you need to provide following environment parameters:

```
gitlab_personal_access_token=token-a,token-b
gitlab_hosts=mygitlab-a.com,theirgitlab-b.com
token_scope=aws.bla.cme-workbench
```

## Running the tests

```
mvn test
```
or
```
by running the test files directly
```

## Deployment

The gitlab-adapter microservice is deployed on k8s and provides two REST API paths

```
GET /gitlab-adapter/<microservice-name>/content/<file-name-root-level>

GET /gitlab-adapter/<microservice-name>/metadata/<file-name-root-level>
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://hub.docker.com/) - Used to create a docker container

## Authors

* **Paul Krugel** - *Initial work*