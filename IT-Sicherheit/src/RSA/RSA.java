package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA 
{
	private static final String TEXT = "Geheim!!!";
	
	private static final BigInteger PRIM1 = BigInteger.probablePrime(1024, new SecureRandom());
	private static final BigInteger PRIM2 = BigInteger.probablePrime(1024, new SecureRandom());
	private static final BigInteger PHI = PRIM1.subtract(BigInteger.ONE).multiply(PRIM2.subtract(BigInteger.ONE));
	
	public static BigInteger createkey(BigInteger pukey)
	{
		//implizit teilerfremd, da 1 = pukey*prkey mod phi = prkey^-1 mod phi
		BigInteger prkey = pukey.modInverse(PHI);
		return prkey;
	}
	
	public static BigInteger encrypt(BigInteger text, BigInteger pukey, BigInteger modulos)
	{
		return text.modPow(pukey, modulos);
	}
	
	public static BigInteger decrypt(BigInteger chiffre, BigInteger prkey, BigInteger modulos)
	{
		return chiffre.modPow(prkey, modulos);
	}
	
	public static void main(String[] args) 
	{
		//Klartext umwandeln
		byte[] bytes = TEXT.getBytes();
		BigInteger message = new BigInteger(bytes);
		System.out.println(message);
		
		BigInteger publickey = new BigInteger("65537");
		BigInteger n = PRIM1.multiply(PRIM2);
		
		BigInteger privatekey = createkey(publickey);
		
		//Start
		BigInteger send = encrypt(message, publickey, n);
		System.out.println(send);
		BigInteger recieve = decrypt(send, privatekey, n);
		System.out.println(recieve);
	}

}
