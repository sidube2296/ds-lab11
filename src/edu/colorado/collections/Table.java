// File: Table.java from the package edu.colorado.collections
// Complete documentation is available from the Table link in:
//   http://www.cs.colorado.edu/~main/docs

package edu.colorado.collections;

import java.io.IOException;
import java.io.Writer;

/******************************************************************************
* A <CODE>Table</CODE> is an open-address hash table with a fixed capacity.
* The purpose is to show students how an open-address hash table is
* implemented. Programs should generally use java.util.Hashtable
* rather than this hash table.
*
* <dt><b>Java Source Code for this class:</b><dd>
*   <A HREF="../../../../edu/colorado/collections/Table.java">
*   http://www.cs.colorado.edu/~main/edu/colorado/collections/Table.java
*   </A>
*
* @author Michael Main 
*   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
*
* @version
*   Jun 12, 1998
******************************************************************************/
public class Table
{
   // Invariant of the Table class:
   //   1. The number of items in the table is in the instance variable manyItems.
   //   2. The preferred location for an element with a given key is at index
   //      hash(key). If a collision occurs, then next-Index is used to search
   //      forward to find the next open address. When an open address is found
   //      at an index i, then the element itself is placed in data[i] and the
   //      elements key is placed at keys[i].
   //   3. An index i that is not currently used has data[i] and key[i] set to
   //      null.
   //   4. If an index i has been used at some point (now or in the past), then
   //      hasBeenUsed[i] is true; otherwise it is false.
   private int manyItems;
   private Object[ ] keys;
   private Object[ ] data;
   private boolean[ ] hasBeenUsed;   

