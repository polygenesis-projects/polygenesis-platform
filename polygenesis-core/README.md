# PolyGenesis Core

**WORK IN PROGRESS.**

## Data Types

Every programming language provides a set of **primitive** data types (i.e. int, char etc.). 
Many languages also provide **object** data types (i.e. DateTime, Map, Collection etc.). 
Additionally, developers can define their own **custom** data types (i.e. PostalAddress value object etc.) 

These data types are used as:

* Function Arguments
* Function Return Value
* Object Properties
* Local & Global Variables and Constants

In PolyGenesis Core Context, all the above are called: **Data Types**. 
When a **Data Type** is combined with a Variable/Constant name is called **DataObject**

Example:

```java
int add(int a, int b) { 
  int c = a + b;
  return c;
}
``` 

We have:
* DataModel(int, a) -> The argument a
* DataModel(int, b) -> The argument b
* DataModel(int, c) -> The local variable c
* DataModel(int, response) -> The return value



## References
https://en.wikibooks.org/wiki/Java_Programming/Primitive_Types
https://en.wikipedia.org/wiki/Primitive_data_type
https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html

