package com.sauljohnson.humoresque.transpiler;

import com.sauljohnson.humoresque.parser.*;
import com.sauljohnson.humoresque.parser.model.Function;
import com.sauljohnson.humoresque.parser.model.Program;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    /**
     * Reads a file into a string.
     *
     * @param path          the path of the file to read
     * @return              the file contents as a string
     * @throws IOException  if the file cannot be read
     */
    private static String readFileText(String path) throws IOException {
        return FileUtils.readFileToString(new File(path));
    }

    /**
     * Gets the value associated with a flag on the command line.
     *
     * @param args  the input arguments array
     * @param key   the key
     * @return      the value
     */
    private static String getArgumentValue(String[] args, String key) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(key)) {
                return args[i + 1];
            }
        }
        return null;
    }

    /**
     * Parses a list of arguments given as a string array into an {@link Arguments} object.
     *
     * @param args  the input argument array
     * @return      the parsed {@link Arguments} object
     */
    private static Arguments parseArguments(String[] args) {

        // Parse arguments list into arguments object.
        Arguments parsedArgs = new Arguments();
        for (String arg : args) {
            if (arg.startsWith("-") && arg.length() == 2) {
                switch (arg.charAt(1)) {
                    case 'e':
                        // Enumerate functions just means output their names.
                        parsedArgs.setMode(Mode.ENUMERATE_FUNCTIONS);
                        break;
                    case 'f':
                        // Emit functions means emit only specific functions.
                        parsedArgs.setMode(Mode.EMIT_FUNCTIONS);
                        String identifierString = getArgumentValue(args, arg);
                        if (identifierString != null) {
                            parsedArgs.setTargetFunctions(identifierString.split(","));
                        } else {
                            // No function names given.
                            System.err.println("You must specify a set of comma-separated function names after the " +
                                    "-f option.");
                            System.exit(1);
                        }
                        break;
                    case 't':
                        // Allow specification of target language.
                        String targetLanguageString = getArgumentValue(args, arg);
                        if (targetLanguageString != null) {
                            if (targetLanguageString.toLowerCase().equals("java")) {
                                parsedArgs.setTargetLanguage(TargetLanguage.JAVA);
                            } else {
                                // Invalid target language given,
                                System.err.println("The language '" + targetLanguageString + "' is not a valid " +
                                        "target.");
                                System.exit(1);
                            }
                        } else {
                            // No target language given,
                            System.err.println("You must specify a target language after the -t option.");
                            System.exit(1);
                        }
                }
            }
        }

        // Input file comes last.
        if (args.length > 0) {
            parsedArgs.setSourceFile(args[args.length - 1]);
        }
        return parsedArgs;
    }

    public static void main(String[] args) {

        // If no arguments passed.
        Arguments parsedArgs = parseArguments(args);
        if (parsedArgs.getSourceFile() == null) {
            System.err.println("No file path specified.");
            System.exit(1);
        }

        // Deal with source file.
        try {
            // Read in source.
            String source = readFileText(parsedArgs.getSourceFile());

            // Tokenize, discard empty statements and parse.
            Tokenizer tokenizer = new HahaTokenizer();
            TokenStreamTransformer transformer = new ConsecutiveTokenFilter(TokenType.PUNCTUATOR);
            Program program = Program.parse(transformer.transform(new TokenStream(tokenizer.tokenize(source))));

            // Select transpiler and pretty printer based on target language.
            Transpiler transpiler;
            PrettyPrinter prettyPrinter;
            switch (parsedArgs.getTargetLanguage()) {
                case DEFAULT:
                case JAVA:
                default:
                    transpiler = new JavaTranspiler();
                    prettyPrinter = new JavaPrettyPrinter();
                    break;
            }

            // Detect application mode.
            switch (parsedArgs.getMode()) {
                case DEFAULT:
                    // Transpile the whole program.
                    String compiledSource = transpiler.transpile(program);
                    String prettySource = prettyPrinter.prettyPrint(compiledSource);
                    System.out.println(prettySource);
                    break;
                case ENUMERATE_FUNCTIONS:
                    // Just print out all function names.
                    for (Function function : program.getFunctions()) {
                        System.out.println(function.getIdentifier());
                    }
                    break;
                case EMIT_FUNCTIONS:
                    // For each target function to transpile.
                    String[] targetFunctionNames = parsedArgs.getTargetFunctions();
                    for (String targetFunctionName : targetFunctionNames) {

                        // Find and emit function.
                        boolean found = false;
                        for (Function function : program.getFunctions()) {
                            if (targetFunctionName.equals(function.getIdentifier())) {
                                String compiledFunction = transpiler.transpileFunction(function);
                                String prettyFunction = prettyPrinter.prettyPrint(compiledFunction);
                                System.out.println(prettyFunction);
                                found = true;
                                break;
                            }
                        }

                        // Function not found.
                        if (!found) {
                            System.err.println("No function named '" + targetFunctionName + "' found.");
                            System.exit(1);
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            System.err.println("Could not open file at '" + parsedArgs.getSourceFile() + "'");
            System.exit(1);
        } catch (TokenizationException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
