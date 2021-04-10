package nbt.tag;

/**
 * Functional interface that defines a operation that is used to search for tags that fit certain conditions.
 * @author Killerkoen
 */
@FunctionalInterface
public interface TagOperation {
    /**
     * Returns {@code true} if the tag fits the specified conditions.
     * @param tag the tag to compare against
     * @return {@code true} if the tag fits the specified conditions
     */
    boolean findTag(Tag tag);
}
