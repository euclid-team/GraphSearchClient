# GraphSearchClient (Εργασια 3)

Υποχρεωτική εργασία 3 του μαθήματος Αλγόριθμοι και Δομές Δεδομένων.

## How to run in terminal

Για να δουλέψει χρειάζετε να κατεβάσετε το [gradle](https://gradle.org/install/).

Αφού κάνετε clone το repo τρέξτε μεσα στο φάκελο:

```bash
gradle wrapper
```

Για να το τρέξετε σε Linux/Mac:

```bash
./gradlew run
```

ενώ για windows:

```bash
gradlew run
```

Για να χρησιμοποιήσετε arguments προσθέστε το παρακάτω flag:

```bash
--args="<arguments-go-here>"
```

Παράδειγμα:

```bash
./gradlew run --args="true euclid.ee.duth.gr 4475 0 100 123"
```
