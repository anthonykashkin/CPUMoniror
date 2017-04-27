package org.test.code;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CPUMonitor implements ICPUMonitor {
    private final int IDLE_COLUMN_INDEX = 11;
    private final String cmd = "mpstat -P ALL 1 1";
    private Process process;

    public int getCoreCount() {
        try {
            return getCoreCount(getReader());
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getCoreCount(BufferedReader reader) {
        return getAllUsageCPU(reader).size() - 1;
    }

    public Map<String, Double> getAllUsageCPU() {
        try {
            return getAllUsageCPU(getReader());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Map<String, Double> getAllUsageCPU(BufferedReader reader) {
        Map<String, Double> cpuUsageList = new HashMap<>();
        try {
            cpuUsageList.put("ALL", getUsageCPU(reader));
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null && line.length() > 1) {
                cpuUsageList.put(String.valueOf(i), 100 - extractValue(line));
                ++i;
            }
            return cpuUsageList;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CPUMonitor{" +
                getAllUsageCPU() +
                '}';
    }

    private double getUsageCPU(BufferedReader reader) throws IOException, NullPointerException {
        for (int i = 0; i < 3; i++) {
            reader.readLine();
        }
        String result = reader.readLine();
        if (result == null) {
            throw new NullPointerException();
        }
        return extractValue(result);
    }

    private Double extractValue(String line) {
        String[] splitString = line.replaceAll(",", ".").split("\\s+");
        return Double.parseDouble(splitString[IDLE_COLUMN_INDEX]);
    }

    private BufferedReader getReader() throws IOException {
        initProcess();
        InputStream is = process.getInputStream();
        return new BufferedReader(new InputStreamReader(is));
    }

    private void initProcess() throws IOException {
        if (process != null) {
            process.destroy();
            process = null;
        }
        process = Runtime.getRuntime().exec(cmd);
    }
}