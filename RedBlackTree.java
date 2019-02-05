import jdk.nashorn.internal.scripts.JO;

import java.util.ArrayList;




public class RedBlackTree {

    private final int RED = 0;
    private final int BLACK = 1;


    class RedBlackNode {

        Job job = new Job(-1, 0, 0);
        int color = BLACK;
        RedBlackNode left = nil, right = nil, parent = nil;

        RedBlackNode(Job job) {
            this.job = job;
        }
    }


    private final RedBlackNode nil = new RedBlackNode(new Job(-1, 0, 0));

    //initialize the root as nil
    private RedBlackNode root = nil;

    //leave the rightmost node in the variable, to find the position to insert the node.
    private RedBlackNode rightMostNode = nil;

    public ArrayList<Job> rangejob = new ArrayList<Job>();

    //check if the node exists, when doing a deletion
    //node starts with the root, nodetofind is the node to check

    private RedBlackNode ifNodeExist(RedBlackNode nodetofind, RedBlackNode node) {
        if (root == nil) {
            return null;
        }

        if (nodetofind == null){
            return null;
        }

        if (nodetofind.job.Id < node.job.Id) {
            if (node.left != nil) {
                return ifNodeExist(nodetofind, node.left);
            }
        } else if (nodetofind.job.Id > node.job.Id) {
            if (node.right != nil) {
                return ifNodeExist(nodetofind, node.right);
            }
        } else if (nodetofind.job.Id == node.job.Id) {
            return node;
        }
        return null;
    }

    //Insert node into the RBT
    public void insert(Job item){
        if (root == nil){
            RedBlackNode node = new RedBlackNode(item);
            node.color = BLACK;
            node.parent = nil;
            root = node;
        }else{
            insert(item, null, root, false);
        }
    }

    //    //Insert "Job" node into the Red Black tree
    public void insert(Job item, RedBlackNode oldroot, RedBlackNode root, Boolean flag) {
        if (root == nil){
            RedBlackNode node = new RedBlackNode(item);
            node.color = RED;
            node.parent = oldroot;

            if (flag == false){
                oldroot.right = node;
            }else {
                oldroot.left = node;
            }
            rb_insertRotation(node);
        }else if (root.job.Id > item.Id){
            insert(item, root, root.left, true);
        } else {
            insert(item, root, root.right, false);
        }
    }
//    //Insert "Job" node into the Red Black tree
//    public void insert(Job item) {
//        RedBlackNode node = new RedBlackNode(item);
//
//        RedBlackNode compareVal = root;
//
//        //when the tree is empty, insert the node into the root
//        if (root == nil) {
//            root = node;
//            rightMostNode = root;
//            node.color = BLACK;
//            node.parent = nil;
//        } else  if (root.job.Id < item.getId()){
//            //use the rightMostNode value to get the insert position
//            node.color = RED;
//            compareVal = rightMostNode;
//            compareVal.right = node;
//            node.parent = compareVal;
//            rightMostNode = node;
//
//            // Call rb_insertRotation to do the rotation and fix up the color violation
//            rb_insertRotation(node);
//        }else{
//            node.color = RED;
//            compareVal = rightMostNode;
//            compareVal.left = node;
//            node.parent = compareVal;
//            rightMostNode = node;
//        }
//    }

    //find the smallest job id of the tree rooted at subRoot(root of the subtree)
    RedBlackNode treeMin(RedBlackNode subRoot){
        while(subRoot.left!=nil){
            subRoot = subRoot.left;
        }
        return subRoot;
    }

    //find the job in the Red Black tree
    //check if there is a node in the Red Black Tre with the given ID = currentID

    public RedBlackNode findNode(int currentID)
    {
        return findNode(root, currentID);
    }
    private RedBlackNode findNode(RedBlackNode root, int currentID)
    {
        while (root != nil)
        {
            int rootvalue = root.job.Id;
            if (currentID < rootvalue)
                root = root.left;
            else if (currentID > rootvalue)
                root = root.right;
            else
            {
                return root;
            }
        }
        return null;
    }

    //Find the next existing node in the tree, given currentID
    public Job nextJob(int currentID){
        RedBlackNode compareVal = root;
        RedBlackNode answer = null;

        //initialize compareVal = root. If compareVal > currentID
        //check in the left subtree, to find if there exists other values, that is smaller than compareVal but bigger than currentID.
        //if it is a smaller job in compareVal, check in the right subtree.
        //break when the tree goes to an end
        //if can't find any such node: answer == null =>

        if (compareVal == nil){
            return null;
        }

        while(true) {
            if (compareVal.job.Id > currentID) {
                answer = compareVal;
                if(compareVal.left != nil)
                    compareVal = compareVal.left;
                else
                    break;
            } else if (compareVal.job.Id <= currentID) {
                if (compareVal.right != nil)
                    compareVal = compareVal.right;
                else
                    break;
            }
        }

        if (answer == null){
            //printNull();
            return null;
        }
        else{
            return printJob(answer.job.Id);
        }
    }

