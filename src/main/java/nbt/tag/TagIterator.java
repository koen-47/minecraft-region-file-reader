package nbt.tag;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TagIterator implements Iterator<Tag> {
    private Tag currentTag;
    private Iterator<Tag> currentIterator;
    private Stack<Iterator<Tag>> iteratorStack;

    public TagIterator(Tag currentTag) {
        this.currentTag = currentTag;
        if (this.currentTag instanceof CompoundTag) {
            this.currentIterator = ((CompoundTag) this.currentTag).getPayload().iterator();
        } else {
            this.currentIterator = Arrays.asList(((ListTag) this.currentTag).getPayload()).iterator();
        }

        this.iteratorStack = new Stack<>();
        this.iteratorStack.push(this.currentIterator);
    }

    @Override
    public boolean hasNext() {
        return this.currentIterator.hasNext();
    }

    @Override
    public Tag next() {
        Tag nextTag = this.currentIterator.next();
        System.out.println(nextTag.getClass());

        if (nextTag instanceof CompoundTag && ((CompoundTag) nextTag).getPayload().size() > 0) {
            this.iteratorStack.push(this.currentIterator);
            this.currentIterator = ((CompoundTag) nextTag).getPayload().iterator();
        } else if (nextTag instanceof EndTag) {
            this.currentIterator = this.iteratorStack.pop();
        } else if (nextTag instanceof ListTag && ((ListTag) nextTag).getPayload().length > 0) {
            this.iteratorStack.push(this.currentIterator);
            this.currentIterator = Arrays.asList(((ListTag) nextTag).getPayload()).iterator();
        } else if (nextTag.getParent() instanceof ListTag && !this.currentIterator.hasNext()) {
            System.out.println("end of list tag reached");
            this.currentIterator = this.iteratorStack.pop();
        }

        return nextTag;
    }
}
