package mca.parsing;

import nbt.tag.*;
import util.CompoundTagString;

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

    public StringTag getBlockStateAtIndex(int blockStateIndex) {
        long blockState = this.blockStates.getPayload()[blockStateIndex];
        int bitsPerBlock = this.blockStates.getPayload().length >> 6;
        int blockStateLength = 0;
        System.out.println(Long.toBinaryString(blockState));
        System.out.println(Long.toBinaryString(this.extractBitString(blockState, 0, 4)));
        System.out.println(Long.toBinaryString(this.extractBitString(blockState, 5, 8)));

        while (blockStateLength < 63) {
            long extractedBitString = this.extractBitString(blockState, blockStateLength, blockStateLength + bitsPerBlock);
            //System.out.println(extractedBitString);

            blockStateLength += bitsPerBlock;
        }

        return null;
    }

    private long extractBitString(long totalBit, long startPos, long endPos) {
        return (totalBit >> startPos) & ((1L << (endPos)) - 1L);
    }
}
