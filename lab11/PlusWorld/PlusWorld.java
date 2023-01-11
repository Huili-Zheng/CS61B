package PlusWorld;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Draw a row of tiles to the board, anchored at a given position
     */
    public static void drawRow(TETile[][] tiles ,Position p, TETile tile, int size) {
        for (int dx = 0; dx < size; dx++) {
            if (0<=p.x + dx && p.x + dx<WIDTH && 0<=p.y && p.y<HEIGHT) {
                tiles[p.x + dx][p.y] = tile;
            }
            else {
                return;
            }
        }
    }

    public static void drawSquare(TETile[][] tiles ,Position p, TETile tile, int size) {
        for (int dy = 0; dy > -size; dy--) {
            Position start = p.shift(0, dy);
            drawRow(tiles, start, tile, size);
        }
    }



    /**
     * Helper method for addPlus
     */
    private static void addPlusHelper(TETile[][] tiles, Position p, TETile tile, int size) {
        // top  square
        Position startTop = p.shift(size, 0);
        drawSquare(tiles, startTop, tile, size);

        // left  square
        Position startLeft = p.shift(0, -size);
        drawSquare(tiles, startLeft, tile, size);
        // center  square
        Position startCenter = p.shift(size, -size);
        drawSquare(tiles, startCenter, tile, size);

        // right  square
        Position startRight = p.shift(2 * size, -size);
        drawSquare(tiles, startRight, tile, size);

        // bottom  square
        Position startBottom = p.shift(size, -2 * size);
        drawSquare(tiles, startBottom, tile, size);

    }

    /**
     * Add a plus to the world at position P of size {CODE size}
     */
    public static void addPlus(TETile[][] tiles, Position p, TETile tile, int size) {
        addPlusHelper(tiles, p, tile, size);
    }

    /**
     * Add a column of NUM plus, each of whose biomes are chosen randomly
     * to the world at position p.
     */
    public static void addPlusColumn(TETile[][] tiles, Position p, int size, int num ) {
        if (num < 1) {
            return;
        }
        // Draw the first plus
        addPlus(tiles, p, randomTile(), size);

        // Draw n-1 plus
        if (num > 1) {
            Position bottomNeighbor = getBottomNeighbor(p, size);
            addPlusColumn(tiles, bottomNeighbor, size, num - 1);
        }
    }

    public static void addPlusRow(TETile[][] tiles, Position p, int size, int num ) {
        if (num < 1) {
            return;
        }
        // Draw the first plus
        addPlus(tiles, p, randomTile(), size);

        // Draw n-1 plus
        if (num > 1) {
            Position rightNeighbor = getRightNeighbor(p, size);
            addPlusRow(tiles, rightNeighbor, size, num - 1);
        }
    }

    public static void addPlusDiagonalHelper(TETile[][] tiles, Position p, int size) {

        addPlusColumn(tiles, p, size, HEIGHT/(size * (1 + size)));
        addPlusRow(tiles, p, size, WIDTH/(size * (1 + size)));
    }

    public static void addPlusDiagonal(TETile[][] tiles, Position p, int size) {
        addPlusDiagonalHelper(tiles, p.shift(size,2*size), size);
        addPlusDiagonalHelper(tiles, p, size);
        addPlusDiagonalHelper(tiles, p.shift(-size,-2*size), size);


    }

    private static Position getRightNeighbor(Position p, int size) {
        return p.shift(5 * size, 0);
    }
    private static Position getBottomNeighbor(Position p, int size) {
        return p.shift(0, -5 * size);
    }



    /** Fills the given 2D array of tile with blank tiles
     *
     */
    public static void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Picks a random biome tile
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            case 3: return Tileset.AVATAR;
            case 4: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

    //private helper class to deal with position
    private static class Position {
        int x;
        int y;

         Position(int x, int y) {
             this.x = x;
             this.y = y;
        }

        public Position shift(int dx, int dy) {
             return new Position(x + dx, y + dy);
        }
    }

    /**
     * Draws the plus world
     */
    public static void drawWorld(TETile[][] tiles) {
        fillWithNothing(tiles);
        Position p = new Position(0,59);
        //addPlus(tiles, p, randomTile(), 4);
        //addPlusColumn(tiles, p, 3, 3);
        addPlusDiagnose(tiles, p, 2);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        drawWorld(world);

        ter.renderFrame(world);

    }


}
