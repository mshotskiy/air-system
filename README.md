# air-system

To run a project in docker, first you need to create a test database for the project to compile.

For example, this can be done with the command
> docker run --name airsys-postgres --volume /media/windows-share/docker-sonar-volume/var/lib/mysql/data -e  MYSQL_ROOT_PASSWORD=qqq -e MYSQL_DATABASE=airsystem -e MYSQL_USER=root -p 3308:3306 mysql:5.7

Then build the project.
For example, this can be done from the root of the project by running the command(you need to have a maven installed)
> mvn package

After that, the compiled .jar file you should place in the directory
> air-system/docker/services/app/

By default, database tables and files are created. The dump is located in
> air-system/docker/services/mysql/dump.sql

If you don't need it, delete it and the line from the file
> air-system/docker/services/mysql/Dockerfile.development | COPY dump.sql /docker-entrypoint-initdb.d

To run the docker container go to the directory
>air-system/docker/

And use command 
>docker-compose up

Then the project starts to run
(During startup, there may be exceptions for connecting to the database, this is normal, wait until the database is created and everything will work)


