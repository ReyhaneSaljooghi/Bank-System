

public class Manager {
}

class State {
    public double x_min, y_min, x_max, y_max;
    String name;

    State(double xmin, double ymin, double xmax, double ymax, String name) {
        this.x_min = xmin;
        this.y_min = ymin;
        this.x_max = xmax;
        this.y_max = ymax;
        this.name = name;
    }

    boolean contains(Point p) {
        return (p.x >= x_min) && (p.x <= x_max) && (p.y >= y_min) && (p.y <= y_max);
    }

    @Override
    public String toString() {
        return "State{" +
                "x_min=" + x_min +
                ", y_min=" + y_min +
                ", x_max=" + x_max +
                ", y_max=" + y_max +
                ", name='" + name + '\'' +
                '}';
    }

    int intContains(Point p, boolean base) {
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


}

class Point {
    public double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Node {
    Point point;
    Node left;
    Node right;
    String name;
    boolean isBranch = false;
    boolean xCompare = false;
    boolean isDeleted = false;
    String MainBankName;
    KDTree Branches; // branch bank

    Node(double x, double y, String name) {
        this.point = new Point(x, y);
        left = right = null;
        this.name = name;
        Branches = new KDTree();
    }

    @Override
    public String toString() {
        return "" + point +
                "";
    }
}

class MyArrayList<E> {

    private static final int capacity = 50;

    private int Size;
    private E[] Items;

    public MyArrayList() {
        Items = (E[]) new Object[capacity];
    }

    public void clear() {
        Size = 0;
    }

    public void addCapacity(int newCapacity) {
        if (newCapacity < Size) return;

        E[] old = Items;
        Items = (E[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            Items[i] = old[i];
        }
    }

    public int size() {
        return Size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public E get(int index) {
        if (index < 0 || index >= size()) throw new ArrayIndexOutOfBoundsException();

        return Items[index];
    }

    public E set(int index, E newVal) {
        if (index < 0 || index >= size()) throw new ArrayIndexOutOfBoundsException();
        E old = Items[index];
        Items[index] = newVal;
        return old;
    }

    public boolean add(E element) {
        add(size(), element);
        return true;
    }

    public void add(int index, E element) {
        if (Items.length == size())
            addCapacity(size() * 2 + 1);
        for (int i = Size; i > index; i--) {
            Items[i] = Items[i - 1];
        }
        Items[index] = element;
        Size++;
    }

    public E remove(int index) {
        E removeItem = Items[index];
        for (int i = index; i < size() - 1; i++) {
            Items[i] = Items[i + 1];
        }
        Size--;
        return removeItem;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < size(); i++)
            res = res + (Items[i]).toString();
        return res;
    }
}