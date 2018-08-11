package compareTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// 入力された指示の通りに並べかえを行うプログラムです。

public class Main {

	public static Scanner scan = new Scanner(System.in);	// 入力用クラス
	private static final int ERROR = -99;

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

		// 入力を代入（何が入力されてもエラーにならないように一旦String型に格納）
		System.out.println("ソート方法を入力してください");
		System.out.println("社員番号 [0]昇順 [1]降順");
		System.out.println("名前　　 [2]昇順 [3]降順");
		System.out.println("年齢　　 [4]昇順 [5]降順");
		String inputStr = scan.next();
		int inputInt = checkInput(inputStr);	// 正規チェックしてint型に変換

		// 条件を満たした数字が入力されるまで繰り返す
		while ( inputInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			inputStr = scan.next();
			inputInt = checkInput(inputStr);
		}

		// employeeComparatorで定義された通りにソート（引数でソート方法を渡す）
		Collections.sort(employees, new Employee.EmployeeComparator(inputInt) );

		// 表示
		for (Employee value : employees) {
			System.out.println(value.toString());
		}
	}

	// 入力された値が正しいかチェック
    public static int checkInput(String inputStr) {
    	if ( inputStr.matches("^[0-9]{1}$") ) {	// 入力された文字が数字の1桁だった場合
			int inputInt = Integer.parseInt(inputStr);	// int型に変換
			if ( 0 <= inputInt && inputInt <= 5 ) {	// 入力された文字が0〜5の間だった場合
				return inputInt;
			}
    	}
    	return ERROR;
    }
}
