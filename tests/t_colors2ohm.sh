#! /usr/bin/env atf-sh

atf_test_case nargs
nargs_head()
{
	atf_set "descr" "Test status of the argless call"
}

nargs_body()
{
	atf_check -s not-exit:0 -o empty -e match:Usage \
		-x "../colors2ohm/resistance"
}

atf_test_case 250K
250K_head()
{
	atf_set "descr" "Test conversation of 250K call"
}

250K_body()
{
	atf_check -s exit:0 -o inline:"Resistance: 2.500e+05\n"\
		-x "../colors2ohm/resistance red green -m yellow"
}

atf_init_test_cases()
{
	atf_add_test_case nargs
	atf_add_test_case 250K
}

