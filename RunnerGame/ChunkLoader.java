import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;

public class ChunkLoader {
    public static void main(String args[]) {
        ArrayList<ArrayList<Wall>> myGreatList = chunks();

        System.out.println(myGreatList.get(1).size());
    }


    //     0 = empty, 1 = wall
    public static ArrayList<ArrayList<Wall>> chunks() {
//        init 2d arraylist of arraylist of walls
        ArrayList<ArrayList<Wall>> chunksList = new ArrayList<ArrayList<Wall>>();

        BufferedImage image = null;
        try {
//            read in image using file stream instead of new file object so we can package properly in jar and it is cross compatible
            image = ImageIO.read(Objects.requireNonNull(ChunkLoader.class.getResourceAsStream("assets/chunks.png")));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
//       check to make sure we can actually parse this image
        if (image == null || image.getHeight() != 104 || image.getWidth() != 104) {
            System.out.println("Error: Image is not acceptable format");
            System.exit(1);
        }
//        loop first indexing by x chunk then y chunk. within each chunk loop through each pixel and check if it is a wall or not
        for (int chunkY = 0; chunkY < 5; chunkY++) {
            for (int chunkX = 0; chunkX < 5; chunkX++) {

                System.out.println("chunk " +(chunkY*5 + chunkX));
                int chunkXOffset = chunkX * 21;
                int chunkYOffset = chunkY * 21;
                ArrayList<Wall> chunk = new ArrayList<Wall>();
                int ycounter = 0;
                for (int y = chunkYOffset; y < 20 + chunkYOffset; y++)
                {
                    int xcounter = 0;
                    for (int x = chunkXOffset; x < 20 + chunkXOffset; x++){
                        System.out.println("x " + x + " y " + y);


                        xcounter++;
                        int pixel = image.getRGB(x, y);
//                        if the pixel is transparent, do nothing
                        if (pixel >> 24 == 0x00) {
                            continue;
                        }
//                        otherwise if it's black, add a wall objct at that pixel's xy loc
                        else if (new Color(pixel).equals(new Color(0, 0, 0))) {
//                            this is a wall because wall green
                            chunk.add(new Wall(xcounter, ycounter, 50, 50));
                            System.out.println("adding wall to chunk " + (chunkY*5 + chunkX) + " at " + x + " " + y);
                        }

                    }
                    ycounter++;
                }
                System.out.println("adding chunk" + chunk.size());
                chunksList.add(chunk);

            }

        }

        return chunksList;
    }
}

