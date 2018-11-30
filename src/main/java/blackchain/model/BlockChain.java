package blackchain.model;

import java.util.Iterator;
import java.util.LinkedList;

public class BlockChain {

	private LinkedList<Block> blockChain = new LinkedList<>();
	private int difficulty;

	public BlockChain(int difficulty) {
		this.difficulty = difficulty;
	}

	public void addBlock(String data) {
		Block last = blockChain.peekLast();
		String hash = last == null ? Integer.toString(0) : last.getHash();
		Block b1 = new Block(data, hash);
		b1.mineBlock(difficulty);
		blockChain.add(b1);
	}

	public boolean checkIntegrity() {
		Iterator<Block> iter = blockChain.iterator();
		Block toBeChecked = iter.next();

		System.out.println("Checking block: 1");

		if (!toBeChecked.getHashOfPreviousBlock().equals("0")) {
			System.out.println("First block hash isn't 0!");
			return false;
		}
		if (!toBeChecked.getHash().equals(HashUtils.createHash(toBeChecked))){
			System.out.println("Hash: " + toBeChecked.getHash());
			System.out.println("Generated hash: " + HashUtils.createHash(toBeChecked));
			System.out.println("First hash isn't valid!");
			return false;
		}

		Block previous = toBeChecked;
		int counter = 2;
		while (iter.hasNext()) {
			System.out.println("Checking block: " + counter++);
			toBeChecked = iter.next();
			if (!areCorrectBlocks(previous, toBeChecked)){
				System.out.println("Hash of: " + System.lineSeparator() + previous);
				System.out.println("and " + System.lineSeparator() + toBeChecked);
				System.out.println("aren't valid!");
				return false;
			}
			previous = toBeChecked;
		}
		return true;
	}

	private boolean areCorrectBlocks(Block previous, Block toBeChecked) {
		if (!HashUtils.createHash(previous).equals(toBeChecked.getHashOfPreviousBlock())) return false;
		return previous.getHash().equals(toBeChecked.getHashOfPreviousBlock());
	}

	public void printBlocks() {
		System.out.println("Blocks: " + blockChain.size());
		for (Block block: blockChain) {
			System.out.println(block);
		}
	}
}
