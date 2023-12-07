# GSTBill
JUnit- Java Unit Testing

What is JUnit? JUnit is a unit testing open-source framework for the Java programming language. Java Developers use this framework to write and execute automated tests. In Java, there are test cases that have to be re-executed every time a new code is added.

JUnit- Java Framework for write test cases to test functionality/methods

Why JUnit Test Cases:

To find bugs early in the development phase, which increases the code’s reliability
The framework enables the developer to invest more time in reading the code than writing it JUnitFramework
This makes the code more readable, reliable, and bug-free
It boosts the confidence of the developer and motivates them immensely
Mocking- Mocking is a process of developing the objects that act as the mock or clone of the real objects. In other words, mocking is a testing technique where mock objects are used instead of real objects for testing purposes. Mock objects provide a dummy output for a dummy input passed to it.

Mockito Framework- Mockito is a Java-based mocking framework used for unit testing of Java application. Mockito plays a crucial role in developing testable applications.

@SpringBootTest- It consider class as Test class

@RunWith(SpringRunner.class)- A JUnit Runner is class that extends JUnit's abstract Runner class. Runners are used for running test classes. The Runner that should be used to run a test can be set using the @RunWith annotation.

when() method- It enables stubbing methods. It should be used when we want to mock to return specific values when particular methods are called. In simple terms, "When the getAllData() method is called, then return actual values." It is mostly used when there is some condition to execute.

verify() method- The verify() method is used to check whether some specified methods are called or not. In simple terms, it validates the certain behavior that happened once in a test.

times() method- It is used to verify the exact number of method invocations, which means it declares how many times a method is invoked.

this file contain GST Billing Assessment