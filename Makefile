resistance : resistance.c resistance.h 
	$(CC) resistance.c -o resistance -l

install : resistance
	install resistance /usr/bin

