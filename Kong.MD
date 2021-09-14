# Configuring MEDIator with a Kong API Gateway

Assuming you have a Kong instance running in http://xxx.yy.zz.222:8000/, let's look into how to deploy MEDIator in another VM xxx.yy.zz.223 and front it with MEDIator.

## Configure a Kong service 
```
$ curl -i -X POST --url http://localhost:8001/services/ --data 'name=mediator' --data 'url=http://xxx.yy.zz.223:8040/'
```
HTTP/1.1 201 Created
Date: Tue, 14 Sep 2021 13:56:16 GMT
Content-Type: application/json; charset=utf-8
Connection: keep-alive
Access-Control-Allow-Origin: *
Server: kong/0.14.0
Content-Length: 256

{"host":"xxx.yy.zz.223","created_at":1631627776,"connect_timeout":60000,"id":"c66b86a7-994f-41ee-897e-a506e79672e6","protocol":"http","name":"mediator","read_timeout":60000,"port":8040,"path":"\/","updated_at":1631627776,"retries":5,"write_timeout":60000}

## Configure route to the service
```
$ curl -i -X POST --url http://localhost:8001/services/mediator/routes --data 'paths=/mediator'
```
HTTP/1.1 201 Created
Date: Tue, 14 Sep 2021 13:56:40 GMT
Content-Type: application/json; charset=utf-8
Connection: keep-alive
Access-Control-Allow-Origin: *
Server: kong/0.14.0
Content-Length: 289

{"created_at":1631627800,"strip_path":true,"hosts":null,"preserve_host":false,"regex_priority":0,"updated_at":1631627800,"paths":["\/mediator"],"service":{"id":"c66b86a7-994f-41ee-897e-a506e79672e6"},"methods":null,"protocols":["http","https"],"id":"5b50e9de-3828-4bf4-941b-4edcb1eb68d1"}

## Access via browser

[http://xxx.yy.zz.222:8000/mediator]
