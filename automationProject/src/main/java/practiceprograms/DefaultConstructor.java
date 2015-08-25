package practiceprograms;

public class DefaultConstructor {
	//Constructor in java is a special type of method that is used to intialize the object
	int i;
	String str;
	DefaultConstructor(){
		System.out.println("the values are:" +i +" "+str);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultConstructor obj = new DefaultConstructor();
	}

}