    //find the last existing node in the tree, given currentID
    public Job prevJob(int currentID){
        RedBlackNode compareVal = root;
        RedBlackNode answer = null;


        // have same method but opposite comparison as the above mentioned NextJob
        // if compareVal >= currentID, check if there exists other values, that is smaller than compareVal but bigger than currentID
        // check in the left subtree
        // if it is a smaller job in compareVal, check the right subtree.(condition # 1 below)
        // Break when the tree goes to an end

        if (compareVal == nil){
            return null;
        }

        while(true) {
            //System.out.print(compareVal.job.Id);
            if (compareVal.job.Id >= currentID) {
                if(compareVal.left != nil)
                    compareVal = compareVal.left;
                else
                    break;
            } else if (compareVal.job.Id < currentID) {
                answer = compareVal;
                if (compareVal.right != nil)
                    compareVal = compareVal.right;
                else
                    break;
            }
        }


        if (answer == null)
            //printNull();
            return null;
        else
            return printJob(answer.job.Id);
    }


    //print nodes in given range
    public ArrayList<Job> printRange(int id1, int id2){

        rangejob.clear();
        getRange(root, id1, id2);
        if (rangejob.size() == 0){
            return null;
        }else {
            return rangejob;
        }
    }
    public void getRange(RedBlackNode root, int id1, int id2){
        if (root == nil) {
            return;
        }

        if (root.job.Id == id1 && root.job.Id == id2){
            rangejob.add(root.job);
            return;
        }
        // if node is within given range, print the child nodes
        if (root.job.Id >= id1 && root.job.Id <= id2){
            getRange(root.left, id1, id2);
            rangejob.add(root.job);
            getRange(root.right, id1, id2);
            return;
        }
        // if node is smaller than id1
        // check the right nodes
        else if (root.job.Id < id1){
            getRange(root.right, id1, id2);
            return;
        }
        //if node is greater than id2
        //check the left nodes
        else if (root.job.Id > id2){
            getRange(root.left, id1, id2);
            return;
        }else{
            return;
        }
    }




    //Get the node ready to be called to print
    public Job printJob(int Id){
        RedBlackNode node = findNode(root, Id);
        if (node == null){
            return null;
        }else {
//            System.out.print("(" + node.job.Id + ","
//                    + node.job.exe_time + "," + node.job.total_time + ")");
            return node.job;

        }
    }

    // delete a node
    //Call rb_moveNode, to realize the deletion
    public boolean deleteNode(int Id){
        return deleteNode(findNode(Id));
    }

    boolean deleteNode(RedBlackNode z){
        if((z = ifNodeExist(z, root))==null)
            return false;
        RedBlackNode x;
        RedBlackNode y = z;
        int y_original_color = y.color;

        if(z.left == nil){
            x = z.right;
            rb_moveNode(z, z.right);
        }else if(z.right == nil){
            x = z.left;
            rb_moveNode(z, z.left);
        }else{
            y = treeMin(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                rb_moveNode(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            rb_moveNode(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if(y_original_color==BLACK)
            rb_deleteRotation(x);
        return true;
    }



    // Check what kind of the rotation we need to operate after insertion, and adjust the color
    //Call leftRotate and rightRotate to do the rotation to keep the Red Black tree without violation
    // argument: newly inserted node

    private void rb_insertRotation(RedBlackNode node) {
        // always insert new node_Color = Red
        //parent is RED, violation

        while (node.parent.color == RED) {
            RedBlackNode y = nil;

            //Parent is left child of its parent: call rotateRight
            //Parent is right child of its parent: call rotateLeft

            if (node.parent == node.parent.parent.left) {
                y = node.parent.parent.right;

                if (y != nil && y.color == RED) {
                    node.parent.color = BLACK;
                    y.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.right) {
                    node = node.parent;
                    leftRotate(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                rightRotate(node.parent.parent);
            } else {
                y = node.parent.parent.left;
                if (y != nil && y.color == RED) {
                    node.parent.color = BLACK;
                    y.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    node = node.parent;
                    rightRotate(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                leftRotate(node.parent.parent);
            }
        }
        root.color = BLACK;
    }

    void leftRotate(RedBlackNode node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {
            // rotate root to the left
            RedBlackNode right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    void rightRotate(RedBlackNode node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {
            //rotate root to the right
            RedBlackNode left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }

    //Replace the old subtree with the rotated subtree-to-be
    void rb_moveNode(RedBlackNode u, RedBlackNode v){
        if(u.parent == nil){
            root = v;
        }else if(u == u.parent.left){
            u.parent.left = v;
        }else
            u.parent.right = v;
        v.parent = u.parent;
    }



    // Check what kind of the rotation we need to operate after deletion, and adjust the color
    //Call leftRotate and rightRotate to do the rotation

    void rb_deleteRotation(RedBlackNode x){
        while(x!=root && x.color == BLACK){
            if(x == x.parent.left){
                RedBlackNode w = x.parent.right;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == BLACK && w.right.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.right.color == BLACK){
                    w.left.color = BLACK;
                    w.color = RED;
                    rightRotate(w);
                    w = x.parent.right;
                }
                if(w.right.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            }else{
                RedBlackNode w = x.parent.left;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == BLACK && w.left.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.left.color == BLACK){
                    w.right.color = BLACK;
                    w.color = RED;
                    leftRotate(w);
                    w = x.parent.left;
                }
                if(w.left.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

}