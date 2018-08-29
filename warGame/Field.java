package warGame;
import java.util.Scanner;

public class Field {
	private final int ALLCARD = 26;
	private Deck onHoldDeck = new Deck();	// 保留中のカードを格納するデッキ
	private Deck defaultDeck = new Deck();	// プレイヤーに振り分ける前のデッキ
	private int round;	// 第*回戦
	private Scanner scan = new Scanner(System.in);	// 入力用クラス

	public Field() {
		this.onHoldDeck.makeDeck(ALLCARD);	// 保留中のカードを格納するデッキ
		this.defaultDeck.init();	// プレイヤーに振り分ける前のデッキ
		round = 1;	// 第*回戦
	}

	/*
	 *	onHoldDeck用
	 */

	// カードを1枚受け取ってonHoldDeckにセット
	public void setCardToOnHoldDeck(Card card) {
		onHoldDeck.insertCard(card);
	}

	// onHoldDeckのカード枚数をreturn
	public int getLengthOfOnHoldDeck() {
		return this.onHoldDeck.getDeckLength();
	}

	// onHoldDeckのカード枚数を文字で表示
	public void displayLengthOfOnHoldDeck() {
		System.out.println("（保留中の札:" + this.onHoldDeck.getDeckLength() + "枚）");
	}

	// onHoldDeck内の指定の番号のカードを取得（元のデッキからは消す）
	public Card getCardFromOnHoldDeckAndErase(int index) {
		return this.onHoldDeck.getCardFromDeckAndErase(index);
	}

	// onHoldDeck内の指定の番号のカードを文字で表現する
	public void displayCardFromOnHoldDeck(int index) {
		onHoldDeck.displayCardFromDeck(index);
	}

	/*
	 *	defaultDeck用
	 */

	// defaultDeckのカード枚数をreturn
	public int getLengthOfDefaultDeck() {
		return this.defaultDeck.getDeckLength();
	}

	// defaultDeckからnullではないカードを1枚受け取ってカードを引いて、元のデッキから消す
	public Card takeCardFromDefaultDeck() {
		Card newCard = defaultDeck.takeCardFromDeck();
		return newCard;
	}

	/*
	 *	その他必要な操作
	 */

	// 札を切るかどうかきく
	public boolean selectContinue() {
		System.out.println("札を切りますか？(d:札を切る, q:中断)");
		String selectContinue = scan.next();	// 入力を代入

		// 条件を満たした値が入力されるまで繰り返す
		while ( !selectContinue.equals("d") && !selectContinue.equals("q") ) {
			System.out.println("入力が正しくありません");
			selectContinue = scan.next();
		}

		if ( selectContinue.equals("d") ) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 *	ラウンド用
	 */

	// ラウンド数を取得（表示およびインクリメントはしない）
	public int getRound() {
		return round;
	}

	// ラウンド数をセット
	public void setRound(int round) {
		this.round = round;
	}

	// ラウンド数を表示してインクリメント
	public void displayRound() {
		System.out.println("### 第" + round + "回戦 ###");
		round++;
	}

	// 保留デッキを破棄
	public void setNulltoOnHoldDeck() {
		this.onHoldDeck.setNull();
	}

	// デフォルトデッキを破棄
	public void setNulltoDefaultDeck() {
		this.defaultDeck.setNull();
	}


}
