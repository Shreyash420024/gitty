import java.io.*;
import java.util.*;
public class twoPowerXaryHeap<T extends Comparable<T>> {
    private List<T> heap;
    private int maxNumberOfChildren;

    public twoPowerXaryHeap(int maxNumberOfChildren) {
    if (maxNumberOfChildren <= 0 || !isPowerOfTwo(maxNumberOfChildren))
        throw new IllegalArgumentException("maxNumberOfChildren must be a positive and a power of 2.");

    this.maxNumberOfChildren = maxNumberOfChildren;
    heap = new ArrayList<>();
    }

    private boolean isPowerOfTwo(int number) {
        return (number & (number - 1)) == 0;
    }
    /*simply adds the elements in te list and then asks the heapify function "correctUpwards" to put the element in its correct place */
    public void insert(T item) {
        heap.add(item);
        correctUpwards(heap.size() - 1);
    }
    /*Now the logic here is that the correct answer, i.e the max element resides in the first index of the list , so inorder to extract it we
    store its value removed the last element and set is as the first element,then performed the heapify function "correctDownwards" to put the last
    index in its correct place, this way the size also correctly reduces by 1*/
    public T popMax() {
        if (heap.isEmpty())
            throw new IllegalStateException("Empty Heap Exception");

        T max = heap.get(0);
        int lastIdx = heap.size() - 1;
        heap.set(0, heap.get(lastIdx));
        heap.remove(lastIdx);
        correctDownwards(0);

        return max;
    }
    //corrects the heap property by simply checking the element at the index with its parent 
    private void correctUpwards(int index) {
        int parentIndex = (index - 1) / maxNumberOfChildren;

        while (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / maxNumberOfChildren;
        }
    }
    //Similiar to the correctUpwards function just that it checks the element with each of its children to fix the heap property
    private void correctDownwards(int index) {
        int firstchild = maxNumberOfChildren * index + 1;
        int lastchild = Math.min(firstchild + maxNumberOfChildren, heap.size());

        while (firstchild < lastchild) {
            int maxChildIndex = findMaxChildIndex(firstchild, lastchild);
            if (heap.get(index).compareTo(heap.get(maxChildIndex)) >= 0)
                break;

            swap(index, maxChildIndex);
            index = maxChildIndex;
            firstchild = maxNumberOfChildren * index + 1;
            lastchild = Math.min(firstchild + maxNumberOfChildren, heap.size());
        }
    }
    //out of a maximum of 2^x children, this finds the maxchild
    private int findMaxChildIndex(int startIdx, int endIdx) {
        int maxChildIndex = startIdx;
        for (int i = startIdx + 1; i < endIdx; i++) {
            if (heap.get(i).compareTo(heap.get(maxChildIndex)) > 0)
                maxChildIndex = i;
        }
        return maxChildIndex;
    }
    //swap function
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    public boolean isEmpty()
    {
        return heap.isEmpty();
    }
}
