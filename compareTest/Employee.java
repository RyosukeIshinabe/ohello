package compareTest;
import java.util.Comparator;

public class Employee {
	private int number;
	private String name;
	private int age;

	public Employee(int number, String name, int age) {
		this.number = number;
		this.name = name;
		this.age = age;
	}

	public int getNumber() {
		return this.number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	// Comparator用の内部クラス
	public static class employeeComparator implements Comparator<Employee> {

		@Override	// 従業員ナンバーを昇順で並べ替え
		public int compare(Employee employee1, Employee employee2) {
			return employee1.number - employee2.number;
		}
	}

	public String toString() {
		return "number:" + this.number + ", "
			 + "name:"	 + this.name + ", "
			 + "age:"	 + this.age;
	}
}
