#!/bin/bash
java -classpath ./jooq-3.10.6.jar:./jooq-meta-3.10.6.jar:./jooq-codegen-3.10.6.jar:./mysql-connector-java-6.0.6.jar:. org.jooq.util.GenerationTool library.xml
