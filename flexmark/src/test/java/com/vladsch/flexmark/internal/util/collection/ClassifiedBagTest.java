package com.vladsch.flexmark.internal.util.collection;

import org.junit.Assert;
import org.junit.Test;

public class ClassifiedBagTest {
    @Test
    public void testBasic() throws Exception {
        ClassifiedBag<Class<?>, Object> bag = new ClassifiedBag<>(new Computable<Class<?>, Object>() {
            @Override
            public Class<?> compute(Object value) {
                return value.getClass();
            }
        });

        Object item;
        for (int i = 0; i < 10; i++) {
            item = (Integer) i;
            bag.add(item);
        }

        Assert.assertEquals(true, bag.containsCategory(Integer.class));
        Assert.assertEquals(10, bag.getCategoryCount(Integer.class));
        
        Assert.assertEquals(false, bag.containsCategory(String.class));
        Assert.assertEquals(0, bag.getCategoryCount(String.class));
        
        for (int i = 0; i < 10; i++) {
            item = String.valueOf(i);
            bag.add(item);
        }
        
        Assert.assertEquals(true, bag.containsCategory(String.class));
        Assert.assertEquals(10, bag.getCategoryCount(String.class));
        
        // now we removeIndex them 
        for (int i = 0; i < 10; i+=2) {
            item = (Integer) i;
            bag.remove(item);
        }
        
        Assert.assertEquals(true, bag.containsCategory(Integer.class));
        Assert.assertEquals(5, bag.getCategoryCount(Integer.class));

        Assert.assertEquals(true, bag.containsCategory(String.class));
        Assert.assertEquals(10, bag.getCategoryCount(String.class));

        // now we removeIndex them 
        for (int i = 0; i < 10; i+=2) {
            item = String.valueOf(i);
            bag.remove(item);
        }
        
        Assert.assertEquals(true, bag.containsCategory(Integer.class));
        Assert.assertEquals(5, bag.getCategoryCount(Integer.class));

        Assert.assertEquals(true, bag.containsCategory(String.class));
        Assert.assertEquals(5, bag.getCategoryCount(String.class));

        // now we removeIndex them 
        for (int i = 1; i < 10; i+=2) {
            item = (Integer) i;
            bag.remove(item);
        }
        
        Assert.assertEquals(false, bag.containsCategory(Integer.class));
        Assert.assertEquals(0, bag.getCategoryCount(Integer.class));

        Assert.assertEquals(true, bag.containsCategory(String.class));
        Assert.assertEquals(5, bag.getCategoryCount(String.class));

        // now we removeIndex them 
        for (int i = 1; i < 10; i+=2) {
            item = String.valueOf(i);
            bag.remove(item);
        }
        
        Assert.assertEquals(false, bag.containsCategory(Integer.class));
        Assert.assertEquals(0, bag.getCategoryCount(Integer.class));

        Assert.assertEquals(false, bag.containsCategory(String.class));
        Assert.assertEquals(0, bag.getCategoryCount(String.class));

    }
    
    @Test
    public void testInterleave() throws Exception {
        ClassifiedBag<Class<?>, Object> bag = new ClassifiedBag<>(new Computable<Class<?>, Object>() {
            @Override
            public Class<?> compute(Object value) {
                return value.getClass();
            }
        });

        Object item;
        for (int i = 0; i < 10; i++) {
            item = (Integer) i;
            bag.add(item);
            item = String.valueOf(i);
            bag.add(item);
        }

        Assert.assertEquals(true, bag.containsCategory(Integer.class));
        Assert.assertEquals(10, bag.getCategoryCount(Integer.class));
        
        Assert.assertEquals(true, bag.containsCategory(String.class));
        Assert.assertEquals(10, bag.getCategoryCount(String.class));
    }
}
