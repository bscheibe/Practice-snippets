#from module import * 
#	(imports all names that a module defines)
def double_fun(n):
	temp = n * n
	print temp
	return temp

if __name__ == "__main__":
	import sys
	double_fun(int(sys.argv[1]))
