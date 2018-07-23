package othello;

public class Board {

	public final int SIDES = 10;	// 一辺のサイズ（番兵のマスも含めて）
	public int[][] status;	// [縦][横]=マスのステータス
	public int playerID;	// 白のプレイヤーID
	public int otherPlayerID;	// 黒のプレイヤーID

	// ボードの初期化
	public Board() {

		//   [-][A][B][C][D][E][F][G][H][-]
		//[-] -1 -1 -1 -1 -1 -1 -1 -1 -1 -1
		//[1] -1  0  0  0  0  0  0  0  0 -1
		//[2] -1  0  0  0  0  0  0  0  0 -1
		//[3] -1  0  0  0  0  0  0  0  0 -1
		//[4] -1  0  0  0  2  1  0  0  0 -1
		//[5] -1  0  0  0  1  2  0  0  0 -1
		//[6] -1  0  0  0  0  0  0  0  0 -1
		//[7] -1  0  0  0  0  0  0  0  0 -1
		//[8] -1  0  0  0  0  0  0  0  0 -1
		//[-] -1 -1 -1 -1 -1 -1 -1 -1 -1 -1

	    int i, j;
	    this.status = new int[10][10];

	    // 全ての値に-1を代入
	    for (i = 0; i < this.SIDES; i++) {
	        for (j = 0; j < this.SIDES; j++) {
	            this.status[i][j] = -1;
	        }
	    }

	    // 中央の8×8マスに0を代入
	    for (i = 1; i <= 8; i++) {
	        for (j = 1; j <= 8; j++) {
	        	this.status[i][j] = 0;
	        }
	    }

	    // 中央の2×2マスに1と2を代入
	    this.status[4][5] = this.status[5][4] = 1;
	    this.status[4][4] = this.status[5][5] = 2;
	}

	// ボードを表示
	public void displayStatus() {
		int i, j;

		System.out.println("  | A B C D E F G H");
		System.out.println("-------------------");
	    for (i = 1; i <= 8; i++) {
	    	System.out.print(i + " | ");
	        for (j = 1; j <= 8; j++) {
	        	if ( this.status[i][j] == 1 ) {
	        		System.out.print("◯" + " ");
	        	} else if ( this.status[i][j] == 2 ) {
	        		System.out.print("●" + " ");
	        	} else {
	        		System.out.print("∴" + " ");
	        	}
	        }
	        System.out.println("");
	    }
	}

	// ひっくり返す石の数を数える
	public int countReverseStone(int playerID, int p, int q, int d, int e) {
	    int i;
	    otherPlayerID = 3 - playerID;

	    // 置いたマスの隣に相手の石があれば、その数だけiをインクリメント
	    // 自分の石、空白、端っこ、のいずれかを察知して止まる
	    for (i = 1; this.status[p+i*d][q+i*e] == otherPlayerID; i++) {};

	    // インクリメントが止まった場所が自分の石であれば、iから1引いてリターン
	    if (this.status[p+i*d][q+i*e] == playerID) {
	        return i-1;
	    // それ以外（空白、端っこ、の場合は0をリターン）
	    } else {
	        return 0;
	    }
	}

	// 入力された値（座標p,q）に、石が置けるかどうか、8方向ごとにcountReverseStone関数でチェックする
	public int checkPutable(int playerID, int p, int q) {

		// 置かれた場所の座標が1〜8以外の場所の場合、0を返す
	    if ( p < 1 || p > 8 || q < 1 || q > 8 ) {
	    	return 0;
	    }

	    // 置かれた場所が空白ではない場合、0を返す
	    if ( this.status[p][q] != 0 ) {
	    	return 0;
	    }

	    // 8方向ごとにチェック（どれか1方向でも置けるなら1をリターン）
	    if ( this.countReverseStone(playerID, p, q, -1,  0) != 0 ||
	    	 this.countReverseStone(playerID, p, q,  1,  0) != 0 ||
	    	 this.countReverseStone(playerID, p, q,  0, -1) != 0 ||
	    	 this.countReverseStone(playerID, p, q,  0,  1) != 0 ||
	    	 this.countReverseStone(playerID, p, q, -1, -1) != 0 ||
	    	 this.countReverseStone(playerID, p, q, -1,  1) != 0 ||
	    	 this.countReverseStone(playerID, p, q,  1, -1) != 0 ||
	    	 this.countReverseStone(playerID, p, q,  1,  1) != 0 ) {
	    	return 1;
	    } else {
	    	return 0;
	    }
	}

	// 手が残されているか（詰んでいないか）、全マスをcheckPutable関数にかけてチェック
	public int checkTurnContinue(int playerID) {
		int i, j;

		for ( i = 1; i <= 8; i++ ) {
			for ( j = 1; j <= 8; j++ ) {
				if ( this.checkPutable(playerID, i, j) == 1 ) {
					return 1;
				}
			}
		}
		return 0;
	}

