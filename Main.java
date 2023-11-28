import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static MyArrayList<State> states = new MyArrayList<>();
    public static MyArrayList<Node> Banks = new MyArrayList<>();
    public static KDTree kdTree;

    public static void main(String[] args) {
        kdTree = new KDTree();
        Scanner input = new Scanner(System.in);
        outer:
        while (input.hasNext()) {
            String order = input.next();

            switch (order) {
                case "addN":
                    System.out.println("Enter Name of the State:");
                    String Name = input.next();
                    System.out.println("enter minimum x:");
                    double xmin = input.nextDouble();
                    System.out.println("enter maximum x:");
                    double xmax = input.nextDouble();
                    System.out.println("enter minimum y:");
                    double ymin = input.nextDouble();
                    System.out.println("enter maximum y:");
                    double ymax = input.nextDouble();
                    State s = new State(xmin, ymin, xmax, ymax, Name);
                    addN(Name, s);
                    break;
                    //its okk
                case "addB":
                    System.out.println("Enter Name of the Bank:");
                    Name = input.next();
                    System.out.println("enter x dimension:");
                    double x = input.nextDouble();
                    System.out.println("enter y dimension:");
                    double y = input.nextDouble();
                    Node bank = new Node(x, y, Name);
                    if (kdTree.search(kdTree.KdRoot, bank)) {
                        System.out.println("It is duplicate Bank ");
                        break;
                    }
                    Banks.add(bank);
                    System.out.println("If you want to add branch enter ... yes...");
                    String branchOrder = input.next();
                    if (branchOrder.equals("yes")) {
                        System.out.println("enter the number of branches");
                        int num = input.nextInt();
                        for (int i = 0; i < num; i++) {
                            System.out.println("Enter Name of the Branch Bank:");
                            String BrName = input.next();
                            System.out.println("enter x dimension:");
                            double xBr = input.nextDouble();
                            System.out.println("enter y dimension:");
                            double yBr = input.nextDouble();
                            Node Brbank = new Node(xBr, yBr, BrName);
                            Brbank.MainBankName = Name;
                            Brbank.isBranch = true;
                            if (kdTree.search(kdTree.KdRoot, bank)) {
                                System.out.println(" it is duplicate Branch");
                                continue;
                            }
                            addB(Brbank);
                            Node Brbank2 = new Node(xBr, yBr, BrName);
                            Brbank2.MainBankName = Name;
                            Brbank2.isBranch = true;
                            bank.Branches.insert(Brbank2);
                        }
                    }
                    addB(bank);
                   // System.out.println(kdTree.counter);
                    break;
                    //its okk
                case "addBr":
                    System.out.println("enter x dimension:");
                    double xBr = input.nextDouble();
                    System.out.println("enter y dimension:");
                    double yBr = input.nextDouble();
                    System.out.println("Enter Name of the Bank:");
                    Name = input.next();
                    System.out.println("Enter Name of the Branch:");
                    String BrName = input.next();
                    Node Brbank = new Node(xBr, yBr, BrName);
                    Brbank.MainBankName = Name;
                    Brbank.isBranch = true;
                    Node bankFound = getBank(Name);
                    if (kdTree.search(kdTree.KdRoot, Brbank)) {
                        System.out.println(" It is duplicate Branch");
                        continue;
                    }
                    addB(Brbank);
                    Node Brbank2 = new Node(xBr, yBr, BrName);
                    Brbank2.MainBankName = Name;
                    Brbank2.isBranch = true;
                    bankFound.Branches.insert(Brbank2);
                    break;
                ///its ok
                case "delBr":
                    System.out.println("enter x dimension:");
                    xBr = input.nextDouble();
                    System.out.println("enter y dimension:");
                    yBr = input.nextDouble();
                    Point p = new Point(xBr, yBr);
                    if (kdTree.search(kdTree.KdRoot, p) == null) {
                        System.out.println(" This bank doesnt exist");
                        continue outer;
                    }
                    if (kdTree.search(kdTree.KdRoot, p).isBranch == false) {
                        System.out.println(" It is not branch");
                        continue ;
                    }

                    String mainBankName = kdTree.search(kdTree.KdRoot, p).MainBankName;
                    Node b = getBank(mainBankName);
                    b.Branches.deleteNode(p);
                    //System.out.println("Bank Nodes" + b.Branches.toString(b.Branches.KdRoot));
                    kdTree.deleteNode(p);
//                    System.out.println(kdTree.toString(kdTree.KdRoot));
//                    Node get=kdTree.search(kdTree.KdRoot,b.point);
//                    System.out.println("brs:"+b.Branches.toString(b.Branches.KdRoot));
//                    System.out.println("brs2:"+get.Branches.toString(get.Branches.KdRoot));
//                    System.out.println(kdTree.toString(kdTree.KdRoot));

                    break;
                    //its okk
                case "listB":
                    System.out.println("Enter Name of the State:");
                    Name = input.next();
                    State st = getState(Name);
                    kdTree.ListB(kdTree.KdRoot, st, true);
                    break;
                    //its okk
                case "listBr":
                    System.out.println("Enter Name of the Bank:");
                    Name = input.next();
                    bank = getBank(Name);
                    System.out.println(bank.Branches.toString(bank.Branches.KdRoot));

                    break;
                    //its okk
                case "availB":
                    System.out.println("Enter R:");
                    Double R = input.nextDouble();
                    System.out.println("enter x dimension:");
                    double x_center = input.nextDouble();
                    System.out.println("enter y dimension:");
                    double y_center = input.nextDouble();
                    kdTree.availB(kdTree.KdRoot, new Point(x_center, y_center), R, true);
                    break;
                case "exit":
                    break outer;
                    //its okk
                case "nearB":
                    System.out.println("enter x dimension:");
                    x = input.nextDouble();
                    System.out.println("enter y dimension:");
                    y = input.nextDouble();
                    Node nearest = kdTree.nearestNeighbor(kdTree.KdRoot, new Point(x, y), true);
                    System.out.println(nearest.point);
                    System.out.println(nearest.name);
                    break;
                    //its okk
                case "nearBr":
                    System.out.println("enter x dimension:");
                    x = input.nextDouble();
                    System.out.println("enter y dimension:");
                    y = input.nextDouble();
                    System.out.println("Enter Name of the Bank:");
                    mainBankName = input.next();
                    Node mainbank = getBank(mainBankName);
                    if (mainbank != null) {
                        Node nearestBranch = mainbank.Branches.nearestNeighbor(mainbank.Branches.KdRoot, new Point(x, y), true);
                        System.out.println(nearestBranch.point);
                        System.out.println(nearestBranch.name);
                    }
                    else {
                        System.out.println("main bank nulle");
                        continue ;
                    }
                    break;
            }
        }
        System.out.println(kdTree.toString(kdTree.KdRoot));

    }

    public static void addN(String name, State state) {
        states.add(state);
    }

    public static void addB(Node bank) {

        kdTree.insert(bank);


    }


    public static void listB(String name) {

///???????????

    }

    public static State getState(String name) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).name.equals(name))
                return states.get(i);
        }
        return null;
    }

    public static Node getBank(String name) {
        for (int i = 0; i < Banks.size(); i++) {
            if (Banks.get(i).name.equals(name))
                return Banks.get(i);
        }
        return null;
    }

    public static void deleteFromTheBranches(String name, Point p) {
        Node bank = getBank(name);
        bank.Branches.deleteNode(bank.Branches.KdRoot, p, true);
    }
}

