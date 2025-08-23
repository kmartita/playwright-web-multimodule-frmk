package tech.kmartita.tools.helpers;

import org.apache.commons.lang3.StringUtils;
import tech.kmartita.tools.browsers.Browsers;
import tech.kmartita.tools.browsers.IBrowser;

import static tech.kmartita.tools.helpers.EnvManager.*;

public class ConfigurationManager {

    private static final String BROWSER_VARIABLE = "browser";
    private static final String HEADLESS_VARIABLE = "headless";
    private static final String THREAD_COUNT_VARIABLE = "threads";
    private static final String ENV_VARIABLE = "env";

    private ConfigurationManager() {}

    public static String getBrowserVariable() {
        return System.getProperty(BROWSER_VARIABLE);
    }

    public static String getHeadlessVariable() {
        return System.getProperty(HEADLESS_VARIABLE);
    }

    public static String getThreadCountVariable() {
        return System.getProperty(THREAD_COUNT_VARIABLE);
    }

    public static IBrowser getBrowser() {
        IBrowser defaultBrowser = Browsers.CHROME;
        String browserParam = getBrowserVariable();

        if (!StringUtils.isEmpty(browserParam)) {
            try {
                defaultBrowser = Browsers.from(browserParam);
            } catch (Exception e) {
                System.out.printf("Invalid browser parameter '%s', using default '%s'\n", browserParam, defaultBrowser);
            }
        }

        System.out.printf("Selected browser: '%s'\n", defaultBrowser);
        return defaultBrowser;
    }

    public static boolean isGraphicalMode() {
        String modeParam = getHeadlessVariable();
        boolean isHeadless = true;

        if (!StringUtils.isEmpty(modeParam)) {
            isHeadless = Boolean.parseBoolean(modeParam);
        }

        System.out.printf("Headless mode: '%s'\n", isHeadless);
        return !isHeadless;
    }

    public static int getThreadCount() {
        int count = -1;
        String threadParam = getThreadCountVariable();

        if (StringUtils.isEmpty(threadParam))
            return count;

        if (!StringUtils.isNumeric(threadParam))
            throw new IllegalArgumentException("\nValue of the command-line parameter '" + THREAD_COUNT_VARIABLE
                                               + "' must be a positive integer number\n");
        count = Integer.parseInt(threadParam);
        System.out.printf("Thread count: '%s'\n", threadParam);

        if (count <= 0)
            throw new IllegalArgumentException("\nValue of the command-line parameter '" + THREAD_COUNT_VARIABLE
                                               + "' must be a positive integer number\n");
        return count;
    }

    public static EnvType getEnvironment() {
        String env = System.getProperty(ENV_VARIABLE);
        env = (env != null && !env.isEmpty()) ? env.toLowerCase() : EnvType.TEST.getName();

        System.out.printf("Selected environment: '%s'\n", env);
        EnvType envType = EnvType.from(env);
        System.setProperty(ENV_VARIABLE, envType.getName().toLowerCase());
        return envType;
    }
}
