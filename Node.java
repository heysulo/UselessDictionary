import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Node{
    public String word;
    public String definition;
    public String def_hash;
    public String data;
    public Node left;
    public Node right;
    
    
    //node init
    public Node(String _word,String _def){
        word = _word;
        definition = _def;
        def_hash = getMD5(_def);
        data = word;
        left = null;
        right = null;
    }
    
    /*
    THE MD5 HASHING USAGE
    SYNNONIMS ARE COLLECTED BY SEARCHING THE BINARY TREE WITH DEFEINITIONS.
    IF THE DEFINITION IS VERY BIG THIS COULD TAKE SOME TIME.
    THEREFORE A THE DEFINITION IS HASED INSIDE THE NODE FOR FASTER COMPARISIONS.
    MD5 (Message-Digest algorithm 5) is a well-known cryptographic hash function with a 128-bit resulting hash value.
    */
    public String getMD5(String txt){
        String ss;
        ss = txt;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(ss.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            ss = bigInt.toString(16);
        } catch (Exception e) {
            //System.out.println(getTime() +"ERRR : " + "MD5 generation failed for the word " + txt + "; " + e.getMessage());
            ss = "NULL";
        } finally{
            System.out.println(getTime() +"INFO : " + "MD5 generated " + ss);
            return ss;
        }
        
    }
    
    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date) + "   ";
    }

}