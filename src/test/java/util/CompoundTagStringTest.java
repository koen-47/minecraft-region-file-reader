package util;

import nbt.CompoundTag;
import nbt.IntTag;
import org.junit.jupiter.api.Test;

import java.util.Random;

// this is not really a test class, this is only really used to see if stuff is printed correctly when i try
// to convert a Tag to a String without having to call main :)

public class CompoundTagStringTest {

    @Test
    public void testBasicCompoundTagPrinter() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                       new IntTag("testIntTag", 1));

        CompoundTagString testCompoundTagString = new CompoundTagString(testCompoundTag);
        System.out.println(testCompoundTagString.getString());
    }

    @Test
    public void testNestedCompoundTagPrinter() {
        CompoundTag testCompoundTagInner = new CompoundTag("testCompoundTagInner",
                                                            new IntTag("testIntTagInner1", 1),
                                                            new IntTag("testIntTagInner2", 2));

        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter",
                                                            new IntTag("testIntTagOuter1", 3),
                                                            new IntTag("testIntTagOuter2", 4),
                                                            testCompoundTagInner);

        CompoundTagString testCompoundTagString = new CompoundTagString(testCompoundTagOuter);
        System.out.println(testCompoundTagString.getString());

    }

    @Test
    public void testDoubleNestedCompoundTagPrinter() {
        CompoundTag testCompoundTagDoubleInner = new CompoundTag("testCompoundTagDoubleInner",
                                                                 new IntTag("testIntTagDoubleInner1", 0),
                                                                 new IntTag("testIntTagDoubleInner2", 1));

        CompoundTag testCompoundTagInner = new CompoundTag("testCompoundTagInner",
                                                            new IntTag("testIntTagInner1", 2),
                                                            new IntTag("testIntTagInner2", 3),
                                                            testCompoundTagDoubleInner);

        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter",
                                                            new IntTag("testIntTagOuter1", 4),
                                                            new IntTag("testIntTagOuter2", 5),
                                                            testCompoundTagInner);

        CompoundTagString compoundTagString = new CompoundTagString(testCompoundTagOuter);
        System.out.println(compoundTagString.getString());
    }
    
    @Test
    public void testRandomCompoundTagPrinter() {
        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter");
        CompoundTag testCompleteCompoundTag = this.generateNestedCompoundTag(testCompoundTagOuter, 0);
        System.out.println(new CompoundTagString(testCompleteCompoundTag).getString());
    }

    private CompoundTag generateNestedCompoundTag(CompoundTag currentCompoundTag, int depth) {
        Random rng = new Random();
        for (int i = 0; i < rng.nextInt(3)+1; i++)
            currentCompoundTag.append(new IntTag("testIntTag", rng.nextInt(100)+1));

        CompoundTag nestedCompoundTag = new CompoundTag("testCompoundTag" + depth, currentCompoundTag);

        if (depth == 8)
            return nestedCompoundTag;

        return this.generateNestedCompoundTag(nestedCompoundTag, depth + 1);
    }

}
