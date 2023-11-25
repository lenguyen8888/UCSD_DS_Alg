#!/bin/bash

# Directory where to search files. Replace with the desired path.
SEARCH_DIR="."

# Use find to locate .py, .java, and .cpp files and loop through them
find "$SEARCH_DIR" -type f \( -name "*.py" -o -name "*.java" -o -name "*.cpp" \) | while read file; do
    # Determine the new file name by adding .orig before the file extension
    new_file="${file%.*}.orig.${file##*.}"
    echo "Done with $file"
    # Copy the original file to the new file
    cp "$file" "$new_file"
done

echo "Files have been copied."

