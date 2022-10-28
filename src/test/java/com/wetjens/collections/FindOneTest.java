package com.wetjens.collections;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.wetjens.collections.FindOne.findOne;
import static org.junit.jupiter.api.Assertions.*;

class FindOneTest {

    @Test
    void moreThanOne() {
        var exception = new RuntimeException("more than 1");

        var thrown = assertThrows(RuntimeException.class, () ->
                Stream.of(1, 2, 3).collect(findOne(() -> exception)));

        assertSame(thrown, exception);
    }

    @Test
    void one() {
        var element = new Object();
        var optional = Stream.of(element).collect(findOne(() -> new RuntimeException("more than 1")));

        assertFalse(optional.isEmpty());
        assertSame(element, optional.get());
    }

    @Test
    void none() {
        var optional = Stream.empty().collect(findOne(() -> new RuntimeException("more than 1")));

        assertTrue(optional.isEmpty());
    }
}