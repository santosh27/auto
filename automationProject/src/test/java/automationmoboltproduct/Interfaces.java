package automationmoboltproduct;
interface printable{
	void print();
}

class Interfaces implements printable{

	public void print(){
		System.out.println("Interfaces");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Interfaces obj = new Interfaces();
		obj.print();

	}

}
