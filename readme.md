### Build and package
```
gradle clean build jar
```

### Build docker image
```
sudo docker build -t google-calendar-demo .
```
### Run container
```
sudo docker run -p 8080:8080 --name=google-calendar-demo google-calendar-demo:latest
```
