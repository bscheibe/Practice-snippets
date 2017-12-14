#include <stdio.h>

void main() {// we input numbers and output it squared unless zero.
	int in = 1;
	while (in) {
		printf("Please enter a number: ");
		scanf("%d", &in);
		if (in) 
			printf("%d squared is %d\n", in, in * in);
	}
	printf("All done!\n");
}
