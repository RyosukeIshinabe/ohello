package bigOrSmall;
import java.util.Scanner;

public class Main {
	public static int count[] = new int[3];	// [0]出題回数、[1]win回数、[2]lose回数
	public static Trump myTrump = new Trump();	// デッキを生成
	public static Coin myCoin = new Coin();	// 自分のコイン
	public static Scanner scan = new Scanner(System.in);	// 入力用クラス
	private static final int ERROR = -99;	// エラー用

	public static void main(String[] args) {
		counter("reset");	// カウンターをリセット
		displayFixedMsg("first");	// 開始用メッセージ表示
		myCoin.betCoin();	// コインをbetさせる
		Card preCard = myTrump.takeCard();	// 新しいカードを引いてpreCardとして保存

		// カードを出し切るまでこの中を繰り返す
		while ( myTrump.remaining() != 0 ) {
			counter("all","display");	// 出題数をインクリメントして表示
			System.out.println("現在のカード：" + preCard.toString());	// 現在のカードを表示
			Card nextCard = myTrump.takeCard();	// 次のカードを保存

			// BigorSmallの選択==preCardとnextCardの比較がtrueだった場合（つまりwin）
			if ( selectBorS() == preCard.compareCard(nextCard) ) {
				System.out.println(nextCard.toString() + "でした！");	// 次のカードを表示
				treatWin();	// win用の処理（カウンターとBETを倍に）
				if ( myTrump.remaining() == 0 ) {	// カードが全部出切ったならwhile文を抜ける
					break;
				}
				if ( challengeNextDoubleUp() ) {	// さらに挑戦する場合は
					preCard = nextCard;	// 次のカードを前のカードとして移行
					continue;	// while文の先頭へ
				}
				myCoin.change();	// BETを所持コインに換金

			// loseだった場合
			} else {
				System.out.println(nextCard.toString() + "でした！");	// 次のカードを表示
				treatLose();	// lose用の処理（カウンターとBETを0に）
			}

			// while文を抜ける条件
			if ( myTrump.remaining() == 0 ) {	// カードが全部出切ったとき
				break;
			}
			if ( myCoin.getCoinSum() < 1 ) {	// コインが0になったとき
				break;
			}
			if ( !continueGame() ) {	// 続けるかどうか聞いて、続けないを選択したとき
				break;
			}

			// ゲームを続ける場合の各種初期化
			counter("reset");	// カウンターをリセット
			myTrump.init();	// カードを初期化
			myCoin.betCoin();	// コインをbetさせる
			preCard = myTrump.takeCard();	// 新しいカードを引いてpreCardとして保存
		}

		// ゲームを終了した後の処理
		counter("reset");	// カウンターをリセット
		myCoin.change();	// BETを所持コインに換金
		displayFixedMsg("last");	// 終了用メッセージ表示
	}

	// 0か1かの正規表現
	public static int checkInputSlct(String inputStr) {
		if ( inputStr.matches("^[0-9]{1}$") ) {	// 入力された文字が数字の1桁だった場合
			int inputNum = Integer.parseInt(inputStr);	// int型に変換

			if ( inputNum == 0 || inputNum == 1 ) {	// 0か1ならその値を返す
				return inputNum;

			// 条件に合わない場合ERROR定数を返す
			} else {
				return ERROR;
			}
		} else {
			return ERROR;
		}
	}

	// 出題数、勝利回数、負け回数をインクリメントして表示
	public static void counter(String kind, String display) {
		if ( kind == "reset" ) {
			count[0] = 0;
			count[1] = 0;
			count[2] = 0;
		} else if ( kind == "all" ){
			count[0]++;
		} else if ( kind == "win" ){
			count[1]++;
		} else if ( kind == "lose" ){
			count[2]++;
		}

		if ( display == "display" ) {
			if ( kind == "all" ) {
				System.out.println(count[0] + "回目のチャレンジです。");
			} else if ( kind == "win" ) {
				System.out.println("連続win回数；" + count[1] + "回");
			} else if ( kind == "lose" ) {
				System.out.println("連続lose回数；" + count[2] + "回");
			}
		}
	}

