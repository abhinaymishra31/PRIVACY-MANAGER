package core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
* This class can hide and discover texts and images.
*
*/

public class LSBImageEncoding {
  private final boolean hideDebug = false;
  private final boolean discoverDebug = false;
  private final String prefixText = "101111011110001010100101111101100101001010100101";//48bit.last 4bit differ
  private final String prefixImg = "101111011110001010100101111101100101001010101010";//48bit 4 LSB differ


  /**
  * To construct an object of the steganography class you don't need parameters.
  */
  public LSBImageEncoding(){}


  /**
  * This is the main method to hide an image. It will create a String named binary which contains the prefix, the size of the image and the pixel of the image.
  *
  * @param container the BufferedImage into which the other image will be hidden
  * @param hide the BufferedImage which will be hidden
  */
  public boolean hideImage(BufferedImage container, BufferedImage hide) {
    if (tooBig(container, hide)) {
      System.out.println("Image too big. It cannot be hidden.");
      return false;
    }

    String text = parseImageToString(hide);
    String binary = this.prefixImg +  getBinSize(container, hide) + text;

    return hide(container,binary);
  }



  


  /**
  * This is the general method to hide the binary String in an image.
  *
  * @param img the BufferedImage into which the given String will be hidden
  * @param binary the String which contains a prefix, the size/length of the image/text and the pixel of the image or the text in binary.
  */
  public boolean hide(BufferedImage img, String binary){
    if (hideDebug){
      System.out.println("binary: " + binary);
    }

    int pos = 0;
    for (int y = 0; y < (binary.length() / 4) / img.getWidth() + 1; y++) {
      for (int x = 0; x < img.getWidth() && pos < binary.length(); x++) {
        int rgb = img.getRGB(x, y);
        String bits = binary.substring(pos, pos+4);
        img.setRGB(x, y, bitToPixel(bits, rgb));
        if (hideDebug) {
          System.out.println("bits: " + bits);
          System.out.println(Integer.toBinaryString(img.getRGB(x, y)));
        }
        pos+=4;
      }
    }
    return true;
  }

