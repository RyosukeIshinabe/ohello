package warGame;
import java.util.Random;

public class Main {
	private static Player playerOwn;	// 自分
	private static Player playerOpp;	// 相手
	private static Field field;	// フィールド
	private static Recorder recorder;	// レコーダー
	private static final int PLEYERS = 2;	// 参加するプレイヤー数
	private static final int PLAYERCARD = 13;	// プレイヤーが持つカード数
	private static final int ALLCARD = PLEYERS * PLAYERCARD;	// プレイヤーが持つカード数

	public static void main(String[] args) {

		// 事前準備
		playerOwn = new Player(0);	// 自分
		playerOpp = new Player(1);	// 相手
		field = new Field();	// フィールド
		recorder = new Recorder();	// レコーダー
		recorder.getPerformance();	// 過去の成績を読み込む
		recorder.displayPerformance();	// 成績を表示する
		dealCard();	// カードを配る

		System.out.println("準備が整いました。ゲームを始めます。");

		// ゲーム開始（ラウンド数が13以下で、かつプレイヤーカードが残っている間繰り返す）
		while ( field.getRound() <= PLAYERCARD
				&& playerOwn.getLengthOfPlayerDeck() > 0
				&& playerOpp.getLengthOfPlayerDeck() > 0 ) {
			field.displayRound();	// ラウンド数を表示
			playerOwn.displayAllDeckInfo();	// 持ち札と獲得札の枚数を表示する
			playerOpp.displayAllDeckInfo();	// 持ち札と獲得札の枚数を表示する
			field.displayLengthOfOnHoldDeck();	// 保留中の札の枚数を表示する

			if ( field.selectContinue() == false ) {
				break;	// 札を切りますか？がfalseなら抜ける
			}

			playerOwn.takeCardAndDisplay();	// 自分のカードを切って表示
			playerOpp.takeCardAndDisplay();	// 相手のカードを切って表示

			// 使用中のカードを比較して結果をcompareResultに格納
			int compareResult = playerOwn.getInUseDeck().compareCard(playerOpp.getInUseDeck());
			treat(compareResult);	// 勝敗に応じた処理
		}
		System.out.println("### 最終結果 ###");

		// 獲得したカードの枚数を表示
		System.out.print("あなた：");
		playerOwn.displayLengthOfWinDeck();
		System.out.print("CPU：");
		playerOpp.displayLengthOfWinDeck();

		judgeWinnter();	//勝敗判定
		recorder.recordPerformance();	// 成績を書き込む
		recorder.displayPerformance();	// 成績を表示する
	}



	// カードを配る（fieldクラスが持つdefaultDeckから、playerクラスが持つplayerDeckにカードを移動）
	public static void dealCard() {
		Random rand = new Random();	// ランダムな数字を作成するためのクラス
		Card newCard = new Card();	// カードを渡すための臨時の入れ物
		int count = 0;

		do {
			// defaultDeckからnullではない新たなカードを引いて元のを消す
			newCard = field.takeCardFromDefaultDeck();

			if ( newCard == null ) {	// 仮にnewCardがnullなら終了
				break;
			}

			int randPlayerID = rand.nextInt(PLEYERS);	// 振り分け先のプレイヤーのランダム数

			// プレイヤー用のランダム数が0、かつ自分のデッキに空きがある場合
			if ( randPlayerID == 0 && playerOwn.getLengthOfPlayerDeck() < PLAYERCARD ) {
				playerOwn.setCardToPlayerDeck(newCard);	// newCardを自分のデッキに挿入
			} else if ( randPlayerID == 1 && playerOpp.getLengthOfPlayerDeck() < PLAYERCARD ) {
				playerOpp.setCardToPlayerDeck(newCard);	// newCardを相手のデッキに挿入
			} else if ( playerOwn.getLengthOfPlayerDeck() < playerOpp.getLengthOfPlayerDeck() ) {
				playerOwn.setCardToPlayerDeck(newCard);
			} else {
				playerOpp.setCardToPlayerDeck(newCard);
			}
			count++;

		// 振り分け前のデッキが無くなるまで続ける
		} while ( field.getLengthOfDefaultDeck() > 0 && count < ALLCARD );
	}

