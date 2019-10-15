package IO;

/**
 * Created by Daniel Ben Simon
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class Console {

    private static boolean m_outputToFile = true;
    private static final String m_outputLogFileFormat = "logs/server.log.txt";
    private static final String m_outputLogFilePath = GetOutputLogFilePath();
    private static final String m_outputLogTimeStampFormat = "yyyy.MM.dd HH:mm:ss.SSS";
    private static final String m_outputLogFileTimeStampFormat = "yyyy.MM.dd_HH.mm.ss";

    /**
     * Current TimeStamp string
     *
     * @return Current TimeStamp string
     */
    private static String GetLogTimeStamp() {
        return new SimpleDateFormat(m_outputLogTimeStampFormat).format(new Date());
    }

    /**
     * Adds TimeStamp prefix to the text
     *
     * @return text with TimeStamp Prefix
     */
    private static String PrefixTimeStamp(String text) {
        return String.format("%s| %s", GetLogTimeStamp(), text);
    }

    /**
     * return the output log file path
     *
     * @return string which represent the output log file path
     */
    private static String GetOutputLogFilePath() {
        return String.format(m_outputLogFileFormat, new SimpleDateFormat(m_outputLogFileTimeStampFormat).format(new Date()));
    }

    /**
     * Set output to file (true - output, false - do not output)
     *
     * @param bool true to output console to file
     */
    public static void SetOutputToFile(boolean bool) {
        m_outputToFile = bool;
    }

    /**
     * return line separator string
     *
     * @return string which represent line separation
     */
    public static String GetLineSeparator() {
        //return "\r\n";
        return System.getProperty("line.separator");
    }

    /**
     * Print text to console
     *
     * @param text text to print
     */
    public static void PrintLine(String text) {
        String line = PrefixTimeStamp(text);
        System.err.println(line);
        if (m_outputToFile) {
            FileWriter.AppendFile(line + GetLineSeparator(), m_outputLogFilePath);
        }
    }

    /**
     * Print error to console
     *
     * @param text text to print
     */
    public static void PrintDebug(String text) {
        System.err.println(PrefixTimeStamp(text));
    }

    /**
     * Print text to console
     *
     * @param errorText error message as text
     * @param exception an exception object
     */
    public static void PrintException(String errorText, Exception exception) {
        StringBuilder sb = BuildException(errorText, exception);
        //System.err.print(PrefixTimeStamp(sb.toString()));

        if (m_outputToFile) {
            if (!FileWriter.isFileCreated())
                FileWriter.CreateNewFile(m_outputLogFilePath);
            FileWriter.AppendFile(PrefixTimeStamp(sb.toString()), m_outputLogFilePath);
        }
    }

    /**
     * Print text to console
     *
     * @param errorText error message as text
     * @param exception an exception object
     * @return StringBuilder that holds the message
     */
    private static StringBuilder BuildException(String errorText, Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(GetLineSeparator());
        sb.append(GetLineSeparator());
        sb.append(String.format("Failure:   %s", errorText));
        if (exception != null) {
            sb.append(GetLineSeparator());
            sb.append(String.format("Exception: %s:%s",
                    exception.getClass().getSimpleName(),
                    exception.getMessage()
            ));
        }
        sb.append(GetLineSeparator());
        sb.append(GetLineSeparator());
        return sb;
    }

}
