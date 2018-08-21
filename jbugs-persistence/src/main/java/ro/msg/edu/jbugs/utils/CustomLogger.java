package ro.msg.edu.jbugs.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.constraints.NotNull;

public class CustomLogger {

    private static final Logger logger = LogManager.getLogger(CustomLogger.class);

    public static void logEnter(@NotNull final Class className,
                                @NotNull final String methodName,
                                final String... parameters){
        logger.log(Level.INFO,"[ENTER] CLASS={}, METHOD={}, PARAMETERS={}, TIME={}",className,methodName,parameters,System.currentTimeMillis());
    }

    public static void logExit(@NotNull final Class className,
                               @NotNull final String methodName,
                               final String result){

        logger.log(Level.INFO,"[EXIT] CLASS={}, METHOD={}, RESULT={}, TIME={}",methodName,result,System.currentTimeMillis());

    }

    public static void logException(@NotNull final Class className,
                                    @NotNull final String methodName,
                                    @NotNull final String exceptionCode){
        logger.log(Level.INFO,"[EXCEPTION] CLASS={}, METHOD={}, EXCEPTION_CODE={}, TIME={}",className,methodName,exceptionCode,System.currentTimeMillis());
    }

}
