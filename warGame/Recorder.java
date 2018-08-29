package warGame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Recorder {
	private LocalDateTime dateTime;	// 最新の日付
	private int challengeTimes;	// 挑戦した回数
	private int winTimes;	//	勝利した回数
	private int maxWinCards;	// 勝利した時に奪った最大カード数
	private ArrayList<String> gameProgressData;	// ゲームの進捗データ
	private ArrayList<String> allDeckData;	// 全デッキのデータ

	// ディレクトリ
	private static final String DIR = System.getProperty("user.home") + "/Desktop/";
//	private static final String PROG = "war_record_prog.csv";	// レコードファイル名
	private static final String PFMC = "war_record_pfmc.csv";	// レコードファイル名
//	private static final String PROG_DIR = DIR + PROG;	// ディレクトリ + ファイル名
	private static final String PFMC_DIR = DIR + PFMC;	// ディレクトリ + ファイル名

	public Recorder() {
	}

	// 初期化
	public void init() {
		challengeTimes = 0;
		winTimes = 0;
		maxWinCards = 0;
		dateTime = LocalDateTime.now();
	}

	/*
	 * 	dateTime用
	 */

	// ゲッター（更新しないで取得）
	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	// ゲッター（更新した上で取得）
	public LocalDateTime updateAndGetDateTime() {
		updateDateTime();
		return this.dateTime;
	}

	// ゲッター（更新しないで取得）（年/月/日 時:分:秒 のString形式で取得）
	public String getDateTimeFormat() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return dateTimeFormatter.format(this.dateTime);
	}

	// ゲッター（更新した上で取得）（年/月/日 時:分:秒 のString形式で取得）
	public String updateAndGetDateTimeFormat() {
		updateDateTime();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return dateTimeFormatter.format(this.dateTime);
	}

	// 最新時刻を更新
	public void updateDateTime() {
		this.dateTime = LocalDateTime.now();
	}

	// StringからLocalDateTimeに変換してフィールドにset
	public void setDateTime(String newDate) {
		this.dateTime = LocalDateTime.parse(newDate);
	}

	// StringからLocalDateTimeに変換してフィールドにsetした上で取得
	public LocalDateTime setDateTimeAndGet(String newDate) {
		this.dateTime = LocalDateTime.parse(newDate);
		return getDateTime();
	}

	/*
	 * 	challengeTimes用
	 */

	//	ゲッター
	public int getChallengeTimes() {
		return this.challengeTimes;
	}

	//	セッター
	public void setChallengeTimes(int times) {
		this.challengeTimes = times;
	}

	// 1増やす
	public void incrementChallengeTimes() {
		this.challengeTimes++;
	}

	/*
	 * 	winTimes用
	 */

	//	ゲッター
	public int getWinTimes() {
		return this.winTimes;
	}

	//	セッター
	public void setWinTimes(int times) {
		this.winTimes = times;
	}

	// 1増やす
	public void incrementWinTimes() {
		this.winTimes++;
	}

	/*
	 * 	maxWinCards用
	 */

	//	ゲッター
	public int getMaxWinCards() {
		return this.maxWinCards;
	}

	// セッター
	public void setMaxWinCards(int quantity) {
		this.maxWinCards = quantity;
	}

	/*
	 * 	gameProgressData用
	 */

	//	ゲッター
	public ArrayList<String> getGameProgressData() {
		return this.gameProgressData;
	}

	// セットする
	public void setGameProgressData(ArrayList<String> data) {
		this.gameProgressData = data;
	}

	/*
	 * 	allDeckData用
	 */

	//	ゲッター
	public ArrayList<String> getAllDeckData() {
		return this.allDeckData;
	}

	// セットする
	public void setAllDeckData(ArrayList<String> data) {
		this.allDeckData = data;
	}

	/*
	 * 	記録する処理
	 */

	// 成績を抽出する
	public void getPerformance() {

		// データの格納場所
		int cellOfLatestPlayDay = 0;	// 最後に遊んだ日時が格納されている列
		int cellOfChallengeTimes = 1;	// チャレンジ回数が格納されている列
		int cellOfWinTimes = 2;	// 勝利した回数が格納されている列
		int cellOfMaxWinCards = 3;	// 勝利時の最大カード数が格納されている列
		Path inputPath = Paths.get(PFMC_DIR);	// ファイルのPathを格納

		// ここからファイルを読み込む処理
		if ( Files.exists(inputPath) ) {	// 読み込むファイルが存在する場合
			try (
				// 読み込むテキストファイルを開く
				BufferedReader inputtedFile = new BufferedReader(new FileReader(PFMC_DIR));
				) {

					String line = inputtedFile.readLine();	// 読み込んだデータを1行だけ取得

					// ファイルは存在するが白紙だった場合のためにif文を使用
					if ( line != null ) {
						String[] cellStr = line.split(",", -1);	// lineを[,]の文字で分割する

						// String配列からint配列に変換（1行目はStringのままでいいのでスキップ）
						int[] cellInt = new int[cellStr.length];
						for ( int i = 1; i < cellStr.length; i++ ) {
							cellInt[i] = Integer.parseInt(cellStr[i]);
						}

						// フィールド変数にセット
						setDateTime(cellStr[cellOfLatestPlayDay]);	// ここだけStrをセット
						setChallengeTimes(cellInt[cellOfChallengeTimes]);
						setWinTimes(cellInt[cellOfWinTimes]);
						setMaxWinCards(cellInt[cellOfMaxWinCards]);
						System.out.println("成績データの読み込みが完了しました。");

						displayPerformance();	// 成績を表示する

					// ファイルは存在するが白紙だった場合
					} else {
						System.out.println("過去の成績データはありませんでした。");
					}

			// 例外処理
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		} else {	// 読み込むファイルが存在しなかった場合
			System.out.println("過去の成績データはありませんでした。");
			init();	// 初期化
		}
	}

	// 成績を書き込む
	public void recordPerformance() {

		// ここからファイルを書き込む処理
		try (
			// 出力するためのファイルを開く（なければ新規作成）
			// FileWriterクラスのコンストラクタの第2引数… true：追加書き込み　false：上書き
			PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter(PFMC_DIR, false)));
			) {

			// 成績を[,]区切りで一行にする
			String line = updateAndGetDateTime() + ","
						+ getChallengeTimes() + ","
						+ getWinTimes() + ","
						+ getMaxWinCards();

			outputFile.printf(line);	// 書き込み
			System.out.println("成績データの書き込みが完了しました。");

		// 例外処理
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 成績を表示する
	public void displayPerformance() {
		System.out.println("---------------------------");
		System.out.println("最後にプレイした日時: " + getDateTimeFormat());
		System.out.println("これまでの合計チャレンジ回数: " + this.challengeTimes);
		System.out.println("これまでの合計勝利回数: " + this.winTimes);
		System.out.println("勝利時の最大獲得カード枚数: " + this.maxWinCards);
		System.out.println("---------------------------");
	}

}
