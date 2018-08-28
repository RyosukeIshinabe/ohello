package warGame;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
	private Card[] deck;	// カードを入れるための箱
	private static final int MAXNUM = 13;	// 数字の種類
	private static final int MAXMARK = 2;	// 使うマークの種類
	private static final int ALLCARD = MAXNUM * MAXMARK;

	// コンストラクタ
	public Deck() {
	}

	// スペードとダイヤの1〜13のカードを作成してdeckに格納
	public void init() {
		deck = new Card[ALLCARD];
		int index = 0;	// 配列のkey用
		for ( int i = 0; i < MAXMARK; i++ ) {
			for ( int j = 1; j <= MAXNUM; j++ ) {
				deck[index] = new Card(i,j);
				index++;
			}
		}
	}

	// 指定された枚数のdeckを用意
	public void makeDeck(int quantity) {
		deck = new Card[quantity];
	}

	// デッキを取得
	public Card[] getDeck() {
		return deck;
	}

	// デッキをset
	public void setDeck(Card[] deck) {
		this.deck = deck;
	}

	// 自身のデッキから指定された番号のカードを取得
	// 参照するだけ。取得元のデッキからカードを消したい場合は...AndErase()を使うこと
	public Card getCardFromDeck(int index) {
		return this.deck[index];
	}

	// 自身のデッキから指定された番号のカードを取得して元のデッキから消す
	public Card getCardFromDeckAndErase(int index) {
		Card refuge = deck[index];	// 引いたカードを一旦避難
		deck[index] = null;	// 引いたカードの場所をnullにする
		return refuge;	// 避難していたカードをreturn
	}

	// 自身のデッキに格納された（null以外の）カードの枚数を取得
	public int getDeckLength() {
		int count = 0;
		for ( int i = 0; i < this.deck.length; i++ ) {
			if ( deck[i] != null ) {
				count++;
			}
		}
		return count;
	}

	// 自身のデッキからランダムな未使用（nullではない）カードを引いて、元のデッキから消す
	public Card takeCardFromDeck() {
		if ( this.getDeckLength() == 0 ) {	// 残り枚数が0ならnullを返す
			return null;
		}

		Random rand = new Random();	// ランダムな数字を作成するためのクラス
		int index;
		int count = 0;

		do {
			index = rand.nextInt(this.deck.length);	// ランダムな数字を作成して
			count++;
		} while ( deck[index] == null && count <= 100 );	// そのカードがnullだった場合do{}内を繰り返す（countと100は後で消す）

		Card refuge = deck[index];	// 引いたカードを一旦避難
		deck[index] = null;	// 引いたカードの場所をnullにする
		return refuge;	// 避難していたカードをreturn
	}

	// 自身のデッキの末尾にカードを挿入する
	public void insertCard(Card card) {
		for ( int i = 0; i < this.deck.length; i++ ) {
			if ( this.deck[i] == null ) {
				this.deck[i] = card;
				break;
			}
		}
	}

	// 自身のデッキ内のカードを全て表示する
	public void displayDeck() {
		for ( int i = 0; i < this.deck.length; i++ ) {
			System.out.println(this.deck[i].toString());
		}
	}

	// 自身のデッキ内の1枚目をreturn（消去はしない）
	public Card getFirstCardFromDeck() {
		return this.deck[0];
	}

	// 自身のデッキ内の1枚目をreturnして消去
	public Card getFirstCardFromDeckAndErase() {
		Card refuge = deck[0];	// 引いたカードを一旦避難
		deck[0] = null;	// 引いたカードの場所をnullにする
		return refuge;
	}

	// 自身のデッキと引数で指定されたデッキの枚数を比較する（多い2、少ない1、同じ0）
	public int compareCardLength(Deck deck) {
		if ( getDeckLength() > deck.getDeckLength() ) {
			return 2;
		} else if ( getDeckLength() < deck.getDeckLength() ) {
			return 1;
		} else {
			return 0;
		}
	}

	// DeckをCsv形式に格納してreturnする
	public ArrayList<String> convertFormatOfCsv(int playerID, int type) {
		ArrayList<String> data = new ArrayList<>();	// カードの枚数分の行を用意
		// 全てのカードを順番に取得して、[playerID,type,Mark,Number] の形式でString化
		for ( int i = 0; i < this.getDeckLength(); i++ ) {
			String line = playerID + "," + type
						+ "," + this.getCardFromDeck(i).getMark()
						+ "," + this.getCardFromDeck(i).getNumber()
						+ "%n";
			data.add(line);	// 行を追加する
		}
		return data;
	}

	// デッキ内のカードを全て破棄
	public void setNull() {
		for ( int i = 0; i < getDeckLength(); i++ ) {
			deck[i] = null;
		}
	}


}
