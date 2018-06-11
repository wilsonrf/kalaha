# Kalaha

[![CircleCI](https://circleci.com/gh/wilsonrf/kalaha/tree/master.svg?style=svg)](https://circleci.com/gh/wilsonrf/kalaha/tree/master)

## Running localhost, using a local mongodb
To run localhost you will need a MongoDB running on port 27017.

### Running a MongoDB docker container

To run a MongoDB Container on your machine, execute the command bellow:

```
docker run --name mongo -d -p 27017:27017 mongo
```

### Running the application localhost

And then, run the application with the command bellow, at the application folder.

```
./gradlew bootRun
```