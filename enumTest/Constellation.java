package enumTest;
import java.time.MonthDay;

    // 列挙型
    public enum Constellation {
        Aries("牡羊座", MonthDay.of(3, 20), MonthDay.of(4, 20)),
        Taurus("牡牛座", MonthDay.of(4, 19), MonthDay.of(5, 21)),
        Gemini("双子座", MonthDay.of(5, 20), MonthDay.of(6, 22)),
        Cancer("蟹座", MonthDay.of(6, 21), MonthDay.of(7, 23)),
        Leo("獅子座", MonthDay.of(7, 22), MonthDay.of(8, 23)),
        Virgo("乙女座", MonthDay.of(8, 22), MonthDay.of(9, 23)),
        Libra("天秤座", MonthDay.of(9, 22), MonthDay.of(10, 24)),
        Scorpio("蠍座", MonthDay.of(10, 23), MonthDay.of(11, 23)),
        Sagittarius("射手座", MonthDay.of(11, 22), MonthDay.of(12, 22)),
        Capricorn("山羊座", MonthDay.of(12, 21), MonthDay.of(1, 20)),
        Aquarius("水瓶座", MonthDay.of(1, 19), MonthDay.of(2, 19)),
        Pisces("魚座", MonthDay.of(2, 18), MonthDay.of(3, 21));

        private String name;
        private MonthDay start;
        private MonthDay end;

        // コンストラクタ（必須）
        private Constellation(String name, MonthDay start, MonthDay end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }

        // ゲッター
        public String getName() {
            return this.name;
        }

        // 引数argMonthDayを元に星座を特定
        public static String getType(MonthDay argMonthDay) {
            if ( argMonthDay.isAfter(Aries.start) && argMonthDay.isBefore(Aries.end) ) {
                return Aries.name;
            } else if ( argMonthDay.isAfter(Taurus.start) && argMonthDay.isBefore(Taurus.end) ) {
                return Taurus.name;
            } else if ( argMonthDay.isAfter(Gemini.start) && argMonthDay.isBefore(Gemini.end) ) {
                return Gemini.name;
            } else if ( argMonthDay.isAfter(Cancer.start) && argMonthDay.isBefore(Cancer.end) ) {
                return Cancer.name;
            } else if ( argMonthDay.isAfter(Leo.start) && argMonthDay.isBefore(Leo.end) ) {
                return Leo.name;
            } else if ( argMonthDay.isAfter(Virgo.start) && argMonthDay.isBefore(Virgo.end) ) {
                return Virgo.name;
            } else if ( argMonthDay.isAfter(Libra.start) && argMonthDay.isBefore(Libra.end) ) {
                return Libra.name;
            } else if ( argMonthDay.isAfter(Scorpio.start) && argMonthDay.isBefore(Scorpio.end) ) {
                return Scorpio.name;
            } else if ( argMonthDay.isAfter(Sagittarius.start) && argMonthDay.isBefore(Sagittarius.end) ) {
                return Sagittarius.name;
            } else if ( argMonthDay.isAfter(Capricorn.start) || argMonthDay.isBefore(Capricorn.end) ) {
                return Capricorn.name;
            } else if ( argMonthDay.isAfter(Aquarius.start) && argMonthDay.isBefore(Aquarius.end) ) {
                return Aquarius.name;
            } else if ( argMonthDay.isAfter(Pisces.start) && argMonthDay.isBefore(Pisces.end) ) {
                return Pisces.name;
            } else {
            	return "無効な日付";
            }
        }


}
