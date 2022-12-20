# GraphSearch2022 (Εργασια 3)

Υποχρεωτική εργασία 3 του μαθήματος αλγόριθμοι και δομές δεδομένων.

## How to run in terminal

Για να δουλέψει χρειάζεται να κατεβάσεται το [gradle](https://gradle.org/install/).

Αφού κάνεται clone το repo τρέχουμε μεσα στο φάκελο:

```bash
gradle wrapper
```

Για να το τρέξεται χρησιμοποιούμε για linux/mac:

```bash
./gradlew run
```

και για windows:

```bash
gradlew run
```

Για να χρησιμοποιήσουμε arguments βάζουμε το flag:

```bash
--args="<arguments-go-here>"
```

Παράδειγμα:

```bash
./gradlew run --args="true euclid.ee.duth.gr 4475 0 100 123"
```
