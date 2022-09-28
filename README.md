# Tres Java Client

## Getting Started

Clone project:

```bash
git clone https://github.com/trestech/tres-java.git
cd tres-java
```

## Usage

```java
  TresContext context = new TresContext();
  context.login("username", "password", "PCC");
  System.out.println("Version: " + context.version());
  context.close();
```

Or with self closing pattern:

```java
  new TresContext("username", "password", "PCC", (context) -> {
    System.out.println("Version: " + context.version());
  });
```

## Test and Build

Tests:

```bash
mvn test
```

Build:

```bash
mvn package
```
