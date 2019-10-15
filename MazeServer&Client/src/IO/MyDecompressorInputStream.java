package IO;

/**
 * Created by Daniel Ben Simon
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }


    public int[] toInt(byte[] arr) {
        int resultSize = 0;
        int[] temp = new int[0XFFFF];
        for (int i = 0, j = 0; i < arr.length; i++,j++) {
            int current = 0;
            if (arr[i] < 0) {
                current = arr[i] * (-100);
                if (i+1 < arr.length) {
                    current += arr[i + 1];
                    i++;
                }
                if (i != 0)
                    temp[j] = current;
                else
                    resultSize = current;
            }
            else
                if (i != 0 )
                    temp[j] = arr[i];
                else
                    resultSize = arr[i];
        }
        int[] result = new int[resultSize*2];
        for (int i = 0, j =1; i < resultSize*2; i++,j++) {
            result[i] = temp[j];
        }

        return result;
    }

    public byte[] readBytes() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        try {
            for (int len = in.read(buffer); len != -1; len = in.read(buffer)) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }
}
