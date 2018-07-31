package poker;
import java.util.Scanner;

public class Poker {

	// 使うメンバーの方々
	public static Scanner scan = new Scanner(System.in);	// 入力用クラス
	public static Card myDeck = new Card();	// デッキ（カード一式）を生成
	public static Hand myHand = new Hand();	// 手を生成
	public static Field myField = new Field();	// フィールドを生成

	// 定数の方々
	public static final int FIELD = 5;	// フィールド上のカードの枚数
	public static final int MAXCHALLENGE = 2;	// チャレンジ回数上限

	// ポーカーを動かしてランク（価値）を返すクラス
	public int gameStart() {
		int challengeCount = 0;	// チャレンジ回数カウント用
		int rank = 0;	// ランク（役のポイント）
		myDeck.initCard();	// カードを初期化

		myField.displayFirstMsg();	// ルールを表示
		myHand.displayFirstMsg();	// 役一覧を表示
		myDeck.checkCardsQuantityOnField();	// 未使用の5枚を引く
		String inputStr = "6";	// 回避用の数字

		// チャレンジ回数が上限になるまで繰り返す
		while ( challengeCount < MAXCHALLENGE ) {
			System.out.println((challengeCount+1) + "/" + MAXCHALLENGE + "回目のシャッフルです。");
			// 0が入力されるまで繰り返す
			while ( myField.inputSorC(inputStr) == false ) {
				System.out.println("=========");
				displayCardAndSorC();	// カードとSTAYorCHANGEを表示
				inputStr = scan.next();	// ユーザーの入力を代入
			}
			System.out.println("CHANGEのカードをシャッフルしました。");
			challengeCount++;; // チャレンジ回数をインクリメント
			myDeck.changeCard(myField.returnChangeCardNum());	// CHANGEのカードを墓場に移動
			myDeck.checkCardsQuantityOnField();	// 未使用の5枚を引く
			inputStr = "6";	// ユーザーの入力用変数に回避用の数字をセット
			myField.resetSorC();	// STAYorCHANGEをリセット
		}
		myDeck.displayCard();	// トランプを表示
		System.out.println("=========");
		rank = myHand.checkAllHand(myDeck.getSameMarkCard(),myDeck.getSameNumCard());
		System.out.println(rank + "点でした！");
		return rank;
	}

	// Cardクラスのカード情報とFieldクラスのSTAYorCHANGEを一列で表示する
	public static void displayCardAndSorC() {
		for ( int i = 0; i < FIELD; i++ ) {
			myDeck.displayCardOfOne(i);
			System.out.print("：");
			myField.displaySorC(i);
			System.out.println("");
		}
		System.out.println("[0] 上記で確定する");
	}
}
