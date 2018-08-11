package compareTest;
import java.util.Comparator;

public class Employee implements Comparable<Employee> {
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
	public static class EmployeeComparator implements Comparator<Employee> {
		private int sortWay;	// ソート方法用

		// コンストラクタ（引数でソート方法を受け取リ、sortWay変数に格納）
		public EmployeeComparator(int inputInt) {
			this.sortWay = inputInt;
		}

		@Override
		public int compare(Employee employee1, Employee employee2) {
			// メンバ変数sortWayに何が入っているかによってソート方法を指定
			switch (this.sortWay) {
				case 0:
					return employee1.number - employee2.number;
				case 1:
					return employee2.number - employee1.number;
				// 文字列には演算子が使えないため、Stringクラスに予め用意されているcompareToクラスを使う
				case 2:
					return employee1.compareTo(employee2);
				case 3:
					return employee2.compareTo(employee1);
				case 4:
					return employee1.age - employee2.age;
				case 5:
					return employee2.age - employee1.age;
				default:
					return 0;
			}
		}
	}

	@Override	// 文字列用の大小比較
	public int compareTo(Employee employee) {
		return name.compareTo(employee.name);
	}

	public String toString() {
		return "number:" + this.number + ", "
			 + "name:"	 + this.name + ", "
			 + "age:"	 + this.age;
	}
}
