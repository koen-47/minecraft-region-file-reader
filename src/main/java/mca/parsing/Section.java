package mca.parsing;

import nbt.tag.*;
import util.CompoundTagString;

import java.util.Arrays;
import java.util.Iterator;

public class Section {
    private LongArrayTag blockStates;
    private ListTag palette;
    private ByteTag yLevel;
    private ByteArrayTag blockLight;
    private ByteArrayTag skyLight;

    public Section(CompoundTag section) {
        for (Tag nextTag : section) {
            switch (nextTag.getName()) {
                case "Y":
                    this.yLevel = (ByteTag) nextTag;
                    break;
                case "BlockStates":
                    this.blockStates = (LongArrayTag) nextTag;
                    break;
                case "Palette":
                    this.palette = (ListTag) nextTag;
                    break;
                case "BlockLight":
                    this.blockLight = (ByteArrayTag) nextTag;
                    break;
                case "SkyLight":
                    this.skyLight = (ByteArrayTag) nextTag;
                    break;
            }
        }
    }

    public CompoundTag getBlockStateAtIndex(int x, int y, int z) {
        System.out.println(Arrays.toString(this.blockStates.getPayload()));

        int blockNumber = (((y * 16) + z) * 16) + x;
        long blockState = this.blockStates.getPayload()[z];
        int bitsPerBlock = this.blockStates.getPayload().length / 64;
        int startLong = ((blockNumber * bitsPerBlock) / 64);
        int startOffset = (blockNumber * bitsPerBlock) % 64;
        int endLong = ((blockNumber + 1) * bitsPerBlock - 1)/ 64;
        System.out.println("Block state string: " + Long.toBinaryString(blockState) + " (" + blockState + ")");
        System.out.println("Length of block states array: " + this.blockStates.getPayload().length + ", bits per block: " + bitsPerBlock );
        System.out.println("Block number: " + blockNumber + ", start long: " + startLong);
        System.out.println("blockState[startLong] = " + this.blockStates.getPayload()[startLong]);
        System.out.println("blockState[endLong] = " + this.blockStates.getPayload()[endLong]);

        long data;
        if (startLong == endLong) {
            data = (this.blockStates.getPayload()[startLong] >> startOffset);
        } else {
            data = (this.blockStates.getPayload()[startLong] >> startOffset | this.blockStates.getPayload()[endLong] << (64 - startOffset));
        }

        data &= ((1L << bitsPerBlock) - 1);

        return (CompoundTag) this.palette.getPayload()[(int) data];
    }

    private long extractBitString(long totalBit, int startPos, int endPos) {
        return (totalBit >> startPos) & ((1L << (Math.abs(endPos - startPos))) - 1L);
    }
}
