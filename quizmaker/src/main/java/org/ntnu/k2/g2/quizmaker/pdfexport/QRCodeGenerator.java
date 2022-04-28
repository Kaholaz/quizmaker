package org.ntnu.k2.g2.quizmaker.pdfexport;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.ntnu.k2.g2.quizmaker.data.QuizModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 * Class that creates QR codes.
 * The main shell of the class is from here: https://www.journaldev.com/470/java-qr-code-generator-zxing-example
 */
public class QRCodeGenerator {

    /**
     * Creates a QR based on the url of quiz and saves it to disk.
     * @param quiz The quiz to create a QR code of.
     * @return The QR code as an image.
     * @throws WriterException Throws an exception if the operation was unsuccessful.
     */
    public static Image getQRImage(QuizModel quiz) throws WriterException {
        String qrCodeText = quiz.getUrl();
        int size = 125;
        return createQRImage(qrCodeText, size);
    }

    /**
     *
     * @param qrCodeText The text to encode into the qr code (usually a URL)
     * @param size The size of the QR code.
     * @return The QR code image.
     * @throws WriterException Throws an exception if the operation was unsuccessful.
     */
    private static Image createQRImage(String qrCodeText, int size) throws WriterException {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        // throws WriterException ------------V
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);

        // Make the BufferedImage that hold the QRCode
        int matrixSize = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixSize, matrixSize, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();

        // Fill picture with white (background)
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixSize, matrixSize);

        // Set brush color to black and paints QR code
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (byteMatrix.get(i, j)) {
                    // Paint black if the position in the byte-matrix evaluates to 1 (true)
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }
}
