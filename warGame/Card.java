package warGame;

public class Card {
	private int mark;
	private int number;
	private static final int SPADE = 1;	// スペード
	private static final int HEART = 2;	// ハート
	private static final int DIA = 0;	// ダイア
	private static final int CLUB = 3;	// クラブ

	// コンストラクタ
	public Card(int mark, int number) {
		this.mark = mark;
		this.number = number;
	}

	public Card() {
	}

	// ゲッターセッター
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

	@Override
	public String toString() {
		String outputMark = "";
		String outputNum = "";

		// マークを変換
        if ( mark == SPADE ) {
        	outputMark = "スペード";
        } else if ( mark == HEART ) {
        	outputMark = "ハート";
        } else if ( mark == DIA ) {
        	outputMark = "ダイヤ";
        } else if ( mark == CLUB ) {
        	outputMark = "クラブ";
        }

        // 数字を変換
        if ( number <= 9 ) {
        	outputNum = String.valueOf(number + 1);
        } else if ( number == 10 ) {
        	outputNum = "J";
        } else if ( number == 11 ) {
        	outputNum = "Q";
        } else if ( number == 12 ) {
        	outputNum = "K";
        } else if ( number == 13 ) {
        	outputNum = "A";
        }

		return outputMark + outputNum;
	}

	// 自身のカードと、引数のカードの数字を比較する（勝利2、負け1、引き分け0）
	public int compareCard(Card otherCard) {
  		if ( this.number > otherCard.number ) {
			return 2;
		} else if ( this.number < otherCard.number ) {
			return 1;
		} else {
			return 0;
		}
  	}

}
