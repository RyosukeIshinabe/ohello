package poker;

public class Hand {

	// 枚数用
	private final int MAXNUM = 13;
	private final int MAXMARK = 4;

	// それぞれの役の強さ（横のコメントは公式ゲームのランク）
	private final int rankOfOnePair = 1;	// 2
	private final int rankOfTwoPair = 3;	// 5
	private final int rankOfThreeCard = 5;	// 10
	private final int rankOfStraight = 7;	// 15
	private final int rankOfFlush = 8;	// 20
	private final int rankOfFullhouse = 10;	// 25
	private final int rankOfFourCard = 20;	// 50
	private final int rankOfStraightFlush = 30;	// 75

	// ペアの枚数を返す（0=ペアなし、1=ワンペア、2=ツーペア）
	public int countPairs(int[] sameNumCardList) {
		int countPair = 0;
		for ( int i = 0; i < MAXNUM; i++ ) {
			// 同じ数字のカードが2枚あるならペア数をインクリメント
			if ( sameNumCardList[i] == 2 ) {
				countPair++;
			}
		}
		return countPair;
	}

	// 同じ数字のカードの枚数を返す（3=スリーカード、4=フォーカード）
	public int countSameNumCard(int[] sameNumCardList) {
		for ( int i = 0; i < MAXNUM; i++ ) {
			// 同じ数字のカードが3枚あるなら3を返す
			if ( sameNumCardList[i] == 3 ) {
				return 3;
			} else if ( sameNumCardList[i] == 4 ) {
				return 4;
			}
		}
		return 0;
	}

	// フルハウス
	public boolean checkFullhouse(int[] sameNumCardList) {
		// ワンペアとスリーカードが両立した場合trueを返す
		if ( countPairs(sameNumCardList) == 1 && countSameNumCard(sameNumCardList) == 3 ) {
			return true;
		// それ以外はfalse
		} else {
			return false;
		}
	}

	// フラッシュ
	public boolean checkFlush(int[] sameMarkCardList) {
		for ( int i = 0; i < MAXMARK; i++ ) {
			// 同じマークのカードが5枚あるならtrueを返す
			if ( sameMarkCardList[i] == 5 ) {
				return true;
			}
		}
		return false;
	}

	// ストレート
	public boolean checkStraight(int[] sameNumCardList) {
		// [0]つまり1 から、[8]つまり9まで検証
		for ( int i = 0; i < MAXNUM - 4; i++ ) {
			// 連続する5枚でカードの枚数が1だったらtrueを返す
			if ( sameNumCardList[i] == 1 &&
				 sameNumCardList[i+1] == 1 &&
				 sameNumCardList[i+2] == 1 &&
				 sameNumCardList[i+3] == 1 &&
				 sameNumCardList[i+4] == 1 ) {
				 return true;
			}
		}

		// 10-11-12-13-1 もストレートになるらしい…
		if ( sameNumCardList[9] == 1 &&
			 sameNumCardList[10] == 1 &&
			 sameNumCardList[11] == 1 &&
			 sameNumCardList[12] == 1 &&
			 sameNumCardList[0] == 1 ) {
			 return true;
		}
		return false;
	}

	// ストレートフラッシュ
	public boolean checkStraightFlush(int[] sameMarkCardList, int[] sameNumCardList) {

		// ストレートもフラッシュも満たしているならtrueを返す
		if ( checkStraight(sameNumCardList) && checkFlush(sameMarkCardList) ) {
			return true;

		} else {
			return false;
		}
	}

	// 全ての役をチェックしてランクを返す（高い順に検索してリターン）
	public int checkAllHand(int[] sameMarkCardList, int[] sameNumCardList) {

		if ( checkStraightFlush(sameMarkCardList, sameNumCardList) ) {
			System.out.println("ストレートフラッシュです！");
			return rankOfStraightFlush;

		} else if ( countSameNumCard(sameNumCardList) == 4 ) {
			System.out.println("4カードです！");
			return rankOfFourCard;

		} else if ( checkFullhouse(sameNumCardList) ) {
			System.out.println("フルハウスです！");
			return rankOfFullhouse;

		} else if ( checkFlush(sameMarkCardList) ) {
			System.out.println("フラッシュです！");
			return rankOfFlush;

		} else if ( checkStraight(sameNumCardList) ) {
			System.out.println("ストレートです！");
			return rankOfStraight;

		} else if ( countSameNumCard(sameNumCardList) == 3 ) {
			System.out.println("3カードです！");
			return rankOfThreeCard;

		} else if ( countPairs(sameNumCardList) == 2 ) {
			System.out.println("2ペアです！");
			return rankOfTwoPair;

		} else if ( countPairs(sameNumCardList) == 1 ) {
			System.out.println("1ペアです！");
			return rankOfOnePair;

		} else {
			return 0;
		}
	}

	// 定型文表示
	public void displayFirstMsg() {
		System.out.println("【役一覧】");
		System.out.println("1ペア：" + rankOfOnePair + "点");
		System.out.println("2ペア：" + rankOfTwoPair + "点");
		System.out.println("3カード：" + rankOfThreeCard + "点");
		System.out.println("ストレート：" + rankOfStraight + "点");
		System.out.println("フラッシュ：" + rankOfFlush + "点");
		System.out.println("フルハウス：" + rankOfFullhouse + "点");
		System.out.println("4カード：" + rankOfFourCard + "点");
		System.out.println("ストレートフラッシュ：" + rankOfStraightFlush + "点");
		System.out.println("=====================");
	}
}
