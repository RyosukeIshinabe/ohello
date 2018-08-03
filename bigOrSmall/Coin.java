package bigOrSmall;
import java.util.Scanner;

public class Coin {
	private int coin10 = 10;	// 10単位のチップ
	private int coin1 = 0;	// 1単位のチップ
	private int bet;	// BET
	private final int MAXBET = 20; // betの最大値
	private final int MINBET = 1;	// betの最小値
	private final int ERROR = -99;	// エラー用
	public static Scanner scan = new Scanner(System.in);	// 入力用クラス

	// コンストラクタ
	public Coin() {
		coin10 = 10;
		coin1 = 0;
		bet = 0;
	}

	// イニシャライズ
	public void initCoin() {
		this.coin10 = 10;
		this.coin1 = 0;
		this.bet = 0;
	}

	// 10単位のコインの枚数を返す
	public int getCoin10() {
		return this.coin10;
	}

	// 1単位のコインの枚数を返す
	public int getCoin1() {
		return this.coin1;
	}

	// BET額を返す
	public int getBet() {
		return this.bet;
	}

	// 所持コインをセットする
	public void setCoin(int newCoinSum) {
		this.coin10 = newCoinSum / 10;
		this.coin1 = newCoinSum % 10;
	}

	// チップの合計値を返す
	public int getCoinSum() {
		int coinSum = ( this.coin10 * 10 ) + ( this.coin1 * 1 );
		return coinSum;
	}

	// ベットされたコインを手元から引く
	public void bet() {
		int coinSum = getCoinSum();
		coinSum = coinSum - this.bet;
		setCoin(coinSum);
	}

	// betを両替して所持コインに換算
	public void change() {
		while ( this.bet >= 10 ) {
			this.coin10 = this.coin10 + 1;	// BETを10ごとにcoin10をインクリメント
			this.bet = this.bet - 10;
		}
		this.coin1 = this.coin1 + this.bet;	// 残りをcoin1に加える

		// ゲームを続けると余ったcoin1が貯まって10を超えることがあるので以下が必要
		while ( this.coin1 >= 10 ) {
			this.coin10 = this.coin10 + 1;	// coin1を10ごとにcoin10をインクリメント
			this.coin1 = this.coin1 - 10;
		}
		this.bet = 0;	// BETをリセット
		System.out.println("BETを所持コインに換金しました。");
	}

	// betの倍率処理
	public void multiplicate(int multiplication) {
		this.bet = this.bet * multiplication;
	}

	// 新規betする
	public void betCoin() {
		System.out.println("手持ちコイン：" + getCoinSum() + "（[10]" + getCoin10() + "枚 [1]" + getCoin1() + "枚）");
		System.out.println("いくらBETしますか？（最大20）");
		String betStr = scan.next();	// 入力を代入（何が入力されてもエラーにならないように一旦String型に格納）
		int betInt = checkInputBet(betStr);	// 正規チェックしてint型に変換

		// 条件を満たした数字が入力されるまで繰り返す
		while ( betInt == ERROR ) {
			System.out.println("正しい数字を入力してください");
			betStr = scan.next();
			betInt = checkInputBet(betStr);
		}
		this.bet = betInt;	// BETする
		bet();	// BETされたコインを手元から引く
	}

	// BETの正規表現
	public int checkInputBet(String betStr) {

		if ( betStr.matches("^[0-9]{1,2}$") ) {	// 入力された文字が数字の1桁or2桁だった場合
			int betInt = Integer.parseInt(betStr);	// int型に変換

			// 1以上で、かつ20以下で、かつ所持コインより小さければその値を返す
			if ( betInt >= MINBET && betInt <= MAXBET && betInt <= getCoinSum() ) {
				return betInt;
			// 条件を満たさない場合はERROR定数を返す
			} else {
				return ERROR;
			}
		} else {
			return ERROR;
		}
	}
}
