#!/bin/bash

# Copy .py files
find . -type f -name "*.py" -exec cp {} {}.orig.py \;

# Copy .java files
find . -type f -name "*.java" -exec cp {} {}.orig.java \;
