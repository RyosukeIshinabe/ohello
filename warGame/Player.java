package warGame;
import java.util.ArrayList;

public class Player {

	private int id;	// プレイヤー識別子（0:自分、1:CPU）
	private Deck winDeck = new Deck();	// 勝利して獲得したカードを格納するデッキ
	private Deck playerDeck = new Deck();	// 各プレイヤーに振り分けるカードを格納するデッキ
	private Deck inUseDeck = new Deck();	// 使用中のカードを格納するデッキ
	private static final int PLAYERCARD = 13;	// 各プレイヤーが持つカードの最大値
	private static final int ALLCARD = 26;	// 全カード数

	// デッキの箱を用意（ゲーム開始時に使用）
	public Player(int id) {
		this.playerDeck.makeDeck(PLAYERCARD);
		this.winDeck.makeDeck(ALLCARD);
		this.inUseDeck.makeDeck(1);
		this.id = id;
	}

	// 自身が持つ全てのデッキの情報を表示
	public void displayAllDeckInfo() {
		if ( this.id == 1 ) {
			System.out.println("【CPU】");
		} else if ( this.id == 0 ) {
			System.out.println("【あなた】");
		}
		displayLengthOfPlayerDeck();	// 自身が持っている未使用カードを表示
		displayLengthOfWinDeck();	// 自身が奪った勝利カードを表示
	}

	/*
	 *	playerDeck用
	 */

	// カードを1枚受け取ってplayerDeckにセット
	public void setCardToPlayerDeck(Card card) {
		this.playerDeck.insertCard(card);
	}

	// playerDeckのカード枚数をreturn
	public int getLengthOfPlayerDeck() {
		return this.playerDeck.getDeckLength();
	}

	// playerDeckのカード枚数を文字で表示
	public void displayLengthOfPlayerDeck() {
		System.out.println("　持っている札:" + this.playerDeck.getDeckLength() + "枚");
	}

	/*
	 *	winDeck用
	 */

	// winDeckを取得
	public Deck getWinDeck() {
		return this.winDeck;
	}

	// カードを1枚受け取ってwinDeckにセット
	public void setCardToWinDeck(Card card) {
		winDeck.insertCard(card);
	}

	// winDeckのカード枚数をreturn
	public int getLengthOfWinDeck() {
		return this.winDeck.getDeckLength();
	}

	// winDeckのカード枚数を文字で表示
	public void displayLengthOfWinDeck() {
		System.out.println("　獲得した札:" + this.winDeck.getDeckLength() + "枚");
	}

	/*
	 *	inUseDeck用
	 */

	// カードを1枚受け取ってinUseDeckにセット
	public void setCardToInUseDeck(Card card) {
		inUseDeck.insertCard(card);
	}

	// inUseDeckのカード枚数をreturn（必ず1枚のはずだけど一応）
	public int getLengthOfInUseDeck() {
		return this.inUseDeck.getDeckLength();
	}

	// inUseDeckのカードを文字で表示
	public void displayInUseDeck() {
		if ( this.id == 1 ) {
			System.out.print("【CPU】が切った札：");
		} else if ( this.id == 0 ) {
			System.out.print("【あなた】が切った札：");
		}
		inUseDeck.displayDeck();	// 使用中デッキの中のカードを表示
	}

	// inUseDeck内の先頭のカードを取得（消去しない）
	public Card getInUseDeck() {
		return this.inUseDeck.getFirstCardFromDeck();
	}

	// inUseDeck内の先頭のカードを取得（消去する）
	public Card getInUseDeckAndErase() {
		return this.inUseDeck.getFirstCardFromDeckAndErase();
	}

	/*
	 *	その他必要な操作
	 */

	// playerDeckからランダムなカードを引いて使用中にし、表示する
	public void takeCardAndDisplay() {
		Card newCard;
		newCard = playerDeck.takeCardFromDeck();	// 未使用のカードを引いて、nullにする
		inUseDeck.insertCard(newCard);	// 引いたカードを使用中のデッキへ移動
		displayInUseDeck();	// 使用中デッキの中のカードを表示
	}

	// 自身と、引数で指定された他プレイヤーのwinDeckの枚数を比較する
	public int compareCardLengthOfWinDeck(Player otherPlayer) {
		return this.winDeck.compareCardLength(otherPlayer.getWinDeck());
	}

	/*
	 *	セーブ用の操作
	 */

	// 自身のplayerDeckとwinDeckをString型のListにしてreturn
	public ArrayList<String> convertFormatOfCsv() {
		ArrayList<String> playerDeckData = new ArrayList<>();
		ArrayList<String> winDeckData = new ArrayList<>();

		// それぞれのDeckをSring型のListに変換
		playerDeckData = playerDeck.convertFormatOfCsv(this.id, 0);
		winDeckData = winDeck.convertFormatOfCsv(this.id, 1);
		playerDeckData.addAll(winDeckData);	// リスト同士を結合

		return playerDeckData;	// 結合後のリストをreturn
	}

	// 分解済みのデータ（1行）を受け取って自身のデッキとして保存
	public void roadDeck(int deckType, int mark, int number) {
		Card roadCard = new Card(mark, number);	// カードクラスをロード
		if ( deckType == 0 ) {	// typeが0の場合
			this.playerDeck.insertCard(roadCard);	// playerDeckとしてロード
		} else if ( deckType == 1 ) {	// typeが1の場合
			this.winDeck.insertCard(roadCard);	// winDeckとしてロード
		}
	}

	// 全てのデッキを破棄
	public void setNulltoAllDeck() {
		this.winDeck.setNull();
		this.playerDeck.setNull();
		this.inUseDeck.setNull();
	}

}
