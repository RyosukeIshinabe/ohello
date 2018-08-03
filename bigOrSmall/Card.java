package bigOrSmall;

public class Card {
	private int mark;
	private int number;
	private static final int SPADE = 3;	// スペード
	private static final int HEART = 2;	// ハート
	private static final int DIA = 1;	// ダイア
	private static final int CLUB = 0;	// クラブ

	// コンストラクタとゲッターセッター
	public Card(int mark, int number) {
		this.mark = mark;
		this.number = number;
	}

	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	// カードのマークと数字をセットで返す
	public String toString() {
		String markStr = "";
        if ( mark == SPADE ) {
            markStr = "スペード";
        } else if ( mark == HEART ) {
            markStr = "ハート";
        } else if ( mark == DIA ) {
            markStr = "ダイヤ";
        } else if ( mark == CLUB ) {
            markStr = "クラブ";
        }
		return markStr + number;
	}

	// カードの強さを比較する（次のカードが強ければtrue）
	public boolean compareCard(Card nextCard) {
  		if ( this.number < nextCard.number ) {
			return true;
		} else if ( this.number > nextCard.number ) {
			return false;
		} else if ( this.mark < nextCard.mark ) {	// 数字が同じ場合マークで比較する
			return true;
		} else {
			return false;
		}
  	}

}
