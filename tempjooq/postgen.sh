#!/bin/bash
java -classpath ./jooq-3.10.6.jar:./jooq-meta-3.10.6.jar:./jooq-codegen-3.10.6.jar:./postgresql-42.2.2.jar:. org.jooq.util.GenerationTool post-library.xml
