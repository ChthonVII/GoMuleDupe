/*******************************************************************************
 *
 * Copyright 2007 Andy Theuninck & Randall
 *
 * This file is part of gomule.
 *
 * gomule is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * gomule is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * gomlue; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 *
 ******************************************************************************/

package gomule.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.ArrayList;

// this class is for reading and writing on
// a bit level. i'd rename it, but then i'd
// have to go track down all the references
// to it in other classes
// it can be used on an entire file or just
// an array of bytes
public class D2BitReader {
    String filename; // file to read from
    int position; // current position (in bits)
    byte[] filedata; // all of the file, in memory

    // CONSTRUCTOR:
    // create a new bitreader for file f
    public D2BitReader(String f) {
        filename = f;
        position = 0;
        load_file();
    }

    // CONSTRUCTOR:
    // wrap a new bitreader around a byte array
    public D2BitReader(byte[] b) {
        filedata = b;
        position = 0;
    }
    
    // COPY CONSTRUCTOR
    // create new bitreader from existing bitreader
    public D2BitReader(D2BitReader original){
        filename = original.filename;
        position = original.position;
        filedata = original.filedata.clone();
    }

    public String getFileName() {
        return filename;
    }

    // load the file into a byte-array in
    // memory to avoid future file i/o
    // load it into a vector first then copy it
    // to a properly sized byte array
    public boolean load_file() {
        try {
            File lFile = new File(filename);
            if (lFile.exists()) {
                if (!lFile.canRead()) {
                    return false;
                }
                FileInputStream in = new FileInputStream(filename);
                byte[] data = new byte[1024];
                Vector v = new Vector();
                do {
                    int num = in.read(data);
                    if (num == -1)
                        break;
                    for (int i = 0; i < num; i++)
                        v.add(new Byte(data[i]));
                } while (true);
                filedata = new byte[v.size()];
                for (int i = 0; i < v.size(); i++)
                    filedata[i] = ((Byte) v.elementAt(i)).byteValue();
                in.close();
                return true;
            } else {
                // new empty file
                filedata = new byte[0];
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Error loading file " + filename
                    + " into memory");
            return false;
        }
    }

    public boolean isNewFile() {
        return (filedata != null && filedata.length == 0);
    }

    // search for an ASCII string in the byte array
    // return an array of locations where it was found
    // there is no mechanism built in for a flag not
    // being found, so know what you're looking for
    public int[] find_flags(String flag) {
        //        String data = new String(filedata);
        return findBytes(flag.getBytes());
    }

    public int[] findBytes(byte[] target) {
        // offset is maintained to keep
        // correct indexing stored as i move
        // forward by substring-ing
        /*
         * int offset = 0; do { int i = data.indexOf(flag); if (i == -1) break;
         * v.add(new Integer(i+offset)); System.out.println(filedata[i+offset]);
         * offset += i+1; data = data.substring(i+1); }while(true);
         */
        Vector v = new Vector();
        for (int i = 0; i < filedata.length; i++) {
            if (filedata[i] == target[0]) {
                boolean found = true;
                for (int j = 0; j < target.length; j++) {
                    if (filedata[i + j] != target[j]) {
                        found = false;
                    }
                }
                if (found) {
                    v.add(new Integer(i));
                }
            }
        }
        int[] idata = new int[v.size()];
        for (int i = 0; i < v.size(); i++) {
            idata[i] = ((Integer) v.elementAt(i)).intValue();
        }
        return idata;
    }

    public int findNextFlag(String flag, int pPos) {
        return findNextBytes(flag.getBytes(), pPos);
    }

    public int findNextBytes(byte[] target, int pPos) {
        for (int i = pPos; i < filedata.length; i++) {
            if (filedata[i] == target[0]) {
                boolean found = true;
                for (int j = 0; j < target.length; j++) {
                    if (filedata[i + j] != target[j]) {
                        found = false;
                    }
                }
                if (found) {
                    return i;
                }
            }
        }
        return -1;
    }

    // get the current position (in bits)
    public int get_pos() {
        return position;
    }

    public int getNextByteBoundaryInBits() {
        return (position + 7) & (~7);
    }

    // set the current position (in bits)
    public void set_pos(int p) {
        position = p;
    }

    public int get_byte_pos() {
        return position / 8;
    }

