package com.barclays.utils;

import static java.lang.String.format;

/**
 * Created on 13/4/15, 12:06 AM
 * Assertions.java
 *
 * @author gshankar
 */
public final class Assertions
{
    private Assertions(){
        // do nothing
    }

    public static <T> T checkNotNull( T reference, String errorMessageTemplate, Object... errorMessageArgs ) {
        if ( reference == null ) {
            // If either of these parameters is null, the right thing happens anyway
            throw new NullPointerException( format( errorMessageTemplate, errorMessageArgs ) );
        }
        return reference;
    }
}
