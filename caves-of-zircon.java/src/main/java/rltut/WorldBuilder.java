package rltut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldBuilder {
	private int width;
	private int height;
	private int depth;
	private Tile[][][] tiles;
	private int[][][] regions;
	private int nextRegion;

	public WorldBuilder(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.tiles = new Tile[width][height][depth];
		this.regions = new int[width][height][depth];
		this.nextRegion = 1;
	}

	public World build() {
		return new World(tiles);
	}

	private WorldBuilder randomizeTiles() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < depth; z++) {
					tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
				}
			}
		}
		return this;
	}

	private WorldBuilder smooth(int times) {
		Tile[][][] tiles2 = new Tile[width][height][depth];
		for (int time = 0; time < times; time++) {

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					for (int z = 0; z < depth; z++) {
						int floors = 0;
						int rocks = 0;
	
						for (int ox = -1; ox < 2; ox++) {
							for (int oy = -1; oy < 2; oy++) {
								if (x + ox < 0 || x + ox >= width || y + oy < 0
										|| y + oy >= height)
									continue;
	
								if (tiles[x + ox][y + oy][z] == Tile.FLOOR)
									floors++;
								else
									rocks++;
							}
						}
						tiles2[x][y][z] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
					}
				}
			}
			tiles = tiles2;
		}
		return this;
	}
	
	private WorldBuilder createRegions(){
		regions = new int[width][height][depth];
		
		for (int z = 0; z < depth; z++){
			for (int x = 0; x < width; x++){
				for (int y = 0; y < height; y++){
					if (tiles[x][y][z] != Tile.WALL && regions[x][y][z] == 0){
						int size = fillRegion(nextRegion++, x, y, z);
						
						if (size < 25)
							removeRegion(nextRegion - 1, z);
					}
				}
			}
		}
		return this;
	}
	
	private void removeRegion(int region, int z){
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				if (regions[x][y][z] == region){
					regions[x][y][z] = 0;
					tiles[x][y][z] = Tile.WALL;
				}
			}
		}
	}
	
	private int fillRegion(int region, int x, int y, int z) {
		int size = 1;
		ArrayList<Point> open = new ArrayList<Point>();
		open.add(new Point(x,y,z));
		regions[x][y][z] = region;
		
		while (!open.isEmpty()){
			Point p = open.remove(0);

			for (Point neighbor : p.neighbors8()){
				if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height)
					continue;
				
				if (regions[neighbor.x][neighbor.y][neighbor.z] > 0
						|| tiles[neighbor.x][neighbor.y][neighbor.z] == Tile.WALL)
					continue;

				size++;
				regions[neighbor.x][neighbor.y][neighbor.z] = region;
				open.add(neighbor);
			}
		}
		return size;
	}
	
	public WorldBuilder connectRegions(){
		for (int z = 0; z < depth-1; z++){
			connectRegionsDown(z);
		}
		return this;
	}
	
	private void connectRegionsDown(int z){
		List<Integer> connected = new ArrayList<Integer>();
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				int r = regions[x][y][z] * 1000 + regions[x][y][z+1];
				if (tiles[x][y][z] == Tile.FLOOR
						&& tiles[x][y][z+1] == Tile.FLOOR
						&& !connected.contains(r)){
					connected.add(r);
					connectRegionsDown(z, regions[x][y][z], regions[x][y][z+1]);
				}
			}
		}
	}
	
	private void connectRegionsDown(int z, int r1, int r2){
		List<Point> candidates = findRegionOverlaps(z, r1, r2);
		
		int stairs = 0;
		do{
			Point p = candidates.remove(0);
			tiles[p.x][p.y][z] = Tile.STAIRS_DOWN;
			tiles[p.x][p.y][z+1] = Tile.STAIRS_UP;
			stairs++;
		}
		while (candidates.size() / stairs > 250);
	}

	public List<Point> findRegionOverlaps(int z, int r1, int r2) {
		ArrayList<Point> candidates = new ArrayList<Point>();
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				if (tiles[x][y][z] == Tile.FLOOR
						&& tiles[x][y][z+1] == Tile.FLOOR
						&& regions[x][y][z] == r1 
						&& regions[x][y][z+1] == r2){
					candidates.add(new Point(x,y,z));
				}
			}
		}
		
		Collections.shuffle(candidates);
		return candidates;
	}
	
	private WorldBuilder addExitStairs() {
		int x = -1;
		int y = -1;
		
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		while (tiles[x][y][0] != Tile.FLOOR);
		
		tiles[x][y][0] = Tile.STAIRS_UP;
		return this;
	}

	public WorldBuilder makeCaves() {
		return randomizeTiles()
				.smooth(8)
				.createRegions()
				.connectRegions()
				.addExitStairs();
	}
}