    // set the current position (in bytes)
    public void set_byte_pos(int b) {
        position = 8 * b;
    }

    // skip forward s bits
    public void skipBits(int s) {
        position += s;
    }

    public void skipBytes(int s) {
        position += s * 8;
    }

    // read the next bits from the file
    // in theory you can request up to 64 bits
    // however, data is pulled from a byte-aligned block
    // of 8 bytes, so really you can pull 1 to 64 bits
    // from an 8 byte block, not 64 bits from anywhere
    // position (in bits) is advanced upon completion
    // --- THIS APPEARS TO BE CORRECT, BUT ---
    // I'm replacing it with an implementation similar to the new write() function
    // for the sake of consistency,
    // and to remove the alignment restrictions,
    // and to be really sure there's no undetected bugs lurking here like with write()
    /*
    public long read(int bits) {
        // number of bytes represented by the position
        int byte_num = position / 8;
        // number of bits past the last byte mark
        int bits_past = position % 8;

        // put the current 8 bytes into a long
        long fixed_data = 0;
        for (int i = 0; i < 8; i++) {
            fixed_data = fixed_data << 8;
            if (byte_num + i < filedata.length) {
                int unsigned = 0x000000ff & flip(filedata[byte_num + i]);
                fixed_data += unsigned;
            } else {
                fixed_data += 0;
            }
        }

        // shift to get the correct bits
        // discard leading bits
        fixed_data = fixed_data << bits_past;
        // move desired bits to the right side of the long
        fixed_data = fixed_data >>> (64 - bits);

        
        // test new read method
        //long oldread = unflip(fixed_data, bits);
        //long newread = read_new(bits);
        //if (oldread != newread){
        //    System.out.println("read disagreement! position: " + position + "; old: " + oldread + " (0x" + Long.toHexString(oldread) + "); new: " + newread + "(0x" + Long.toHexString(newread) + ")");
        //}
        
        
        
        // advance position by the number of bits read
        position += bits;

        // give data as a long
        return unflip(fixed_data, bits);
    }
    */

    // replace the read() function with one similar to new write()
    // as an added bonus, the alignment restrictions are gone -- we can always read up to 64 bits regardless of alignment
    // see comments above write()
    public long read(int bits) {
        
        // get current position in bits and bytes
        int byte_num = position / 8;
        int bits_past = position % 8;
        
        // how many bytes must we process?
        int bitspan = bits + bits_past;
        int bitsremainder = bitspan % 8;
        int bitsleft = 0;
        if (bitsremainder != 0){
            bitsleft = 8 - bitsremainder;
            bitspan += bitsleft;
        }
        int bytespan = bitspan/8;
        
        //System.out.println("bytespan: " + bytespan + "; bits_past: " + bits_past + "; bitsleft: " + bitsleft);
        
        // read the existing bytes into a fully-little endian (bytewise and bitwise) array
        ArrayList bagobitsread = new ArrayList();
        bagobitsread.ensureCapacity(bitspan);
        for (int i=0; i<bytespan; i++){
            
            // if there's data to read, do so
            if (byte_num + i < filedata.length){
            
                // read in byte
                int byteread = 0xFF & filedata[byte_num + i];
                //System.out.println("byte #" + i + " raw = 0x" + Integer.toHexString(byteread));
                
                // put it in the array
                for (int j=0; j<8; j++){
                    boolean thisbit = false;
                    if (((byteread >>> j) & 0x1) == 1){
                        thisbit = true;
                    }
                    bagobitsread.add(thisbit);
                }
            }
            // if we're past the end of the file, pad with 0s
            else {
                boolean falsebit = false;
                 for (int j=0; j<8; j++){
                    bagobitsread.add(falsebit);
                 }
            }
        }
        // array now contains fully little-endian data (both byte order and bit order)
        
        // strip leading bits to align it
        for (int i=0; i<bits_past; i++){
            bagobitsread.remove(0);
        }
        
        // now convert to a long
        long output = 0;
        for (int i=0; i<bits; i++){
            boolean readbit = (boolean)bagobitsread.get(i);
            if (readbit){
                long thisbit = 1;
                output += (thisbit << i);
            }
        }
        
        // advance position
        position += bits; // comment this out if testing inside old read()
        
        return output;
    }
    
    
    // flips the last x bits of the long as specified by
    // 'bits'. This changes the number represented by
    // those bits back to the order expected by java
    // so they are evaluated to the proper number
    public long unflip(long l, int bits) {
        long ret = 0;
        for (int i = 0; i < bits; i++) {
            int bit = (int) ((l >> i) & 0x01);
            ret = ret << 1;
            ret += bit;
        }
        return ret;
    }

