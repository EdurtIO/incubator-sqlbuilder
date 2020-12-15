# SqlBuilder

SqlBuilder is a sql auto builder tools!

### How to compile?

---

use shell script:

```shell
./mvnw clean install package
```

if you skip test, please run:

```shell
./mvnw clean install package -DskipTests
```

check that the code is formatted correctly, please run:

```shell
./mvnw clean checkstyle:check -DskipTests
```

check the code for code-level bugs, please run:

```shell
./mvnw clean findbugs:check -DskipTests
```
