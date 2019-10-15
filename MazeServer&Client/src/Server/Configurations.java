package Server;

/**
 * Created by Daniel Ben Simon
 */

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    protected Properties properties;
    protected InputStream inputStream;


    public Configurations() throws Exception{
        properties = new Properties();
        inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
    }

    public String getNumOfThreads(){
        return properties.getProperty("NumOfThreads");

    }
    public String getSearchAlgorithm(){
        return properties.getProperty("SearchAlgorithms");
    }
    public String getGeneratorType(){
        return properties.getProperty("Generator");
    }

    public void setSearchAlgorithm(String AlgoName) {
        this.properties.setProperty("SearchAlgorithms", AlgoName);
    }

    public void setGeneratorType(String type) {
        this.properties.setProperty("Generator", type);
    }

    public void setNumOfThreads(int num) {
        this.properties.setProperty("NumOfThreads", Integer.toString(num));
    }


}