    // write the content of data's right bits into
    // the current specified number of bits
    // again, the 64 bits 'in theory' issue applies
    // the same way as it does in read
    // position (in bits) advanced on completion
    /*
    --- THIS IS TOTALLY BUGGY ---
    public void write(long data, int bits) {
        // get current position in bits and bytes
        int byte_num = position / 8;
        int bits_past = position % 8;

        // get the current bits into a long
        long writeable_data = 0;
        for (int i = 0; i < 8; i++) {
            writeable_data = writeable_data << 8;
            if (byte_num + i < filedata.length) {
                writeable_data += flip(filedata[byte_num + i]);
            } else
                writeable_data += 0;
        }

        // generate a mask to clear the bits
        // that are going to be written
        long mask = (1 << bits) - 1;
        mask = mask << (64 - bits - bits_past);
        mask = ~mask;

        // clear the bits
        writeable_data = writeable_data & mask;

        // move the data bits to be written into
        // the correct bit position
        data = unflip(data, bits);
        data = data << (64 - bits - bits_past);

        // set the bits to be written
        writeable_data = writeable_data | data;

        // put the bytes back
        for (int i = 7; i >= 0; i--) {
            long current_byte = writeable_data & 0xff;
            current_byte = unflip(current_byte, 8);
            if (byte_num + i < filedata.length)
                filedata[byte_num + i] = (byte) current_byte;
            writeable_data = writeable_data >>> 8;
        }

        // advance position
        position += bits;
    }
    */

    // replace the buggy write() function with one that works correctly
    // as an added bonus, the alignment restrictions are gone -- we can always write up to 64 bits regardless of alignment
    // Since Java is a distinctly awful language that's especially awful at bit-level manipulations
    // (which is probably why the original write() function was buggy),
    // I've decided to adopt Daan Coppens strategy of using an array of bools to bypass all that nonsense.
    // (see https://daancoppens.wordpress.com/2017/01/26/understanding-the-diablo-2-save-file-format-part-2/)
    // This probably isn't super efficient (not that anything is ever efficient in Java...),
    // but it couples the logicial operations to the bitwise operations in a very clear manner that
    // largely eliminates the possibility of error. 
    public void write(long data, int bits) {
                
        // get current position in bits and bytes
        int byte_num = position / 8;
        int bits_past = position % 8;
        
        // how many bytes must we process?
        int bitspan = bits + bits_past;
        int bitsremainder = bitspan % 8;
        int bitsleft = 0;
        if (bitsremainder != 0){
            bitsleft = 8 - bitsremainder;
            bitspan += bitsleft;
        }
        int bytespan = bitspan/8;
        
        //System.out.println("bytespan: " + bytespan + "; bits_past: " + bits_past + "; bitsleft: " + bitsleft);
        
        // read the existing bytes into a fully-little endian (bytewise and bitwise) array
        ArrayList bagobitsread = new ArrayList();
        bagobitsread.ensureCapacity(bitspan);
        for (int i=0; i<bytespan; i++){
            
            // break if past end of filedata
            if (byte_num + i >= filedata.length){
                break;
            }
        
            // read in byte
            int byteread = 0xFF & filedata[byte_num + i];
            //System.out.println("byte #" + i + " raw = 0x" + Integer.toHexString(byteread));
            
            // put it in the array
            for (int j=0; j<8; j++){
                boolean thisbit = false;
                if (((byteread >>> j) & 0x1) == 1){
                    thisbit = true;
                }
                bagobitsread.add(thisbit);
            }
        }
        // array now contains fully little-endian data (both byte order and bit order)
        
        // also convert the data to be written into a fully little-endian (bytewise and bitwise) array
        ArrayList bagobitswrite = new ArrayList();
        bagobitswrite.ensureCapacity(bitspan);
        for (int i =0; i<bits; i++){
            boolean thisbit = false;
            if (((data >>> i) & 0x1) == 1){
                thisbit = true;
            }
            bagobitswrite.add(thisbit);
        }
        // pad the front of the data array to align it
        boolean falsebit = false;
        for (int i=0; i<bits_past; i++){
            bagobitswrite.add(0, falsebit);
        }
        // pad the back to ensure no out-of-bounds read
        for (int i=0; i<bitsleft; i++){
            bagobitswrite.add(falsebit);
        }
        
        // now we can write back
        int bitswritten = 0;
        for (int i=0; i<bytespan; i++){
            // break if past end of filedata
            if (byte_num + i >= filedata.length){
                break;
            }
            int newbyte = 0;
            for (int j=0; j<8; j++){
                boolean readbit = (boolean)bagobitsread.get(bitswritten);
                boolean writebit = (boolean)bagobitswrite.get(bitswritten);
                // use the original bits if we're in the padding; otherwise use the data bits
                if (writebit || (readbit && ((bitswritten < bits_past) || (bitswritten >= bits + bits_past )))){
                    int newbit = 1;
                    newbyte += (newbit << j);
                }
                bitswritten++;
            }
            filedata[byte_num + i] = (byte) newbyte;
            //System.out.println("byte #" + i + " wrote = 0x" + Integer.toHexString(newbyte));
        }
        
        
        // advance position
        position += bits;
    }
    
    
    // reverses the bits in a byte. Necessary
    // for reading properly accross byte marks
    public int flip(byte b) {
        int ret = 0;
        for (int i = 0; i < 8; i++) {
            int bit = (int) ((b >> i) & 0x01);
            ret = ret << 1;
            ret += bit;
        }
        return ret;
    }

