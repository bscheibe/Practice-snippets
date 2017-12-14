#include <stdio.h>

void main() {
	float dividend, divisor;
	printf("Give a dividend: ");
	scanf("%f", &dividend);
	printf("Give a divisor: ");
	scanf("%f", &divisor);
	if (divisor) {
		printf("%6.2f divided by%6.2f equals%6.2f\n", dividend, 		divisor, dividend / divisor);
	}
	else { printf("Cannot divide by zero!\n"); }
}