  /**
  * This method gives back the type of the hidden content in a BufferedImage.
  * 0 -> nothing, 1 -> text, 2 -> image
  *
  * @param img the BufferedImage which hides the content
  */
  public int getType(BufferedImage img) {
    int[] prefixTxt = new int[this.prefixText.length() / 4];
    for (int y = 0; y < (this.prefixText.length() / 4) / img.getWidth() + 1; y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        int index = y * img.getWidth() + x;
        if (index < prefixTxt.length)
          prefixTxt[index] = img.getRGB(x, y);
      }
    }
    if (discoverFromTo(prefixTxt, 0, prefixTxt.length).equals(this.prefixText))
      return 1;
    int[] prefixImg = new int[this.prefixImg.length() / 4];
    for (int y = 0; y < (this.prefixImg.length() / 4) / img.getWidth() + 1; y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        int index = y * img.getWidth() + x;
        if (index < prefixImg.length)
          prefixImg[index] = img.getRGB(x, y);
      }
    }
    if (discoverFromTo(prefixImg, 0, prefixImg.length).equals(this.prefixImg))
      return 2;
    return 0;
  }




  /**
  * This is the main method to discover an hidden image.
  *
  * @param contain the BufferedImage which hides the other image
  */
  public BufferedImage discoverImage(BufferedImage container) {
    int[] data = imageToArray(container);
    int pos = 0;

    if (discoverDebug){
      System.out.println("prefix: " + discoverFromTo(data, pos, this.prefixText.length() / 4));
    }
    if (!discoverFromTo(data, pos, this.prefixText.length() / 4).equals(this.prefixImg)) {
      System.out.println("No prefix!");
      return container;
    }
    pos += this.prefixText.length() / 4;

    int widthBinLen = binLenOf(container.getWidth());
    String widthImgString = discoverFromTo(data, pos, pos + ceilMod4(widthBinLen) / 4);
    int widthImg = Integer.parseInt(widthImgString, 2);
    pos += ceilMod4(widthBinLen) / 4;

    int heightBinLen = binLenOf(container.getHeight());
    String heightImgString = discoverFromTo(data, pos, pos + ceilMod4(heightBinLen) / 4);
    int heightImg = Integer.parseInt(heightImgString, 2);
    pos += ceilMod4(heightBinLen) / 4;

    String[] dataImg = new String[widthImg * heightImg];
    int index;
    for (int i = pos; i < pos + widthImg * heightImg * 8; i++) {
      index = (i - pos) / 8;
      if ((i - pos) % 8 == 0) {
        dataImg[index] = pixelToBit(data[i]);
      } else {
        dataImg[index] += pixelToBit(data[i]);
      }
    }
    return parseImageToBufferedImage(dataImg, widthImg, heightImg);
  }


  /***
  * This method hides four given bits in a pixel.
  *
  * @param bits the given bits
  * @param rgb the int witch contains the information of the argb-channels of the pixel
  */
  public int bitToPixel(String bits, int rgb){
    int[] bitValues = new int[4];
    bitValues[0] = Integer.parseInt(bits.substring(0, 1));
    bitValues[1] = Integer.parseInt(bits.substring(1, 2));
    bitValues[2] = Integer.parseInt(bits.substring(2, 3));
    bitValues[3] = Integer.parseInt(bits.substring(3, 4));

    int a = (rgb >>> 25)*2 + bitValues[0];
    int r = ((rgb >>> 17)*2 + bitValues[1]) & 0xFF;
    int g = ((rgb >>> 9)*2 + bitValues[2]) & 0xFF;
    int b = ((rgb >>> 1)*2 + bitValues[3]) & 0xFF;


    if (hideDebug) {
      System.out.println("a: " + Integer.toBinaryString(a));
      System.out.println("r: " + Integer.toBinaryString(r));
      System.out.println("g: " + Integer.toBinaryString(g));
      System.out.println("b: " + Integer.toBinaryString(b));
    }

    rgb = (a << 24) + (r << 16) + (g << 8) + b;
    return rgb;
  }


  /**
  * This method extracts the four hidden bits in a pixel.
  *
  * @param rgb the int witch contains the information of the argb-channels of a pixel
  */
  public String pixelToBit(int rgb) {
    int a = (rgb >> 24) & 0x1;
    int r = (rgb >> 16) & 0x1;
    int g = (rgb >> 8) & 0x1;
    int b = rgb & 0x1;

    return String.valueOf(a) + String.valueOf(r) + String.valueOf(g) + String.valueOf(b);
  }



  /**
  * This method calculates the size of the image to hide in binary. It also restocks the size so it is only dependant on the image in which the other will be hidden.
  *
  * @param container the BufferedImage in which the other will be hidden
  * @param img the BufferedImage which will be hidden
  */
  public String getBinSize(BufferedImage container, BufferedImage img) {
    String widthBin = Integer.toBinaryString(img.getWidth());
    String heightBin = Integer.toBinaryString(img.getHeight());

    int widthBinLen = Integer.toBinaryString(container.getWidth()).length();
    int heightBinLen = Integer.toBinaryString(container.getHeight()).length();

    widthBin = restock(widthBin, ceilMod4(widthBinLen));
    heightBin = restock(heightBin, ceilMod4(heightBinLen));

    String sizeS = widthBin + heightBin;

    return sizeS;
  }


  /**
  * This method rounds the given number up to a number which is divisible by four.
  *
  * @param n given number
  */
  public int ceilMod4(int n){
    return n + (4 - n % 4);
  }


  /**
  * This method restocks a given String with left-hand zeros so it is as long as we want it to.
  *
  * @param bin the String which shall be restocked
  * @param len the int which says how long the String shall be
     * @return 
  */
  public String restock(String bin, int len) {
    while (bin.length() < len) {
      bin = "0" + bin;
    }
    return bin;
  }


  /**
  * This method calculates the maximal possible length of a text which shall be hidden in an image.
  *
  * @param image the BufferedImage in which the text shall be hidden
  */
  public int maxLength(BufferedImage image) {
    int len = 0;
    len = (image.getWidth() * image.getHeight() * 4 - this.prefixText.length()) / 8;
    String lenS = Integer.toBinaryString(len);
    if (lenS.length() % 8 == 0) {
      len -= lenS.length() / 8;
    } else {
      len -= lenS.length() / 8 + 1;
    }
    return len - 1;
  }


  /**
  * This method checks if the image which will be hidden doesn't have more than 10.000 pixel and that this image isn't
  * too big to be hidden in the other image.
  *
  * @param container this BufferedImage is the one in which the other will be hidden
  * @param img this BufferedImage is the one which will be hidden
     * @return 
  */
  public boolean tooBig(BufferedImage container, BufferedImage img) {
    if (img.getHeight() * img.getWidth() > 100000) {
      return true;
    }

    int sizeC = container.getHeight() * container.getWidth();
    int sizeI = img.getHeight() * img.getWidth();
    return (sizeC / 8 - prefixImg.length() - (Integer.toBinaryString(container.getHeight()) + Integer.toBinaryString(container.getWidth())).length()) <= sizeI;
  }


  /**
  * This method converts the pixel of the given BufferedImage into a String.
  *
  * @param img the BufferedImage which will be converted
  */
  public String parseImageToString(BufferedImage img) {
    String data = "";
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        data += Integer.toBinaryString(img.getRGB(x, y));
      }
    }
    return data;
  }


  /**
  * This method converts the pixel of the given BufferedImage into a String.
  *
  * @param img the BufferedImage which will be converted
  */
  public int[] imageToArray(BufferedImage img){
    int[] data = new int[img.getHeight() * img.getWidth()];

    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        data[y*img.getWidth() + x] = img.getRGB(x, y);
      }
    }

    return data;
  }


  /**
  * This method is the opposite to imageToArray. It creats a new BufferedImage with the given width and height and the in the given array stored pixel.
  *
  * @param data array in which the information of the argb-channels of the pixel are stored
  * @param width the width of the new BufferedImage
  * @param height the height of the new BufferedImage
  */
  public BufferedImage parseImageToBufferedImage(String[] data, int width, int height) {
    if (data.length > width * height) {
      System.out.println("Data is too long or size is not correct!");
      return null;
    }

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        long rgb = Long.parseLong(data[y*width + x], 2);
        img.setRGB(x, y, (int) rgb);
      }
    }
    return img;
  }


  /**
  * This method discovers the binary code in the as an array given image.
  *
  * @param data the image as an array
  * @param a the starting position
  * @param z the final position
  */
  public String discoverFromTo(int[] data, int a, int z){
    String result = "";
    for (int i = a; i < z; i++) {
      result += pixelToBit(data[i]);
    }
    return result;
  }


  /**
  * This method estimates the length of a given number in binary.
  *
  * @param number the given number
  */
  public int binLenOf(int number){
    return Integer.toBinaryString(number).length();
  }
}
