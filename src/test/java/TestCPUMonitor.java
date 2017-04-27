import org.test.code.CPUMonitor;
import junit.framework.TestCase;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TestCPUMonitor extends TestCase{
    private CPUMonitor cpuMonitor;
    private File testFile;
    private BufferedReader reader;

    @Before
    public void setUp() throws Exception {
        cpuMonitor = new CPUMonitor();
        testFile = new File(this.getClass().getResource("/testMPSTAT").getFile());
        reader = new BufferedReader(new FileReader(testFile));
    }

    public void testGetAllUsageMonitor() throws Exception {
        assertEquals(cpuMonitor.getAllUsageCPU(reader).get("ALL"), 75.25);
    }

    public void testGetCoreCount() throws Exception{
        assertEquals(cpuMonitor.getCoreCount(reader), 2);
    }
}


