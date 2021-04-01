package nbt.tag;

import java.util.Iterator;
import java.util.Stack;

public class CompoundTagIterator implements Iterator<Tag> {
    private CompoundTag currentCompoundTag;
    private int currentIndex = 0;
    private Stack<Integer> indexStack;

    public CompoundTagIterator(CompoundTag currentCompoundTag) {
        this.currentCompoundTag = currentCompoundTag;
        this.currentIndex = 0;
        this.indexStack = new Stack<>();
    }

    @Override
    public boolean hasNext() {
        return this.currentCompoundTag != null;
    }

    @Override
    public Tag next() {
        Tag nextTag = this.currentCompoundTag.getPayload().get(currentIndex++);

        if (nextTag instanceof CompoundTag) {
            this.indexStack.push(this.currentIndex);
            this.currentIndex = 0;
            this.currentCompoundTag = (CompoundTag) nextTag;
        } else if (nextTag instanceof EndTag) {
            this.currentCompoundTag = (CompoundTag) this.currentCompoundTag.getParent();
            if (!this.indexStack.isEmpty()) {
                this.currentIndex = this.indexStack.pop();
            }
        } else if (nextTag instanceof ListTag) {
            this.currentCompoundTag = (CompoundTag) nextTag;
        }

        return nextTag;
    }

    private Tag next(Tag nextCompoundTag) {
        return null;
    }
}
