package tech.kmartita.tests;

import com.github.rsheremeta.AllureEnv;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlSuite;
import tech.kmartita.app.WebApp;

import java.util.HashMap;
import java.util.Map;

import static tech.kmartita.tools.helpers.ConfigurationManager.*;
import static tech.kmartita.tools.reports.BaseHTMLReporter.*;

public abstract class AbstractWebTest extends BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup(ITestContext context) {
        startFillClassRunResult(context);

        int threadCount = getThreadCount();
        if (threadCount == -1)
            return;
        XmlSuite suite = context.getSuite().getXmlSuite();
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(threadCount);
        suite.setGroupByInstances(Boolean.TRUE);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(ITestContext context) {
        allClassesStatuses.put(this.getClass().getName(), "true");

        try {
            WebApp.quitSession();
        } finally {
            if(!getMapWithTestAndClasses().isEmpty()) {
                getMapWithTestAndClasses().get(context.getName()).put(this.getClass().getName(),"finished");

                long testsLeft = getMapWithTestAndClasses().get(context.getName()).values().stream().filter(o -> o.equalsIgnoreCase("started")).count();
                int percentage = (int) ((float) testsLeft/(getMapWithTestAndClasses().get(context.getName()).size()) * 100);

                System.out.println("=====================================");
                System.out.printf("Tests left: %s\n", percentage);
                System.out.println("=====================================");

                for(String keyTestName : getMapWithTestAndClasses().get(context.getName()).keySet()) {
                    if(getMapWithTestAndClasses().get(context.getName()).get(keyTestName).equals("started"))
                        System.out.printf("Not started test class [%s]%n", keyTestName);
                }
                System.out.println("=====================================");
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite(ITestContext context) {
        System.out.println("=====================================");
        System.out.printf("Is fully automated: %s%n", isFullyAutomated());

        Map<String, String> envData = new HashMap<>();
        envData.put("Approach:", "Web");
        envData.put("Platform:", System.getProperty("os.name"));
        envData.put("Environment:", getEnvironment().name());
        envData.put("Browser:", getBrowser().toString());

        AllureEnv.createAllureEnvironmentFile(envData);
    }
}

