#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "resistance.h"

static const char *colors[] = {BAND};
static const unsigned int colors_index[] = {BAND_INDEX};
static size_t bandc = sizeof colors / sizeof colors[0];
static void usage();


static int
_keycmp(const char **s1, const char **s2)
{
  return strcmp(*s1, *s2);
}

/*
 * Returns iterator to the ring's color number in the details table
 */
static const char **
find_ring(const char *key)
{
  return (const char**)bsearch(&key, colors, bandc, sizeof *colors,
	(int (*)(const void *, const void *)) _keycmp);
}

/*
 * Returns resistance digit from color code
 */
static int
get_resistance_digit(const char *s)
{
	const char **ring = find_ring(s);
	if (ring == NULL) {
		printf("Band \"%s\" is not found\n", s);
		usage();
	}
        else
		return  -FIRST_SIGNIFICANT_INDEX 
			+ (int)colors_index[find_ring(s) - colors];
}

/*
 * Parse colors vector, returning float resistance in ohms
 */
double
pcolors(const char * const* value)
{
  int base = 0, pw = 0;
  while (strcmp(*value, "-m")) {
	base *= 10;
	base += get_resistance_digit(*value);
	++value;
  }

  value++;
  pw = get_resistance_digit(*(value++));
  return base * pow((double)10, (double)pw);
}

/*
 * Process colors vector printing resistance value
 */	
void 
process_bands(const char **colors)
{
  printf("Resistance: %.3e\n", pcolors(colors));
}

/*
 * Print program usage reference
 */
static void
usage()
{
  fprintf(stderr,
	"Usage: resistance BAND BAND [BAND ...] -m BAND\n"
	"Converts EIA color code to resistance value.\n");
  exit(EXIT_FAILURE);
}

/*
 * Program entry point
 */
int
main(const int argc, const char *argv[])
{
  argv++;
  if (argc > 4)
	  process_bands(argv);
  else 
	  usage();
  return 0;
}

