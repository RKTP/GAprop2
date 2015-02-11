package algorithm;
import java.util.*;

public class Gene implements Comparable<Gene> {
	private Vector<Integer> gene;
	private double accuracy;
	private double sensitivity;
	private double specificity;
	private InputFile data = InputFile.getInstance();
	
	@Override
	public int compareTo(Gene object) {
		if(this.accuracy>object.accuracy) {
			return -1;
		} else if(this.accuracy<object.accuracy) {
			return 1;
		} else return 0;
	}
	
//Initializing first Generation
	public Gene() {
		initGen();
		setAccuracy();
	}
	
//Generating new Generation from current Generation//
	public Gene(Vector<Integer> fGene, Vector<Integer> sGene) {
		crossGene(fGene,sGene);
		mutateGene();
		setAccuracy();
	}
	
//Randomly generate the gene
	private void initGen() {
		double insertFlag;
		gene = new Vector<Integer>();
		
		for(int index=0;index<data.getNFeature();index++) {
			insertFlag=Math.random();
			if(insertFlag<0.005) this.gene.add(index);
		}
	}

//Generate Gene from selected genes//
	private void crossGene(Vector<Integer> fGene,Vector<Integer> sGene) {
		double deleteFlag;
		this.gene = new Vector<Integer>();
		
		this.gene.addAll(fGene);
		this.gene.addAll(sGene);
		if(this.gene.size()>this.data.getNFeature()/200) {
			for(int index=0;index<this.gene.size();index++) {
				deleteFlag=Math.random();
				if(deleteFlag<0.4) this.gene.remove(index--);
			}
		}
		for(int index=0;index<this.gene.size();index++) {
			for(int cIndex=index+1;cIndex<this.gene.size();cIndex++) {
				if(this.gene.elementAt(index)==this.gene.elementAt(cIndex)) this.gene.remove(cIndex--);
			}
		}
	}

//Randomly mutate the gene//
	public void mutateGene() {
		double mutateFlag;
		int tryCount;
		
		for(int index=0;index<this.gene.size();index++) {
			mutateFlag=Math.random();
			tryCount=0;
			if(mutateFlag<0.2) {
				int temp=this.gene.elementAt(0);
				while(this.gene.contains(temp)||tryCount<5000) {
					temp=(int)(68*Math.random());
					tryCount++;
				}
				if(tryCount<5000) this.gene.add(index, temp);
			}
		}
	}
	
//Get Accuracy of the gene//
	private void setAccuracy() {
		double[] accArr = new double[this.data.getNData()];
		double avgAcc = 0;
		for(int index=0;index<this.data.getNData();index++) {
			accArr[index] = multiLabelAccuracy(index);
			avgAcc += accArr[index]/this.data.getNData();
		}
		this.accuracy = avgAcc;
	}

//MLA//
	private double multiLabelAccuracy(int dataIndex) {
		double dataAccuracy=0;
		Vector<Boolean> prediction = new Vector<Boolean>();
		prediction.addAll(predict(dataIndex));
		int intersection=0, union=0;
		for(int index=0;index<data.getNLabel();index++) {
			if(prediction.elementAt(index)&&data.label[index][dataIndex]) intersection++;
			if(prediction.elementAt(index)||data.label[index][dataIndex]) union++;
		}
		if(union==0) return 0;
		dataAccuracy=(double)intersection/(double)union;
		return dataAccuracy;
	}
	
//k-NN for selecting similar data + Predicting label//
	private Vector<Boolean> predict(int dataIndex) {
		Vector<Boolean> prediction = new Vector<Boolean>();
		double[][] distanceSet = new double[3][2];
		
		for(int index=0;index<3;index++) {
			distanceSet[index][0] = Double.MAX_VALUE;
		}
		
		for(int index=0;index<this.data.getNData();index++) {
			double temp=dataDistance(dataIndex,index);
			for(int sIndex=0;sIndex<3;sIndex++) {
				if(distanceSet[sIndex][0]>=temp) {
					for(int tIndex=2;tIndex>sIndex;tIndex--) {
						distanceSet[tIndex][0] = distanceSet[tIndex-1][0];
						distanceSet[tIndex][1] = distanceSet[tIndex-1][1];
					}
					distanceSet[sIndex][0] = temp;
					distanceSet[sIndex][1] = index;
				}
			}
		}
//Prediction from this point//
		for(int index=0;index<this.data.getNLabel();index++) {
			int labelFlag=0;
			for(int sIndex=0;sIndex<3;sIndex++) {
				if(data.label[index][(int)distanceSet[sIndex][1]]) {
					labelFlag++;
				} else {
					labelFlag--;
				}
			}
			if(labelFlag>0) {
				prediction.add(true);
			} else {
				prediction.add(false);
			}
		}
		
		return prediction;
	}
	
//Calculate distance between two data for k-NN//
	private double dataDistance(int dataIndex, int compare) {
		double distance;
		double sum=0;
		if(dataIndex==compare)
			return Double.MAX_VALUE;
		
		for(int index=0;index<this.gene.size();index++) {
			sum += Math.pow((data.feature[gene.elementAt(index)][dataIndex]-data.feature[gene.elementAt(index)][compare]),2.0);
		}
		distance = Math.sqrt(sum);
		return distance;
	}
	
	public Vector<Integer> getGene() {
		return gene;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public String showGene() {
		String geneElement="";
		
		this.gene.sort(null);
		for(int index=0;index<gene.size();index++) {
			geneElement+=gene.elementAt(index);
			geneElement+=" ";
		}
		geneElement += "| " + this.accuracy;
		return geneElement;
	}
}
