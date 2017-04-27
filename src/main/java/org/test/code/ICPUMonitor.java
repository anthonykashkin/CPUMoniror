package org.test.code;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface using for monitoring CPU loading.
 */
public interface ICPUMonitor extends Serializable {

    /**
     *
     * @return {@link Map} of all usage by CPU, where key is index of
     * CPU, and value is percentage of CPU load.
     */
    public abstract Map<String, Double> getAllUsageCPU();

    /**
     *
     * @return The number of core, used by CPU.
     */
    public abstract int getCoreCount();
}

