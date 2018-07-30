package poker;

import java.util.Scanner;

public class Coin {

	private int coin;	// コイン
	private int bet;	// bet
	private final int ERROR = -99;	// エラー用の数値
	private static Scanner scan = new Scanner(System.in);	// 入力用クラス

	// 初期化
	public Coin() {
		this.coin = 20;
		this.bet = 0;
	}

	// コインの枚数を返す
	public int getCoin() {
		return this.coin;
	}

	// BETを返す
	public int getBet() {
		return this.bet;
	}

	// コインの枚数を文字付きで表示
	public void getCoinStr() {
		System.out.println("所持コイン：" + this.coin);
	}

	// 所持コインをセットする
	public void setCoin(int newCoin) {
		this.coin = newCoin;
	}

	// betしてその分を所持コインから引く（最初用）
	//（betの正規判定はこのメソッドの前に行っておくこと）
	public void initFixBet(int bet) {
		this.bet = bet;
		this.coin = this.coin - bet;
	}

	// betしてその分を所持コインから引く（続けてゲーム用）
	public void fixBet(int bet) {
		this.bet = bet;
	}

	// betを所持コインに換金
	public void change() {
		this.coin = this.coin + this.bet;
		this.bet = 0;
	}

	// ゲーム終了後、betとrankをかける
	public void win(int rank) {
		this.bet = this.bet * rank;
	}

	// betの入力を促して正規判定する（最初用）
	public void initInputBet() {
		System.out.println("所持コイン：" + this.coin);
		System.out.println("いくらBETしますか？（最大99）");
		String inputStr = scan.next();	// ユーザーの入力を代入

		// 入力された値が1〜所持coinの数字だったらint型で返す（問題があればERROR定数を返す）
		int inputInt = checkInputInt(inputStr, 1, this.coin);

		// 正しい入力があるまで以下を繰り返す
		while ( inputInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			String newStr = scan.next();
			inputInt = checkInputInt(newStr, 1, this.coin);
		}

		// 正規チェック済みのbetを確定
		initFixBet(inputInt);
	}

	// betの入力を促して正規判定する（続けた後用）
	public void InputBet() {
		System.out.println("現在のBET：" + this.bet + "を使用して続けます。");
		fixBet(this.bet);
	}

	// 入力された値がmin〜maxの数字だったらint型で返す
	public int checkInputInt(String inputStr, int min, int max) {

		// 入力された文字が数字の1桁or2桁だった場合
		if ( inputStr.matches("^[0-9]{1}$") || inputStr.matches("^[0-9]{2}$") ) {
			// int型に変換
			int inputInt = Integer.parseInt(inputStr);

			// min〜maxの値が入力されたならその値を返す
			if ( inputInt >= min && inputInt <= max ) {
				return inputInt;

			// それ以外はERROR定数を返す
			} else {
				return ERROR;
			}
		} else {
			return ERROR;
		}
	}

	// rankが確定した後の処理
	public void afterBet(int rank) {
		win(rank);
		System.out.println("BETが" + this.bet + "になりました。");
	}

	// 続けるかどうかの入力を促して正規判定する（同じBETで続ける場合）
	public boolean continueGameAsSameBet() {
		System.out.println("このBETを利用してさらにゲームを続けますか？");
		System.out.println("[0]はい　[1]いいえ");
		String inputStr = scan.next();	// ユーザーの入力を代入

		// 入力された値が0〜1の数字だったらint型で返す（問題があればERROR定数を返す）
		int inputInt = checkInputInt(inputStr, 0, 1);

		// 正しい入力があるまで以下を繰り返す
		while ( inputInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			String newStr = scan.next();
			inputInt = checkInputInt(newStr, 0, 1);
		}

		if ( inputInt == 0 ) {
			return true;
		} else {
			return false;
		}
	}

	// 続けるかどうかの入力を促して正規判定する（BETを破棄・確定した後に続ける場合）
	public boolean continueGame() {
		System.out.println("ゲームを続けますか？");
		System.out.println("[0]はい　[1]いいえ");
		String inputStr = scan.next();	// ユーザーの入力を代入

		// 入力された値が0〜1の数字だったらint型で返す（問題があればERROR定数を返す）
		int inputInt = checkInputInt(inputStr, 0, 1);

		// 正しい入力があるまで以下を繰り返す
		while ( inputInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			String newStr = scan.next();
			inputInt = checkInputInt(newStr, 0, 1);
		}

		if ( inputInt == 0 ) {
			return true;
		} else {
			return false;
		}
	}


}
