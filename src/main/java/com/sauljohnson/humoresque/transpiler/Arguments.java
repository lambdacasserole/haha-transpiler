package com.sauljohnson.humoresque.transpiler;

/**
 * Represents arguments passed to the application.
 *
 * @since 04/11/2019
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
@SuppressWarnings({"unused", "WeakerAccess"}) // API class.
public class Arguments {

    private Mode mode;

    private String[] targetFunctions;

    private String sourceFile;

    private TargetLanguage targetLanguage;

    /**
     * Initialises a new instance of a set of application arguments.
     */
    public Arguments() {
        mode = Mode.DEFAULT;
        targetLanguage = TargetLanguage.DEFAULT;
    }

    /**
     * Gets the application mode.
     *
     * @return  the application mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Sets the application mode.
     *
     * @param mode  the application mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Gets the target functions to transpile.
     *
     * @return  the target functions to transpile
     */
    public String[] getTargetFunctions() {
        return targetFunctions;
    }

    /**
     * Sets the target functions to transpile.
     *
     * @param targetFunctions   the target functions to transpile
     */
    public void setTargetFunctions(String[] targetFunctions) {
        this.targetFunctions = targetFunctions;
    }

    /**
     * Gets the source file to transpile.
     *
     * @return  the source file to transpile
     */
    public String getSourceFile() {
        return sourceFile;
    }

    /**
     * Sets the source file to transpile.
     *
     * @param sourceFile    the source file to transpile
     */
    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Gets the target language to transpile to.
     *
     * @return  the target language to transpile to
     */
    public TargetLanguage getTargetLanguage() {
        return targetLanguage;
    }

    /**
     * Sets the target language to transpile to.
     *
     * @param targetLanguage    the target language to transpile to
     */
    public void setTargetLanguage(TargetLanguage targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
