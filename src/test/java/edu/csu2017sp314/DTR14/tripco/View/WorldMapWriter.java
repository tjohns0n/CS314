package edu.csu2017sp314.DTR14.tripco.View;

public class WorldMapWriter extends SVGWriter {

	public WorldMapWriter(String filename) {
		super(filename);
		width = 1440;
		height = 720;
		initXml();
		scaleBase();
	}
	
	public WorldMapWriter() {
		super();
		width = 1440;
		height = 720;
	}
	
	@Override
	int[] mapPoints(double x, double y) {
		int[] mappedPoints = new int[2];
		mappedPoints[0] = (int)(180 + x) * 4;
		mappedPoints[1] = (int)(90 - y) * 4;
		return mappedPoints;
	}

	@Override
	void addTitle(String text, String id) {
		double[] coordinates = {width / 2, 30};
		XMLElement title = addText(text, coordinates, 24, id, true, false);
		content.add(title.getStart());
		content.add(text);
		content.add(title.getEnd());
	}

	@Override
	void addFooter(String text, String id) {
		double[] coordinates = {width / 2, height - 20};
		XMLElement foot = addText(text, coordinates, 24, id, true, false);
		content.add(foot.getStart());
		content.add(text);
		content.add(foot.getEnd());
	}
	
	public void addWorldLine(double[] coordinates) {
		
	}
	
	private void scaleBase() {
		originalContent.add(0, "<g transform=\"matrix(1.40625 0 0 1.40625 0 0)\">");
		originalContent.add(originalContent.size(), "</g>");
	}

	public static void main(String[] args) {
		WorldMapWriter wmw = new WorldMapWriter("World3.svg");
		wmw.addTitle("Here is a test title", "title");
		wmw.addFooter("Here is a test footer", "footer");
		wmw.addLine(new double[] {-104.6737, 39.8561, 144.8410, -37.6690}, "blue", 2, true);
		wmw.writeSVG("cool.svg");
	}
}
