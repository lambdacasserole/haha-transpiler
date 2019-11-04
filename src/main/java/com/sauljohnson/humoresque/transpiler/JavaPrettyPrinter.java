package com.sauljohnson.humoresque.transpiler;

/**
 * Represents a pretty printer for Java source code.
 *
 * @since 04/11/2019
 * @author Saul Johnson <saul.a.johnson@gmail.com>
 */
@SuppressWarnings({"unused", "WeakerAccess"}) // API class.
public class JavaPrettyPrinter implements PrettyPrinter {

    private String indentString;

    /**
     * Initialises a new instance of a pretty printer for Java source code.
     */
    public JavaPrettyPrinter() {
        indentString = "\t";
    }

    /**
     * Gets the string to use for indentation.
     *
     * @return  the string to use for indentation
     */
    public String getIndentString() {
        return indentString;
    }

    /**
     * Sets the string to use for indentation.
     *
     * @param indentString  the string to use for indentation
     */
    public void setIndentString(String indentString) {
        this.indentString = indentString;
    }

    /**
     * Gets a string consisting of the indent string repeated to a depth.
     *
     * @param depth the depth
     * @return      the resulting string
     */
    private String getIndentString(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(indentString);
        }
        return sb.toString();
    }

    /**
     * @inheritDoc
     */
    public String prettyPrint(String code) {

        // Normalise source code.
        String normalisedSource = code.replace("\n", "")
                .replace("\r", "")
                .replace(";", ";\n")
                .replace("{", "\n{\n")
                .replace("}", "\n}\n");

        // Split into lines.
        String[] lines = normalisedSource.split("\n");

        // Indent based on nesting level.
        StringBuilder sb = new StringBuilder();
        int level = 0;
        for (String line : lines) {
            if (line.startsWith("{")) {
                sb.append(getIndentString(level)).append(line).append("\n");
                level++;
            } else if (line.startsWith("}")) {
                level--;
                sb.append(getIndentString(level)).append(line).append("\n");
            } else {
                sb.append(getIndentString(level)).append(line).append("\n");
            }
        }
        return sb.toString();
    }
}
