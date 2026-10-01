/**
*
*/
package PL_DoublyLinkedList_Initial.src.test.java.esinf;

import PL_DoublyLinkedList_Initial.src.main.java.esinf.DoublyLinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class DoublyLinkedListIteratorTest {
    
    public DoublyLinkedListIteratorTest() {
    }    
    /**
     * Test of hasNext, next methods, of class DoublyLinkedListIterator.
     */
    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        
        DoublyLinkedList<Integer> instance = new DoublyLinkedList <>();
        
        for (int x=0; x<10; x++)
            instance.addLast(x);
        
        ListIterator <Integer> itr = instance.listIterator();
        for (int x=0; x<10; x++) {
            assertTrue( (itr.next()==x), "There should be as many as there were inserted");
        }
    }
    /**
     * Test of hasPrevious, previous methods, of class DoublyLinkedListIterator.
     */    
    @Test
    public void testHasPrevious() {
        System.out.println("hasPrevious");
        
        DoublyLinkedList <Integer> instance = new DoublyLinkedList <>();        
        
        for (int x=0; x<10; x++)
            instance.addFirst(x);
        
        ListIterator <Integer> itr = instance.listIterator();
        while (itr.hasNext()) itr.next();
        for (int x=0; x<10; x++) {
            assertTrue( (itr.previous()==x), "There should be as many as there were inserted");
        }
    }
    /**
     * Test of nextIndex method, of class DoublyLinkedListIterator.
     */
    @Test
    public void testNextIndex() {
        System.out.println("nextIndex");
        
        DoublyLinkedList <Integer> instance = new DoublyLinkedList <>();        
        
        for (int x=0; x<10; x++)
            instance.addLast(x);
        
        ListIterator <Integer> itr = instance.listIterator();
        for (int x=0; x<=10; x++) {
            assertTrue( (itr.nextIndex()==x), "The indexes should be in order");
            if (itr.hasNext()) itr.next();
        }
    }
    /**
     * Test of previousIndex method, of class DoublyLinkedListIterator.
     */    
    @Test
    public void testPreviousIndex() {
        System.out.println("previousIndex");
        
        DoublyLinkedList <Integer> instance = new DoublyLinkedList <>();        
        
        for (int x=0; x<10; x++)
            instance.addFirst(x);
        
        ListIterator <Integer> itr = instance.listIterator();
        while (itr.hasNext()) itr.next();
        for (int x=9; x>=-1; x--) {
            assertTrue( (itr.previousIndex()==x), "The indexes should be in inverse order");
            if (itr.hasPrevious()) itr.previous();
        }
    }    
    /**
     * Test of remove method, of class DoublyLinkedListIterator.
     */    
    @Test
    public void testRemove() {
        System.out.println("remove");
        
        DoublyLinkedList <Integer> instance = new DoublyLinkedList <>();        
        
        for (int x=0; x<10; x++)
            instance.addLast(x);
        
        ListIterator <Integer> itr = instance.listIterator();

        try {
            itr.remove();
            assertTrue( false, "There should have been an exception" );
        } catch (NoSuchElementException e) {         
        }        
        
        for (int x=0; x<10; x++) {
            itr.next();
            itr.remove();
        }

        assertThrows(NoSuchElementException.class, () -> itr.remove());
    }
    /**
     * Test of remove method, of class DoublyLinkedListIterator.
     */    
    @Test
    public void testSet() {
        System.out.println("set");
        
        DoublyLinkedList <Integer> instance = new DoublyLinkedList <>();        
        
        for (int x=0; x<10; x++)
            instance.addLast(x);
        
        ListIterator <Integer> itr = instance.listIterator();

        assertThrows(NoSuchElementException.class, () -> itr.set(99));
        
        for (int x=0; x<10; x++) {
            itr.next();
            itr.set(99);
        }

        for (Integer x : instance)
            assertTrue( (x==99), "All elements should be 99");
    }
    /**
     * Test of add method, of class DoublyLinkedListIterator.
     */    
    @Test
    public void testAdd() {
        System.out.println("add");
        
        DoublyLinkedList <Integer> instance = new DoublyLinkedList <>();        
        ListIterator <Integer> itr = instance.listIterator();
        
        for (int x=0; x<10; x++)
            itr.add(x);
        
        while (itr.hasPrevious())
            itr.previous();
        
        for (int x=0; x<10; x++) {
            assertTrue( (itr.next()==x), "All elements should be in order");
        }
    }
    /**
     * Test of add method, of class DoublyLinkedListIterator.
     */
    @Test
    public void testCurrentModifications() {
        DoublyLinkedList <Integer> instance = new DoublyLinkedList <>();
        for (int x=0; x<10; x++)
            instance.addLast(x);

        ListIterator <Integer> itr1 = instance.listIterator();
        itr1.next();
        instance.addFirst(99);
        assertThrows(ConcurrentModificationException.class, () -> itr1.add(99));
        assertThrows(ConcurrentModificationException.class, () -> itr1.remove());

        ListIterator <Integer> itr2 = instance.listIterator();
        itr2.next();
        instance.addLast(99);
        assertThrows(ConcurrentModificationException.class, () -> itr2.add(99));
        assertThrows(ConcurrentModificationException.class, () -> itr2.remove());

        ListIterator <Integer> itr3 = instance.listIterator();
        itr3.next();
        instance.removeFirst();
        assertThrows(ConcurrentModificationException.class, () -> itr3.add(99));
        assertThrows(ConcurrentModificationException.class, () -> itr3.remove());

        ListIterator <Integer> itr4 = instance.listIterator();
        itr4.next();
        instance.removeLast();
        assertThrows(ConcurrentModificationException.class, () -> itr4.add(99));
        assertThrows(ConcurrentModificationException.class, () -> itr4.remove());
    }

    @Test
    public void testRemoveRequiresLastReturnedElement() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        instance.addLast(1);
        instance.addLast(2);

        ListIterator<Integer> itr = instance.listIterator();
        assertThrows(NoSuchElementException.class, itr::remove);

        itr.next();
        assertDoesNotThrow(itr::remove);
        assertThrows(NoSuchElementException.class, itr::remove);
    }

    @Test
    public void testRemoveAfterPreviousMaintainsBidirectionalLinksAndIndexes() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        instance.addLast(1);
        instance.addLast(2);
        instance.addLast(3);

        ListIterator<Integer> itr = instance.listIterator();
        assertEquals(1, itr.next());
        assertEquals(2, itr.next());
        assertEquals(2, itr.previous());

        itr.remove();

        assertEquals(2, instance.size());
        assertEquals(1, itr.nextIndex());
        assertEquals(0, itr.previousIndex());
        assertEquals(1, itr.previous());
        assertFalse(itr.hasPrevious());
        assertEquals(1, itr.next());
        assertEquals(1, instance.first());
    }

    @Test
    public void testSetRequiresLastReturnedElement() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        instance.addLast(10);
        instance.addLast(20);

        ListIterator<Integer> itr = instance.listIterator();
        assertThrows(NoSuchElementException.class, () -> itr.set(99));

        itr.next();
        itr.set(77);
        assertEquals(77, instance.first());
    }

    @Test
    public void testSetDetectsConcurrentModification() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        instance.addLast(10);
        instance.addLast(20);

        ListIterator<Integer> itr = instance.listIterator();
        itr.next();
        instance.addLast(30);

        assertThrows(ConcurrentModificationException.class, () -> itr.set(99));
    }

    @Test
    public void testAddAfterRemovalKeepsOrderAndState() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        for (int x = 0; x < 5; x++) {
            instance.addLast(x);
        }

        ListIterator<Integer> itr = instance.listIterator();
        itr.next();
        itr.next();
        itr.remove();
        itr.add(99);

        assertEquals(5, instance.size());
        int[] expected = {0, 99, 2, 3, 4};
        int index = 0;
        for (Integer value : instance) {
            assertEquals(expected[index++], value);
        }
    }

    @Test
    public void testAddUpdatesPreviousLinksAndIndices() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        instance.addLast(1);
        instance.addLast(3);

        ListIterator<Integer> itr = instance.listIterator();
        assertEquals(1, itr.next());
        itr.add(2);

        assertEquals(3, instance.size());
        assertEquals(1, itr.previousIndex());
        assertEquals(2, itr.nextIndex());
        assertEquals(2, itr.previous());
        assertEquals(2, itr.next());
        assertEquals(3, itr.next());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testAddIncrementsSizeAndModCountAcrossMultipleInsertions() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        ListIterator<Integer> itr = instance.listIterator();

        itr.add(10);
        itr.add(20);

        assertEquals(2, instance.size());
        assertEquals(20, instance.last());
        assertEquals(2, itr.nextIndex());
        assertEquals(1, itr.previousIndex());
    }

    @Test
    public void testIteratorShouldFailOnConcurrentModificationAfterNextAndPrevious() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        for (int x = 0; x < 5; x++) {
            instance.addLast(x);
        }

        ListIterator<Integer> itr = instance.listIterator();
        itr.next();
        itr.previous();

        instance.addFirst(100);
        assertThrows(ConcurrentModificationException.class, itr::next);
        assertThrows(ConcurrentModificationException.class, itr::previous);
        assertThrows(ConcurrentModificationException.class, () -> itr.add(99), "should fail when list changed externally");
        assertThrows(ConcurrentModificationException.class, itr::remove);
    }

    @Test
    public void testPreviousIndexAtBeginningAndEnd() {
        DoublyLinkedList<Integer> instance = new DoublyLinkedList<>();
        instance.addLast(1);
        instance.addLast(2);

        ListIterator<Integer> itr = instance.listIterator();
        assertEquals(-1, itr.previousIndex());
        assertEquals(0, itr.nextIndex());

        itr.next();
        assertEquals(0, itr.previousIndex());
        assertEquals(1, itr.nextIndex());

        itr.next();
        assertEquals(1, itr.previousIndex());
        assertEquals(2, itr.nextIndex());
    }
}
