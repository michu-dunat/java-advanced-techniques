# Java Advanced Techniques

## Lab01

An application that allows you to check the indicated directories for changes in the files they contain.

The application calculates the MD5 hash for each examined file in two steps: 1. preparing a "snapshot" of the current situation and 2. verifying, on the basis of the saved "snapshot", whether any changes have been made to the files.

Application iswritten with the use of modules (introduced in Java since jdk 9). A library module and an application module (using the library module) were created.

The application itself offers a user interface (console).

The java.nio class package is used for file and directory operations.

The application uses a security manager and a policy file.

## Lab02

An application that allows you to view the content of files (.png and .txt) while browsing the directory structure. The graphic interface (Swing) consists of two panels - one for presenting a list of files in a given directory (with the possibility of switching between directories) and the other for presenting the content of a file selected from this list.

The app was designed with weak references. When viewing a list of files in a given directory, the contents of the current file are loaded into a HashMap. Weak reference allows you to bypass the need to load the same content multiple times - which can happen when moving forward and backward through the list of files in a given directory.

The application indicates whether the contents of the file have been reloaded or taken from memory.

## Lab03

An application that allows you to consume data obtained from a website offering a public rest API: https://rapidapi.com/wirefreethought/api/geodb-cities/endpoints.

The application is used to test geographic knowledge. Inquiries and responses are displayed on a graphical interface (JavaFX) that allows you to change language (localization) settings. The supported languages are Polish and English. The implementation is based on bundles (files containing keys and values) and using classes from java.text and java.util packages.

Questions:
- How many cities with a population greater than ... are locaten within a radius of 25km from ...?
- In  how many countries ... is the the official currency?
- What time is it in ...?

The user can parameterize the query (in place of the dots, values from the selection lists or numerical values can be entered) and declare the answer.

The system checks the answers entered by the user and presents the correct answers.

Responses support variant (ChoiceFormat class).

## Lab04

An application that allows you to outsource tasks to dynamically loaded class instances of my own class loader. This task was accomplished using the reflection API.

The application provides a graphical interface (JavaFX) on which you can repeatedly enter the text (data to be processed), select the class to be loaded (which instance will process this text), order a processing task and monitor its progress, display the processing result, and unload the loaded classes.

Text processing classes implement the processing.Processor interface.

3 different word processing classes are provided. These classes are compiled separately from the application itself (the application does not know them when it is built). It is assumed that the bytecode of these classes will be placed in a directory that the application will have access to. The application reads the contents of this directory and loads the found classes with its own loader. It was assumed that while the application is running, it will be possible to "add" new classes to this directory.

The selection of the classes is done through a list displaying the names of the loaded classes. These names are accompanied by descriptions obtained by the getInfo() method of the created instances of these classes.

Tasks are commissioned to class instances through the submitTask(String task, StatusListner sl) method. In this method, StatusListener receives information about the change in the processing status.
The processing.Status class is used to represent the processing status (this class is declared so that its attributes are read-only after the status is created).

The processing is implemented in a lagging thread in order to be able to monitor the current status of the processing (on ProgressBar).

## Lab05

I implemented an application with a graphical interface (JavaFX) that allows you to perform cluster analysis on tabular data.
This application enables:
- adding, deleting and displaying tabular data,
- selecting the algorithm with which they will be processed,
- displaying the processing results.

Implemented algorithms:
- determination of the type of number (real number, complex number or imaginary number),
- definition of a quarter of a point.

During the implementation, I used the Service Provider Interface (SPI).
More precisely, using the SPI approach, I provided the application with the ability to load classes implementing a given interface after the application was build itself.
These classes (with selected cluster analysis algorithms implemented) are provided in .jar files.
Two versions of the project were created: standard and modular (Java 9).
