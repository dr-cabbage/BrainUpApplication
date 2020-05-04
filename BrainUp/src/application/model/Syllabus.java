package application.model;

public class Syllabus {
	int classCode;
	public double finalWeight;
	public double examWeight;
	public double quizWeight;
	public double HWWeight;
	public double labWeight;
	public double otherWeight;
	
	public Syllabus(int cl, double finalW, double examW, double quizW, double HWW, double labW, double otherW){
		this.classCode = cl;
		this.finalWeight = finalW;
		this.examWeight = examW;
		this.quizWeight = quizW;
		this.HWWeight = HWW;
		this.labWeight = labW;
		this.otherWeight = otherW;
	}
	
	public double getWeight(char type){
		switch(type){
			case 'F':
				return this.finalWeight;	
			case 'E':
				return this.examWeight;
			case 'Q':
				return this.quizWeight;
			case 'H':
				return this.HWWeight;
			case 'L':
				return this.labWeight;
			case 'O':
				return this.otherWeight;
			default:
				return 0;
		}
	}
	
	public String toCSV() {
		String str = classCode + "," + finalWeight + "," + examWeight + "," + quizWeight + "," + HWWeight + "," + labWeight + "," + otherWeight + "\n";
		return str;
	}
}
