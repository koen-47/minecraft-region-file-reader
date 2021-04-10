package nbt.tag;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * An iterator that is designed to loop through the contained tags within compound and list tags.
 * @author Killerkoen
 */
public class TagIterator implements Iterator<Tag> {
    /**
     * The current instance of a Tag object being iterated over.
     */
    private Tag currentTag;

    /**
     * The current instance of an Iterator object that is used to find the next tag.
     */
    private Iterator<Tag> currentIterator;

    /**
     * An instance of a Stack object that is used to track which iterator needs to be used to find the next tag.
     */
    private Stack<Iterator<Tag>> iteratorStack;

    /**
     * Constructs an instance of a new TagIterator object starting from the specified parameter.
     * @param currentTag the tag from which to start iterating
     */
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

    /**
     * Returns {@code true} if the iteration has more tags.
     * @return {@code true} if the iteration has more tags.
     */
    @Override
    public boolean hasNext() {
        return this.currentIterator.hasNext();
    }

    /**
     * Returns the next tag in this iteration.
     * @return the next tag in this iteration
     */
    @Override
    public Tag next() {
        Tag nextTag = this.currentIterator.next();

        if (nextTag instanceof CompoundTag && ((CompoundTag) nextTag).getPayload().size() > 0) {
            this.iteratorStack.push(this.currentIterator);
            this.currentIterator = ((CompoundTag) nextTag).getPayload().iterator();
        } else if (nextTag instanceof EndTag) {
            if (nextTag.getParent().getParent() instanceof ListTag && !this.iteratorStack.peek().hasNext()) {
                if (!this.iteratorStack.isEmpty()) {
                    this.currentIterator = this.iteratorStack.pop();
                }
            }

            if (!this.iteratorStack.isEmpty()) {
                this.currentIterator = this.iteratorStack.pop();
            }
        } else if (nextTag instanceof ListTag && ((ListTag) nextTag).getPayload().length > 0) {
            this.iteratorStack.push(this.currentIterator);
            this.currentIterator = Arrays.asList(((ListTag) nextTag).getPayload()).iterator();
        } else if (nextTag.getParent() instanceof ListTag && !this.currentIterator.hasNext()) {
            if (!this.iteratorStack.isEmpty()) {
                this.currentIterator = this.iteratorStack.pop();
            }
        }

        return nextTag;
    }
}
