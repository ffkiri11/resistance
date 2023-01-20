#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "resistance.h"

static const char *bandv[] = {BAND};
static const unsigned int bandiv[] = {BAND_INDEX};
static size_t bandc = sizeof bandv / sizeof bandv[0];

static const char *name;
static void usage();

static void setname(const char *s)
{
  name = s;
}

static void fprintvt(FILE *stream, const char *s[])
{
  while (*++s != NULL)
	fprintf(stream, ", %s", *s);
}

static void invalid_argument(const char *opt)
{
  printf("%s: invalid argument value -- ", name);
  printf("'%s'.\nTry '%s -h' for more information.\n", opt, name);
  exit(EXIT_FAILURE);
}

static const int parse_format(const char *opt)
{
  char nadd;

  nadd = *opt++ - '2';
  if (nadd > '\1' || *opt != 's') usage();
  return (int)nadd;
}

static const int parseopt(const char *opt)
{
  if (*opt++ != '-') usage();

  if (*opt == 'h') usage();

  if (*opt++ != 'f') usage();

  return parse_format(opt);
}

static const int parse_tol(const char *to) { return -1; };

static const char *parse_temp(const char *te) { return "-"; };

static int pstrcmp(const char **s1, const char **s2)
{
  return strcmp(*s1, *s2);
}

/*@null@*/ /*@dependent@*/ static const char **parse_band(const char *key)
{
  return bsearch(&key, bandv, bandc, sizeof bandv[0],
	(int (*)(const void *, const void *)) pstrcmp);
}

static int parse_value(const char *s)
{
  /*@dependent@*/ const char **band = parse_band(s);

  if (band == NULL) invalid_argument(s);

  return -FIRST_SIGNIFICANT_INDEX + (int)bandiv[band - bandv];
}

static void process_bands(int nadd, const char *valv[])
{
  int tolerance_permile = NO_RING_TOLERANCE;
  int significant = 0;
  int mul;
  int npos;
  int pw;
  double r;

  /* Initialize calculation */
  mul = 10;
  npos = 2 + nadd;

  /* I don't use floats in this app */
  while (nadd != 0) {
	mul *= 10;
	--nadd;
  }

  /* Accumulate significant part digit by digit */
  while (npos != 0) {
	significant += mul * parse_value(*valv);
	mul /= 10;
	++valv;
	--npos;
  }

  /* Parse ten power */
  if (*valv != NULL)
  	pw = parse_value(*valv++);
  else
	usage();

  /* Print resistance */
  r = significant * pow(10, pw);
  printf("Resistance: %.3e\t", r);

  /* TODO: Parse tolerance */
  if (*valv != NULL)
	tolerance_permile = parse_tol(*valv++);

  printf("Sigma: %.3e\t", r * tolerance_permile / 1000);

  /* TODO: Parse temperature coefficient */
  if (*valv != NULL)
	printf("Temperature coefficient: %s\n",	parse_temp(*valv++));
  else
	printf("\n");

  if (*valv != NULL) invalid_argument(*valv);
}

static void usage()
{
  const char **p;
  fprintf(stderr, "Usage: %s OPTION BAND BAND BAND [BAND...]\n", name);
  fprintf(stderr, "Converts EIA color code to resistance value.\n\n");
  fprintf(stderr, "Options are:\n");
  fprintf(stderr,
	"  -f2s - from two significant bands and multiplier\n");
  fprintf(stderr,
	"  -f3s - from three significant bands and multiplier\n\n");
  fprintf(stderr,
	"Bands shall be space separated, may be written as colors "
	"or codes.\n");
	p = bandv;
  fprintf(stderr, "Colors and color codes are: %s", *p);
  fprintvt(stderr, p);
  fprintf(stderr, ".\n");
  fprintf(stderr, "After values bands could be: ");
  fprintf(stderr, "tolerance band (default is 20%%) ");
  fprintf(stderr, "and temperature coefficient band.\n");
  exit(EXIT_FAILURE);
}

int main(const int argc, const char *argv[])
{
  int nadd;

  setname(*argv++);

  if (argc < 5)
	usage();

  nadd = parseopt(*argv++);

  if ((nadd > 1) || (argc < 5 + nadd))
	usage();
  process_bands(nadd, argv);
  return 0;
}

