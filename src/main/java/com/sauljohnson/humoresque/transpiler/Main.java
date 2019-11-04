package com.sauljohnson.humoresque.transpiler;

import com.sauljohnson.humoresque.parser.*;
import com.sauljohnson.humoresque.parser.model.Function;
import com.sauljohnson.humoresque.parser.model.Program;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private static String getArgumentValue(String[] args, String key) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(key)) {
                return args[i + 1];
            }
        }
        return null;
    }

    private static Arguments parseArguments(String[] args) {
        Arguments dic = new Arguments();
        for (String arg : args) {
            if (arg.startsWith("-") && arg.length() == 2) {
                switch (arg.charAt(1)) {
                    case 'e':
                        dic.setMode(Mode.ENUMERATE_FUNCTIONS);
                        break;
                    case 'f':
                        dic.setMode(Mode.EMIT_FUNCTIONS);
                        String identifierString = getArgumentValue(args, arg);
                        if (identifierString != null) {
                            dic.setTargetFunctions(identifierString.split(","));
                        } else {
                            // TODO: Throw error.
                        }
                        break;
                }
            }
        }
        if (args.length > 0) {
            dic.setSourceFile(args[args.length - 1]);
        }
        return dic;
    }

    public static void main(String[] args) {
        Arguments parsedArgs = parseArguments(args);
        try {
            String source = FileUtils.readFileToString(new File(parsedArgs.getSourceFile()));
            Tokenizer tokenizer = new HahaTokenizer();
            TokenStreamTransformer transformer = new ConsecutiveTokenFilter(TokenType.PUNCTUATOR);
            Program program = Program.parse(transformer.transform(new TokenStream(tokenizer.tokenize(source))));
            Transpiler transpiler = new JavaTranspiler();
            switch (parsedArgs.getMode()) {
                case ENUMERATE_FUNCTIONS:
                    // Just print out all function names.
                    for (Function function : program.getFunctions()) {
                        System.out.println(function.getIdentifier());
                    }
                    break;
                case EMIT_FUNCTIONS:
                    // Get target functions to transpile.
                    String[] targetFunctionString = parsedArgs.getTargetFunctions();
                    List<String> targetFunctionList = Arrays.asList(targetFunctionString);
                    // TODO: Use a stack here yeah?
                    for (Function function : program.getFunctions()) {
                        if (targetFunctionList.contains(function.getIdentifier())) {
                            System.out.println(transpiler.transpileFunction(function));
                        }
                    }
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TokenizationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
