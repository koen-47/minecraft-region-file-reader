# Minecraft Region File Reader
A basic implementation of the NBT format used by Minecraft to store information about 
the state of a generated world. It currently only supports reading functionality
and is mainly used to retrieve information, rather than write it. This feature
may be implemented at a later date.

## Specification
The NBT format is a tag-based information system that is capable of storing different
data types from Java. [This](https://minecraft.fandom.com/wiki/NBT_format#:~:text=The%20Named%20Binary%20Tag%20(NBT,an%20ID%20and%20a%20name.) 
link provides some helpful pointers to how this format is implemented and used in
Minecraft.