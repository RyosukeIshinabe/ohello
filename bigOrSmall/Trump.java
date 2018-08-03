package bigOrSmall;
import java.util.Random;

public class Trump {
	private Card[] cardDeck;
	private static final int MAXNUM = 13;	// 数字の種類
	private static final int MAXMARK = 4;	// マークの種類
	private static final int DECKSIZE = MAXNUM * MAXMARK;

	public Trump() {
		init();
	}

	// 初期化
	public void init() {
		cardDeck = new Card[DECKSIZE];
		int index = 0;	// 配列のkey用
		for ( int i = 0; i < MAXMARK; i++ ) {
			for ( int j = 1; j <= MAXNUM; j++ ) {
				cardDeck[index] = new Card(i,j);
				index++;
			}
		}
	}

	// ゲッターとセッター
	public Card[] getCardDeck() {
		return cardDeck;
	}

	public void setCardDeck(Card[] cardDeck) {
		this.cardDeck = cardDeck;
	}

	// 新しいカードを引く
	public Card takeCard() {
		if ( remaining() == 0 ) {	// 残り枚数が0ならnullを返す
			return null;
		}
		Random rand = new Random();	// ランダムな数字を作成するためのクラス
		Card card;
		int index;
		do {
			index = rand.nextInt(DECKSIZE);	// ランダムな数字を作成して
			card = cardDeck[index];	// 新しいカードを作成
		} while ( card == null );	// そのカードがnullだった場合do{}内を繰り返す
		cardDeck[index] = null;	// 引いたカードをnullにする
		return card;
	}

	// 残りのカードの枚数を返す
	public int remaining() {
		int count = 0;
		for ( int i = 0; i < cardDeck.length; i++ ) {
			if ( cardDeck[i] != null ) {
				count++;
			}
		}
		return count;
	}



}
