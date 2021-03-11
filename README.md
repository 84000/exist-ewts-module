#  eXist-db compatible EWTS (Extended Wylie transliteration) library module

[![Java 8](https://img.shields.io/badge/java-8+-blue.svg)](https://adoptopenjdk.net/)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is an eXist-db compatible XQuery Library Module written in Java and delivered as an EXPath Package using Maven.

It adds XQuery function wrappers for the [Ewts Converter](https://github.com/buda-base/ewts-converter).

## XQuery Functions

* Module Class: `org.tbrc.xquery.extensions.EwtsToUniModule`
* Namespace: `http://tbrc.org/xquery/ewts2unicode`
* Prefix: `ewts`

----

1. `ewts:toUnicode($wylie-string?) as xs:string?`

**Example Use**:
```xquery
import module namespace ewts = "http://tbrc.org/xquery/ewts2unicode";

ewts:toUnicode("sems can thams cad")
```

2. `ewts:toWylie($unicode-string as xs:string?) as xs:string?`

**Example Use**:
```xquery
import module namespace ewts = "http://tbrc.org/xquery/ewts2unicode";

ewts:toWylie("སེམས་ཅན་ཐམས་ཅད")
```


## Building the EXPath Library Package

* Requirements: Java 8, Apache Maven 3.3+, Git.

If you want to create an EXPath Package for the app, you can run:

```bash
$ mvn package
```

There will be a `.xar` file in the `target/` sub-folder that can be deployed to FusionDB, Elemental, or eXist-db via it's Package Manager.


## Publishing the EXPath Library Package

You can use the Maven Release plugin to publish your applications **publicly** to Maven Central.

1. You need to register to manage the `groupId` of your organisation on Maven Central, see: http://central.sonatype.org/pages/ossrh-guide.html#create-a-ticket-with-sonatype

2. Assuming your Git repo is in-sync, you can simply run the following to upload to Sonatype OSS:

```bash
$ mvn release:prepare
$ mvn release:perform
```

3. You need to release the artifacts on the Sonatype OSS web portal, see: http://central.sonatype.org/pages/ossrh-guide.html#releasing-to-central
