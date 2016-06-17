
import java.math.BigInteger;
import java.security.MessageDigest;

class Node{
    public String word;
    public String definition;
    public String def_hash;
    public String data;
    public Node left;
    public Node right;
    
    public Node(String _word,String _def){
        word = _word;
        definition = _def;
        def_hash = getMD5(_def);
        data = word;
        left = null;
        right = null;
    }
    
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
            ss = "NULL";
        } finally{
            return ss;
        }
        
    }

}