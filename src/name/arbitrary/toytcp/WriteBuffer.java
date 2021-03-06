package name.arbitrary.toytcp;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Should become an extension to Buffer that supports:
 * * scribbling on the contents
 * * keeping an append pointer
 * * opening up header/trailer areas.
 */
public class WriteBuffer {
    private final ArrayList<Byte> buffer = new ArrayList<Byte>();

    public WriteBuffer() {
    }

    public WriteBuffer(int... data) {
        for (int i = 0 ; i < data.length; i++) {
            append((byte)data[i]);
        }
    }

    public void append(byte b) {
        buffer.add(b);
    }

    public void append(byte... bs) {
        for (byte b : bs) {
            buffer.add(b);
        }
    }

    public void append(Buffer data) {
        int n = data.length();
        for (int i = 0; i < n; i++) {
            append(data.get(i));
        }
    }

    public void appendU16(int value) {
        buffer.add((byte)(value >> 8));
        buffer.add((byte)(value & 0xFF));
    }

    public void appendU32(int value) {
        buffer.add((byte)(value >> 24));
        buffer.add((byte)(value >> 16));
        buffer.add((byte)(value >> 8));
        buffer.add((byte)(value & 0xFF));
    }

    public int getAppendOffset() {
        return buffer.size();
    }

    public void put(int i, byte value) {
        buffer.set(i, value);
    }

    public void putU8(int i, int value) {
        put(i, (byte)value);
    }

    public void putU16(int i, int value) {
        put(i, (byte)(value >> 8));
        put(i + 1, (byte)(value & 0xFF));
    }

    public byte[] toByteArray() {
        int n = buffer.size();
        byte[] array = new byte[n];
        for (int i = 0; i < n; i++) {
            array[i] = buffer.get(i);
        }
        return array;
    }

    @Override
    public String toString() {
        return "WriteBuffer{" +
                "buffer=" + buffer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WriteBuffer that = (WriteBuffer) o;

        if (!buffer.equals(that.buffer)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return buffer.hashCode();
    }

    public interface Listener {
        void send(WriteBuffer buffer);
    }
}
