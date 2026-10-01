package PL_DoublyLinkedList_Initial.src.test.java.esinf;

import PL_DoublyLinkedList_Initial.src.main.java.esinf.DoublyLinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;


public class DoublyLinkedListTest {
    
    public DoublyLinkedListTest() {
    }
    
    /**
     * Test of size method, of class DoublyLinkedList.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        DoublyLinkedList<String> instance = new DoublyLinkedList<>();
        assertTrue((instance.size()==0), "result should be zero");
        instance.addFirst(null);
        assertTrue((instance.size()==1), "result should be one");
        instance.addLast(null);
        assertTrue((instance.size()==2), "result should be two");
        instance.removeFirst();
        assertTrue((instance.size()==1), "result should be one");
        instance.removeLast();
        assertTrue((instance.size()==0), "result should be zero");
    }

    /**
     * Test of isEmpty method, of class DoublyLinkedList.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        DoublyLinkedList <String> instance = new DoublyLinkedList<>();

        assertTrue((instance.isEmpty()==true), "result should be yes");
        instance.addFirst(null);
        assertTrue((instance.isEmpty()==false), "result should be no");
        instance.addLast(null);
        assertTrue((instance.isEmpty()==false), "result should be no");
        instance.removeFirst();
        assertTrue((instance.isEmpty()==false), "result should be no");
        instance.removeLast();
        assertTrue((instance.isEmpty()==true), "result should be yes");
    }

    /**
     * Test of first method, of class DoublyLinkedList.
     */
    @Test
    public void testFirst() {
        System.out.println("first");
        DoublyLinkedList <String> instance = new DoublyLinkedList<>();

        assertTrue( (instance.first()==null), "result should be null");
        instance.addFirst("Xpto");
        assertTrue((instance.first().compareTo("Xpto")==0), "result should be Xpto");
        instance.addLast("Ypto");
        assertTrue((instance.first().compareTo("Xpto")==0), "result should be Xpto");
        instance.removeFirst();
        assertTrue((instance.first().compareTo("Ypto")==0), "result should be Ypto");
        instance.removeLast();
        assertTrue((instance.first()==null), "result should be null");
    }

    /**
     * Test of last method, of class DoublyLinkedList.
     */
    @Test
    public void testLast() {
        System.out.println("last");
        DoublyLinkedList <String> instance = new DoublyLinkedList <>();

        assertTrue((instance.last()==null), "result should be null");
        instance.addFirst("Xpto");
        assertTrue((instance.last().compareTo("Xpto")==0), "result should be Xpto");
        instance.addLast("Ypto");
        assertTrue((instance.last().compareTo("Ypto")==0), "result should be Ypto");
        instance.removeLast();
        assertTrue((instance.last().compareTo("Xpto")==0), "result should be Xpto");
        instance.removeFirst();        
        assertTrue((instance.last()==null), "result should be null");
    }

    /**
     * Test of addFirst method, of class DoublyLinkedList.
     */
    @Test
    public void testAddFirst() {
        System.out.println("addFirst");
        DoublyLinkedList <String> instance = new DoublyLinkedList <>();
        
        instance.addFirst("Xpto");
        assertTrue((instance.first().compareTo("Xpto")==0), "result should be Xpto");
        instance.addFirst("Ypto");
        assertTrue((instance.first().compareTo("Ypto")==0), "result should be Ypto");
        instance.addFirst("Zpto");
        assertTrue((instance.first().compareTo("Zpto")==0), "result should be Zpto");
    }

    /**
     * Test of addLast method, of class DoublyLinkedList.
     */
    @Test
    public void testAddLast() {
        System.out.println("addLast");
        DoublyLinkedList <String> instance = new DoublyLinkedList <>();
        
        instance.addLast("Xpto");
        assertTrue((instance.last().compareTo("Xpto")==0), "result should be Xpto");
        instance.addLast("Ypto");
        assertTrue((instance.last().compareTo("Ypto")==0), "result should be Ypto");
        instance.addLast("Zpto");
        assertTrue((instance.last().compareTo("Zpto")==0), "result should be Zpto");
    }

