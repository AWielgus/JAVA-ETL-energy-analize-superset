# etl-java


Temp space holder

When DB is full of data, we can proceed forward to Apache Superset.
    
In terminal we need to pull Superset docker image
``docker pull apache/superset``

Now we need to start container

`docker run -d -p 8080:8088 --name superset apache/superset`

and initialize it

`docker exec -it superset superset fab create-admin \
--username admin \
--firstname Superset \
--lastname Admin \
--email admin@superset.com \
--password admin`
