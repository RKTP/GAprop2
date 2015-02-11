package algorithm;
import java.util.*;
import java.io.*;

public class InputFile {
	private static InputFile dataInstance = new InputFile();
	private File inputFile;
//Arcene Data//
	private final String fileName = "totalData.txt";
	private final int numofFeature=10000;
	private final int numofLabel=1;
	private final int numofData=200;
	private final int numofTrain=160;
	private final int numofTest=40;

//CAL500//
//	private final String fileName = "dat.arff";
//	private final int numofLabel=174;
//	private final int numofData=502;
//	private final int numofFeature=68;
	
	public double[][] feature = new double[numofFeature][numofData];
	public double[][] testFeature = new double[numofFeature][numofTest];
	public double[][] trainFeature = new double[numofFeature][numofTrain];
	public boolean[][] label = new boolean[numofLabel][numofData];
	public boolean[][] testLabel = new boolean[numofLabel][numofTest];
	public boolean[][] trainLabel = new boolean[numofLabel][numofTrain];
	
	private InputFile() {
		 inputFile = new File(fileName);
		 String temp;
		 
		 if(!inputFile.exists()) {
		      System.out.println("Source file " + fileName + " does not exist");
		      System.exit(0);
		 }
		Scanner input;
//CAL500
/*
		try {
			int line=0;
			input = new Scanner(inputFile);
			while(input.hasNext()) {
				temp = input.nextLine();
				StringTokenizer tok = new StringTokenizer(temp, ",");
				for(int index=0;index<numofFeature;index++) {
					feature[index][line] = Double.parseDouble(tok.nextToken());
				}
				for(int index=0;index<numofLabel;index++) {
					if(Integer.parseInt(tok.nextToken())==1) {
						label[index][line] = true;
					} else {
						label[index][line] = false;
					}
				}
				line++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
*/
//Arcene Data
		try {
			input = new Scanner(inputFile);
			for(int index=0;index<numofData*2;index++) {
				if(index<numofData) {
					temp = input.nextLine();
					StringTokenizer tok = new StringTokenizer(temp, " ");
					for(int sIndex=0;sIndex<numofFeature;sIndex++) {
						feature[sIndex][index] = Double.parseDouble(tok.nextToken());
					}
				} else {
					for(int sIndex=0;sIndex<numofLabel;sIndex++) {
						temp = input.nextLine();
						if(Integer.parseInt(temp)==1) {
							label[sIndex][index-numofData]=true;
						} else {
							label[sIndex][index-numofData]=false;
						}
					}
				}
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	 
	public int getNFeature() {
		 return numofFeature;
	}
	 
	public int getNLabel() {
		 return numofLabel;
	}
	
	public int getNData() {
		return numofData;
	}
	
	public int getNTest() {
		return numofTest;
	}
	
	public int getNTrain() {
		return numofTrain;
	}
	
	public static InputFile getInstance() {
		return dataInstance;
	}
}
