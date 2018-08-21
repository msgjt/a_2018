package ro.msg.edu.jbugs.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.constraints.NotNull;

public class CustomLogger {

    private static final Logger logger = LogManager.getLogger(CustomLogger.class);

    private static int indentation = 0;



    public static void logEnter(@NotNull final Class className,
                                @NotNull final String methodName,
                                final String... parameters){
        StringBuilder indent = new StringBuilder();
        for(int i = 0; i < indentation; i++)
            indent.append("   ");
        System.out.println();
        logger.log(Level.WARN,"{}[ENTER] CLASS={}, METHOD={}, PARAMETERS={}, TIME={}", indent.toString(),className,methodName,parameters,System.currentTimeMillis());
        indentation++;
    }

    public static void logExit(@NotNull final Class className,
                               @NotNull final String methodName,
                               final String result){
        indentation--;
        String indent = "";
        for(int i = 0; i < indentation; i++)
            indent += "   ";
        logger.log(Level.WARN,"{}[EXIT] CLASS={}, METHOD={}, RESULT={}, TIME={}",indent,className,methodName,result,System.currentTimeMillis());

    }

    public static void logException(@NotNull final Class className,
                                    @NotNull final String methodName,
                                    @NotNull final String exceptionCode){
        indentation--;
        StringBuilder indent = new StringBuilder();
        for(int i = 0; i < indentation; i++)
            indent.append("   ");
        logger.log(Level.WARN,"{}[EXCEPTION] CLASS={}, METHOD={}, EXCEPTION_CODE={}, TIME={}", indent.toString(),className,methodName,exceptionCode,System.currentTimeMillis());
    }

}
