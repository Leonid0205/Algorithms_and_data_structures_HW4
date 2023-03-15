/**
* Реализовать левостороннее полноценное левостороннее красно-черное дерево. И реализовать в нем метод добавления новых элементов с балансировкой.
* Красно-черное дерево имеет следующие критерии:
• Каждая нода имеет цвет (красный или черный)
• Корень дерева всегда черный
• Новая нода всегда красная
• Красные ноды могут быть только левым ребенком
• У краной ноды все дети черного цвета

Соответственно, чтобы данные условия выполнялись, после добавления элемента в дерево необходимо произвести балансировку, благодаря которой все критерии выше станут валидными. Для балансировки существует 3 операции – левый малый поворот, правый малый поворот и смена цвета.
 */
public class RedBlackTree {

    private Node root;

    public class Node {
        
        private int value;
        private Color color;
        private Node leftChild;
        private Node rigthChild;

        @Override
        public String toString(){
        return "Node{" +
                "value=" + value +
                ", color=" + color +
                '}';
    }

    private enum Color {
        RED, BLACK
    }
    
    }

    public boolean add(int value){
        if (root != null) {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = RedBlackTree.Node.Color.BLACK;
            return result;
        } else {
            root = new Node();
            root.color = RedBlackTree.Node.Color.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean addNode(Node node, int value){
        if (node.value == value) {
            return false;
        } else {
            if (node.value > value) {
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = RedBlackTree.Node.Color.RED;
                    node.leftChild.value = value;
                    return true;
                }
            } else {
                if (node.rigthChild != null) {
                    boolean result = addNode(node.rigthChild, value);
                    node.rigthChild = rebalance(node.rigthChild);
                    return result;
                } else {
                    node.rigthChild = new Node();
                    node.rigthChild.color = RedBlackTree.Node.Color.RED;
                    node.rigthChild.value = value;
                    return true;
                }
            }
        }
    }

    private void colorSwap(Node node){
        node.rigthChild.color = RedBlackTree.Node.Color.BLACK;
        node.leftChild.color = RedBlackTree.Node.Color.BLACK;
        node.color = RedBlackTree.Node.Color.RED;
    }

    private Node leftSwap(Node node){
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rigthChild;
        leftChild.rigthChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = RedBlackTree.Node.Color.RED;
        return leftChild;
    }

    private Node rigthSwap(Node node){
        Node rigthChild = node.rigthChild;
        Node betweenChild = rigthChild.leftChild;
        rigthChild.leftChild = node;
        node.rigthChild = betweenChild;
        rigthChild.color = node.color;
        node.color = RedBlackTree.Node.Color.RED;
        return rigthChild;
    }

    private Node rebalance(Node node){
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.rigthChild != null && result.rigthChild.color == RedBlackTree.Node.Color.RED &&
                    (result.leftChild == null || result.leftChild.color == RedBlackTree.Node.Color.BLACK)) {
                needRebalance = true;
                result = rigthSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == RedBlackTree.Node.Color.RED &&
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == RedBlackTree.Node.Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == RedBlackTree.Node.Color.RED &&
                    result.rigthChild != null && result.rigthChild.color == RedBlackTree.Node.Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
            
        } while (needRebalance);
        return result;
    }
    
}