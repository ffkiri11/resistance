#!/bin/bash

result() {
	local error=$?
	if [[ $error == 0 ]]; then
		echo $1.
	elif [[ $error == 1 ]]; then
		echo $1e
	else
		echo $1F
	fi
}

raise()  {
	echo $1 1>&2
	exit 1
}

selfTest() {
	result 'Selftest is OK.'
}

# Test optionless usage
testUsage() {
	echo Usage..
	local output=$(mktemp)
	./resistance 2> $output
	[[ $? -eq 1 ]] || raise 'Abnormal exit code'
	grep -i usage $output > /dev/null
	result 'Usage is OK.'
}

# Test 2 significant multiplied
test2sigmul() {
	local output=$(./resistance -f2s orange black brown)
	local r="3.000e+02"
	local s="6.000e+01"
	local p="Resistance: $r"$'\t'"Sigma: $s"$'\t'
	[[ $output == $p ]]
	result 'Two significant and multiplier value is OK.'
}


selfTest

testUsage

test2sigmul

