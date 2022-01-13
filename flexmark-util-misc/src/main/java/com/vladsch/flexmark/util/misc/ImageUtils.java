/*
 * Copyright (c) 2017, Holger Brandl, All rights reserved.
 *
 * Copyright (c) 2017, Vladimir Schneider, All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * Copyright (c) 2015-2018 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.util.misc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.util.misc.Utils.minLimit;

public class ImageUtils {
    public static Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static Image getImageFromClipboard() {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        return getImageFromTransferable(transferable);
    }

    public static Image getImageFromTransferable(Transferable transferable) {
        try {
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                return (Image) transferable.getTransferData(DataFlavor.imageFlavor);
            } else {
                return null;
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return null;
    }

    public static BufferedImage scaleImage(BufferedImage sourceImage, int newWidth, int newHeight, int opType) {
        if (sourceImage == null) {
            return null;
        }

        if (newWidth == 0 || newHeight == 0) {
            return null;
        }

        AffineTransform at = AffineTransform.getScaleInstance(
                (double) newWidth / sourceImage.getWidth(null),
                (double) newHeight / sourceImage.getHeight(null)
        );

        //  http://nickyguides.digital-digest.com/bilinear-vs-bicubic.htm
        AffineTransformOp op = new AffineTransformOp(at, opType != 0 ? opType : AffineTransformOp.TYPE_BILINEAR);
        return op.filter(sourceImage, null);
    }

    public static BufferedImage toBufferedImage(Image src) {
        if (src == null) {
            return null;
        } else if (src instanceof BufferedImage) {
            return (BufferedImage) src;
        }

        int w = src.getWidth(null);
        int h = src.getHeight(null);
        if (w < 0 || h < 0) {
            return null;
        }

        int type = BufferedImage.TYPE_INT_ARGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();

        return dest;
    }

    public static void save(BufferedImage image, File file, String format) {
        try {
            ImageIO.write(image, format, file);  // ignore returned boolean
        } catch (Throwable e) {
            System.out.println("Write error for " + file.getPath() + ": " + e.getMessage());
        }
    }

    /**
     * @param cachedImageFile file
     * @return Could be {@code null} if the image could not be read from the file (because of whatever strange
     *         reason).
     */
    public static BufferedImage loadImageFromFile(File cachedImageFile) {
        if (cachedImageFile == null || !cachedImageFile.isFile()) {
            return null;
        }

        try {
            // related to http://bugs.java.com/bugdatabase/view_bug.do;jsessionid=dc84943191e06dffffffffdf200f5210dd319?bug_id=6967419
            for (int i = 0; i < 3; i++) {
                BufferedImage read = null;
                try {
                    read = ImageIO.read(cachedImageFile);
                } catch (IndexOutOfBoundsException e) {
                    System.err.print("*");
                    System.err.println("could not read" + cachedImageFile);
                    continue;
                }

                if (i > 0) System.err.println();

                return read;
            }
        } catch (Throwable e) {
            //System.err.println("deleting " + cachedImageFile);
            //cachedImageFile.delete();
            return null;
        }

        return null;
    }

    /**
     * @param cachedImageBytes file
     * @param idPath image file path for error reporting
     * @return Could be {@code null} if the image could not be read from the file (because of whatever strange
     *         reason).
     */
    public static BufferedImage loadImageFromContent(byte[] cachedImageBytes, String idPath) {
        if (cachedImageBytes == null) {
            return null;
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(cachedImageBytes);
        
        try {
            return ImageIO.read(inputStream);
        } catch (IndexOutOfBoundsException | IOException e) {
            System.err.print("*");
            System.err.println("could not read from image bytes for " + idPath);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String base64Encode(BufferedImage image) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "PNG", bos);
            byte[] imageBytes = bos.toByteArray();
            // diagnostic/2553 on windows its \r\n
            imageString = Base64.getEncoder().encodeToString(imageBytes).replace("\r", "").replace("\n", "");
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64," + imageString;
    }

    public static String base64Encode(File file) {
        if (file == null || !file.isFile()) {
            return null;
        }

        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] imageBytes = new byte[(int) file.length()];
            if (fileInputStreamReader.read(imageBytes) != -1) {
                // diagnostic/2553 on windows its \r\n
                return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes).replace("\r", "").replace("\n", "");
            }
            return null;
        } catch (Throwable e) {
            return null;
        }
    }

    public static BufferedImage base64Decode(File file) {
        if (file == null || !file.isFile()) {
            return null;
        }

        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            if (fileInputStreamReader.read(bytes) != -1) {
                String encoded = new String(bytes, StandardCharsets.UTF_8);
                int pos = encoded.indexOf(',');
                if (pos >= 0) {
                    String encodedImage = encoded.substring(pos + 1);
                    byte[] imageBytes = Base64.getDecoder().decode(encodedImage);

                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                    BufferedImage bufferedImage = ImageIO.read(bis);
                    bis.close();
                    return bufferedImage;
                }
            }
            return null;
        } catch (Throwable e) {
            return null;
        }
    }

    final private static Pattern BASE64_ENCODING_PATTERN = Pattern.compile("^data:image/[a-z0-9_-]+;base64,", Pattern.CASE_INSENSITIVE);

    public static boolean isEncodedImage(String encoded) {
        return encoded != null && encoded.startsWith("data:image/") && BASE64_ENCODING_PATTERN.matcher(encoded).find();
    }

    public static boolean isPossiblyEncodedImage(String encoded) {
        return encoded != null && encoded.startsWith("data:image/");
    }

    public static BufferedImage base64Decode(String encoded) {
        if (encoded == null || encoded.isEmpty()) {
            return null;
        }

        try {
            int pos = encoded.indexOf(',');
            if (pos >= 0) {
                String encodedImage = encoded.substring(pos + 1);
                byte[] imageBytes = Base64.getDecoder().decode(encodedImage);

                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                BufferedImage bufferedImage = ImageIO.read(bis);
                bis.close();
                return bufferedImage;
            }
            return null;
        } catch (Throwable e) {
            return null;
        }
    }

    public static BufferedImage loadImageFromURL(String imageURL) {
        return loadImageFromURL(imageURL, false);
    }

    /**
     * Load image from URL.
     * <p>
     * NOTE: Java7 JDK cannot load some images including GitHub emoji. Compiling this library with Java8 solves the problem.
     *
     * @param imageURL           url of the image
     * @param logImageProcessing true if errors are to print to console
     * @return image or null if failed to download.
     */
    public static BufferedImage loadImageFromURL(String imageURL, boolean logImageProcessing) {
        if (imageURL != null) {
            try {
                Image image = ImageIO.read(new URL(imageURL));
                return toBufferedImage(image);
            } catch (IOException e) {
                if (logImageProcessing) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /*
     * http://stackoverflow.com/questions/7603400/how-to-make-a-rounded-corner-image-in-java
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius, int borderWidth) {
        if ((float) cornerRadius == 0) return image;

        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        //BufferedImage output = UIUtil.createImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, (float) cornerRadius, (float) cornerRadius));

        // ... then composing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();
        //output.setRGB(3, 3, 123);
        return output;
    }

    public static BufferedImage addBorder(BufferedImage image, Color borderColor, int borderWidth, int cornerRadius) {
        int w = image.getWidth() + borderWidth * 2;
        int h = image.getHeight() + borderWidth * 2;
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
        g2.setColor(borderColor);
        g2.drawImage(image, borderWidth, borderWidth, image.getWidth(), image.getHeight(), null);
        g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth));
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        int width = w - borderWidth - 1;
        int height = h - borderWidth - 1;
        int halfBorder = borderWidth / 2;
        if (cornerRadius > 0) {
            int adjustedRadius = cornerRadius + borderWidth;
            g2.drawRoundRect(halfBorder, halfBorder, width, height, adjustedRadius, adjustedRadius);
        } else {
            g2.drawRect(halfBorder, halfBorder, width, height);
        }
        g2.dispose();
        return output;
    }

    public static BufferedImage drawRectangle(BufferedImage image, int x, int y, int w, int h, Color borderColor, int borderWidth, int cornerRadius) {

        return drawRectangle(image, x, y, w, h, borderColor, borderWidth, cornerRadius, null, 0.0f);
    }

    public static BufferedImage drawRectangle(BufferedImage image, int x, int y, int w, int h, Color borderColor, int borderWidth, int cornerRadius, float[] dash, float dashPhase) {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
        boolean invert = borderColor == null;

        if (invert) {
            // invert
            int rgb = image.getRGB(x + w / 2, y + h / 2);
            borderColor = Color.getColor("", ~(rgb & 0xFFFFFF));
        }

        g2.drawImage(image, 0, 0, null);
        if (dash != null) {
            g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth, dash, dashPhase));
        } else {
            g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth));
        }

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(borderColor);
        if (cornerRadius > 0) {
            g2.drawRoundRect(x, y, w, h, cornerRadius, cornerRadius);
        } else {
            g2.drawRect(x, y, w, h);
        }
        g2.dispose();
        return output;
    }

    public static BufferedImage drawOval(BufferedImage image, int x, int y, int w, int h, Color borderColor, int borderWidth, float[] dash, float dashPhase) {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();
        boolean invert = borderColor == null;

        if (invert) {
            // invert
            int rgb = image.getRGB(x + w / 2, y + h / 2);
            borderColor = Color.getColor("", ~(rgb & 0xFFFFFF));
        }

        g2.drawImage(image, 0, 0, null);
        if (dash != null) {
            g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth, dash, dashPhase));
        } else {
            g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth));
        }

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(borderColor);
        g2.drawOval(x, y, w, h);
        g2.dispose();
        return output;
    }

    public static BufferedImage drawHighlightRectangle(
            BufferedImage image,
            int x, int y, int w, int h,
            Color borderColor, int borderWidth, int cornerRadius,
            Color innerFillColor
    ) {
        //BufferedImage output = UIUtil.createImage(w, h, BufferedImage.TYPE_INT_ARGB);
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        boolean innerFilled = innerFillColor.getAlpha() != 0;

        g2.drawImage(image, 0, 0, null);

        if (cornerRadius > 0) {
            if (innerFilled) {
                g2.setColor(innerFillColor);
                g2.fillRoundRect(x, y, w, h, cornerRadius, cornerRadius);
            }

            if (borderWidth > 0) {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth));
                g2.drawRoundRect(x, y, w, h, cornerRadius, cornerRadius);
            }
        } else {
            if (innerFilled) {
                g2.setColor(innerFillColor);
                g2.fillRect(x, y, w, h);
            }

            if (borderWidth > 0) {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth));
                g2.drawRect(x, y, w, h);
            }
        }

        g2.dispose();
        return output;
    }

    public static BufferedImage drawHighlightOval(
            BufferedImage image,
            int x, int y, int w, int h,
            Color borderColor, int borderWidth,
            Color innerFillColor
    ) {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        boolean innerFilled = innerFillColor.getAlpha() != 0;

        g2.drawImage(image, 0, 0, null);

        if (innerFilled) {
            g2.setColor(innerFillColor);
            g2.fillOval(x, y, w, h);
        }

        if (borderWidth > 0) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, borderWidth));
            g2.drawOval(x, y, w, h);
        }

        g2.dispose();
        return output;
    }

    public static BufferedImage punchOuterHighlightRectangle(
            BufferedImage image,
            BufferedImage outerImage,
            int x, int y, int w, int h,
            int borderWidth, int cornerRadius,
            Color outerFillColor,
            int outerBorderWidth,
            int outerCornerRadius,
            boolean applyToImage
    ) {
        boolean outerFilled = outerFillColor.getAlpha() != 0;
        if (!outerFilled) {
            return outerImage;
        }

        BufferedImage output = outerImage != null ? outerImage : new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int imgW = image.getWidth();
        int imgH = image.getHeight();

        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (outerImage == null) {
            // first one, we need to fill it
            g2.setColor(outerFillColor);
            if (outerCornerRadius > 0) {
                g2.fillRoundRect(outerBorderWidth, outerBorderWidth, imgW - 2 * outerBorderWidth, imgH - 2 * outerBorderWidth, outerCornerRadius, outerCornerRadius);
            } else {
                g2.fillRect(outerBorderWidth, outerBorderWidth, imgW - 2 * outerBorderWidth, imgH - 2 * outerBorderWidth);
            }
        }

        if (cornerRadius > 0) {
            g2.setColor(TRANSPARENT);
            g2.setComposite(AlphaComposite.Src);
            g2.fillRoundRect(minLimit(0, x - borderWidth / 2), minLimit(0, y - borderWidth / 2), w + borderWidth, h + borderWidth, cornerRadius + borderWidth, cornerRadius + borderWidth);
        } else {
            g2.setColor(TRANSPARENT);
            g2.setComposite(AlphaComposite.Src);
            g2.fillRect(minLimit(0, x - borderWidth / 2), minLimit(0, y - borderWidth / 2), w + borderWidth, h + borderWidth);
        }

        if (applyToImage) {
            // combine with image
            g2.setComposite(AlphaComposite.DstOver);
            g2.drawImage(image, 0, 0, null);
        }

        g2.dispose();
        return output;
    }

    public static BufferedImage punchOuterHighlightOval(
            BufferedImage image,
            BufferedImage outerImage,
            int x, int y, int w, int h,
            int borderWidth,
            Color outerFillColor,
            int outerBorderWidth,
            int outerCornerRadius,
            boolean applyToImage
    ) {
        boolean outerFilled = outerFillColor.getAlpha() != 0;
        if (!outerFilled) {
            return outerImage;
        }

        BufferedImage output = outerImage != null ? outerImage : new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int imgW = image.getWidth();
        int imgH = image.getHeight();

        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (outerImage == null) {
            // first one, we need to fill it
            g2.setColor(outerFillColor);
            if (outerCornerRadius > 0) {
                g2.fillRoundRect(outerBorderWidth, outerBorderWidth, imgW - 2 * outerBorderWidth, imgH - 2 * outerBorderWidth, outerCornerRadius, outerCornerRadius);
            } else {
                g2.fillRect(outerBorderWidth, outerBorderWidth, imgW - 2 * outerBorderWidth, imgH - 2 * outerBorderWidth);
            }
        }

        g2.setColor(TRANSPARENT);
        g2.setComposite(AlphaComposite.Src);
        g2.fillOval(minLimit(0, x - borderWidth / 2), minLimit(0, y - borderWidth / 2), w + borderWidth, h + borderWidth);

        if (applyToImage) {
            // combine with image
            g2.setComposite(AlphaComposite.DstOver);
            g2.drawImage(image, 0, 0, null);
        }

        g2.dispose();
        return output;
    }

    /*
     * http://stackoverflow.com/questions/2386064/how-do-i-crop-an-image-in-java
     */
    public static BufferedImage cropImage(BufferedImage image, int trimLeft, int trimRight, int trimTop, int trimBottom) {
        BufferedImage output = image.getSubimage(trimLeft, trimTop, image.getWidth() - trimLeft - trimRight, image.getHeight() - trimTop - trimBottom);
        return output;
    }

    /*
     * http://stackoverflow.com/questions/464825/converting-transparent-gif-png-to-jpeg-using-java
     */
    public static BufferedImage removeAlpha(BufferedImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), Color.WHITE, null);
        g.dispose();

        return bufferedImage;
    }

    /*
     * http://stackoverflow.com/questions/665406/how-to-make-a-color-transparent-in-a-bufferedimage-and-save-as-png
     */
    public static Image toTransparent(BufferedImage image, Color color, int tolerance) {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            final public int markerRGB = color.getRGB() | 0xFF000000;
            final int radius = tolerance * tolerance * 3;

            final public int filterRGB(int x, int y, int rgb) {
                if (tolerance == 0 && (rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    if ((rgb & 0xFF000000) == 0xFF000000) {

                        int delta1 = ((rgb & 0xFF0000) >> 16) - ((markerRGB & 0xFF0000) >> 16);
                        int delta2 = ((rgb & 0x00FF00) >> 8) - ((markerRGB & 0x00FF00) >> 8);
                        int delta3 = ((rgb & 0x0000FF)) - ((markerRGB & 0x0000FF));

                        int radDiff = delta1 * delta1 + delta2 * delta2 + delta3 * delta3;
                        if (radDiff <= radius) {
                            // Mark the alpha bits as zero - transparent
                            return 0x00FFFFFF & rgb;
                        }
                    }
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static byte[] getImageBytes(BufferedImage image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "PNG", bos);
            byte[] imageBytes = bos.toByteArray();

            bos.close();
            return imageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
