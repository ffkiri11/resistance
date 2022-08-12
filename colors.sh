#!/bin/bash

# Resistor colour code

## Ring colour table:
cat > rings.tsv << \\EOT
		Value,				Temperature
Colour	Code	Power of 10	Tolerance %	coefficient
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

## Lets make all necessary headers:
body() {
	tail -n+3 rings.tsv
}

## Joins string with separator
values_list() {
	local acc=""
	local sep=$1
	local lines=$( cat )
	for f in $lines; do
		test -n "$acc" && acc+=$sep$f || acc=$f;
	done
	echo $acc
}

# Band is vocabular of input values
body | cut -f1 | nl -v0 > acc
body | cut -f2 | nl -v0 >> acc
sort -k2 acc > band.tsv
rm acc
echo '#'define BAND \"$(cat band.tsv | cut -f2 | values_list '", "')\"
echo '#'define BAND_INDEX $(cat band.tsv | cut -f1 | values_list ', ')
echo '#'define NO_RING_TOLERANCE 200
echo '#'define FIRST_SIGNIFICANT_INDEX 3

