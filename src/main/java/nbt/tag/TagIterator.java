package nbt.tag;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TagIterator implements Iterator<Tag> {
    private Tag currentTag;
    private int currentIndex;
    private boolean hasNext;
    private Stack<Integer> indexStack;

    public TagIterator(Tag currentTag) {
        this.currentTag = currentTag;
        this.currentIndex = 0;
        this.indexStack = new Stack<>();
        this.indexStack.push(this.currentIndex);
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
        } else if (nextTag.getParent() instanceof ListTag && this.currentIndex >= ((ListTag) this.currentTag).getPayload().length) {
            System.out.print("End of listtag");
            this.currentTag = nextTag.getParent();
            this.currentIndex = indexStack.pop();

            if (this.currentIndex >= ((ListTag) this.currentTag).getPayload().length) {
                System.out.println("current index is larger than payload length");
                this.currentTag = null;
            }
        }

        return nextTag;
    }
}
