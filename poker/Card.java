package poker;
import java.util.Random;

public class Card {

	// [マーク][数字]=ステータス（0未使用、1フィール上、2墓場）
	private final int[][] status;

	// カード用
	private final int MAXNUM = 13;
	private final int MAXMARK = 4;
	private final int FIELD = 5;

	// カードのステータス用
	private final int unused = 0;
	private final int onField = 1;
	private final int used = 2;

	// カードのマーク用
	private final int spade = 3;
	private final int heart = 2;
	private final int dia = 1;
	private final int club = 0;

	// コンストラクタ
	public Card() {
    	this.status = new int[MAXMARK][MAXNUM];
    	// 全てに0を代入
        for ( int i = 0; i < MAXMARK; i++ ) {
            for ( int j = 0; j < MAXNUM; j++ ) {
                this.status[i][j] = unused;
            }
        }
    }

	// カードを初期化
	public void initCard() {
	   	// 全てに0を代入
	    for ( int i = 0; i < MAXMARK; i++ ) {
	        for ( int j = 0; j < MAXNUM; j++ ) {
	            this.status[i][j] = unused;
	        }
	    }
	}

	// カードを表現する文字を返す（例：スペードの3）
    public String expressCard(int mark, int num) {
    	// マーク用とメッセージ用の変数
        String markStr = "";
        String msg;
        // mark引数に応じてマークを代入
        if ( mark == spade ) {
            markStr = "▲";
        } else if ( mark == heart ) {
            markStr = "◯";
        } else if ( mark == dia ) {
            markStr = "△";
        } else if ( mark == club ) {
            markStr = "●";
        }
        // マークと数字をメッセージに代入してリターン
        msg = markStr + (num+1);
        return msg;
    }

    // changeリストを受け取ってonfieldからusedに変える
    public void changeCard(int[] changeList) {
	    int count = 0;
	    int[] onFieldListMark = new int[FIELD];
	    int[] onFieldListNum = new int[FIELD];
	    for ( int j = 0; j < MAXMARK; j++ ) {
    		for ( int k = 0; k < MAXNUM; k++ ) {
    			if ( this.status[j][k] == onField ) {
    				onFieldListMark[count] = j;
    				onFieldListNum[count] = k;
    				count++;
    			}
    		}
	    }
	    for ( int i = 0; i < changeList.length; i++ ) {
	    	this.status[onFieldListMark[changeList[i]]][onFieldListNum[changeList[i]]] = used;
	    }
    }

    // カードのステータスを変更する
    public void changeStatus(int mark, int num, int status) {
	    // 未使用のデッキに戻す
	    if ( status == 0 ) {
	    	this.status[mark][num] = unused;
	    // フィールドに出す
	    } else if ( status == 1 ) {
	    	this.status[mark][num] = onField;
	    // 墓場に送る
	    } else if ( status == 2 ) {
	    	this.status[mark][num] = used;
	    }
    }

    // カードを全部使い切ったかどうか判定（使い切ればtrue）
    public boolean endGame() {
    	// 配列を1こずつチェックして0が出てきた時点でfalseを返す
        for ( int i = 0; i < MAXMARK; i++ ) {
            for ( int j = 0; j < MAXNUM; j++ ) {
                if ( this.status[i][j] == unused ) {
                    return false;
                }
            }
        }
        // まだ使っていないカードが出てこなかったらtrue
        return true;
    }

    // 指定のカードの状態を返す
    public int getCardStatus(int mark, int num) {
	    return this.status[mark][num];
    }

    // 指定のステータスのカードの枚数を数える
    public int countDesignationStatus(int status) {
	    int count = 0;
	    for ( int i = 0; i < MAXMARK; i++ ) {
	        for ( int j = 0; j < MAXNUM; j++ ) {
	        	// 未使用のカードを数える
	        	if ( status == 0 ) {
	        		if ( this.status[i][j] == unused ) {
	        			count++;
	        		}
	            // フィールド上のカードを数える
	        	} else if ( status == 1 ) {
	        		if ( this.status[i][j] == onField ) {
	       			count++;
	        		}
	        	// 墓場のカードを数える
	        	} else if ( status == 2 ) {
	            	if ( this.status[i][j] == used ) {
	            		count++;
	            	}
	            }
	        }
	    }
	    return count;
    }

    // ランダムな値を生成
    public int createRandNum(int max) {
	    Random rand = new Random();

	    // 0〜3のダンラムな値（マーク用）
	    if ( max == MAXMARK ) {
	    	int randomNumberForMark = rand.nextInt(MAXMARK);
	    	return randomNumberForMark;

    	// 0〜12のランダムな値（数字用）
    	} else if ( max == MAXNUM ) {
    		int randomNumberForNum = rand.nextInt(MAXNUM);
    		return randomNumberForNum;

    	} else {
    		return 0;
    	}
    }

