package application.model;

/**
 * Holds Syllabus Information
 * 
 * @author GroupUs
 * @author Kale King
 * @author Ian Solis
 * @author Jose Morales
 * @author Seyma Oz
 * @author Claudio Ordaz
 * 
 * UTSA CS 3443 - Group Project
 * Spring 2020
 */

public class Syllabus {
	int classCode;
	public double finalWeight;
	public double examWeight;
	public double quizWeight;
	public double HWWeight;
	public double labWeight;
	public double otherWeight;
	
	/**
	 * Syllabus constructor
	 * 
	 * @param cl : Int
	 * @param finalW : Double
	 * @param examW : Double
	 * @param quizW : Double
	 * @param HWW : Double
	 * @param labW : Double
	 * @param otherW : Double
	 */
	public Syllabus(int cl, double finalW, double examW, double quizW, double HWW, double labW, double otherW){
		this.classCode = cl;
		this.finalWeight = finalW;
		this.examWeight = examW;
		this.quizWeight = quizW;
		this.HWWeight = HWW;
		this.labWeight = labW;
		this.otherWeight = otherW;
	}
	
	/**
	 * returns weight for certain assignment type
	 * 
	 * @param type : Char
	 * @return : Double
	 */
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
	
	/**
	 * formats syllabus information for csv file
	 * @return
	 */
	public String toCSV() {
		String str = classCode + "," + finalWeight + "," + examWeight + "," + quizWeight + "," + HWWeight + "," + labWeight + "," + otherWeight + "\n";
		return str;
	}
}
