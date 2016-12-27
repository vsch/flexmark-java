package com.vladsch.flexmark.util.mappers;

public interface IndexMapper {
    IndexMapper NULL = new IndexMapper() {
        @Override
        public int map(final int index) {
            return index;
        }
    };

    int map(int index);
}
