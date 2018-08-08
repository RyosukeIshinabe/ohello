package compareTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 入力された指示の通りに並べかえを行うプログラムです。

public class Main {

	public static void main(String args[]) {

		// Sort_employeeクラスの従業員リスト
		Employee bob = new Employee(1, "Bob", 25);
		Employee eve = new Employee(2, "Eve", 35);
		Employee alice = new Employee(3, "Alice", 30);
		Employee ken = new Employee(6, "Ken", 46);
		Employee mike = new Employee(4, "Mike", 42);
		Employee sophie = new Employee(7, "Sophie", 18);
		Employee angelo = new Employee(5, "Angelo", 22);

		// List「employees」に格納
		List<Employee> employees = new ArrayList<>();
		employees.add(alice);
		employees.add(bob);
		employees.add(ken);
		employees.add(mike);
		employees.add(eve);
		employees.add(sophie);
		employees.add(angelo);

		// employeeComparatorで定義された通りにソート
		Collections.sort(employees, new Employee.employeeComparator() );

		// 表示
		for (Employee value : employees) {
			System.out.println(value.toString());
		}
	}
}
