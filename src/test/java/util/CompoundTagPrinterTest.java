package util;

import nbt.CompoundTag;
import nbt.EndTag;
import nbt.IntTag;
import org.junit.jupiter.api.Test;

import java.util.Random;

// this is not really a test class :) this is only really used to see if stuff is printed correctly
public class CompoundTagPrinterTest {

    @Test 
    public void testBasicCompoundTagPrinter() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag", new IntTag("testIntTag", 1), new EndTag());
        CompoundTagPrinter testCompoundTagPrinter = new CompoundTagPrinter(testCompoundTag);
        System.out.println(testCompoundTagPrinter.stringify());
    }

    @Test
    public void testNestedCompoundTagPrinter() {
        CompoundTag testCompoundTagInner = new CompoundTag("testCompoundTagInner",
                                                            new IntTag("testIntTagInner1", 1),
                                                            new IntTag("testIntTagInner2", 2),
                                                            new EndTag());

        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter",
                                                            new IntTag("testIntTagOuter1", 3),
                                                            new IntTag("testIntTagOuter2", 4),
                                                            testCompoundTagInner,
                                                            new EndTag());

        CompoundTagPrinter testCompoundTagPrinter = new CompoundTagPrinter(testCompoundTagOuter);
        System.out.println(testCompoundTagPrinter.stringify());

    }

    @Test
    public void testDoubleNestedCompoundTagPrinter() {
        CompoundTag testCompoundTagDoubleInner = new CompoundTag("testCompoundTagDoubleInner",
                                                                 new IntTag("testIntTagDoubleInner1", 0),
                                                                 new IntTag("testIntTagDoubleInner2", 1),
                                                                 new EndTag());

        CompoundTag testCompoundTagInner = new CompoundTag("testCompoundTagInner",
                                                            new IntTag("testIntTagInner1", 2),
                                                            new IntTag("testIntTagInner2", 3),
                                                            testCompoundTagDoubleInner,
                                                            new EndTag());

        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter",
                                                            new IntTag("testIntTagOuter1", 4),
                                                            new IntTag("testIntTagOuter2", 5),
                                                            testCompoundTagInner,
                                                            new EndTag());

        CompoundTagPrinter compoundTagPrinter = new CompoundTagPrinter(testCompoundTagOuter);
        System.out.println(compoundTagPrinter.stringify());
    }
    
    @Test
    public void testRandomCompoundTagPrinter() {
        Random rng = new Random();

    }

}