	// 指手の入力を縦方向の座標に変換する
	public int convertCoordinatesY(String inputNum) {
		if ( inputNum.indexOf("1") == 0 || inputNum.indexOf("1") == 1 ) {
			return 1;
		} else if ( inputNum.indexOf("2") == 0 || inputNum.indexOf("2") == 1 ) {
			return 2;
		} else if ( inputNum.indexOf("3") == 0 || inputNum.indexOf("3") == 1 ) {
			return 3;
		} else if ( inputNum.indexOf("4") == 0 || inputNum.indexOf("4") == 1 ) {
			return 4;
		} else if ( inputNum.indexOf("5") == 0 || inputNum.indexOf("5") == 1 ) {
			return 5;
		} else if ( inputNum.indexOf("6") == 0 || inputNum.indexOf("6") == 1) {
			return 6;
		} else if ( inputNum.indexOf("7") == 0 || inputNum.indexOf("7") == 1 ) {
			return 7;
		} else if ( inputNum.indexOf("8") == 0 || inputNum.indexOf("8") == 1 ) {
			return 8;
		} else {
			return 0;
		}
	}

	// 指手の入力を横方向の座標に変換する
		public int convertCoordinatesX(String inputNum) {
			if ( inputNum.indexOf("A") == 1 || inputNum.indexOf("A") == 0 ) {
				return 1;
			} else if ( inputNum.indexOf("B") == 1 || inputNum.indexOf("B") == 0 ) {
				return 2;
			} else if ( inputNum.indexOf("C") == 1 || inputNum.indexOf("C") == 0 ) {
				return 3;
			} else if ( inputNum.indexOf("D") == 1 || inputNum.indexOf("D") == 0 ) {
				return 4;
			} else if ( inputNum.indexOf("E") == 1 || inputNum.indexOf("E") == 0 ) {
				return 5;
			} else if ( inputNum.indexOf("F") == 1 || inputNum.indexOf("F") == 0 ) {
				return 6;
			} else if ( inputNum.indexOf("G") == 1 || inputNum.indexOf("G") == 0 ) {
				return 7;
			} else if ( inputNum.indexOf("H") == 1 || inputNum.indexOf("H") == 0 ) {
				return 8;
			} else {
				return 0;
			}
		}

	// 石をおき、挟んだ石を裏がえす
	public void putAndReverse(int playerID, int p, int q) {
	    int count, d, e, i;

	    // 8方向にcountReverseStone関数を試す（ただし0,0は置いた所と同じ場所なのでスキップ）
	    for (d = -1; d <= 1; d++) {
	        for (e = -1; e <= 1; e++) {
	            if (d == 0 && e == 0) {
	            	continue;
	            }
	            // countReverseStone関数で返ってきた値（つまり裏返せる石の数）をcount変数に格納
	            count = countReverseStone(playerID, p, q, d, e);

	            // count変数の値分、石の持っている値をplayerIDに変換
	            for (i = 1; i <= count; i++) {
	            	this.status[p+i*d][q+i*e] = playerID;
	            }
	        }
	    }
	    // 石を置く
	    this.status[p][q] = playerID;
	}

	// ターンのプレイヤー名を表示する
	public String displayTurn(int playerID) {
		if ( playerID == 1 ) {
			return "白のターンです。";
		} else if ( playerID == 2 ) {
			return "黒のターンです。";
		} else {
			return "ターンの検出でエラーが発生しました。";
		}
	}

	// 指定の色の石の数を数えてどちが勝ちか返す
	public int aggregatePlayer1() {
		int i;
		int j;
		int count1 = 0;

		for (i = 1; i <= 8; i++) {
	        for (j = 1; j <= 8; j++) {

	        	if ( this.status[i][j] == 1 ) {
	        		count1++;
	        	} else {
	        	}
	        }
		}
		return count1;
	}

	// 指定の色の石の数を数えてどちが勝ちか返す
	public int aggregatePlayer2() {
		int i;
		int j;
		int count2 = 0;

		for (i = 1; i <= 8; i++) {
	        for (j = 1; j <= 8; j++) {

	        	if ( this.status[i][j] == 2 ) {
	        		count2++;
	        	} else {
	        	}
	        }
		}
		return count2;
	}

	// どちらが勝ちかを返す
	public String judgeWinPlayer(int count1, int count2) {
		if ( count1 > count2 ) {
			return "白の勝ち！";
		} else if ( count2 > count1 ) {
			return "黒の勝ち！";
		} else {
			return "同点でした";
		}
	}


}

