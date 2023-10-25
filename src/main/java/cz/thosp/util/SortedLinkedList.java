package cz.thosp.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

public class SortedLinkedList<T> extends LinkedList<T> {
    
    private String elementClassName;
    
    public SortedLinkedList(Class classType) {
        String className = classType.getCanonicalName();
        if (!(className.equals(String.class.getCanonicalName()) || className.equals(Integer.class.getCanonicalName()))) {
            throw new UnsupportedOperationException();
        }
        this.elementClassName = className;
    }
    
    @Override
    public synchronized boolean add(T e) {
        if (!e.getClass().getCanonicalName().equals(elementClassName)) {
            throw new UnsupportedOperationException();
        }
        super.add(getIndexOfElement(e), e);
        return true;
    }    
    
    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }
    
    private int getIndexOfElement(T newElement) {
        ListIterator<T> iteratorForElements = listIterator();
        while (iteratorForElements.hasNext()) {
            int index = iteratorForElements.nextIndex();
            if (newValueIsLess(iteratorForElements.next(), newElement)) {
                return index;
            }
        }
        return size();
    }
    
    private boolean newValueIsLess(T currentValue, T newValue) {
        if (newValue instanceof Integer integer) {
            return ((Integer) currentValue) > integer;
        } else if (newValue instanceof String string) {
            return ((String) currentValue).compareToIgnoreCase(string) > 0;
        }
        return false;
    }
}
