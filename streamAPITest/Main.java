package streamAPITest;
import java.util.ArrayList;
import java.util.List;

// 文章から単語を取り出し、指定文字数以上の単語だけを表示するプログラムです
// 今回は「匿名クラス」「ラムダ式」「Stream API」を使って実装してみます

public class Main {
	public static void main(String[] args) {

		final int MAXLENGTH = 10;	// 何文字以上にするか

		String text =
			    "Alice was beginning to get very tired of sitting by "
			    + "her sister on the bank, and of having nothing to do: once or "
			    + "twice she had peeped into the book her sister was reading, but "
			    + "it had no pictures or conversations in it, `and what is the use "
			    + "of a book,\' thought Alice `without pictures or conversation?\'\n"
			    + "So she was considering in her own mind (as well as she could, "
			    + "for the hot day made her feel very sleepy and stupid), whether "
			    + "the pleasure of making a daisy-chain would be worth the trouble "
			    + "of getting up and picking the daisies, when suddenly a White "
			    + "Rabbit with pink eyes ran close by her.\nThere was nothing so "
			    + "very remarkable in that; nor did Alice think it so very much "
			    + "out of the way to hear the Rabbit say to itself, `Oh dear! "
			    + "Oh dear! I shall be late!\' (when she thought it over afterwards, "
			    + "it occurred to her that she ought to have wondered at this, but "
			    + "at the time it all seemed quite natural); but when the Rabbit "
			    + "actually took a watch out of its waistcoat-pocket, and looked "
			    + "at it, and then hurried on, Alice started to her feet, for it "
			    + "flashed across her mind that she had never before seen a rabbit "
			    + "with either a waistcoat-pocket, or a watch to take out of it, "
			    + "and burning with curiosity, she ran across the field after it, "
			    + "and fortunately was just in time to see it pop down a large "
			    + "rabbit-hole under the hedge.";

		// 英単語を構成する文字(a~z, A~Z, _) 以外の文字で分割する
		String[] wordArray = text.split("\\W+");

//		// ① なんの省略もなく実装した場合
//		// インターフェース「CountCharacters」を実装した「countCharactersFunction（匿名クラス）」を作成
//		// 匿名クラスが使えるのは抽象メソッドが1つだけの場合のみ
//		CountCharacters countCharactersFunction = new CountCharacters() {
//
//			@Override	// インターフェース内の「countCharacters」メソッドをオーバーライド
//			public void countCharacters(String[] wordList) {
//				for ( int i = 0; i < wordList.length; i++ ) {
//					if ( wordList[i].length() >= MAXLENGTH ) {
//						System.out.println(wordList[i]);
//					}
//				}
//			}
//		};
//		// 匿名クラス「countCharactersFunction」の「countCharacters」メソッドを実行
//		// コメント外す時は抽象クラスの引数の型も直してください
//		countCharactersFunction.countCharacters(wordArray);

//		// ② 1をラムダ式で書いてみた場合
//		// new CountCharacters() は省略可能
//		// 引数の型と、引数を囲む()も省略可能
//		// 処理部分が1文の時は{}も省略できるらしい（今回は無理）
//		CountCharacters countCharactersFunction = tmplList -> {
//			for ( int i = 0; i < tmplList.length; i++ ) {
//				if ( tmplList[i].length() >= MAXLENGTH ) {
//					System.out.println(tmplList[i] + tmplList[i].length());
//				}
//			}
//		};
//		// 匿名クラス「countCharactersFunction」の「countCharacters」メソッドを実行
//		// コメント外す時は抽象クラスの引数の型も直してください
//		countCharactersFunction.countCharacters(wordArray);

		// ③ 2をStream APIを使ってみた場合
		// Stream APIはListで使うため、一旦String[]からListに変換
		List<String> wordList = new ArrayList<>();
		for ( int i = 0; i < wordArray.length; i++ ) {
			wordList.add(wordArray[i]);
		}

		CountCharacters countCharactersFunction = tmplList -> {
			wordList.stream()	// Stream APIを使用
				.filter(printWord -> printWord.length() >= MAXLENGTH)	// 指定文字数以上の単語だけを
				.distinct() // 重複を取り除いた上で
				.forEach(printWord -> System.out.println(printWord + printWord.length()));	// 全て表示
		};

		// 匿名クラス「countCharactersFunction」の「countCharacters」メソッドを実行
		countCharactersFunction.countCharacters(wordList);
	}
}
