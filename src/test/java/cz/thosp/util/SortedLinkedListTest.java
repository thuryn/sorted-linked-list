package cz.thosp.util;

import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortedLinkedListTest {
    
    @Test
    void listOfIntegers() {
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList(Integer.class);
        
        sortedLinkedList.add(3);
        sortedLinkedList.add(2);
        sortedLinkedList.add(8);
        sortedLinkedList.add(1);
        
        ListIterator<Integer> iteratorForElements = sortedLinkedList.listIterator();
        Integer previousElementValue = null;
        while (iteratorForElements.hasNext()) {
            Integer currentElement = iteratorForElements.next();
            //System.out.println("P:" + previousElementValue + ", C: " + currentElement);
            assertTrue((previousElementValue == null) || (previousElementValue < currentElement));
            previousElementValue = currentElement;
        }
    }
    
    @Test
    void listOfStrings() {
        SortedLinkedList sortedLinkedList = new SortedLinkedList(String.class);
        
        sortedLinkedList.add("car");
        sortedLinkedList.add("tree");
        sortedLinkedList.add("house");
        sortedLinkedList.add("zone");
        sortedLinkedList.add("Aprile");
        
        //sortedLinkedList.spliterator().forEachRemaining(System.out::println);
        
        ListIterator<String> iteratorForElements = sortedLinkedList.listIterator();
        String previousElementValue = null;
        while (iteratorForElements.hasNext()) {
            String currentElement = iteratorForElements.next();
            //System.out.println("P:" + previousElementValue + ", C: " + currentElement);
            assertTrue((previousElementValue == null) || (previousElementValue.compareToIgnoreCase(currentElement) < 1));
            previousElementValue = currentElement;
        }
    }
    
    @Test
    void testInThreads() throws InterruptedException {
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList(Integer.class);
        
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i = 0; i < 10000; i++){
            es.execute(() -> {
                sortedLinkedList.add(Double.valueOf(Math.random() * 1000).intValue());
            });
        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
        
        ListIterator<Integer> iteratorForElements = sortedLinkedList.listIterator();
        Integer previousElementValue = null;
        while (iteratorForElements.hasNext()) {
            Integer currentElement = iteratorForElements.next();
            //System.out.println("P:" + previousElementValue + ", C: " + currentElement);
            assertTrue((previousElementValue == null) || (previousElementValue <= currentElement));
            previousElementValue = currentElement;
        }
    }
}
