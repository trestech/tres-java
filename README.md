# Tres Java Client

## Getting Started

Clone project:

```bash
git clone https://github.com/trestech/tres-java.git
cd tres-java
```

## Usage

The following example will show the version of the API server.  Note: you will need to replace "username", "password" and "PCC" with
correct login credentials.

```java
  TresContext context = new TresContext();
  context.login("username", "password", "PCC");
  System.out.println("Version: " + context.version());
  context.close();
```

Or with self-closing pattern:

```java
  new TresContext("username", "password", "PCC", (context) -> {
    System.out.println("Version: " + context.version());
  });
```

Or using try-with-resources pattern (which is also self-closing):

```java
  try ( TresContext context = new TresContext("username", "password", "PCC" ) ) {
    System.out.println("Version: " + context.version());
  }
```

Or using try-with-resources + batch:

```java
  try ( TresContext context = new TresContext("username", "password", "PCC" ) ) {
    context.batch(ctx -> {
      JsonNode profiles = ctx.post("ProfileSearch", profileParams);
      JsonNode trips = ctx.post("TripSearch", tripParams);
      
      .
      .
      .
    });
  }
```

## Command-line Interface

If you have `tres-java.jar` and a `lib` folder, you can run the following command:

```bash
export TRES_URL=https://api-dev.trestechnologies.com/
export TRES_USERNAME=username
export TRES_PASSWORD=password
export TRES_DOMAIN=domain
java -jar tres-java.jar <method>
```

Otherwise, the following command will work from the root of this project:

```bash
export TRES_URL=https://api-dev.trestechnologies.com/
export TRES_USERNAME=username
export TRES_PASSWORD=password
export TRES_DOMAIN=domain
./bin/tres.sh <method>
```

Or, using an existing token:

```bash
export TRES_URL=https://api-dev.trestechnologies.com/
export TRES_TOKEN=...
./bin/tres.sh <method>
````

Example method call (assumes exports):

```bash
mvn package
./bin/tres.sh Version
cat support/ProfileSearchParams.json | ./bin/tres.sh ProfileSearch
cat support/TripSearchParams.json | ./bin/tres.sh TripSearch
```

## Test and Build

Check for outdated dependencies:

```bash
mvn versions:display-dependency-updates
```

Build:

```bash
mvn package
```

Tests:

```bash
mvn test
```