    /**
     * Test of removeFirst method, of class DoublyLinkedList.
     */
    @Test
    public void testRemoveFirst() {
        System.out.println("removeFirst");
        DoublyLinkedList <String> instance = new DoublyLinkedList <>();
        
        instance.addFirst("Xpto");
        instance.addFirst("Ypto");
        instance.addFirst("Zpto");
        
        assertTrue((instance.removeFirst().compareTo("Zpto")==0));
        assertTrue((instance.removeFirst().compareTo("Ypto")==0), "result should be Ypto");
        assertTrue((instance.removeFirst().compareTo("Xpto")==0), "result should be Xpto");
        assertTrue((instance.removeFirst()==null), "result should be null");
    }

    /**
     * Test of removeLast method, of class DoublyLinkedList.
     */
    @Test
    public void testRemoveLast() {
        System.out.println("removeLast");
        DoublyLinkedList <String> instance = new DoublyLinkedList <>();
        
        instance.addLast("Xpto");
        instance.addLast("Ypto");
        instance.addLast("Zpto");
        
        assertTrue((instance.removeLast().compareTo("Zpto")==0));
        assertTrue((instance.removeLast().compareTo("Ypto")==0), "result should be Ypto");
        assertTrue((instance.removeLast().compareTo("Xpto")==0), "result should be Xpto");
        assertTrue((instance.removeLast()==null), "result should be null");
    }
    
    
    /**
     * Test of overridden equals method, of class DoublyLinkedList.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        DoublyLinkedList <String> instance1 = new DoublyLinkedList <>(), instance2 = new DoublyLinkedList <>();
        
        instance1.addLast("Xpto");
        instance1.addLast("Ypto");
        instance1.addLast("Zpto");
        
        instance2.addLast("Xpto");
        assertFalse( (instance1.equals(instance2)), "Lists should not be equal");
        assertFalse( (instance2.equals(instance1)), "Lists should not be equal");
        instance2.addLast("Ypto");
        assertFalse( (instance1.equals(instance2)), "Lists should not be equal");
        assertFalse( (instance2.equals(instance1)), "Lists should not be equal");
        instance2.addLast("Zpto");
        assertTrue( (instance1.equals(instance2)), "Lists should be equal");
        assertTrue( (instance2.equals(instance1)), "Lists should be equal");
    }    

    @Test
    public void testEqualsWithSameInstanceNullAndDifferentType() {
       DoublyLinkedList<String> instance = new DoublyLinkedList<>();

       assertEquals(instance, instance);
       assertNotEquals(instance, null);
       assertNotEquals(instance, "not a list");
    }

    @Test
    public void testHashCodeForEmptyAndPopulatedLists() {
       DoublyLinkedList<String> empty = new DoublyLinkedList<>();
       assertEquals(1, empty.hashCode());

       DoublyLinkedList<String> list = new DoublyLinkedList<>();
       list.addLast("A");
       list.addLast(null);
       list.addLast("B");

       int expected = 1;
       expected = 31 * expected + "A".hashCode();
       expected = 31 * expected;
       expected = 31 * expected + "B".hashCode();

       assertEquals(expected, list.hashCode());
    }
    /**
    * Test of overridden equals method, of class DoublyLinkedList.
    * @throws java.lang.CloneNotSupportedException
    */
    @Test
    public void testClone() throws CloneNotSupportedException {
        System.out.println("clone");
        DoublyLinkedList <String> instance1 = new DoublyLinkedList <>();
        
        instance1.addLast("Xpto");
        instance1.addLast("Ypto");
        instance1.addLast("Zpto");

        @SuppressWarnings("unchecked")
        DoublyLinkedList<String> instance2 = (DoublyLinkedList<String>) instance1.clone();

        assertTrue( (instance1.equals(instance2)), "Lists should be equal");
        assertTrue( (instance1.size()==instance2.size()), "Lists should be of equal size");
        Iterator <String> it1=instance1.iterator();       // Iterator for This list
        Iterator<String> it2=instance2.iterator();        // Iterator for the Other list
        while (it1.hasNext())
        {
            String el1=it1.next(), el2=it2.next();
            assertTrue( (el1.equals(el2)), "Elements should be equal");
        }
        
        // check deep structure
        instance2.removeLast();
        instance2.removeLast();
        instance2.removeLast();
        
        it1=instance1.iterator();       // Iterator for This list
        Integer total=0;
        while (it1.hasNext())
        {
            it1.next();
            total++;
        }
        assertTrue( (total==3), "List should have remained the same");
    }        
}
