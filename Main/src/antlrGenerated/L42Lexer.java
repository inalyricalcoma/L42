// Generated from L42.g4 by ANTLR 4.2.2
package antlrGenerated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class L42Lexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		S=1, Mdf=2, ORoundNoSpace=3, ORoundSpace=4, CRound=5, OSquare=6, CSquare=7, 
		OCurly=8, DotDotDot=9, EndType=10, CCurly=11, Colon=12, Semicolon=13, 
		Dot=14, Equal=15, Ph=16, Implements=17, Case=18, If=19, Else=20, While=21, 
		Loop=22, With=23, On=24, In=25, Catch=26, Var=27, Default=28, Interface=29, 
		Method=30, Using=31, Check=32, FieldSpecial=33, WalkBy=34, Stage=35, Path=36, 
		ClassSep=37, X=38, HashX=39, StringQuote=40, UrlNL=41, Url=42, Doc=43, 
		WS=44, UnOp=45, EqOp=46, BoolOp=47, RelOp=48, DataOp=49, NumParse=50;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"S", "Mdf", "'('", "'\t'", "')'", "'['", "']'", "'{'", "'...'", "'^##'", 
		"'}'", "':'", "';'", "'.'", "'='", "'fwd'", "'<:'", "'case'", "'if'", 
		"'else'", "'while'", "'loop'", "'with'", "'on'", "'in'", "'catch'", "'var'", 
		"'default'", "'interface'", "'method'", "'using'", "'check'", "'##field'", 
		"'##walkBy'", "Stage", "Path", "'::'", "X", "HashX", "StringQuote", "UrlNL", 
		"Url", "Doc", "WS", "UnOp", "EqOp", "BoolOp", "RelOp", "DataOp", "NumParse"
	};
	public static final String[] ruleNames = {
		"Uppercase", "Lowercase", "Digit", "C", "CharsAll", "CharsUrl", "CharsAllStrLine", 
		"CharsAllString", "StrLine", "String", "NumNext", "S", "Mdf", "ORoundNoSpace", 
		"ORoundSpace", "CRound", "OSquare", "CSquare", "OCurly", "DotDotDot", 
		"EndType", "CCurly", "Colon", "Semicolon", "Dot", "Equal", "Ph", "Implements", 
		"Case", "If", "Else", "While", "Loop", "With", "On", "In", "Catch", "Var", 
		"Default", "Interface", "Method", "Using", "Check", "FieldSpecial", "WalkBy", 
		"Stage", "Path", "ClassSep", "X", "HashX", "StringQuote", "UrlNL", "Url", 
		"Doc", "WS", "UnOp", "EqOp", "BoolOp", "RelOp", "DataOp", "NumParse"
	};


	public L42Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "L42.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\64\u023b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\7\5\u0088\n\5\f\5\16\5"+
		"\u008b\13\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\7\n\u0097\n\n\f\n"+
		"\16\n\u009a\13\n\3\n\3\n\3\13\7\13\u009f\n\13\f\13\16\13\u00a2\13\13\3"+
		"\f\3\f\3\f\3\f\3\f\5\f\u00a9\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00bf\n\r\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\5\16\u00d7\n\16\3\17\3\17\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34"+
		"\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3 \3"+
		" \3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3%\3%"+
		"\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3)\3)\3"+
		")\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3,\3,\3"+
		",\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3.\3.\3.\3/\3/\3"+
		"/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3"+
		"/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\5/\u018b\n/\3\60\3\60"+
		"\3\60\3\60\7\60\u0191\n\60\f\60\16\60\u0194\13\60\3\61\3\61\3\61\3\62"+
		"\3\62\3\62\3\62\3\62\7\62\u019e\n\62\f\62\16\62\u01a1\13\62\3\63\3\63"+
		"\3\63\3\63\3\63\7\63\u01a8\n\63\f\63\16\63\u01ab\13\63\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\7\64\u01b3\n\64\f\64\16\64\u01b6\13\64\3\64\3\64\7\64"+
		"\u01ba\n\64\f\64\16\64\u01bd\13\64\3\64\6\64\u01c0\n\64\r\64\16\64\u01c1"+
		"\3\64\7\64\u01c5\n\64\f\64\16\64\u01c8\13\64\3\64\3\64\5\64\u01cc\n\64"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\65\6\65\u01d5\n\65\r\65\16\65\u01d6\3"+
		"\65\6\65\u01da\n\65\r\65\16\65\u01db\3\65\3\65\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\6\66\u01e7\n\66\r\66\16\66\u01e8\3\66\6\66\u01ec\n\66\r\66"+
		"\16\66\u01ed\3\67\3\67\5\67\u01f2\n\67\6\67\u01f4\n\67\r\67\16\67\u01f5"+
		"\38\68\u01f9\n8\r8\168\u01fa\38\38\39\39\3:\3:\3:\3:\3:\3:\3:\3:\3:\3"+
		":\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\3:\5:\u021b\n:\3;\3;\3"+
		"<\3<\3<\3<\3<\3<\3<\3<\3<\5<\u0228\n<\3=\3=\3=\3=\3=\3=\3=\3=\3=\5=\u0233"+
		"\n=\3>\3>\7>\u0237\n>\f>\16>\u023a\13>\2\2?\3\2\5\2\7\2\t\2\13\2\r\2\17"+
		"\2\21\2\23\2\25\2\27\2\31\3\33\4\35\5\37\6!\7#\b%\t\'\n)\13+\f-\r/\16"+
		"\61\17\63\20\65\21\67\229\23;\24=\25?\26A\27C\30E\31G\32I\33K\34M\35O"+
		"\36Q\37S U!W\"Y#[$]%_&a\'c(e)g*i+k,m-o.q/s\60u\61w\62y\63{\64\3\2\r\4"+
		"\2&\'C\\\4\2aac|\5\2\"#%(*\u0080\7\2\"#%(*|~~\u0080\u0080\4\2\"#%\u0080"+
		"\4\2\"\"..\5\2\f\f\"\"..\4\2##\u0080\u0080\4\2((~~\4\2>>@@\5\2,-//\61"+
		"\61\u0269\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S"+
		"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2"+
		"\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2"+
		"\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y"+
		"\3\2\2\2\2{\3\2\2\2\3}\3\2\2\2\5\177\3\2\2\2\7\u0081\3\2\2\2\t\u0083\3"+
		"\2\2\2\13\u008c\3\2\2\2\r\u008e\3\2\2\2\17\u0090\3\2\2\2\21\u0092\3\2"+
		"\2\2\23\u0094\3\2\2\2\25\u00a0\3\2\2\2\27\u00a8\3\2\2\2\31\u00be\3\2\2"+
		"\2\33\u00d6\3\2\2\2\35\u00d8\3\2\2\2\37\u00da\3\2\2\2!\u00dc\3\2\2\2#"+
		"\u00de\3\2\2\2%\u00e0\3\2\2\2\'\u00e2\3\2\2\2)\u00e4\3\2\2\2+\u00e8\3"+
		"\2\2\2-\u00ec\3\2\2\2/\u00ee\3\2\2\2\61\u00f0\3\2\2\2\63\u00f2\3\2\2\2"+
		"\65\u00f4\3\2\2\2\67\u00f6\3\2\2\29\u00fa\3\2\2\2;\u00fd\3\2\2\2=\u0102"+
		"\3\2\2\2?\u0105\3\2\2\2A\u010a\3\2\2\2C\u0110\3\2\2\2E\u0115\3\2\2\2G"+
		"\u011a\3\2\2\2I\u011d\3\2\2\2K\u0120\3\2\2\2M\u0126\3\2\2\2O\u012a\3\2"+
		"\2\2Q\u0132\3\2\2\2S\u013c\3\2\2\2U\u0143\3\2\2\2W\u0149\3\2\2\2Y\u014f"+
		"\3\2\2\2[\u0157\3\2\2\2]\u018a\3\2\2\2_\u018c\3\2\2\2a\u0195\3\2\2\2c"+
		"\u0198\3\2\2\2e\u01a2\3\2\2\2g\u01cb\3\2\2\2i\u01cd\3\2\2\2k\u01df\3\2"+
		"\2\2m\u01f3\3\2\2\2o\u01f8\3\2\2\2q\u01fe\3\2\2\2s\u021a\3\2\2\2u\u021c"+
		"\3\2\2\2w\u0227\3\2\2\2y\u0232\3\2\2\2{\u0234\3\2\2\2}~\t\2\2\2~\4\3\2"+
		"\2\2\177\u0080\t\3\2\2\u0080\6\3\2\2\2\u0081\u0082\4\62;\2\u0082\b\3\2"+
		"\2\2\u0083\u0089\5\3\2\2\u0084\u0088\5\3\2\2\u0085\u0088\5\5\3\2\u0086"+
		"\u0088\5\7\4\2\u0087\u0084\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0086\3\2"+
		"\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a"+
		"\n\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008d\t\4\2\2\u008d\f\3\2\2\2\u008e"+
		"\u008f\t\5\2\2\u008f\16\3\2\2\2\u0090\u0091\4\"\u0080\2\u0091\20\3\2\2"+
		"\2\u0092\u0093\t\6\2\2\u0093\22\3\2\2\2\u0094\u0098\7)\2\2\u0095\u0097"+
		"\5\17\b\2\u0096\u0095\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2"+
		"\u0098\u0099\3\2\2\2\u0099\u009b\3\2\2\2\u009a\u0098\3\2\2\2\u009b\u009c"+
		"\7\f\2\2\u009c\24\3\2\2\2\u009d\u009f\5\21\t\2\u009e\u009d\3\2\2\2\u009f"+
		"\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\26\3\2\2"+
		"\2\u00a2\u00a0\3\2\2\2\u00a3\u00a9\5\7\4\2\u00a4\u00a5\7/\2\2\u00a5\u00a9"+
		"\5\7\4\2\u00a6\u00a7\7\60\2\2\u00a7\u00a9\5\7\4\2\u00a8\u00a3\3\2\2\2"+
		"\u00a8\u00a4\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\30\3\2\2\2\u00aa\u00ab"+
		"\7t\2\2\u00ab\u00ac\7g\2\2\u00ac\u00ad\7v\2\2\u00ad\u00ae\7w\2\2\u00ae"+
		"\u00af\7t\2\2\u00af\u00bf\7p\2\2\u00b0\u00b1\7g\2\2\u00b1\u00b2\7t\2\2"+
		"\u00b2\u00b3\7t\2\2\u00b3\u00b4\7q\2\2\u00b4\u00bf\7t\2\2\u00b5\u00b6"+
		"\7g\2\2\u00b6\u00b7\7z\2\2\u00b7\u00b8\7e\2\2\u00b8\u00b9\7g\2\2\u00b9"+
		"\u00ba\7r\2\2\u00ba\u00bb\7v\2\2\u00bb\u00bc\7k\2\2\u00bc\u00bd\7q\2\2"+
		"\u00bd\u00bf\7p\2\2\u00be\u00aa\3\2\2\2\u00be\u00b0\3\2\2\2\u00be\u00b5"+
		"\3\2\2\2\u00bf\32\3\2\2\2\u00c0\u00c1\7v\2\2\u00c1\u00c2\7{\2\2\u00c2"+
		"\u00c3\7r\2\2\u00c3\u00d7\7g\2\2\u00c4\u00c5\7o\2\2\u00c5\u00c6\7w\2\2"+
		"\u00c6\u00d7\7v\2\2\u00c7\u00c8\7t\2\2\u00c8\u00c9\7g\2\2\u00c9\u00ca"+
		"\7c\2\2\u00ca\u00d7\7f\2\2\u00cb\u00cc\7n\2\2\u00cc\u00cd\7g\2\2\u00cd"+
		"\u00ce\7p\2\2\u00ce\u00d7\7v\2\2\u00cf\u00d0\7e\2\2\u00d0\u00d1\7c\2\2"+
		"\u00d1\u00d2\7r\2\2\u00d2\u00d3\7u\2\2\u00d3\u00d4\7w\2\2\u00d4\u00d5"+
		"\7n\2\2\u00d5\u00d7\7g\2\2\u00d6\u00c0\3\2\2\2\u00d6\u00c4\3\2\2\2\u00d6"+
		"\u00c7\3\2\2\2\u00d6\u00cb\3\2\2\2\u00d6\u00cf\3\2\2\2\u00d7\34\3\2\2"+
		"\2\u00d8\u00d9\7*\2\2\u00d9\36\3\2\2\2\u00da\u00db\7\13\2\2\u00db \3\2"+
		"\2\2\u00dc\u00dd\7+\2\2\u00dd\"\3\2\2\2\u00de\u00df\7]\2\2\u00df$\3\2"+
		"\2\2\u00e0\u00e1\7_\2\2\u00e1&\3\2\2\2\u00e2\u00e3\7}\2\2\u00e3(\3\2\2"+
		"\2\u00e4\u00e5\7\60\2\2\u00e5\u00e6\7\60\2\2\u00e6\u00e7\7\60\2\2\u00e7"+
		"*\3\2\2\2\u00e8\u00e9\7`\2\2\u00e9\u00ea\7%\2\2\u00ea\u00eb\7%\2\2\u00eb"+
		",\3\2\2\2\u00ec\u00ed\7\177\2\2\u00ed.\3\2\2\2\u00ee\u00ef\7<\2\2\u00ef"+
		"\60\3\2\2\2\u00f0\u00f1\7=\2\2\u00f1\62\3\2\2\2\u00f2\u00f3\7\60\2\2\u00f3"+
		"\64\3\2\2\2\u00f4\u00f5\7?\2\2\u00f5\66\3\2\2\2\u00f6\u00f7\7h\2\2\u00f7"+
		"\u00f8\7y\2\2\u00f8\u00f9\7f\2\2\u00f98\3\2\2\2\u00fa\u00fb\7>\2\2\u00fb"+
		"\u00fc\7<\2\2\u00fc:\3\2\2\2\u00fd\u00fe\7e\2\2\u00fe\u00ff\7c\2\2\u00ff"+
		"\u0100\7u\2\2\u0100\u0101\7g\2\2\u0101<\3\2\2\2\u0102\u0103\7k\2\2\u0103"+
		"\u0104\7h\2\2\u0104>\3\2\2\2\u0105\u0106\7g\2\2\u0106\u0107\7n\2\2\u0107"+
		"\u0108\7u\2\2\u0108\u0109\7g\2\2\u0109@\3\2\2\2\u010a\u010b\7y\2\2\u010b"+
		"\u010c\7j\2\2\u010c\u010d\7k\2\2\u010d\u010e\7n\2\2\u010e\u010f\7g\2\2"+
		"\u010fB\3\2\2\2\u0110\u0111\7n\2\2\u0111\u0112\7q\2\2\u0112\u0113\7q\2"+
		"\2\u0113\u0114\7r\2\2\u0114D\3\2\2\2\u0115\u0116\7y\2\2\u0116\u0117\7"+
		"k\2\2\u0117\u0118\7v\2\2\u0118\u0119\7j\2\2\u0119F\3\2\2\2\u011a\u011b"+
		"\7q\2\2\u011b\u011c\7p\2\2\u011cH\3\2\2\2\u011d\u011e\7k\2\2\u011e\u011f"+
		"\7p\2\2\u011fJ\3\2\2\2\u0120\u0121\7e\2\2\u0121\u0122\7c\2\2\u0122\u0123"+
		"\7v\2\2\u0123\u0124\7e\2\2\u0124\u0125\7j\2\2\u0125L\3\2\2\2\u0126\u0127"+
		"\7x\2\2\u0127\u0128\7c\2\2\u0128\u0129\7t\2\2\u0129N\3\2\2\2\u012a\u012b"+
		"\7f\2\2\u012b\u012c\7g\2\2\u012c\u012d\7h\2\2\u012d\u012e\7c\2\2\u012e"+
		"\u012f\7w\2\2\u012f\u0130\7n\2\2\u0130\u0131\7v\2\2\u0131P\3\2\2\2\u0132"+
		"\u0133\7k\2\2\u0133\u0134\7p\2\2\u0134\u0135\7v\2\2\u0135\u0136\7g\2\2"+
		"\u0136\u0137\7t\2\2\u0137\u0138\7h\2\2\u0138\u0139\7c\2\2\u0139\u013a"+
		"\7e\2\2\u013a\u013b\7g\2\2\u013bR\3\2\2\2\u013c\u013d\7o\2\2\u013d\u013e"+
		"\7g\2\2\u013e\u013f\7v\2\2\u013f\u0140\7j\2\2\u0140\u0141\7q\2\2\u0141"+
		"\u0142\7f\2\2\u0142T\3\2\2\2\u0143\u0144\7w\2\2\u0144\u0145\7u\2\2\u0145"+
		"\u0146\7k\2\2\u0146\u0147\7p\2\2\u0147\u0148\7i\2\2\u0148V\3\2\2\2\u0149"+
		"\u014a\7e\2\2\u014a\u014b\7j\2\2\u014b\u014c\7g\2\2\u014c\u014d\7e\2\2"+
		"\u014d\u014e\7m\2\2\u014eX\3\2\2\2\u014f\u0150\7%\2\2\u0150\u0151\7%\2"+
		"\2\u0151\u0152\7h\2\2\u0152\u0153\7k\2\2\u0153\u0154\7g\2\2\u0154\u0155"+
		"\7n\2\2\u0155\u0156\7f\2\2\u0156Z\3\2\2\2\u0157\u0158\7%\2\2\u0158\u0159"+
		"\7%\2\2\u0159\u015a\7y\2\2\u015a\u015b\7c\2\2\u015b\u015c\7n\2\2\u015c"+
		"\u015d\7m\2\2\u015d\u015e\7D\2\2\u015e\u015f\7{\2\2\u015f\\\3\2\2\2\u0160"+
		"\u0161\7%\2\2\u0161\u0162\7%\2\2\u0162\u0163\7n\2\2\u0163\u0164\7g\2\2"+
		"\u0164\u0165\7u\2\2\u0165\u018b\7u\2\2\u0166\u0167\7%\2\2\u0167\u0168"+
		"\7%\2\2\u0168\u0169\7o\2\2\u0169\u016a\7g\2\2\u016a\u016b\7v\2\2\u016b"+
		"\u018b\7c\2\2\u016c\u016d\7%\2\2\u016d\u016e\7%\2\2\u016e\u016f\7r\2\2"+
		"\u016f\u0170\7n\2\2\u0170\u0171\7w\2\2\u0171\u018b\7u\2\2\u0172\u0173"+
		"\7%\2\2\u0173\u0174\7%\2\2\u0174\u0175\7u\2\2\u0175\u0176\7v\2\2\u0176"+
		"\u0177\7c\2\2\u0177\u018b\7t\2\2\u0178\u0179\7%\2\2\u0179\u017a\7%\2\2"+
		"\u017a\u017b\7p\2\2\u017b\u017c\7g\2\2\u017c\u017d\7g\2\2\u017d\u017e"+
		"\7f\2\2\u017e\u017f\7c\2\2\u017f\u0180\7d\2\2\u0180\u0181\7n\2\2\u0181"+
		"\u018b\7g\2\2\u0182\u0183\7%\2\2\u0183\u0184\7%\2\2\u0184\u0185\7p\2\2"+
		"\u0185\u0186\7g\2\2\u0186\u0187\7g\2\2\u0187\u0188\7f\2\2\u0188\u0189"+
		"\7g\2\2\u0189\u018b\7f\2\2\u018a\u0160\3\2\2\2\u018a\u0166\3\2\2\2\u018a"+
		"\u016c\3\2\2\2\u018a\u0172\3\2\2\2\u018a\u0178\3\2\2\2\u018a\u0182\3\2"+
		"\2\2\u018b^\3\2\2\2\u018c\u0192\5\t\5\2\u018d\u018e\5a\61\2\u018e\u018f"+
		"\5\t\5\2\u018f\u0191\3\2\2\2\u0190\u018d\3\2\2\2\u0191\u0194\3\2\2\2\u0192"+
		"\u0190\3\2\2\2\u0192\u0193\3\2\2\2\u0193`\3\2\2\2\u0194\u0192\3\2\2\2"+
		"\u0195\u0196\7<\2\2\u0196\u0197\7<\2\2\u0197b\3\2\2\2\u0198\u019f\5\5"+
		"\3\2\u0199\u019e\5\3\2\2\u019a\u019e\5\5\3\2\u019b\u019e\5\7\4\2\u019c"+
		"\u019e\7%\2\2\u019d\u0199\3\2\2\2\u019d\u019a\3\2\2\2\u019d\u019b\3\2"+
		"\2\2\u019d\u019c\3\2\2\2\u019e\u01a1\3\2\2\2\u019f\u019d\3\2\2\2\u019f"+
		"\u01a0\3\2\2\2\u01a0d\3\2\2\2\u01a1\u019f\3\2\2\2\u01a2\u01a9\7%\2\2\u01a3"+
		"\u01a8\5\3\2\2\u01a4\u01a8\5\5\3\2\u01a5\u01a8\5\7\4\2\u01a6\u01a8\7%"+
		"\2\2\u01a7\u01a3\3\2\2\2\u01a7\u01a4\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a7"+
		"\u01a6\3\2\2\2\u01a8\u01ab\3\2\2\2\u01a9\u01a7\3\2\2\2\u01a9\u01aa\3\2"+
		"\2\2\u01aaf\3\2\2\2\u01ab\u01a9\3\2\2\2\u01ac\u01ad\7$\2\2\u01ad\u01ae"+
		"\5\25\13\2\u01ae\u01af\7$\2\2\u01af\u01cc\3\2\2\2\u01b0\u01b4\7$\2\2\u01b1"+
		"\u01b3\t\7\2\2\u01b2\u01b1\3\2\2\2\u01b3\u01b6\3\2\2\2\u01b4\u01b2\3\2"+
		"\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01b7\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b7"+
		"\u01bf\7\f\2\2\u01b8\u01ba\t\7\2\2\u01b9\u01b8\3\2\2\2\u01ba\u01bd\3\2"+
		"\2\2\u01bb\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01be\3\2\2\2\u01bd"+
		"\u01bb\3\2\2\2\u01be\u01c0\5\23\n\2\u01bf\u01bb\3\2\2\2\u01c0\u01c1\3"+
		"\2\2\2\u01c1\u01bf\3\2\2\2\u01c1\u01c2\3\2\2\2\u01c2\u01c6\3\2\2\2\u01c3"+
		"\u01c5\t\7\2\2\u01c4\u01c3\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2"+
		"\2\2\u01c6\u01c7\3\2\2\2\u01c7\u01c9\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9"+
		"\u01ca\7$\2\2\u01ca\u01cc\3\2\2\2\u01cb\u01ac\3\2\2\2\u01cb\u01b0\3\2"+
		"\2\2\u01cch\3\2\2\2\u01cd\u01ce\7t\2\2\u01ce\u01cf\7g\2\2\u01cf\u01d0"+
		"\7w\2\2\u01d0\u01d1\7u\2\2\u01d1\u01d2\7g\2\2\u01d2\u01d4\3\2\2\2\u01d3"+
		"\u01d5\7\"\2\2\u01d4\u01d3\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d4\3\2"+
		"\2\2\u01d6\u01d7\3\2\2\2\u01d7\u01d9\3\2\2\2\u01d8\u01da\5\r\7\2\u01d9"+
		"\u01d8\3\2\2\2\u01da\u01db\3\2\2\2\u01db\u01d9\3\2\2\2\u01db\u01dc\3\2"+
		"\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01de\7\f\2\2\u01dej\3\2\2\2\u01df\u01e0"+
		"\7t\2\2\u01e0\u01e1\7g\2\2\u01e1\u01e2\7w\2\2\u01e2\u01e3\7u\2\2\u01e3"+
		"\u01e4\7g\2\2\u01e4\u01e6\3\2\2\2\u01e5\u01e7\7\"\2\2\u01e6\u01e5\3\2"+
		"\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9"+
		"\u01eb\3\2\2\2\u01ea\u01ec\5\r\7\2\u01eb\u01ea\3\2\2\2\u01ec\u01ed\3\2"+
		"\2\2\u01ed\u01eb\3\2\2\2\u01ed\u01ee\3\2\2\2\u01eel\3\2\2\2\u01ef\u01f1"+
		"\5\23\n\2\u01f0\u01f2\5o8\2\u01f1\u01f0\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2"+
		"\u01f4\3\2\2\2\u01f3\u01ef\3\2\2\2\u01f4\u01f5\3\2\2\2\u01f5\u01f3\3\2"+
		"\2\2\u01f5\u01f6\3\2\2\2\u01f6n\3\2\2\2\u01f7\u01f9\t\b\2\2\u01f8\u01f7"+
		"\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01f8\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb"+
		"\u01fc\3\2\2\2\u01fc\u01fd\b8\2\2\u01fdp\3\2\2\2\u01fe\u01ff\t\t\2\2\u01ff"+
		"r\3\2\2\2\u0200\u0201\7-\2\2\u0201\u021b\7?\2\2\u0202\u0203\7/\2\2\u0203"+
		"\u021b\7?\2\2\u0204\u0205\7,\2\2\u0205\u021b\7?\2\2\u0206\u0207\7\61\2"+
		"\2\u0207\u021b\7?\2\2\u0208\u0209\7(\2\2\u0209\u021b\7?\2\2\u020a\u020b"+
		"\7~\2\2\u020b\u021b\7?\2\2\u020c\u020d\7<\2\2\u020d\u021b\7?\2\2\u020e"+
		"\u020f\7>\2\2\u020f\u0210\7>\2\2\u0210\u021b\7?\2\2\u0211\u0212\7@\2\2"+
		"\u0212\u0213\7@\2\2\u0213\u021b\7?\2\2\u0214\u0215\7-\2\2\u0215\u0216"+
		"\7-\2\2\u0216\u021b\7?\2\2\u0217\u0218\7,\2\2\u0218\u0219\7,\2\2\u0219"+
		"\u021b\7?\2\2\u021a\u0200\3\2\2\2\u021a\u0202\3\2\2\2\u021a\u0204\3\2"+
		"\2\2\u021a\u0206\3\2\2\2\u021a\u0208\3\2\2\2\u021a\u020a\3\2\2\2\u021a"+
		"\u020c\3\2\2\2\u021a\u020e\3\2\2\2\u021a\u0211\3\2\2\2\u021a\u0214\3\2"+
		"\2\2\u021a\u0217\3\2\2\2\u021bt\3\2\2\2\u021c\u021d\t\n\2\2\u021dv\3\2"+
		"\2\2\u021e\u0228\t\13\2\2\u021f\u0220\7?\2\2\u0220\u0228\7?\2\2\u0221"+
		"\u0222\7>\2\2\u0222\u0228\7?\2\2\u0223\u0224\7@\2\2\u0224\u0228\7?\2\2"+
		"\u0225\u0226\7#\2\2\u0226\u0228\7?\2\2\u0227\u021e\3\2\2\2\u0227\u021f"+
		"\3\2\2\2\u0227\u0221\3\2\2\2\u0227\u0223\3\2\2\2\u0227\u0225\3\2\2\2\u0228"+
		"x\3\2\2\2\u0229\u0233\t\f\2\2\u022a\u022b\7>\2\2\u022b\u0233\7>\2\2\u022c"+
		"\u022d\7@\2\2\u022d\u0233\7@\2\2\u022e\u022f\7-\2\2\u022f\u0233\7-\2\2"+
		"\u0230\u0231\7,\2\2\u0231\u0233\7,\2\2\u0232\u0229\3\2\2\2\u0232\u022a"+
		"\3\2\2\2\u0232\u022c\3\2\2\2\u0232\u022e\3\2\2\2\u0232\u0230\3\2\2\2\u0233"+
		"z\3\2\2\2\u0234\u0238\5\7\4\2\u0235\u0237\5\27\f\2\u0236\u0235\3\2\2\2"+
		"\u0237\u023a\3\2\2\2\u0238\u0236\3\2\2\2\u0238\u0239\3\2\2\2\u0239|\3"+
		"\2\2\2\u023a\u0238\3\2\2\2 \2\u0087\u0089\u0098\u00a0\u00a8\u00be\u00d6"+
		"\u018a\u0192\u019d\u019f\u01a7\u01a9\u01b4\u01bb\u01c1\u01c6\u01cb\u01d6"+
		"\u01db\u01e8\u01ed\u01f1\u01f5\u01fa\u021a\u0227\u0232\u0238\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}