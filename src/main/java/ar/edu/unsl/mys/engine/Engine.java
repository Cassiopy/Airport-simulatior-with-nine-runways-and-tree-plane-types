package ar.edu.unsl.mys.engine;

public interface Engine
{
    /**
     * This method initiates the execution of the simulation
     */
    void execute();

    /**
     * This method generates an output whichs contains the result of 
     * the simulation execution.
     * @param intoFile true for store the result into a file.
     * @param filePath the full path of the file (including name and extension) in which the 
     * report will be stored.
     */
    String generateReport(boolean intoFile, String filePath);
}