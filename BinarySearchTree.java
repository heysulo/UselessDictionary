
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class BinarySearchTree {

    public Node root;
    public String export;
    private DefaultListModel listModel = new DefaultListModel();

    public BinarySearchTree() {
        this.root = null;
    }

    public void deletenode(String word) {
        Node parent = root;
        Node current = root;
        word = word.trim().toLowerCase();
        boolean rightChild = false;
        while (current != null && current.data.equals(word)==false) {
            parent = current;
            if (word.compareTo(current.word) > 0) {
                rightChild = true;
                current = current.right;
            } else {
                rightChild = false;
                current = current.left;
            }
            if (current == null) {
                JOptionPane.showMessageDialog(null, word + " is not declared in the dictionary to delete");
                return;
            }
        }
        if (current == null) {
            JOptionPane.showMessageDialog(null, word + " is not declared in the dictionary to deletez");
            return;
        }else{
            JOptionPane.showMessageDialog(null, current.word + " found");
        }
        
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }
            if (rightChild == true) {
                parent.right = null;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            } else {
                parent.left = null;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }
        }else if(current.left == null){
            if (current == root) {
                root = current.right;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }else if (rightChild){
                parent.right = current.left;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }else{
                parent.left = current.left;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }
        }else if(current.right == null){
            if (current == root) {
                root = current.left;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }else if (rightChild){
                parent.left = current.right;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }else{
                parent.right = current.right;
                JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
            }
        }else if(current.left != null && current.right != null){
            Node succ = findSuccessor(current);
            if (current == root) {
                root = succ;
            } else if (rightChild) {
                parent.right = succ;
            } else {
                parent.left = succ;
            }
            succ.right = current.right;
            JOptionPane.showMessageDialog(null, "The word " + word + " deleted from the dictionary.");
        }
    }

    

    public void insert(String word, String def) {
        Node newNode = new Node(word, def);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;

        while (true) {
            parent = current;
            if (word.compareTo(current.word) > 0) {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            } else if (word.compareTo(current.word) < 0) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            } else {
                current.definition = newNode.definition;
                return;
            }
        }
    }

    public Node find(String word) {
        Node current = root;
        while (current != null) {
            if (current.data.equals(word)) {
                return current;
            } else if (word.compareTo(current.word) > 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return null;
    }

    public void travSearch(String def, Node n) {
        if (n == null) {
            n = root;
        }
        if (n.left != null) {
            travSearch(def, n.left);
        }

        if (n.def_hash.equals(def)) {
            listModel.addElement(n.word);
        }

        if (n.right != null) {
            travSearch(def, n.right);
        }

    }

    public DefaultListModel findSyn(String def) {
        listModel.removeAllElements();

        travSearch(getMD5(def), null);

        return listModel;

    }
    
    public String exportTree(){
        export = "";
        if (root != null){
            travExport(root);
            return export;
        }else{
            return null;
        }
        
    }
    
    public void travExport( Node n) {
        if (n == null) {
            n = root;
        }
        if (n != null){
            export = export + "\n" + n.data + ";" +n.definition;
        }
        if (n.left != null) {
            travExport(n.left);
        }


        if (n.right != null) {
            travExport( n.right);
        }

    }
    
    public Node findSuccessor(Node node) {
        Node targ = null;
        Node targ_parent = null;
        Node current = node.left;
        while (current != null) {
            targ_parent = targ;
            targ = current;
            current = current.right;
        }
        if (targ != node.left) {
            targ_parent.right = targ.left;
            targ.left = node.left;
        }
        return targ;
    }

    public String getMD5(String txt) {
        String ss;
        ss = txt;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(ss.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            ss = bigInt.toString(16);
        } catch (Exception e) {
            ss = "NULL";
        } finally {
            return ss;
        }

    }

    /*
        def inorder(n):
            if type(n.l) != type(None):
                #print "left of",n.d,type(n.l)
                inorder(n.l)
            print n.d
            if type(n.r) != type(None):
                #print "right of",n.d,type(n.l)
                inorder(n.r)
     */
}
