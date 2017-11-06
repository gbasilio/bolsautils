package br.gustavo.db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

public class decryptMd5 {

	public static void main(String[] args) throws Exception{
		Date inicio = new Date();

		//		for (char c = ' '; c <= '~'; c++){
		//			System.out.println(c);
		//		}

		//		for (char c1 = ' '; c1 <= '~'; c1++){
		//			String s1 = ""+c1;
		//			for (char c2 = ' '; c2 <= '~'; c2++){
		//				String s2 = ""+c2;
		//				for (char c3 = ' '; c3 <= '~'; c3++){
		//					String s3 = ""+c3;
		//					for (char c4 = ' '; c4 <= '~'; c4++){
		//						String s4 = ""+c4;
		//						for (char c5 = ' '; c5 <= '~'; c5++){
		//							System.out.println(s1+s2+s3+s4+c5);
		//						}
		//					}
		//				}
		//			}
		//		}

		String test = "BBBBBB";
		for (int i1 = 32; i1 <= 127; i1++){
			for (int i2 = 32; i2 <= 127; i2++){
				for (int i3 = 32; i3 <= 127; i3++){
					for (int i4 = 32; i4 <= 127; i4++){
						for (int i5 = 32; i5 <= 127; i5++){
							for (int i6 = 32; i6 <= 127; i6++){
								String auto = ""+(char)i1+(char)i2+(char)i3+(char)i4+(char)i5+(char)i6;
								if (test.equals(auto)){
									System.out.println(test + " = " + auto);
								}
							}
						}
					}
				}
			}
		}



		System.out.println(((new Date()).getTime() - inicio.getTime()) / 1000);
	}


	private static String md5Sum(String input) throws Exception{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] md5sum = md5.digest(input.getBytes("LATIN1"));
		BigInteger bigInt = new BigInteger(1, md5sum);
		String output = bigInt.toString(16);

		return output;
	}


	private static void printAllLetterSequences(String prefix, int length) {
		System.out.println(prefix);
		if (prefix.length() < length)
			for (char c = ' '; c <= '~'; c++)
				printAllLetterSequences(prefix + c, length);
	}

}
