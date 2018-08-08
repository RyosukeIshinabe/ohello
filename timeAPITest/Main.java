package timeAPITest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

// 1900〜2100年の間で年、及び月の数字をそれぞれ半角で入力させ、その年月のカレンダーを表示するプログラムです。

public class Main {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);   // スキャナークラス作成

		// ユーザーの入力を受け取る
        int inputYear;
        do {
        	System.out.println("年を入力してください（1900〜2100）> ");
            inputYear = scanner.nextInt();
        } while ( !checkInputYear(inputYear) );

        int inputMonth;
        do {
        	System.out.println("月を入力してください（1〜12）> ");
            inputMonth = scanner.nextInt();
        } while ( !checkInputMonth(inputMonth) );

        // 入力された年/月を受け取ってinputYearMonth型に変換（月よりも細かい指標は適当に入力）
        LocalDateTime inputYearMonth = LocalDateTime.of(inputYear, inputMonth, 1, 1, 1, 1);

        // カレンダー表示
        displayCalendar(inputYearMonth);

        scanner.close();

	}

    // 入力された年が正しいかチェック
    public static boolean checkInputYear(int year) {
    	if ( 1900 <= year && year <= 2100 ) {
    		return true;
    	}
    	return false;
    }

	// 入力された月が正しいかチェック
    public static boolean checkInputMonth(int month) {
    	if ( 1 <= month && month <= 12 ) {
    		return true;
    	}
    	return false;
    }

    // その月のカレンダーを表示
    public static void displayCalendar(LocalDateTime start) {

    	// 表示フォーマットの設定をdateTimeFormatterに保存
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日（EE）", Locale.JAPANESE);
    	// スタートの月を取っておく
    	int startMonth = start.getMonthValue();

    	do {
    		// yyyy年mm月dd日（曜日）を表示
    		System.out.println(start.format(dateTimeFormatter));
    		// 引数startに1日をプラスしたもの（要は翌日）をstartに代入し直す
    		start = start.plusDays(1);
    	// 翌日の月が、最初に取っておいた月と同じ場合繰り返す
    	} while ( startMonth == start.getMonthValue() );
    }

}
