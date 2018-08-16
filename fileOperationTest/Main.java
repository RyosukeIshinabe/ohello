package fileOperationTest;
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
import java.util.Scanner;

// ゲームのプレイヤーのデータ（csv）を読み込み、アイテム付与、ゴールド付与など各種処理を行った後でcsvを出力するプログラムです。

public class Main {

	// 読み込むファイルと保存先のファイル
	private static final String DIRECTORY = System.getProperty("user.home") + "/Desktop/";
	private static String INPUT_FILE_NAME = "game_player.csv";
	private static String OUTPUT_FILE_NAME = "output.csv";
	private static final String INPUT_FILE = DIRECTORY + INPUT_FILE_NAME;
	private static final String OUTPUT_FILE = DIRECTORY + OUTPUT_FILE_NAME;

	// データの格納場所
	static int startItemCell = 7;	// アイテム用のセルが始まる列
	static int endItemCell = 10;	// アイテム用のセルが終わる列
	static int levelCell = 3;	// レベルが格納されてる列
	static int goldCell = 4;	// ゴールドが格納されてる列

	public static void main(String[] args) {
		Path inputPath = Paths.get(INPUT_FILE);	// 読み込むファイルのPathを格納

		if ( Files.exists(inputPath) ) {	// 読み込むファイルが存在する場合
			System.out.println("元ファイルを読み込みました。");

			Path outputPath = Paths.get(OUTPUT_FILE);	// アウトプット先のファイルのPathを作成

			if ( Files.exists(outputPath) ) {	// 同じ名前のファイルがすでに存在した場合
				boolean overwrite = selectOverwrite();	// 上書きするかどうか入力を促してbooleanを取得

				if ( overwrite ) {	// 上書きする場合
					readFile();	// ファイルの読み込みと書き出し
				} else {	// 上書きしない場合
					System.out.println("作業を中断します。");
				}
			} else {	// 同じ名前のファイルが存在しない場合
				readFile();	// ファイルの読み込みと書き出し
			}
		} else {	// 読み込むファイルが存在しなかった場合
			System.out.println("元ファイルが見つかりません。");
		}
	}

