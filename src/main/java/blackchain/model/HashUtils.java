package blackchain.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class HashUtils {

	public static String SHA256 = "SHA-256";

	private static byte[] getSha256(byte[] data) {
		var digest = getDigest(SHA256)
				.orElseThrow(IllegalStateException::new);

		return digest.digest(new String(data, StandardCharsets.UTF_8).getBytes());
	}

	private static Optional<MessageDigest> getDigest(String algorithm) {
		Optional<MessageDigest> digest = Optional.empty();
		try {
			digest =  Optional.ofNullable(MessageDigest.getInstance(algorithm));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Algorithm " + algorithm + " does not exist!");
			e.printStackTrace();
		}
		return digest;
	}

	public static String createHash(Block block) {
		String hashSeed = block.getHashOfPreviousBlock().substring(0, block.getHashOfPreviousBlock().length()/2);
		hashSeed += block.getData();
		hashSeed += block.getHashOfPreviousBlock().substring(block.getHashOfPreviousBlock().length() / 2 + 1);
		hashSeed += block.getTimestamp();
		hashSeed += block.getNonce();
		var hashBytes = getSha256(hashSeed.getBytes(StandardCharsets.UTF_8));
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < hashBytes.length; i++) {
			stringBuilder.append(Integer.toHexString(255 & hashBytes[i]));
		}
		return stringBuilder.toString();
	}
}
