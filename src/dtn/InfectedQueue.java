/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dtn;

/**
 *
 * @author Renegade
 */
public class InfectedQueue{

    protected int front;
    protected int rear;
    private Node queue[];

    public InfectedQueue(int maxElements) {
        queue=new Node[maxElements];
        front=0;
        rear=0;
    }

    public void add(Node n) {
        int temp=rear;
        rear= (rear +1) % queue.length;
        if( front == rear )
            rear = temp;
        queue[rear] = n;
    }

    public Node get(int pos) {
        return queue[pos];
    }

    public boolean isEmpty() {
        return front==rear;
    }

    public boolean isFull() {
        return ((rear + 1) % queue.length) == front;
    }

    public Node remove() {
        if(front==rear);
            //ERROR!!
        front = (front + 1) % queue.length;
        return queue[front];
    }
}
