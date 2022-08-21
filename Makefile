resistance: resistance.c resistance.h
	cc -g -Wall -lm -o resistance resistance.c

resistance.h: colors.sh
	./colors.sh > resistance.h

clean:
	rm resistance; rm *.tsv; rm resistance.h

test: resistance
	./test_resistance.sh && echo . || echo F
install:
	install resistance /usr/bin

