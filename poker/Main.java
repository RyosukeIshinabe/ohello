package poker;

public class Main {

	// メンバーの方々
	public static Poker myPoker = new Poker();
	public static Coin myCoin = new Coin();	// 初期コイン
	public static int rank = 0;	// ランク（役の価値）

	public static void main(String[] args) {
		boolean slectCoutinue = true;

		// 続けないを選ぶまでこの中を繰り返す
		while ( slectCoutinue == true ) {
			gameOfOneTimeBet();	// 1回のBETで続けるゲーム
			if ( myCoin.getCoin() == 0 ) {	// Coinが0になったら続けるか聞く前に抜ける
				break;
			}
			myCoin.getCoinStr();	// コインを表示
			slectCoutinue = myCoin.continueGame();	// 続けるか聞いて返事をslectCoutinue変数に入れる
		}

		myCoin.getCoinStr();	// コインを表示
		System.out.println("またのお越しをお待ちしております。");
   }

	// 1回のBETで続けるゲーム
	public static void gameOfOneTimeBet() {
		myCoin.initInputBet();	// 最初のBET
		boolean slectCoutinue = true;

		// 続けないを選ぶまでこの中を繰り返す
		while ( slectCoutinue == true ) {
			rank = myPoker.gameStart();	// ポーカーを行ってランクを取得
			myCoin.afterBet(rank);	// ランクをBETに反映
			rank = 0;	// ランクをリセット
			if ( myCoin.getBet() == 0 ) {	// BETが0になったら続けるか聞く前に抜ける
				break;
			}
			slectCoutinue = myCoin.continueGameAsSameBet();	// 続けるか聞いて返事をslectCoutinue変数に入れる
		}

		myCoin.change();	// BETを所持コインに換金
		System.out.println("BETを所持コインに換金しました。");
	}
}
