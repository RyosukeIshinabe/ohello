package poker;
import java.util.Scanner;

public class Field {

	private int[] sorc;	// Changeかstayを入れておく変数
	private static Scanner scan = new Scanner(System.in);	// 入力用クラス
	private final int FIELD = 5;	// フィールドの最大値
	private final int CHANGE = 1;	// カードを変更する
	private final int STAY = 0;	// カードを残す
	private final int ERROR = -99;	// エラー用の数値

	public Field() {
		this.sorc = new int[FIELD];
		for ( int i = 0; i < FIELD; i++ ) {
			this.sorc[i] = STAY;
		}
	}

	// sorc配列をリセットする
	public void resetSorC() {
		for ( int i = 0; i < FIELD; i++ ) {
			this.sorc[i] = STAY;
		}
	}

	// sorc配列のキーを入力してsetする
	public void setSorC(int key, int num) {
		this.sorc[key] = num;
	}

	// sorc配列のキーを入力して0か1かを返す
	public int getSorC(int num) {
		return this.sorc[num];
	}

	// 全てのカードのSTAYかCHANGEかを表示する
	public void displayAllSorC() {
		for ( int i = 0; i < FIELD; i++ ) {
			if ( this.sorc[i] == STAY ) {
				System.out.println("[" + (i+1) + "] STAY");
			} else if ( this.sorc[i] == CHANGE ) {
				System.out.println("[" + (i+1) + "] CHANGE");
			}
		}
	}

	// 指定のカードのSTAYかCHANGEかを表示する
	public void displaySorC(int num) {
		if ( this.sorc[num] == STAY ) {
			System.out.print("STAY");
		} else {
			System.out.print("CHANGE");
		}
	}

	// CHANGEカードの番号を配列に格納して返す
	public int[] returnChangeCardNum() {
		int count = 0;
		// CHANGEカードの個数をcount変数に入れる
		for ( int i = 0; i < FIELD; i++ ) {
			if ( this.sorc[i] == CHANGE ) {
				count++;
			}
		}
		// CHANGEカードの個数分配列を用意
		int[] changeList = new int[count];
		int k = 0;

		// チェンジリストにCHANGEカードの番号を格納する
		for ( int j = 0; j < FIELD; j++ ) {
			if ( this.sorc[j] == CHANGE ) {
				changeList[k] = j;
				k++;
			}
		}
		return changeList;
	}

	// STAYかCHANGEかをスイッチする
	public void switchSorC(int num) {
		if ( this.sorc[num-1] == STAY ) {
			this.sorc[num-1] = CHANGE;
		} else if ( this.sorc[num-1] == CHANGE ) {
			this.sorc[num-1] = STAY;
		}
	}

	// STAYorCHANGEの入力を判定する
	public boolean inputSorC(String inputStr) {

		// 入力された値が0〜5の数字だったらint型で返す（問題があればERROE定数を返す）
		int inputInt = checkInputInt(inputStr, 0, 6);

		// 条件を満たした数字が入力されるまで繰り返す
		while ( inputInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			String newStr = "";
			newStr = scan.next();
			inputInt = checkInputInt(newStr, 0, 5);
		}

		// 0が入力された場合はtrueを返す
		if ( inputInt == 0 ) {
			return true;

		// 6が入力された場合は何もせずfalseを返す
		} else if ( inputInt == 6 ) {
			return false;

		// 1〜5が入力された場合はSTAYorCHANGEをスイッチする
		} else {
			switchSorC(inputInt);
			return false;
		}
	}

	// 入力された値がmin〜maxの数字だったらint型で返す（1桁用）
	public int checkInputInt(String inputStr, int min, int max) {

		// 入力された文字が数字の1桁だった場合
		if ( inputStr.matches("^[0-9]{1}$") ) {

			// int型に変換
			int inputInt = Integer.parseInt(inputStr);

			// min〜maxの値が入力されたならその値を返す
			if ( inputInt >= min && inputInt <= max ) {
				return inputInt;

			} else {
				return ERROR;
			}

		} else {
			return ERROR;
		}
	}

	// 定型文表示
	public void displayFirstMsg() {
		System.out.println("GAME START");
		System.out.println("[]内の番号を入力してEnterを押すと、STAYとCHANGEを変更できます。");
		System.out.println("（一度の入力で1枚ずつ指定してください）");
		System.out.println("確定するには、0を入力してEnterを押してください。");
		System.out.println("最大2回シャッフルできます。");
		System.out.println("ジョーカーは入っていません。");
		System.out.println("=====================");
	}
}