	// 出題数、勝利回数、負け回数をインクリメント
	public static void counter(String kind) {
		if ( kind == "reset" ) {
			count[0] = 0;
			count[1] = 0;
			count[2] = 0;
		} else if ( kind == "all" ){
			count[0]++;
		} else if ( kind == "win" ){
			count[1]++;
		} else if ( kind == "lose" ){
			count[2]++;
		}
	}

	// システム上必要なメッセージ表示のメソッド
	public static void displayFixedMsg(String kind) {

		// 初期用メッセージを表示
		if ( kind == "first" ) {
			System.out.println("◇◆◇◆START◇◆◇◆");
			System.out.println("次のカードの数字が大きいか小さいかを当ててください。");
			System.out.println("数字が同じ場合はマークで比較します。");
			System.out.println("マークの強さ：スペード > ダイヤ > ハート > クラブ");
			System.out.println("ジョーカーは含まれていません。");
			System.out.println("52枚全て出切ったら終了です。");
			System.out.println("====================");

		// 終了時の共通メッセージ
		} else if ( kind == "last" ) {
			System.out.println("====================");
			System.out.println("◇◆◇◆END◇◆◇◆");
			counter("win","display");
			counter("lose","display");
			System.out.println("【所持コイン】" + myCoin.getCoinSum() + "（[10]" + myCoin.getCoin10() + "枚, [1]" + myCoin.getCoin1() + "枚）");
			System.out.println("またのお越しをお待ちしております。");
		}
	}

	// Big or Smallを選択するメソッド
	public static boolean selectBorS() {
		System.out.println("次のカードは…？　0:Big  1:Small");
		String slctStr = scan.next();	// 入力を代入（何が入力されてもエラーにならないように一旦String型に格納）
		int slctInt = checkInputSlct(slctStr);	// 正規チェックしてint型に変換

		// 条件を満たした数字が入力されるまでこの中を繰り返す
		while ( slctInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			slctStr = scan.next();
			slctInt = checkInputSlct(slctStr);
		}

		if ( slctInt == 0) {
			return true;
		} else {
			return false;
		}
	}

	// ゲームを続けるかどうかを選択するメソッド（true:続ける、false:終了する）
	public static boolean continueGame() {
		System.out.println("ゲームを続けますか？　0:はい  1:いいえ");
		String continueStr = scan.next();	// 入力を代入（何が入力されてもエラーにならないように一旦String型に格納）
		int continueInt = checkInputSlct(continueStr);	// 正規チェックしてint型に変換

		// 条件を満たした数字が入力されるまで繰り返す
		while ( continueInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			continueStr = scan.next();
			continueInt = checkInputSlct(continueStr);
		}

		if ( continueInt == 0 ) {
			return true;
		} else {
			return false;
		}
	}

	// 手持ちのBETを利用してさらに続けるかどうかを選択するメソッド（true:続ける、false:終了する）
	public static boolean challengeNextDoubleUp() {
		System.out.println("現在のBETを利用してさらに挑戦しますか？　0:はい  1:いいえ");
		String challengeStr = scan.next();	// 入力を代入（何が入力されてもエラーにならないように一旦String型に格納）
		int challengeInt = checkInputSlct(challengeStr);	// 正規チェックしてint型に変換

		// 条件を満たした数字が入力されるまで繰り返す
		while ( challengeInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			challengeStr = scan.next();
			challengeInt = checkInputSlct(challengeStr);
		}

		if ( challengeInt == 0 ) {
			return true;
		} else {
			return false;
		}
	}

    // winの処理
    public static void treatWin() {
    	System.out.println("Win!");
		counter("win");
		myCoin.multiplicate(2);	// betを2倍に
		System.out.println("現在のBETコイン： " + myCoin.getBet());
    }

	// loseの処理
    public static void treatLose() {
    	System.out.println("lose...");
		counter("lose");
		myCoin.multiplicate(0);	// betを0に
		System.out.println("現在のBETコイン： " + myCoin.getBet());
    }
}


