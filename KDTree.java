
public class KDTree {
    final int K = 2;
    Node KdRoot;
    int counter;

    Node insert(Node root, Node node, boolean base) {

        if (root == null) {
            node.xCompare = base;
            counter++;
            return node;

        }
        if (root.point.x == node.point.x && root.point.y == node.point.y)
            return root;
        if (root.xCompare && node.point.x < root.point.x || !root.xCompare && node.point.y < root.point.y)
            root.left = insert(root.left, node, !root.xCompare);
        else
            root.right = insert(root.right, node, !root.xCompare);

        return root;
    }

    void insert(Node node) {
        KdRoot = insert(KdRoot, node, true);
    }

    boolean search(Node root, Node node) {
        if (root == null)
            return false;
        if (root.point.x == node.point.x && root.point.y == node.point.y)
            return true;
        if (root.xCompare && node.point.x < root.point.x || !root.xCompare && node.point.y < root.point.y)
            return search(root.left, node);
        else
            return search(root.right, node);
    }

    Node search(Node root, Point p) {
        if (root == null)
            return null;
        if (root.point.x == p.x && root.point.y == p.y)
            return root;
        if (root.xCompare && p.x < root.point.x || !root.xCompare && p.y < root.point.y)
            return search(root.left, p);
        else
            return search(root.right, p);
    }


    Node findMin(Node root, boolean current, boolean base) {
        if (root == null)
            return null;

        if (base == current) {
            if (root.left == null)
                return root;
            return findMin(root.left, current, !base);
        } else
            return Minimum(root, findMin(root.left, current, !base), findMin(root.right, current, !base), current);
    }

    Node Minimum(Node root, Node n1, Node n2, boolean base) {
        Node result = root;
        if (n1 != null)
            if ((base && n1.point.x < result.point.x
                    ||
                    !base && n1.point.y < result.point.y))
                result = n2;

        if (n2 != null)
            if ((base && n2.point.x < result.point.x
                    ||
                    !base && n2.point.y < result.point.y))
                result = n2;

        return result;
    }

    void deleteNode(Point p) {

        KdRoot=deleteNode(KdRoot, p, true);
    }

    Node deleteNode(Node root, Point p, boolean base) {
        if (root == null)
            return null;

        if (root.point.x == p.x && root.point.y == p.y) {
            if (root.right != null) {

                Node min = findMin(root.right, true, true);
                copy(root, min);
                root.right = deleteNode(root.right, new Point(min.point.x, min.point.y), !base);
            } else if (root.left != null) {

                Node min = findMin(root.left, true, true);
                copy(root, min);
                root.right = deleteNode(root.left, new Point(min.point.x, min.point.y), !base);
                root.left = null;

            } else {

                root = null;
                counter--;
                return root;
            }
            return root;
        }
        if (root.xCompare && p.x < root.point.x || !root.xCompare && p.y < root.point.y)
            root.left = deleteNode(root.left, p, !base);
        else
            root.right = deleteNode(root.right, p, !base);
        return root;
    }

    void copy(Node n1, Node n2) {
        n1.point.x = n2.point.x;
        n1.point.y = n2.point.y;
    }

    public String toString(Node root) {
        if (root == null)
            return "";
        return root.toString() + "\n" + toString(root.left) + toString(root.right);
    }

    public void ListB(Node root, State state, boolean base) {
        if (root == null)
            return;
        if (state.contains(root.point)) {
            System.out.println(root.toString());
            System.out.println("name: " +
                    "" + root.name);
        }
//        ListB(root.left, state, !base);
//        ListB(root.right, state, !base);
        if (state.intContains(root.point, base) == 1) {
            if (root.left != null)
                ListB(root.left, state, !base);
        } else if (state.intContains(root.point, base) == 0) {
            if (root.left != null)
                ListB(root.left, state, !base);
            if (root.right != null)
                ListB(root.right, state, !base);
        } else {
            if (root.right != null)
                ListB(root.right, state, !base);
        }
    }


