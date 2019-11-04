package com.sauljohnson.humoresque.transpiler;

/**
 * Represents a code pretty printing service.
 *
 * @since 04/11/2019
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public interface PrettyPrinter {

    /**
     * Formats a string containing source code.
     *
     * @param code  the source code to format
     * @return      the formatted source code
     */
    String prettyPrint(String code);
}
