package com.sauljohnson.humoresque.transpiler;

import com.sauljohnson.humoresque.parser.model.Function;
import com.sauljohnson.humoresque.parser.model.Program;

/**
 * Represents a code transpilation service.
 *
 * @since 24/10/2019
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
@SuppressWarnings({"unused"}) // API class.
public interface Transpiler {

    /**
     * Transpiles a function to a target language.
     *
     * @param function  the function to transpile
     * @return          the transpiled function source code
     */
    String transpileFunction(Function function);

    /**
     * Transpiles a program to a target language.
     *
     * @param program   the program to transpile
     * @return          the transpiled program source code
     */
    String transpile(Program program);
}
