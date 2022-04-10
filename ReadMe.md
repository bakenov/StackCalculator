#Simple Stack machine with arithmetic operations

Simple Stack machine with arithmetic operations is presented.

I did not use Java standard stack implementation to fulfil the test tasks.
The custom stack with UNDO command is implemented.

The implemented stack is not generic but works with numerics such as doubles. 
The reason for this is to avoid boxing and unboxing double to/from Double objects. 
It is assumed, for demonstration purpose, that the stack works in low latency environment.
To reduce pressure on Garbage Collection, I reuse command object.

The underlying structure is array of doubles, but not a linked list. 
The use of array allows more efficient implementation of the UNDO operation 
(use of system array copy versus list iteration). 

The UI, arithmetic service and stack structure are separated and interact over correspondent interfaces.

The project has some limitations due to time constrain

* Command line UI
* Simple logging to standard output.
* No javadocs (code is simple with self explainable variable and method names according "clear code" methodology)
* Test coverage is limited. Just cover main functionalities in key classes.

###The project addressed following tasks:

* Interaction with user over simple UI
* Commands for the arithmetic service can be applied using UI
* Implemented service commands are:
    - PUSH
    - POP
    - CLEAR
    - ADD
    - MUL
    - NEG
    - INV
    - UNDO
    - PRINT
    - QUIT

### Development environment
* MacOS
* Java 11
* Apache Maven 3.6.3

### Build and Run the program
The project can be built using maven command:
mvn clean install
To run the program you can use command:
java -jar target/calculator-jar.jar

