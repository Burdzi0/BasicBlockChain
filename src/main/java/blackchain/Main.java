package blackchain;

import blackchain.model.BlockChain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("1st argument -> random seed");
			System.out.println("2nd argument -> mining difficulty");
			System.out.println("3rd argument -> number of blocks");
			System.exit(1);
		}
		int seed = Integer.parseInt(args[0]);
		int difficulty = Integer.parseInt(args[1]);
		int blocks = Integer.parseInt(args[2]);

		var start = LocalDateTime.now();

		BlockChain blockChain = new BlockChain(difficulty);
		Random random = new Random(seed);
		for (int i = 0; i < blocks; i++) {
			System.out.print("Mining block: " + i + " ");
			blockChain.addBlock(generateRandomString(random));
		}

		blockChain.printBlocks();

		var end = LocalDateTime.now();

		System.out.println("Is blockchain valid?: " + blockChain.checkIntegrity());

		System.out.format("Duration: %d seconds\n", Duration.between(start, end).getSeconds());
	}

	private static String generateRandomString(Random random ) {
		int textLength = random.nextInt(10);
		StringBuilder builder = new StringBuilder(textLength);
		for (int i = 0; i < textLength; i++) {
			builder.append((char) (65 + random.nextInt(57)));
		}
		return builder.toString();
	}
}
