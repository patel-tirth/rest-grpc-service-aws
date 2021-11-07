# rest-grpc-service-aws
#### Tirth Patel

### Designing and implementing a RESTful service and a lambda function that are accessed from clients using gRPC.

#### To run the program for GRPC:

``` sbt clean compile "run grpc" ```

#### You will be prompted to enter a time to search for existence of logs in a log file that is stored in AWS S3 bucket. 
Enter time in format: HH:MM::SS.sss

#### Next, you will be prompted to enter a time delta.
Enter a number (minutes). (This is to search for logs in range of provided minutes)

#### Corresponding results will be displyed to console.


#### To run the program for REST

``` sbt clean compile "run rest"```

Follow the same steps as above to invoke a lambda function on aws using rest api. 

#### GRPC and AKKA HTTP code outline from:
https://github.com/xuwei-k/grpc-scala-sample/tree/master/grpc-scala/src/main/scala/io/grpc/examples/helloworld 

https://doc.akka.io/docs/akka-http/current/client-side/request-level.html#future-based-variant

## Functionality
Your homework assignment consists of two interlocked parts: first, create a client program that uses gRPC to invoke a lambda function deployed on AWS to determine if the desired timestamp is in the log file and second, to  a client program and the corresponding lambda function that use the REST methods (e.g., GET or POST) to interact. While you are free to determine how your lambda function works, there is a small subset of mandatory functionality that you must implement: given the input of time stamp and time interval determine if the log files in some bucket that are being constantly updated with new log messages contain messages in the given time interval from the designated input time stamp and to return a MD5-generated hash code from these messages or some 400-level HTTP client response to designate that log files do not contain any messages in the given time interval.

You will deploy an instance of the log file generation program on EC2 and configure it to run for some period of time producing and storing log messages into log file in some S3-allocated storage. If you need to modify the generator for this purpose please go ahead and fork the repo and make appropriate changes. You can create additional projects for the RESTful service and its client and extend the sbt script to include these subprojects as dependencies.
