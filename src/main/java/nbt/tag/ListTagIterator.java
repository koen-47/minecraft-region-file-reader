package nbt.tag;

import java.util.Iterator;
import java.util.Stack;

public class ListTagIterator implements Iterator<Tag> {
    private ListTag currentListTag;
    private int currentIndex;
    private Stack<Integer> indexStack;

    public ListTagIterator(ListTag currentListTag) {
        this.currentListTag = currentListTag;
        this.currentIndex = 0;
        this.indexStack = new Stack<>();
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Tag next() {
        Tag nextTag = this.currentListTag.getPayload()[currentIndex++];

        if (nextTag instanceof ListTag) {
            this.indexStack.push(this.currentIndex);
            this.currentIndex = 0;
            this.currentListTag = (ListTag) nextTag;
        } else if (this.currentIndex > this.currentListTag.getPayload().length-1) {
            this.currentListTag = (ListTag) this.currentListTag.getParent();
            if (!this.indexStack.isEmpty()) {
                this.currentIndex = indexStack.pop();
            }
        } else if (nextTag instanceof CompoundTag) {
            System.out.println("compound tag reached");
            CompoundTagIterator it = new CompoundTagIterator((CompoundTag) nextTag);
            nextTag = it.next();
        }

        return nextTag;
    }
}
