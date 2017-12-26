#!/bin/bash

echo $1

full=$1
filename="${full%.*}"
nasm -f elf $1 && gcc -m32 -o $filename "$filename.o"
