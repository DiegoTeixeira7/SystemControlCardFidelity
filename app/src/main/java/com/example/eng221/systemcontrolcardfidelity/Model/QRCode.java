package com.example.eng221.systemcontrolcardfidelity.Model;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.qrcode.QRCodeReader;

// classe para gerar QR COode

public class QRCode{

    public final static int QRCodeWidth = 500;
    Bitmap bitmap;

    private int WHITE = 0xFFFFFFFF;
    private int BLACK = 0xFF000000;

    public QRCode() {

    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public String getCode() throws WriterException, ChecksumException, NotFoundException, FormatException {
        return imageToTextDecode(bitmap);
    }

    public void generateQrCode(String value) throws WriterException {
        bitmap = textToImageEncode(value);
    }

    private Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(value,
                    BarcodeFormat.QR_CODE, QRCodeWidth, QRCodeWidth, null);
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offSet = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offSet + x] = bitMatrix.get(x, y) ? this.BLACK : this.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private String imageToTextDecode(Bitmap bitmap) throws WriterException, FormatException, ChecksumException, NotFoundException {

        int[] intArray = new int[bitmap.getWidth()*bitmap.getHeight()];

        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(),intArray);

        BinaryBitmap binary_bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new QRCodeReader();

        Result result = reader.decode(binary_bitmap);

        return result.getText();
    }

}