    public void availB(Node root, Point center, double R, boolean base) {
        if (root == null)
            return;
        double res = intCircleContains(root.point, R, center, base);
        // the distance should be less than or equal to R
        if (res == 0 && distanceSquare(root.point, center) <= R * R) {
            System.out.println(root.toString());
            System.out.println("name: " +
                    "" + root.name);
        }
        double x_min = center.x - R;
        double y_min = center.y - R;
        double x_max = center.x + R;
        double y_max = center.y + R;

        //check based on the parameters x or y:
        if (!base) {
            if (root.point.y < y_min)
                availB(root.right, center, R, !base);
            else if (root.point.y > y_max)
                availB(root.left, center, R, !base);
            else {
                if (root.left != null)
                    availB(root.left, center, R, !base);
                if (root.right != null)
                    availB(root.right, center, R, !base);
            }
        } else {
            if (root.point.x < x_min)
                availB(root.right, center, R, !base);
            else if (root.point.x > x_max)
                availB(root.left, center, R, !base);
            else {
                if (root.left != null)
                    availB(root.left, center, R, !base);
                if (root.right != null)
                    availB(root.right, center, R, !base);
            }

        }
    }

    // consider circle as a square and make the order better than always  checking both sides of the tree!
    double intCircleContains(Point p, double R, Point center, boolean base) {
        double x_min = center.x - R;
        double y_min = center.y - R;
        double x_max = center.x + R;
        double y_max = center.y + R;
        if (!base) {
            if (p.y < y_min)
                return -1;
            else if (p.y > y_max)
                return 1;
            else
                return 0;
        } else {
            if (p.x < x_min)
                return -1;
            else if (p.x > x_max)
                return 1;
            else
                return 0;

        }

    }


    double distanceSquare(Point p, Point center) {
        return (p.x - center.x) * (p.x - center.x) + (p.y - center.y) * (p.y - center.y);
    }


    Node nearestNeighbor(Node root, Point target, boolean base) {

        if (root == null) return null;
        Node checkFirst = null;
        Node checkNext = null;
        if (root.xCompare && target.x < root.point.x || !root.xCompare && target.y < root.point.y) {
            checkFirst = root.left;
            checkNext = root.right;
        } else {
            checkFirst = root.right;
            checkNext = root.left;
        }
        Node temp = nearestNeighbor(checkFirst, target, !base);
        Node closest = null;
        if (temp == null || root == null) {
            if (root == null)
                closest = temp;
            if (temp == null)
                closest = root;
        } else {
            if (distanceSquare(target, temp.point) > distanceSquare(target, root.point))
                closest = root;
            else
                closest = temp;
        }
        double radiusSquared = distanceSquare(target, closest.point);
        double dist;
        if (base)
            dist = target.x - root.point.x;
        else
            dist = target.y - root.point.y;

        if (radiusSquared >= dist * dist) {
            temp = nearestNeighbor(checkNext, target, !base);
            if (temp == null || closest == null) {
                if (closest == null)
                    closest = temp;
                if (temp == null)
                    closest = closest;
            } else {
                if (distanceSquare(target, temp.point) < distanceSquare(target, closest.point))
                    closest = temp;
                else
                    closest = closest;
            }
        }
        return closest;
    }

    Node nearestBranchNeighbor(Node root, Point target, boolean base) {

        if (root == null) return null;
        Node checkFirst = null;
        Node checkNext = null;
        if (root.xCompare && target.x < root.point.x || !root.xCompare && target.y < root.point.y) {
            checkFirst = root.left;
            checkNext = root.right;
        } else {
            checkFirst = root.right;
            checkNext = root.left;
        }
        Node temp = nearestBranchNeighbor(checkFirst, target, !base);
        Node closest = null;
        if (temp == null || root == null) {
            if (root == null)
                closest = temp;
            if (temp == null)
                closest = root;
        } else {
            if (distanceSquare(target, temp.point) > distanceSquare(target, root.point))
                closest = root;
            else
                closest = temp;
        }
        double radiusSquared = distanceSquare(target, closest.point);
        double dist;
        if (base)
            dist = target.x - root.point.x;
        else
            dist = target.y - root.point.y;

        if (radiusSquared >= dist * dist) {
            temp = nearestBranchNeighbor(checkNext, target, !base);
            if (temp == null || closest == null) {
                if (closest == null)
                    closest = temp;
                if (temp == null)
                    closest = closest;
            } else {
                if (distanceSquare(target, temp.point) < distanceSquare(target, closest.point))
                    closest = temp;
                else
                    closest = closest;
            }
        }
        return closest;
    }

}