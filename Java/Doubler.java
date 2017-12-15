public class Doubler {

	public static void main(String[] args) {

		try {
			int temp = Integer.parseInt(args[0]) * 2;
			System.out.println(temp);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Give an input, please!");
		}
	}
}
