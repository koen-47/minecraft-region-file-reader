package nbt.tag;

import java.util.Iterator;
import java.util.Stack;

public class ListTagIterator implements Iterator<Tag> {
    private ListTag currentListTag;
    private int currentIndex = 0;
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
        System.out.println(this.currentIndex);
        Tag nextTag = this.currentListTag.getPayload()[currentIndex++];

        if (nextTag instanceof ListTag) {
            this.indexStack.push(this.currentIndex);
            this.currentIndex = 0;
            this.currentListTag = (ListTag) nextTag;
        } else if (currentIndex == this.currentListTag.getPayload().length) {
            this.currentListTag = (ListTag) nextTag.getParent();
            if (!this.indexStack.isEmpty()) {
                this.currentIndex = this.indexStack.pop();
            }

            System.out.println(currentListTag.toString());
        }


        return nextTag;
    }
}