	// 勝負の結果による処理
	public static void treat(int compareResult) {


		if ( compareResult == 2 ) {	// 勝利
			System.out.println("あなたの勝利です！");
			moveCardToWinner(0);	// カードを移動
		} else if ( compareResult == 1 ) {	// 負け
			System.out.println("CPUの勝利です");
			moveCardToWinner(1);	// カードを移動
		} else {	// 引き分け
			System.out.println("引き分けでした");
			moveCardToOnHoldDeck();	// フィールド上のonHoldに保存
		}
		System.out.println("");
	}

	// 負けたプレイヤーから勝ったプレイヤーにカードを移動
	public static void moveCardToWinner(int winPlayerID) {

		// 自分の勝利
		if ( winPlayerID == 0 ) {
			// inUseカードを、winDeckの空きの先頭に挿入する（inUseカードは消去）
			playerOwn.setCardToWinDeck(playerOwn.getInUseDeckAndErase());
			playerOwn.setCardToWinDeck(playerOpp.getInUseDeckAndErase());

			// 先に保留中のカードの枚数を取得
			// for文の()内で毎回取得するとループする度にlengthが変わる？
			int length = field.getLengthOfOnHoldDeck();

			// 保留中のカードもwinDeckへ（保留は解除する）
			for ( int i = 0; i < length; i++ ) {
				playerOwn.setCardToWinDeck(field.getCardFromOnHoldDeckAndErase(i));
			}

		// 相手の勝利
		} else if ( winPlayerID == 1 ) {
			// inUseカードを、winDeckの空きの先頭に挿入する（inUseカードは消去）
			playerOpp.setCardToWinDeck(playerOwn.getInUseDeckAndErase());
			playerOpp.setCardToWinDeck(playerOpp.getInUseDeckAndErase());

			// 保留中のカードもwinDeckへ（保留は解除する）
			for ( int i = 0; i < field.getLengthOfOnHoldDeck(); i++ ) {
				playerOpp.setCardToWinDeck(field.getCardFromOnHoldDeckAndErase(i));
			}
		}
	}

	// inUseカードをフィールド上のonHoldに保存（inUseカードは消去）
	public static void moveCardToOnHoldDeck() {
		field.setCardToOnHoldDeck(playerOwn.getInUseDeckAndErase());
		field.setCardToOnHoldDeck(playerOpp.getInUseDeckAndErase());
	}

	// 最後の勝敗判定
	public static void judgeWinnter() {
		int result = playerOwn.compareCardLengthOfWinDeck(playerOpp);
		recorder.incrementChallengeTimes();	// チャレンジ回数を1増やす

		if ( result == 2 ) {
			System.out.println("あなたが勝ちました！おめでとう！");
			recorder.incrementWinTimes();	// 勝利回数を1増やす
			// 獲得カード数が過去の成績よりも多い場合は記録する
			if ( recorder.getMaxWinCards() < playerOwn.getLengthOfWinDeck() ) {
				recorder.setMaxWinCards(playerOwn.getLengthOfWinDeck());
			}
		} else if ( result == 1 ) {
			System.out.println("CPUが勝ちました。");
		} else {
			System.out.println("同点でした。");
		}

		field.setNulltoAllDeck();	// 保留中の札を破棄
	}

	// 分解済みのデータを受け取って自身のデッキとして保存（分解はrecorderクラスで事前に行う前提）
	public void roadDeck(int playerId, int deckType, int mark, int number) {
		if ( playerId == 0 ) {
			playerOwn.roadDeck(deckType, mark, number);
		} else if ( playerId == 1 ) {
			playerOpp.roadDeck(deckType, mark, number);
		}
	}

	// デッキ情報（カード1枚分）をint型配列として受け取って、Playerクラスに譲渡
	public void passCard(int[] data) {

		// データの格納場所
		int cellOfPlayerID = 0;	// プレイヤーIDが格納されている列
		int cellOfDeckType = 1;	// デッキの種類が格納されている列
		int cellOfMarkOfCard = 2;	// カードのマークが格納されている列
		int cellOfNumberOfCard = 3;	// カードの数字が格納されている列

		if ( data[cellOfPlayerID] == 0 ) {
			playerOwn.roadDeck(data[cellOfDeckType], data[cellOfMarkOfCard], data[cellOfNumberOfCard]);
		} else if ( data[cellOfPlayerID] == 1 ) {
			playerOpp.roadDeck(data[cellOfDeckType], data[cellOfMarkOfCard], data[cellOfNumberOfCard]);
		}
	}

}
