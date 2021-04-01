package nbt.tag;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TagIterator implements Iterator<Tag> {
    private Tag currentTag;
    private int currentIndex;
    private int currentTagLength;
    private Stack<Integer> indexStack;

    public TagIterator(Tag currentTag) {
        this.currentTag = currentTag;
        this.currentIndex = 0;
        this.indexStack = new Stack<>();
    }

    @Override
    public boolean hasNext() {
        return this.currentTag != null;
    }

    @Override
    public Tag next() {
        System.out.println("Current index " + this.currentIndex);
        if (this.currentTag instanceof ListTag && ((ListTag) this.currentTag).getPayload().length == 0) {
            this.currentTag = this.currentTag.getParent();
            this.currentIndex = this.indexStack.pop();
        } else if (this.currentTag instanceof CompoundTag && ((CompoundTag) this.currentTag).getPayload().size() == 0) {
            this.currentTag = this.currentTag.getParent();
            this.currentIndex = this.indexStack.pop();
        }

        Tag nextTag = (this.currentTag instanceof CompoundTag) ?
                        ((CompoundTag) this.currentTag).getPayload().get(currentIndex++) :
                        ((ListTag) this.currentTag).getPayload()[currentIndex++];

        if (nextTag instanceof CompoundTag) {
            this.indexStack.push(this.currentIndex);
            this.currentIndex = 0;
            this.currentTag = nextTag;
        } else if (nextTag instanceof EndTag) {
            this.currentTag = this.currentTag.getParent();
            if (!this.indexStack.isEmpty()) {
                this.currentIndex = this.indexStack.pop();
            }
        } else if (nextTag instanceof ListTag) {
            this.indexStack.push(this.currentIndex);
            this.currentIndex = 0;
            this.currentTag = nextTag;
        } else if (nextTag.getParent() instanceof ListTag && this.currentIndex > ((ListTag) this.currentTag).getPayload().length) {
            this.currentTag = this.currentTag.getParent();
            if (!this.indexStack.isEmpty()) {
                this.currentIndex = indexStack.pop();
            }
        }

        return nextTag;
    }
}
