# Java XUnit

A simple testing framework, inspired by the xUnit framework family, for Java. I'm following along with Kent Beck's [Test-Driven Development: By Example](https://www.amazon.com/Test-Driven-Development-Kent-Beck/dp/0321146530) book.

The main goal of this project is to create a testing framework (with a Test-Driven Development approach) and use this framework to test the framework itself (yeap, I know, it's a pretty weird idea).

Currently, the framework can run tests and report the results. It also supports `setUp` and `tearDown` methods.

## Implemented (and tested!) requirements

- [x] Invokes the test method
- [x] Invokes the setup method first
- [x] Invokes the teardown method last
- [x] Invokes tear down even if the test method fails
- [x] Run multiple tests
- [x] Reports test results
- [x] Catches and reports errors during setup
