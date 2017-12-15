import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

class Horse implements Comparable<Horse> {
	int raceNumber;
	String name;

	Horse(int raceNumber, String name) {
		this.raceNumber = raceNumber;
		this.name = name;
	}

	public static void main(String[] args){
		ArrayList<Horse> race = new ArrayList<Horse>();
		race.add(new Horse(1,"jeff"));
		race.add(new Horse(2, "abby"));
		Collections.sort(race);
//this tests our compareTo method, natural sorting by raceNumber
		System.out.println(race);
//lets java iterate through the list
		
		Iterator<Horse> hit = race.iterator();
		while (hit.hasNext())
			System.out.println(hit.next());
//test our iterator by printing out names
		
		Collections.sort(race, new HorseComparator());
//test our Comparator class sorting by name
		System.out.println(race);
		
	}

	public int compareTo(Horse h) {
		if (raceNumber < h.raceNumber) {
			return -1;
		}
		if (raceNumber > h.raceNumber) {
			return 1;
		}
		else {
			return 0;
		}
	}//defined inside the class

	public String toString(){
		return name;
	}//"does not print" 

	class HorseIterator implements Iterator<Object> {
		private int raceSize = 2;
		public boolean hasNext(){
			boolean holder = raceSize > 0;
			raceSize++;
			return holder;
		}
		
		public Object next(){
			return name;
		}
	}//defined iterator class inside of Horse class
}


class HorseComparator implements Comparator<Horse> {
	public int compare(Horse h1, Horse h2) {
		int value = h2.name.compareTo(h1.name);
		if (value > 0)
			return -1;
		if (value < 0)
			return 1;
		return 0;
	}
}//define Comparator class outside of Horse class




