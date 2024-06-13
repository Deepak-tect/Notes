# Maven 

Maven is a powerful `build automation and project management tool` primarily used in Java projects. It simplifies the process of managing a project's build, dependencies, documentation, and other tasks by providing a standardized way to handle them.

Here are some key aspects of Maven:

## 1. Project Object Model (POM)

1. `POM File:` The cornerstone of a Maven project is the POM (Project Object Model) file, usually named pom.xml. This XML file contains information about the project and configuration details used by Maven to build the project.
2. `Dependencies:` Within the POM file, you can specify the libraries and frameworks your project depends on. Maven then automatically downloads these dependencies from a central repository and includes them in your project.

## 2. Dependency Management
1. `Central Repository:` Maven uses a central repository (Maven Central) to host and distribute libraries. When you declare a dependency, Maven fetches it from this repository if it's not already available locally.
2. `Transitive Dependencies:` Maven handles transitive dependencies automatically. If your project depends on a library that, in turn, depends on other libraries, Maven will download and include all necessary dependencies.

## 3. Build Lifecycle
1. Phases: Maven defines a standard build lifecycle comprising several phases such as `validate, compile, test, package, verify, install, and deploy.` Each phase represents a stage in the build process.
2. Goals: Within these phases, Maven executes specific tasks called goals. For instance, the compile goal compiles the project's source code, and the test goal runs the unit tests.

## 4. Plugins
1. Plugin Architecture: Maven's functionality is extended through plugins. Plugins are used to perform tasks such as compiling code, running tests, generating documentation, and more.
2. Built-in Plugins: Maven comes with a set of standard plugins (e.g., Compiler Plugin, Surefire Plugin) that cover most common tasks.

## 5. Consistent Project Structure
1. `Standard Directory Layout:` Maven promotes a standardized directory layout, making it easier for developers to understand and navigate a project. For instance, source code is placed in src/main/java, and test code is in src/test/java.

# Maven life cycle:

## 0. validate:
1. `Purpose`: Maven verifies that the project is correctly structured and configured.

2. `Actions`: Project Structure Check: Maven checks that the project follows the standard directory layout and that essential directories (src/main, src/test, etc.) exist.


## 1. Compile
1. `Purpose:` The compile phase compiles the source code of the project.
2. `Actions:` During this phase, Maven compiles all the Java source files (*.java) in the project's src/main/java directory into bytecode (*.class files).
3. `Usage:` This phase ensures that the project's Java source code is syntactically correct and can be transformed into executable bytecode.
## 2. Test
1. `Purpose:` The test phase runs tests against the compiled source code.
2. `Actions:` Maven executes unit tests located in the project's src/test/java directory. It typically uses testing frameworks like JUnit or TestNG to run these tests.
3. `Usage:` This phase ensures that the project behaves as expected by validating its functionality through automated tests. It helps maintain code quality and detect regressions.
## 3. Package
1. `Purpose:` The package phase packages the compiled code into a distributable format.
2. `Actions:` Maven creates an artifact (e.g., JAR, WAR) containing the compiled classes and any additional resources (e.g., configuration files, static content) required for the application.
3. `Usage:` This phase prepares the application for deployment by bundling all necessary files into a single, portable package.
## 4. Verify
1. `Purpose:` The verify phase runs additional checks to verify the integrity and quality of the package.
2. `Actions:` Maven performs various verification tasks, such as checking for coding standards compliance, generating code coverage reports, and running integration tests.
3. `Usage:` This phase ensures that the packaged artifact meets quality standards and is ready for deployment. It provides additional validation beyond unit testing.

## 5. Install
1. `Purpose:` The install phase installs the project's artifact into the local Maven repository.
2. `Actions:` Maven copies the packaged artifact (e.g., JAR, WAR) into the local repository (~/.m2/repository by default).
3. `Usage:` This phase makes the project's artifact available for use as a dependency in other Maven projects on the local machine. It facilitates code reuse and simplifies development.
## 6. Deploy

1. ` Purpose:` The deploy phase deploys the project's artifact to a remote repository for sharing with other developers or projects.

2. `Actions:` Maven uploads the packaged artifact to a remote repository, such as a company-wide repository manager or a public repository like Maven Central.

3. `Usage:` This phase makes the project's artifact accessible to other developers and projects. It enables collaboration and ensures that the artifact is available for use in different environments.


