package nbt;

public class IntTag extends Tag {
    private final int TAG_ID = 3;

    private String name;
    private int value;

    public IntTag(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public IntTag(int value) {
        this.name = "";
        this.value = value;
    }

    @Override
    public int getTagID() {
        return this.TAG_ID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public boolean equals(IntTag other) {
        return (this.name.equals(other.getName()) && this.value == other.getValue());
    }

    public String toString() {
        return "TAG_Int(" + this.name + "): " + this.value;
    }
}