    // 未使用のカードを引き、ステータスを変更する
    // 未使用のカードがあるかどうかの検証は含まれません。存在が確定後に使用すること
    public void drowCard() {
    	int randomNumberForMark = createRandNum(MAXMARK);	// マーク用
		int randomNumberForNum = createRandNum(MAXNUM);	// 数字用

		// そのカードがすでに出ていた場合は繰り返す
		while ( this.status[randomNumberForMark][randomNumberForNum] != unused ) {
		randomNumberForMark = createRandNum(MAXMARK);	// マーク用
		randomNumberForNum = createRandNum(MAXNUM);	// 数字用
		}

		// ステータスを1（フィールドに出ている状態）に
		changeStatus(randomNumberForMark, randomNumberForNum, onField);
    }

    // フィールド上のカードを表示
    public void displayCard() {
	    int count = 1;
	    for ( int i = 0; i < MAXMARK; i++ ) {
	    	for ( int j = 0; j < MAXNUM; j++ ) {
	           	if ( this.status[i][j] == onField ) {
	           		System.out.print("[" + count + "] ");
	           		System.out.println(expressCard(i,j));
	           		count++;
	           	}
	        }
	    }
    }

    // フィールド上の指定のカードを表示
    public void displayCardOfOne(int designate) {
	    int count = 0;
	    PARENT:	// これはアンカー。breakがどこまで戻るかを指定する
	    for ( int i = 0; i < MAXMARK; i++ ) {
	    	for ( int j = 0; j < MAXNUM; j++ ) {
	           	if ( this.status[i][j] == onField ) {
	           		if ( count == designate ) {
	           			System.out.print("[" + (count+1) + "] ");
		           		System.out.print(expressCard(i,j));
		           		break PARENT;	// アンカー「PARENT:」の所までbreakする
	           		} else {
	           			count++;
	           		}
	           	}
	    	}
	    }
    }

    // フィールド上のカードが5枚あるかどうか検証し、なければカードを引く
    public void checkCardsQuantityOnField() {

	    // status1（フィールド上）のカードを数える
	    int countCardOnField = countDesignationStatus(onField);
	    // status2（未使用）のカードの枚数を数える
		int countCardUnused = countDesignationStatus(unused);

		// もしフィールド上に足りないカードの枚数が残りの未使用カードを上回ったら
		if ( FIELD - countCardOnField > countCardUnused ) {
			// 墓場のカードを未使用に戻しちゃえww
			restoreUsedCard();
		}

	    // status1（フィールド上）のカードが5枚未満だった場合は
	    while ( countCardOnField < FIELD ) {
	    	// 5枚になるまでカードを引く
	    	drowCard();
	    	countCardOnField++;
	    }
    }

    // 墓場のカードを未使用に戻す
    public void restoreUsedCard() {
	    for ( int i = 0; i < MAXMARK; i++ ) {
	    	for ( int j = 0; j < MAXNUM; j++ ) {

	          	// ステータスが2のカードを0にする
	    		if ( this.status[i][j] == used ) {
	    			changeStatus(i, j, unused);
		        }
	    	}
	    }
    }

	// フィールド上のカードのうち、（マーク関係なく）それぞれの番号が何枚出ているかを返す
 	public int[] getSameNumCard() {
 		int[] count = new int[MAXNUM];

 		for ( int i = 0; i < MAXMARK; i++ ) {
 			for ( int j = 0; j < MAXNUM; j++ ) {

 				// count[0]... （マーク関係なく）1のカードの枚数
 				// count[1]... （マーク関係なく）2のカードの枚数
 				// count[12]... （マーク関係なく）13のカードの枚数
 				// という配列を作成する
 				if ( this.status[i][j] == onField ) {
 					count[j]++;
 				}
 			}
 		}
 		return count;
 	}

	// フィールド上のカードのうち、（数字関係なく）それぞれのマークが何枚出ているかを返す
  	public int[] getSameMarkCard() {
  		int[] count = new int[MAXMARK];

  		for ( int i = 0; i < MAXMARK; i++ ) {
  			for ( int j = 0; j < MAXNUM; j++ ) {

  				// count[0]... （数字関係なく）クラブのカードの枚数
  				// count[1]... （数字関係なく）ダイヤのカードの枚数
  				// count[2]... （数字関係なく）ハートのカードの枚数
  				// count[3]... （数字関係なく）クラブのカードの枚数
  				// という配列を作成する
  				if ( this.status[i][j] == onField ) {
  					count[i]++;
  				}
  			}
  		}
  		return count;
  	}
}
