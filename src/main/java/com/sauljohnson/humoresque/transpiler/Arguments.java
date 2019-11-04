package com.sauljohnson.humoresque.transpiler;

public class Arguments {
    private Mode mode;

    private String[] targetFunctions;

    private String sourceFile;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String[] getTargetFunctions() {
        return targetFunctions;
    }

    public void setTargetFunctions(String[] targetFunctions) {
        this.targetFunctions = targetFunctions;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }
}
