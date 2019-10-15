package IO;

/**
 * Created by Daniel Ben Simon
 */

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
    }

    public byte[] toByte(int[] arr) {
        byte[] result = new byte[0XFFFF];
        for (int i = 0, j = 0; i < arr.length; i++,j++) {
            if (arr[i] > 127) {
                    result[j] = (byte) (arr[i]/ -100);
                    j += 1;
                    result[j] = (byte) (arr[i] % 100);
                }
                else
                    result[j] = (byte) (arr[i]);
        }
        return result;
    }
}
