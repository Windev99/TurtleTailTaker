package com.wintereur.turtletail.taker;

import com.wintereur.turtletail.taker.exceptions.ParsingException;

import java.util.List;

/**
 * Collectors are used to simplify the collection of information
 * from takers
 * @param <I> the item type
 * @param <E> the taker type
 */
public interface Collector<I, E> {

    /**
     * Try to add an taker to the collection
     * @param taker the taker to add
     */
    void commit(E taker);

    /**
     * Try to extract the item from an taker without adding it to the collection
     * @param taker the taker to use
     * @return the item
     * @throws ParsingException thrown if there is an error extracting the
     *                          <b>required</b> fields of the item.
     */
    I extract(E taker) throws ParsingException;

    /**
     * Get all items
     * @return the items
     */
    List<I> getItems();

    /**
     * Get all errors
     * @return the errors
     */
    List<Throwable> getErrors();

    /**
     * Reset all collected items and errors
     */
    void reset();
}