   /**
   * Initialize an empty table with a specified capacity.
   * @param <CODE>capacity</CODE>
   *   the capacity for this new open-address hash table
   * <dt><b>Postcondition:</b><dd>
   *   This table is empty and has the specified capacity.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the specified capacity. 
   **/   
   public Table(int capacity)
   {
      // The manyItems instance variable is automatically set to zero.
      // which is the correct initial value. The three arrays are allocated to
      // be the specified capacity. The boolean array is automatically
      // initialized to falses, and the other two arrays are automatically
      // initialized to all null.
      if (capacity <= 0)
         throw new IllegalArgumentException("Capacity is negative");
      keys = new Object[capacity];
      data = new Object[capacity];
      hasBeenUsed = new boolean[capacity];
   }
   
   
   /**
   * Determines whether a specified key is in this table.
   * @param <CODE>key</CODE>
   *   the non-null key to look for
   * <dt><b>Precondition:</b><dd>
   *   <CODE>key</CODE> cannot be null.
   * @return
   *   <CODE>true</CODE? (if this table contains an object with the specified 
   *   key); <CODE>false</CODE> otherwise. Note that <CODE>key.equals( )</CODE>
   *   is used to compare the <CODE>key</CODE> to the keys that are in the 
   *   table.
   * @exception NullPointerException
   *   Indicates that <CODE>key</CODE> is null.
   **/
   public boolean containsKey(Object key)
   {
      return findIndex(key) != -1;
   }
   
   
   private int findIndex(Object key)
   // Postcondition: If the specified key is found in the table, then the return
   // value is the index of the specified key. Otherwise, the return value is -1.
   {
      int count = 0;
      int i = hash(key);
      
      while (count < data.length && hasBeenUsed[i])
      {
         if (key.equals(keys[i]))
            return i;
         count++;
         i = nextIndex(i);
      }
      
      return -1;
   }
      
   
   /** Retrieves an object for a specified key.
   * @param <CODE>key</CODE>
   *   the non-null key to look for
   * <dt><b>Precondition:</b><dd>
   *   <CODE>key</CODE> cannot be null.
   * @return
   *   a reference to the object with the specified <CODE>key</CODE (if this 
   *   table contains an such an object);  null otherwise. Note that 
   *   <CODE>key.equals( )</CODE> is used to compare the <CODE>key</CODE>
   *   to the keys that are in the table.
   * @exception NullPointerException
   *   Indicates that <CODE>key</CODE> is null.
   **/
   public Object get(Object key)
   {
      int index = findIndex(key);
      
      if (index == -1)
         return null;
      else
         return data[index];
   }
   
   
   private int hash(Object key)
   // The return value is a valid index of the tables arrays. The index is
   // calculated as the remainder when the absolute value of the keys
   // hash code is divided by the size of the tables arrays.
   {
      return Math.abs(key.hashCode( )) % data.length;
   }
   
   
   private int nextIndex(int i)
   // The return value is normally i+1. But if i+1 is data.length, then the 
   // return value is zero instead.
   {
      if (i+1 == data.length)
         return 0;
      else
         return i+1;
   }
   
   
   /**
   * Add a new element to this table, using the specified key.
   * @param <CODE>key</CODE>
   *   the non-null key to use for the new element
   * @param <CODE>element</CODE>
   *   the new element thats being added to this table
   * <dt><b>Precondition:</b><dd>
   *   If there is not already an element with the specified <CODE>key</CODE>,
   *   then this tables size must be less than its capacity 
   *   (i.e., <CODE>size() < capacity()</CODE>). Also, neither <CODE>key</CODE>
   *   nor </CODE>element</CODE> is null.
   * <dt><b>Postcondition:</b><dd>
   *   If this table already has an object with the specified <CODE>key</CODE>,
   *   then that object is replaced by </CODE>element</CODE>, and the return 
   *   value is a reference to the replaced object. Otherwise, the new 
   *   </CODE>element</CODE> is added with the specified <CODE>key</CODE>
   *   and the return value is null.
   * @exception IllegalStateException
   *   Indicates that there is no room for a new object in this table.
   * @exception NullPointerException
   *   Indicates that <CODE>key</CODE> or <CODE>element</CODE> is null.   
   **/
   public Object put(Object key, Object element)
   {
      int index = findIndex(key);
      Object answer;
      
      if (index != -1)
      {  // The key is already in the table.
         answer = data[index];
         data[index] = element;
         return answer;
      }
      else if (manyItems < data.length)
      {  // The key is not yet in this Table.
         index = hash(key);
         while (keys[index] != null)
            index = nextIndex(index);
         keys[index] = key;
         data[index] = element;
         hasBeenUsed[index] = true;
         manyItems++;
         return null;
      }
      else
      {  // The table is full.
         throw new IllegalStateException("Table is full.");
      }
   }
      
   
   /**
   * Removes an object for a specified key.
   * @param <CODE>key</CODE>
   *   the non-null key to look for
   * <dt><b>Precondition:</b><dd>
   *   <CODE>key</CODE> cannot be null.
   * <dt><b>Postcondition:</b><dd>
   *   If an object was found with the specified </CODE>key</CODE>, then that
   *   object has been removed from this table and a copy of the removed object
   *   is returned; otherwise, this table is unchanged and the null reference
   *   is returned.  Note that 
   *   <CODE>key.equals( )</CODE> is used to compare the <CODE>key</CODE>
   *   to the keys that are in the table.
   * @exception NullPointerException
   *   Indicates that </CODE>key</CODE> is null.
   **/
   public Object remove(Object key)
   {
      int index = findIndex(key);
      Object answer = null;
      
      if (index != -1)
      {
         answer = data[index];
         keys[index] = null;
         data[index] = null;
	 manyItems--;
      }
      
      return answer;
   }
   
   /**
    * Prints the contents of the table in the following format
    * 
    * | string : data | string : data | string : data |
    * where string is the string representation of the Object
    * 
    * @param out, stream to write to
    * @throws IOException 
    */
   public void printContents(Writer out) throws IOException {
	   out.write("| ");
	   for(int i = 0; i < keys.length; i++) {
		   out.append(keys[i] == null ? "<empty>" : keys[i].toString() );
		   out.append(" : ");
		   out.append(data[i] == null ? "<empty>" : data[i].toString() );
		   out.append(" | ");
	   }
	   out.flush();
   }
}
           