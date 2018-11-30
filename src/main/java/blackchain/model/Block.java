package blackchain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Block {

	private LocalDateTime timestamp;
	private String data;
	private String hashOfPreviousBlock;
	private String hash;
	private int nonce;

	public Block(String data, String hashOfPreviousBlock) {
		this.data = data;
		this.hashOfPreviousBlock = hashOfPreviousBlock;
		timestamp = LocalDateTime.now();
		hash = HashUtils.createHash(this);
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getData() {
		return data;
	}

	public String getHashOfPreviousBlock() {
		return hashOfPreviousBlock;
	}

	public String getHash() {
		return hash;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(hash);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Block)) return false;

		return ((Block) obj).hash.equals(hash);
	}

	@Override
	public String toString() {
		return "Block{" +
				"timestamp=" + timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
				", data='" + data + '\'' +
				", hashOfPreviousBlock='" + hashOfPreviousBlock + '\'' +
				", hash='" + hash + '\'' +
				'}';
	}

	public void mineBlock(int difficulty) {
		String target = "0".repeat(difficulty); //Create a string with difficulty * "0"
		while(!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = HashUtils.createHash(this);
		}
		System.out.println("Block mined: " + hash);
	}

	public int getNonce() {
		return nonce;
	}
}
