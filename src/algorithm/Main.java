package algorithm;

public class Main {

	public static void main(String[] args) {
		final int maxGen = 100;
		Generation geneticAlgorithm = new Generation(maxGen);
		geneticAlgorithm.algorithm();
	}
}