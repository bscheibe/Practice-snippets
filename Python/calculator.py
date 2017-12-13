from Tkinter import *

top = Tk()
readout = Text(top, height = 1, width = 24)
answer = StringVar()
prevalue = IntVar()
anslabel = Label(top, textvariable = answer, relief = SUNKEN)
prelabel = Label(top, textvariable = prevalue, relief = SUNKEN)
answer.set("0")

def solve():
	answer.set(prevalue.get() + int(readout.get("1.0",'end-1c')))

def clear():
	readout.delete("1.0",END)
	answer.set("0")
	prevalue.set(0)

def add():
	prevalue.set(int(readout.get("1.0",'end-1c')))
	readout.delete("1.0",END)

def pressDigit(n):
	readout.insert(INSERT,n)


zero = Button(top, text = "0", command = lambda: pressDigit(0))
one = Button(top, text = "1", command = lambda: pressDigit(1))
two = Button(top, text = "2", command = lambda: pressDigit(2))
three = Button(top, text = "3", command = lambda: pressDigit(3))
four = Button(top, text = "4", command = lambda: pressDigit(4))
five = Button(top, text = "5", command = lambda: pressDigit(5))
six = Button(top, text = "6", command = lambda: pressDigit(6))
seven = Button(top, text = "7", command = lambda: pressDigit(7))
eight = Button(top, text = "8", command = lambda: pressDigit(8))
nine = Button(top, text = "9", command = lambda: pressDigit(9))

addB = Button(top, text = "+", command = lambda: add())
solveB = Button(top, text = "=", command = lambda: solve())
clearB = Button(top, text = "clear", command = lambda: clear())

readout.grid()
prelabel.grid()
anslabel.grid()
zero.grid()
one.grid()
two.grid()
three.grid()
four.grid()
five.grid()
six.grid()
seven.grid()
eight.grid()
nine.grid()
addB.grid()
solveB.grid()
clearB.grid()

top.mainloop()
