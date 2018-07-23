package othello;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		// プレイヤーの入力に使用
		Scanner scan = new Scanner(System.in);
		int x;
		int y;

		// プレイヤーID（先手は1）
		int playerID = 1;

		// 初回用のメッセージ表示
		System.out.println("◇◆◇◆GAME START◇◆◇◆");
		System.out.println("石を置く場所は、縦方向[1〜8]と横方向[A〜H]で座標を指定してください。");
		System.out.println("【正】5C　C5　など");
		System.out.println("【誤】0C　5c　AA　など");
		System.out.println("================================");

		// ボードを作成して表示
		Board myBoard = new Board();
		myBoard.displayStatus();

		// 初回用のメッセージ表示
		System.out.println("================================");

		// 詰む（指し手がなくなる）まで、この中の処理を繰り返す
		while ( myBoard.checkTurnContinue(playerID) == 1 ) {

			// ターンのプレイヤー名を表示
			System.out.println(myBoard.displayTurn(playerID));

			// ユーザーが入力した文字を格納
		    String inputNum = scan.next();

			// 入力された値を座標に変換
			x = myBoard.convertCoordinatesX(inputNum);
			y = myBoard.convertCoordinatesY(inputNum);

			// きちんとした座標が返って来たなら
			if ( x != 0 && y != 0 && myBoard.checkPutable(playerID, y, x) != 0 ) {

				// ひっくり返す
				myBoard.putAndReverse(playerID, y, x);

				// 更新後のボードと石の数を表示
				myBoard.displayStatus();
				System.out.println("現在の状況　◯ " + myBoard.aggregatePlayer1() + " ： " + "● " + myBoard.aggregatePlayer2());

				// ターン変更
				playerID = 3 - playerID;

				// 2回目以降のメッセージ表示
				System.out.println("================================");

			// 置けない場所の場合、playerIDを変える前にwhileの先頭まで戻る
			} else {
				System.out.println("入力が正しくありません。");
				continue;
			}
		}

		System.out.println("◇◆◇◆GAME END◇◆◇◆");

		// 集計
		System.out.println("白のプレイヤー：" + myBoard.aggregatePlayer1());
		System.out.println("黒のプレイヤー：" + myBoard.aggregatePlayer2());
		System.out.println(myBoard.judgeWinPlayer(myBoard.aggregatePlayer1(), myBoard.aggregatePlayer2()));

	}
}
