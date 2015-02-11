package algorithm;
import java.util.*;

public class Generation {
	private int genNum = 0;
	private final int pNum=100;
	private final int maxSupremeElem = 10;
	private Vector<Gene> genePopulation;
	private Vector<Gene> supremeSet;
	private int maxGen;

//Start the algorithm by initializing first Generation//
	public Generation(int maxGen) {
		this.maxGen = maxGen;
		initGen();
	}
	
//Structure of the algorithm//
	public void algorithm() {
		while(genNum<this.maxGen) {
			nextGen();
			supremeGene();
			System.out.println("GENERATION " +genNum+ " GENERATED");
		}
		System.out.println("PROCESS OVER");
	}

//Initializing//
	public void initGen() {
		genePopulation = new Vector<Gene>();
		for(int index=0;index<pNum;index++) {
			Gene temp = new Gene();
			this.genePopulation.add(temp);
		}
		genNum++;
		supremeGene();
		System.out.println("GENERATION 1 INITIALIZED");
	}

//New Generation//
	public void nextGen() {
		for(int index=maxSupremeElem;index<pNum;index++) {
			int fGene,sGene;
			fGene = (int)(10*Math.random());
			sGene = ((int)(9*Math.random()) + fGene)%10;
			genePopulation.add(new Gene(supremeSet.elementAt(fGene).getGene(),supremeSet.elementAt(sGene).getGene()));
		}
		genNum++;
	}

//Select supreme gene from current generation//
	private void supremeGene() {
		supremeSet = new Vector<Gene>();
		genePopulation.sort(null);
		for(int index=0;index<maxSupremeElem;index++) {
			supremeSet.add(genePopulation.elementAt(index));
			System.out.println((index+1) + " : " + supremeSet.elementAt(index).showGene());
		}
	}
}
