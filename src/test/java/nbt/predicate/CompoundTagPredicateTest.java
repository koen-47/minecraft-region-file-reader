package nbt.predicate;

import nbt.tag.CompoundTag;
import nbt.tag.IntTag;
import nbt.tag.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CompoundTagPredicateTest {

    @Test
    public void testPredicate() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        Predicate<Integer> p1 = num -> num > 2;
        List<Integer> filtered = list.stream().filter(p1).collect(Collectors.toList());
        System.out.println(filtered);

        ArrayList<IntTag> list2 = new ArrayList<>();
        list2.add(new IntTag("abc", 2));
        list2.add(new IntTag("abc", 3));
        list2.add(new IntTag("abc", 1));

        IntTag testIntTag = new IntTag("abc", 1);
        Predicate<IntTag> p2 = tag -> tag.getName().equals(testIntTag.getName());
        List<IntTag> filtered2 = list2.stream().filter(p2).collect(Collectors.toList());
        System.out.println(filtered2);

        IntTag targetIntTag = new IntTag("testIntTag", 2);
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag", 1),
                                                        new IntTag("testIntTag", 2),
                                                        new IntTag("testIntTag", 3));

        Predicate<Tag> p3 = tag -> tag.getName() == (targetIntTag.getName());
        List<Tag> filtered3 = testCompoundTag.getPayload().stream().filter(p3).collect(Collectors.toList());
        System.out.println(filtered3);

        IntTag targetIntTag2 = new IntTag("testIntTag", 3);
        CompoundTag testCompoundTag2 = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag", 1),
                                                        new CompoundTag("testCompoundTag2",
                                                                new IntTag("testIntTag", 2),
                                                                new IntTag("testIntTag", 3)));

        CompoundTagOperation operation = (tag -> tag.getName().equals("testIntTag"));

    }

}