    public byte getByte() {
        return filedata[position++];
    }

    // fetch 'num' bytes, starting at the current position
    public byte[] get_bytes(int num) {
        int bytepos = position / 8;
        byte[] ret = new byte[num];
        System.arraycopy(filedata, bytepos, ret, 0, num); // bytepos+num >= filedata.length
        return ret;
    }

    public byte[] getFileContent() {
        return filedata;
    }

    public int get_length() {
        return filedata.length;
    }

    public void setBytes(byte pNewBytes[]) {
        filedata = pNewBytes;
    }

    public void setBytes(int pPos, byte pReplaceBytes[]) {
        System.arraycopy(pReplaceBytes, 0, filedata, pPos, pReplaceBytes.length);
    }

//    // replace bytes between 'start' and 'end' (inclusive) with
//    // the data in newbytes
//    public void replace_bytes(int start, int end, byte[] newbytes)
//    {
//        byte[] tempdata = new byte[start + newbytes.length
//                + (filedata.length - end - 1)];
//
//        for (int i = 0; i < start; i++)
//            tempdata[i] = filedata[i];
//
//        for (int i = 0; i < newbytes.length; i++)
//            tempdata[start + i] = newbytes[i];
//
//        for (int i = 0; i < filedata.length - end - 1; i++)
//            tempdata[i + start + newbytes.length] = filedata[i + end + 1];
//
//        filedata = tempdata;
//
//    }

    public void save() {
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(filedata);
            out.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error saving file " + filename, ex);
        }
    }

    public void save(String f) {
        try {
            FileOutputStream out = new FileOutputStream(f);
            out.write(filedata);
            out.close();
        } catch (Exception ex) {
            throw new RuntimeException("Error saving file " + filename, ex);
        }
    }

    public byte[] calculateChecksum() {
        int currentPos = get_pos();
        set_byte_pos(0);
        long lCheckSum = 0; // unsigned integer checksum
        for (int i = 0; i < get_length(); i++) {
            long lByte = read(8);
            if (i >= 12 && i <= 15) lByte = 0;
            long upshift = lCheckSum << 33 >>> 32;
            long add = lByte + ((lCheckSum >>> 31) == 1 ? 1 : 0);
            lCheckSum = upshift + add;
        }
        set_pos(currentPos);
        return new byte[]{
                (byte) (0x000000ff & lCheckSum),
                (byte) ((0x0000ff00 & lCheckSum) >>> 8),
                (byte) ((0x00ff0000 & lCheckSum) >>> 16),
                (byte) ((0xff000000 & lCheckSum) >>> 24)
        };
    }
}