	// ファイル読み込み
	public static void readFile() {
		try (
			// 読み込むテキストファイルを開く
		    // バッファリングするI/OストリームBufferedReaderを利用
		    BufferedReader inputtedFile = new BufferedReader(new FileReader(INPUT_FILE));) {
			writeFile(inputtedFile);	// 書き込みメソッドに受け渡し

		    // 例外処理
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}

	// ファイル書き込み
	public static void writeFile(BufferedReader inputtedFile) {
		try (
		    // 出力するためのファイルを開く
		    // バッファリング出力をするためのI/Oストリームを利用
			// FileWriterクラスのコンストラクタの第2引数… true：追加書き込み　false：上書き
			PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE, false)));
		    ) {

			// breakかcatchしない限りこの中を繰り返す
			while (true) {
		        String line = inputtedFile.readLine();	// 読み込んだデータを1行ずつ取得
		        if (line == null) {	// データが読み込めなければ終了
		        	break;
		        }
		        line = process(line);	// 行ごとに各処理を行う
		        outputFile.printf(line + "%n");	// 1行ずつ書き込み
		    }
			System.out.println("全ての書き込みが完了しました。");

			// 例外処理
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}

	// 上書きするかどうかの選択メソッド
	public static boolean selectOverwrite() {
		Scanner scan = new Scanner(System.in);	// 入力用クラス
		System.out.println("すでにファイルが存在しています。上書きしますか？");
		System.out.println("[y] YES / [n] NO");

		// falseかtrueをreturnしない限りこの中を繰り返す
		while ( true ) {
			String inputStr = scan.next();	// 入力を代入
			if ( inputStr.equals("y") || inputStr.equals("Y") ) {
				scan.close();
				return true;
			} else if ( inputStr.equals("n") || inputStr.equals("N") ) {
				scan.close();
				return false;
			} else {
				System.out.println("入力が正しくありません。");
				continue;
			}
		}
	}

	// String型の数字にint型を足してString型で返す
	public static String strPlusInt(String base, int add) {
		int sum = Integer.parseInt(base) + add;	// String型のbaseをintに変換してaddを加算
		return String.valueOf(sum);	// int型のsumをStringに変換してreturn
	}

	// 1行ずつ受け取って、各処理メソッドに渡す
	public static String process(String line) {

		line = giveItemForBeginner(line);	// 初心者アイテム付与
		line = giveGoldForBeginner(line);	// 初心者支援ゴールド付与
		line = exchangeItem(line);	// スペシャルアイテム交換

		return line;
	}

	// 処理１：レベルが1のプレイヤーに「応援旗」を追加。
    // もしすでにアイテムを4個所持している場合は、代わりに所持金にゴールドを加算する。
	public static String giveItemForBeginner(String line) {

		final String ITEM = "応援旗";		// 付与するアイテム
        final int GOIDBONUS = 100;	// 与えるゴールド
        String newline = "";	// 付与した後returnするためのString
        String[] split = line.split(",", -1);	// 引数で受け取ったStringを[,]の文字で分割する
        int splitLength = split.length;	// 列数を保存しておく

        if ( split[levelCell].equals("1") ) {	// レベルが1の場合
        	for ( int i = startItemCell; i < endItemCell; i++ ) {	// アイテム用のセルを順番に精査
        		if ( split[i].equals("") ) {	// 空白セルが見つかれば
        			split[i] = ITEM;	// そこにアイテムを付与して
        			break;	// for文を抜ける
        		}
	        	if ( i == endItemCell - 1 ) {	// 最後のセルもbreakしなかった（空白がなかった）場合
	        		split[goldCell] = strPlusInt(split[goldCell], GOIDBONUS);	// ゴールド付与
	        	}
        	}
        }

        // セルを行として再結合する
        newline = split[0];
        for ( int j = 1; j < splitLength; j++ ) {
        	newline = newline + "," + split[j];
        }
        return newline;
	}

	// 処理２：レベルが10以下のプレイヤーにゴールドを加算する。
	public static String giveGoldForBeginner(String line) {

		final int THRESHOLD = 10;	// レベルの閾値
		final int GOIDBONUS = 100;	// 与えるゴールド
		String newline = "";	// 付与した後returnするためのString
        String[] split = line.split(",", -1);	// 引数で受け取ったStringを[,]の文字で分割する
        int splitLength = split.length;	// 列数を保存しておく

        int level = Integer.parseInt(split[levelCell]);	// レベルをint型に変換
        if ( level <= THRESHOLD ) {	// レベルがTHRESHOLD以下の場合
        	split[goldCell] = strPlusInt(split[goldCell], GOIDBONUS);	// ゴールド付与
        }

        // セルを行として再結合する
        newline = split[0];
        for ( int j = 1; j < splitLength; j++ ) {
        	newline = newline + "," + split[j];
        }
        return newline;
	}

	// 処理３：アイテム「鋼玉」を持っている場合は、「スペシャルソード」と交換する。
	public static String exchangeItem(String line) {

		final String TAKEITEM = "鋼玉";	// 交換する前のアイテム
		final String GIVEITEM = "スペシャルソード";	// 交換した後のアイテム
		String newline = "";	// 付与した後returnするためのString
        String[] split = line.split(",", -1);	// 引数で受け取ったStringを[,]の文字で分割する
        int splitLength = split.length;	// 列数を保存しておく

        for ( int i = startItemCell; i <= endItemCell; i++ ) {	// アイテム用のセルを順番に精査
    		if ( split[i].equals(TAKEITEM) ) {	// TAKEITEMのアイテムが見つかれば
    			split[i] = GIVEITEM;	// アイテムを交換して
    			break;	// for文を抜ける
        	}
    	}

        // セルを行として再結合する
        newline = split[0];
        for ( int j = 1; j < splitLength; j++ ) {
        	newline = newline + "," + split[j];
        }
        return newline;
	}

}
