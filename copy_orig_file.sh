#!/bin/bash

# write a for loop to use find and copy sample.(java|py|cpp) to sample.(java|py|cpp).orig.(java|py|cpp)
for ext in py cpp java; do
    echo "Copying $ext files"
    find . -type f -name "*.$ext" -exec cp {} {}.orig.$ext \;
done
