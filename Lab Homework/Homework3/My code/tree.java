import java.util.*;

class Node {
    int val;
    Node left;
    Node right;

    public Node(int val) {
        this.val = val;
    }
}

public class tree {
    public static boolean ifBST(Integer[] arr, int r) {//r是输入数组的指针，从0开始，递归即每次加1，直到超过数组长度
        int leftChild = 2 * r + 1;
        int rightChild = 2 * r + 2;

        if (leftChild >= arr.length) { // 到二叉树第一个没有左子节点处跳出循环
            return true;
        }
        if (arr[rightChild] != -1 && arr[leftChild] != -1 && arr[r] != -1) {//均不为空
            if (arr[r] < arr[leftChild] || (rightChild < arr.length && arr[r] > arr[rightChild])) {
                return false;
            }
        } else if (arr[r] != -1 && arr[rightChild] != -1) {//左子结点为空
            if (rightChild < arr.length && arr[r] > arr[rightChild]) {
                return false;
            }
        } else if (arr[r] != -1 && arr[leftChild] != -1) {//右子节点为空
            if (arr[r] < arr[leftChild]) {
                return false;
            }
        } else {//剩余一种情况即结点本身为空，则无需比较
            return true;
        }
        return ifBST(arr, leftChild) && ifBST(arr, rightChild); // 递归检查左右子树
    }

    public static Node deleteNode(Node root, int key) {
        if (root.val == -1) {
            return new Node(-1);
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
            return root;
        }
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
            return root;
        }
        if (root.val == key) {
            if (root.left.val == -1 && root.right.val == -1) {
                return new Node(-1);
            }
            if (root.right.val == -1) {
                return root.left;
            }
            if (root.left.val == -1) {
                return root.right;
            }
            Node successor = root.right;
            while (successor.left != null && successor.left.val != -1) {
                successor = successor.left;
            }
            root.right = deleteNode(root.right, successor.val);
            successor.right = root.right;
            successor.left = root.left;
            return successor;
        }
        return root;
    }

    public static Node buildTree(Integer[] nums, int i) {
        if (i >= nums.length || nums[i] == -1 || nums[i] == null) {
            return new Node(-1);
        }
        Node node = new Node(nums[i]);
        node.left = buildTree(nums, 2 * i + 1);
        node.right = buildTree(nums, 2 * i + 2);

        return node;
    }

    public static String serialize(Node root) {
        StringBuilder sb = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node == null) {
                sb.append("-1");
            } else {
                sb.append(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            }
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Integer[] givenTree = new Integer[n];
        for (int i = 0; i < n; i++) {
            givenTree[i] = sc.nextInt();
        }

        int k = sc.nextInt();
        int[] toRemove = new int[k];
        for (int i = 0; i < k; i++) {
            toRemove[i] = sc.nextInt();
        }

        if (ifBST(givenTree, 0)) {
            System.out.println("Yes");
            Node root = buildTree(givenTree, 0);
            for (int j = 0; j < toRemove.length; j++) {
                root = deleteNode(root, toRemove[j]);
            }
            String string = serialize(root);
            String[] arr = string.split(" ");
            String newStr = String.join(" ", Arrays.copyOfRange(arr, 0, n));
            System.out.println(newStr);
        } else {
            System.out.println("No");
        }
    }
}