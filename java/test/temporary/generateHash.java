package test.temporary;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class generateHash {
public static void main(String[] args){
	String password = "Passw0rd@";
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  
	  String hash = passwordEncoder.encode(password);  
	  
	  System.out.println(hash);
}
}
