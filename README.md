# Loom playground (Kotlin)
This project is used for experimenting with project loom and contains several tests that look into certain behaviors. You can run with maven but it will be more beneficial to run it from the IDE. In order for a test to run you need to add command line parameters.
* ***--enable-preview*** for all tests 
* ***--add-modules jdk.incubator.concurrent*** for structured concurrency tests 
* ***--add-exports java.base/jdk.internal.vm=ALL-UNNAMED*** for tests which mess with continutations (internal APIs)
