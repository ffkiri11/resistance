#!/bin/bash

# Resistor colour code

## Ring colour table:
TABLES_FIRST_SIGNIFICAT_VALUE=3

cat > rings.tsv << \\EOT
Colour	Code	Power of 10	Tolerance %	Temperature coefficient
pink	pk	-3		-		-
silver	sr	-2		10		-
gold	gd	-1		5		-
black	bk	0		-		250
brown	bn	1		1		100
red	rd	2		2		50
orange	og	3		0.05		15
yellow	ye	4		0.02		25
green	gn	5		0.5		20
blue	bu	6		0.25		10
violet	vt	7		0.1		5
grey	gy	8		0.01		1
white	wh	9		-		-
\EOT

## Lets make all necessary header:
COLORS=$(tail -n+2 rings.tsv | cut -f1)
COLOR_CODES=$(tail -n+2 rings.tsv | cut -f2)

## Makes quoted comma separated list from array
values_list() {
        acc=""
	for f in $@; do 
		test "$acc" && acc+=", "\"$f\" || acc=\"$f\";
	done
	echo $acc
}

colors_values=$(values_list $COLORS)
echo '#'define COLORS {$colors_values}

color_codes=$(values_list $COLOR_CODES)
echo '#'define COLOR_CODES {$color_codes}

