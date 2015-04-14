package com.barclays.business;

/**
 * Created on 11/4/15, 7:31 PM
 * Graph.java
 *
 * @author gshankar
 */
public interface Graph<V, E> {

    Iterable<V> getVertices();

    int getOrder();

    Iterable<E> getEdges();

    int getSize();

    boolean containsVertex( V v );

    boolean containsEdge( E e );
}
