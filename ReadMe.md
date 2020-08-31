# Jankins Watch
This proect is a companion project to [Jankins] (https://github.com/200803-java-devops/project-1Reese).

This project encapsulates the functionallity of watching a local git repo and making http calls to locally hosted server.
The entirty of the projects user stories can be found in the other repository with a more complete explanation.

# Usage 
## RUN
### Maven: (From project Repo)
```
mvn:exec -Dexec.args="<Path to Git REPO>" 
```
### Jar: (From project Repo)
```mvn package;
java -jar target/local_repo_watcher-1.0-SNAPSHOT.jar <Path to Git Rep>
```
### Script: (From Any Where)
- Edit the jankins file to

```
java -jar <AbsolutePath>/local_repo_watcher-1.0-SNAPSHOT.jar $1
```
- On linux move file into /bin/bash directory
- On Windows (with git bash installed) move file to C:\Program Files\Git\usr\bin directory
-Run with command
```
jankins <Path to Git REPO>
```
    
 - or skip the work and run with the following command from the Project repository
 ```
 bash jankins <Path to Git REPO>
 ```