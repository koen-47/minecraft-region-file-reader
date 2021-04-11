# Minecraft Region File Reader
A basic implementation of the NBT file format used by Minecraft to store information about 
the state of a generated world. It is currently only capable of reading such files,
rather than write to them. Additionally, this library does not yet support the 
conversion from NBT to SNBT and vice versa. These features may be implemented at a 
later date.

JDK 11 is required to run this library.

### Specification
The NBT format is a tag-based information system that is capable of storing different
data types from Java. <a href="https://minecraft.fandom.com/wiki/NBT_format#:~:text=The%20Named%20Binary%20Tag%20(NBT,an%20ID%20and%20a%20name." target="_blank">This</a> 
link provides some helpful pointers to how this format is implemented and used in
Minecraft.

#### My modfications to these rules
Below is a list of modifications that deviate from the protocols above in an effort to
provide deeper functionality in how these tags can be used:
* Each tag contains a reference to it's parent tag which is any tag that is capable of
holding other tags. In this case, that would only be compound and list tags.
    * The parent of the root tag is ``null``.
    
* Each tag can be converted back into an array of bytes using the ``toByteArray()`` method. 
    * The conversion follows the same protocols laid out in the specification above.
    
* The ``CompoundTag`` and ``ListTag`` class contain methods that can be used to search it's
sub-tags that fit certain conditions. Examples of how this method is used can be seen below. 
At this moment in time, the currently supported search methods are:
    * ``find`` - finds the first instance of the target tag
    * ``findAll`` - finds all instances of the target tag
    * ``findAllParents`` - finds the parents of all instances of the target tag
    * ``contains`` - returns true if this tag contains the target tag

## Installation
TBA

## Examples
### Reading .mca and .dat files
Below is an example of how to read .mca and .dat files.
```java
// .mca files
MCAReader mcaReader = new MCAReader("/r.0.0.mca");
MCAFile mcaFile = mcaReader.readMCAFile();

Chunk chunk00 = mcaFile.getChunk(0, 0);
CompoundTag root = chunk00.toNBTTag();

// .dat files
DATReader datReader = new DATReader("/level.dat");
DATFile datFile = datReader.readDATFile();

CompoundTag root = datFile.getData();
```
It is important to note that the `MCAReader` and `DATReader` class requires that
.mca and .dat files must be placed in the /resources folder in order to find them.

### NBT tags
All tags must have a payload corresponding to the tag type and may optionally
have a name. Below is an example using the ``IntTag`` and ``StringTag`` classes:
```java
// Unnamed
IntTag testIntTag1 = new IntTag(1);
StringTag testStringTag1 = new StringTag("test123");

// Named
IntTag testIntTag2 = new IntTag("testIntTag2", 1);
StringTag testStringTag2 = new StringTag("testStringTag2", "test123");
```
Unnamed tags contain an empty string in place of their name, but still contain
a method to retrieve it. The rule above is followed by all tags, including 
compound and list tags.

#### Compound tags
This block of code shows an example of the first of two possible ways to create a 
```CompoundTag``` object containing a named IntTag and StringTag object:
```java
ArrayList<Tag> containedTags = new ArrayList<>();
containedTags.add(new IntTag("testIntTag", 1));
containedTags.add(new StringTag("testStringTag", "abc"));
CompoundTag testCompoundTag1 = new CompoundTag("testCompoundTag1", containedTags);
```
The second version utilizes variable arguments to produce the same result:
```java
CompoundTag testCompoundTag2 = new CompoundTag("testCompoundTag2",
                                                new IntTag("testIntTag", 1),
                                                new StringTag("testStringTag", "abc"));
```

#### List tags
The ``ListTag`` object follows similar rules but with some slight changes:
```java
ListTag<IntTag> testListTagInteger = new ListTag<IntTag>("testListTagIntTag",
                                                             new IntTag(1),
                                                             new IntTag(2),
                                                             new IntTag(3));
```
Be aware that in cases where a ``ListTag`` object does not contain tags of the 
same type, an``IllegalArgumentException`` is thrown.

#### Tag searching
Let's say we want to search for an instance of a ``StringTag`` object that has the name
``FINDME``. Below is an example of how to complete such an operation using the ``find``
method:
```java
// Compound tag to be searched in
CompoundTag ct = new CompoundTag("ct",
                                    new IntTag("testIntTag", 1),
                                    new ByteTag("testByteTag", (byte) 2),
                                    new StringTag("testStringTag", "decoy"),
                                    new StringTag("FINDME", "the real one"));

StringTag stringTagFindMe = (StringTag) ct.find(StringTag.class, tag -> tag.getName().equals("FINDME"));
```
Now let's say we want to search for that same tag, but based on it's payload (which 
in this case would be ``the real one``). The query in this case would be:
```java
StringTag stringTagFindMe = (StringTag) ct.find(StringTag.class, tag -> ((StringTag) tag).getPayload().equals("the real one"));
```
Combining these two examples results in the following query:
```java
StringTag stringTagFindMe = (StringTag) ct.find(StringTag.class, tag -> tag.getName().equals("FINDME") && 
                                                                        ((StringTag) tag).getPayload().equals("the real one"));
```
These examples apply to the other search methods as well (`findAll`, `findAllParents`, `contains`).
The same also applies to the methods found in the ``ListTag`` class. More information
can be found in the documentation (in the /docs folder).

#### Printing tags
TBA
