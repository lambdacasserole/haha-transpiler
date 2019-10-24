package com.sauljohnson.humoresque.transpiler;

import com.sauljohnson.humoresque.parser.model.Program;

/**
 * Represents a code transpilation service.
 *
 * @since 24/10/2019
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
public interface Transpiler {

    /**
     * Transpiles a program to a target language.
     *
     * @param program   the program to transpile
     * @return          the transpiled program source code
     */
    String transpile(Program program);
}
