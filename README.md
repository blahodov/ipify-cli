# ipify-cli

This is a simple command line tool that consumes the API at <https://api.ipify.org/?format=json> and returns just the IP address part of it.

## Build cli
* Install scala-cli https://scala-cli.virtuslab.org/install/
* Run in terminal:  
```scala-cli --power package src/main/scala/IPifyCLI.scala -o ipify-cli```

## Usage
* Run in terminal:  
```./ipify-cli```

## Build docker image
* Run in terminal:  
```scala-cli --power package --docker src/main/scala/IPifyCLI.scala --docker-image-repository docker-ipify-cli```

## Run docker image
* Run in terminal:  
```docker run docker-ipify-cli:latest```
