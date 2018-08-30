package ro.msg.edu.jbugs.shared.persistence.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.constraints.NotNull;

public class CustomLogger {

    private static final Logger logger = LogManager.getLogger(CustomLogger.class);

    public static void logEnter(@NotNull final Class className,
                                @NotNull final String methodName,
                                final String... parameters) {
        logger.log(Level.INFO, "\n[ENTER]\n____  CLASS = {}\n____  METHOD = {}\n____  PARAMETERS = {}\n", className, methodName, parameters);
    }

    public static void logExit(@NotNull final Class className,
                               @NotNull final String methodName,
                               final String result) {

        logger.log(Level.INFO, "\n[EXIT]\n____  CLASS = {}\n____  METHOD = {}\n____  RESULT = {}\n", className, methodName, result);

    }

    public static void logException(@NotNull final Class className,
                                    @NotNull final String methodName,
                                    @NotNull final String exceptionCode) {
        logger.log(Level.INFO, "\n[EXCEPTION]\n____  CLASS = {}\n____  METHOD = {}\n____  EXCEPTION_CODE = {}\n", className, methodName, exceptionCode);
    }

}
