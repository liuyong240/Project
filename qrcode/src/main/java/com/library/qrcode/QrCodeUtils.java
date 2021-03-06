package com.library.qrcode;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by admin on 2016/11/3.
 */

public class QrCodeUtils {

    /***
     * 生成二维码Bitmap
     *
     * @param value  文本
     * @param width  bitmap 宽
     * @param height bitmap 高
     * @return
     */
    public static Bitmap buildQrCodeBitmap(String value, int width, int height) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap bitmap;
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(value, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        } catch (WriterException e) {
            bitmap = null;
        }
        return bitmap;
    }

    /***
     * 生成条形码Bitmap
     *
     * @param value  文本
     * @param width  bitmap 宽
     * @param height bitmap 高
     * @return
     */
    public static Bitmap buildBarCodeBitmap(String value, int width, int height) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap bitmap;
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(value, BarcodeFormat.CODE_128, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        } catch (WriterException e) {
            bitmap = null;
        }
        return bitmap;
    }

    /***
     * 扫描Bitmap 二维码
     * @param bitmap
     * @return
     */
    public static String decodeQrCode(Bitmap bitmap) {
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置内容的编码
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        String s;
        try {
            Result result = reader.decode(binaryBitmap, hints);
            s = result.toString();
        } catch (Exception e) {
            s = null;
        }
        return s;
    }

    /***
     * 扫描Bitmap 条形码
     * @param bitmap
     * @return
     */
    public static String decodeBarCode(Bitmap bitmap) {
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置内容的编码
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.CODE_128);
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        Code128Reader code128Reader = new Code128Reader();
        String s;
        try {
            Result result = code128Reader.decode(binaryBitmap, hints);
            s = result.toString();
        } catch (Exception e) {
            s = null;
        }
        return s;
    }
}
