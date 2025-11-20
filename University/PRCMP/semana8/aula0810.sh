#!/bin/bash

# Count files
file_count=$(find . -maxdepth 1 -type f | wc -l)

# Count directories (excluding . itself)
dir_count=$(find . -maxdepth 1 -type d ! -name . | wc -l)

echo "Files: $file_count"
echo "Directories: $dir_count"
echo

