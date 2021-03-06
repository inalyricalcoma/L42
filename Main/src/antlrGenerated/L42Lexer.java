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
		Method=30, Using=31, Check=32, Refine=33, FieldSpecial=34, WalkBy=35, 
		Stage=36, Path=37, ClassSep=38, ClassMethSep=39, MX=40, X=41, HashX=42, 
		ContextId=43, StringQuote=44, UrlNL=45, Url=46, Doc=47, WS=48, UnOp=49, 
		EqOp=50, BoolOp=51, RelOp=52, DataOp=53, NumParse=54;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"S", "Mdf", "'('", "'\t'", "')'", "'['", "']'", "'{'", "'...'", "'^##'", 
		"'}'", "':'", "';'", "Dot", "'='", "'fwd'", "'implements'", "'case'", 
		"'if'", "'else'", "'while'", "'loop'", "'with'", "'on'", "'in'", "'catch'", 
		"'var'", "'default'", "'interface'", "'method'", "'use'", "'check'", "'refine'", 
		"'##field'", "'##walkBy'", "Stage", "Path", "ClassSep", "'::'", "MX", 
		"X", "HashX", "ContextId", "StringQuote", "UrlNL", "Url", "Doc", "WS", 
		"UnOp", "EqOp", "BoolOp", "RelOp", "DataOp", "NumParse"
	};
	public static final String[] ruleNames = {
		"Uppercase", "Lowercase", "Digit", "C", "CharsAll", "CharsUrl", "CharsAllStrLine", 
		"CharsAllString", "DocLine", "DocMultiLine", "StrLine", "String", "NumNext", 
		"S", "Mdf", "ORoundNoSpace", "ORoundSpace", "CRound", "OSquare", "CSquare", 
		"OCurly", "DotDotDot", "EndType", "CCurly", "Colon", "Semicolon", "Dot", 
		"Equal", "Ph", "Implements", "Case", "If", "Else", "While", "Loop", "With", 
		"On", "In", "Catch", "Var", "Default", "Interface", "Method", "Using", 
		"Check", "Refine", "FieldSpecial", "WalkBy", "Stage", "Path", "ClassSep", 
		"ClassMethSep", "MX", "X", "HashX", "ContextId", "StringQuote", "UrlNL", 
		"Url", "Doc", "WS", "UnOp", "EqOp", "BoolOp", "RelOp", "DataOp", "NumParse"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\28\u0293\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\3\2\3\2\3\3\3\3\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\7\5\u0094\n\5\f\5\16\5\u0097\13\5\3\6\3\6\3\7\3\7\3\b\3\b"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\7\n\u00a5\n\n\f\n\16\n\u00a8\13\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\7\13\u00b1\n\13\f\13\16\13\u00b4\13\13\3\13\3\13"+
		"\3\13\3\f\3\f\7\f\u00bb\n\f\f\f\16\f\u00be\13\f\3\f\3\f\3\r\7\r\u00c3"+
		"\n\r\f\r\16\r\u00c6\13\r\3\16\3\16\3\16\3\16\3\16\5\16\u00cd\n\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u00e3\n\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\5\20\u00fc\n\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\36\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3!\3!\3!"+
		"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3"+
		"&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3"+
		"*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3"+
		".\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3"+
		"\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3"+
		"\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3"+
		"\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3"+
		"\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\5\62\u01bd\n\62"+
		"\3\63\3\63\3\63\3\63\7\63\u01c3\n\63\f\63\16\63\u01c6\13\63\3\64\3\64"+
		"\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\7\66\u01d2\n\66\f\66\16\66\u01d5"+
		"\13\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\7\67\u01de\n\67\f\67\16\67\u01e1"+
		"\13\67\38\38\38\38\38\78\u01e8\n8\f8\168\u01eb\138\38\38\39\39\39\39\3"+
		"9\79\u01f4\n9\f9\169\u01f7\139\3:\3:\3:\3:\3:\3:\7:\u01ff\n:\f:\16:\u0202"+
		"\13:\3:\3:\7:\u0206\n:\f:\16:\u0209\13:\3:\6:\u020c\n:\r:\16:\u020d\3"+
		":\7:\u0211\n:\f:\16:\u0214\13:\3:\3:\5:\u0218\n:\3;\3;\3;\3;\3;\3;\3;"+
		"\6;\u0221\n;\r;\16;\u0222\3;\6;\u0226\n;\r;\16;\u0227\3;\3;\3<\3<\3<\3"+
		"<\3<\3<\3<\6<\u0233\n<\r<\16<\u0234\3<\6<\u0238\n<\r<\16<\u0239\3=\3="+
		"\5=\u023e\n=\6=\u0240\n=\r=\16=\u0241\3=\3=\5=\u0246\n=\5=\u0248\n=\3"+
		">\6>\u024b\n>\r>\16>\u024c\3>\3>\3?\3?\3@\3@\3@\3@\3@\3@\3@\3@\3@\3@\3"+
		"@\3@\3@\3@\3@\3@\3@\3@\3@\3@\3@\3@\3@\5@\u026a\n@\3A\3A\3B\3B\3B\3B\3"+
		"B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\5B\u0281\nB\3C\3C\3C\3C\3"+
		"C\3C\3C\3C\5C\u028b\nC\3D\3D\7D\u028f\nD\fD\16D\u0292\13D\3\u00b2\2E\3"+
		"\2\5\2\7\2\t\2\13\2\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33\2\35\3\37\4!"+
		"\5#\6%\7\'\b)\t+\n-\13/\f\61\r\63\16\65\17\67\209\21;\22=\23?\24A\25C"+
		"\26E\27G\30I\31K\32M\33O\34Q\35S\36U\37W Y![\"]#_$a%c&e\'g(i)k*m+o,q-"+
		"s.u/w\60y\61{\62}\63\177\64\u0081\65\u0083\66\u0085\67\u00878\3\2\17\4"+
		"\2&\'C\\\4\2aac|\6\2\13\13\"#%(*\u0080\b\2\13\13\"#%(*|~~\u0080\u0080"+
		"\4\2\13\13\"\u0080\5\2\13\13\"#%\u0080\4\2\"\"..\5\2\f\f\"\"..\4\2##\u0080"+
		"\u0080\4\2((~~\4\2>>@@\4\2--//\4\2,,\61\61\u02cf\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3"+
		"\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2"+
		"\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2"+
		"w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2"+
		"\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\3\u0089\3\2\2\2\5\u008b"+
		"\3\2\2\2\7\u008d\3\2\2\2\t\u008f\3\2\2\2\13\u0098\3\2\2\2\r\u009a\3\2"+
		"\2\2\17\u009c\3\2\2\2\21\u009e\3\2\2\2\23\u00a0\3\2\2\2\25\u00ab\3\2\2"+
		"\2\27\u00b8\3\2\2\2\31\u00c4\3\2\2\2\33\u00cc\3\2\2\2\35\u00e2\3\2\2\2"+
		"\37\u00fb\3\2\2\2!\u00fd\3\2\2\2#\u00ff\3\2\2\2%\u0101\3\2\2\2\'\u0103"+
		"\3\2\2\2)\u0105\3\2\2\2+\u0107\3\2\2\2-\u0109\3\2\2\2/\u010d\3\2\2\2\61"+
		"\u0111\3\2\2\2\63\u0113\3\2\2\2\65\u0115\3\2\2\2\67\u0117\3\2\2\29\u0119"+
		"\3\2\2\2;\u011b\3\2\2\2=\u011f\3\2\2\2?\u012a\3\2\2\2A\u012f\3\2\2\2C"+
		"\u0132\3\2\2\2E\u0137\3\2\2\2G\u013d\3\2\2\2I\u0142\3\2\2\2K\u0147\3\2"+
		"\2\2M\u014a\3\2\2\2O\u014d\3\2\2\2Q\u0153\3\2\2\2S\u0157\3\2\2\2U\u015f"+
		"\3\2\2\2W\u0169\3\2\2\2Y\u0170\3\2\2\2[\u0174\3\2\2\2]\u017a\3\2\2\2_"+
		"\u0181\3\2\2\2a\u0189\3\2\2\2c\u01bc\3\2\2\2e\u01be\3\2\2\2g\u01c7\3\2"+
		"\2\2i\u01c9\3\2\2\2k\u01cc\3\2\2\2m\u01d8\3\2\2\2o\u01e2\3\2\2\2q\u01ee"+
		"\3\2\2\2s\u0217\3\2\2\2u\u0219\3\2\2\2w\u022b\3\2\2\2y\u0247\3\2\2\2{"+
		"\u024a\3\2\2\2}\u0250\3\2\2\2\177\u0269\3\2\2\2\u0081\u026b\3\2\2\2\u0083"+
		"\u0280\3\2\2\2\u0085\u028a\3\2\2\2\u0087\u028c\3\2\2\2\u0089\u008a\t\2"+
		"\2\2\u008a\4\3\2\2\2\u008b\u008c\t\3\2\2\u008c\6\3\2\2\2\u008d\u008e\4"+
		"\62;\2\u008e\b\3\2\2\2\u008f\u0095\5\3\2\2\u0090\u0094\5\3\2\2\u0091\u0094"+
		"\5\5\3\2\u0092\u0094\5\7\4\2\u0093\u0090\3\2\2\2\u0093\u0091\3\2\2\2\u0093"+
		"\u0092\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2"+
		"\2\2\u0096\n\3\2\2\2\u0097\u0095\3\2\2\2\u0098\u0099\t\4\2\2\u0099\f\3"+
		"\2\2\2\u009a\u009b\t\5\2\2\u009b\16\3\2\2\2\u009c\u009d\t\6\2\2\u009d"+
		"\20\3\2\2\2\u009e\u009f\t\7\2\2\u009f\22\3\2\2\2\u00a0\u00a1\7\61\2\2"+
		"\u00a1\u00a2\7\61\2\2\u00a2\u00a6\3\2\2\2\u00a3\u00a5\5\17\b\2\u00a4\u00a3"+
		"\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00a9\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00aa\7\f\2\2\u00aa\24\3\2\2"+
		"\2\u00ab\u00ac\7\61\2\2\u00ac\u00ad\7,\2\2\u00ad\u00b2\3\2\2\2\u00ae\u00b1"+
		"\5\17\b\2\u00af\u00b1\7\f\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00af\3\2\2\2"+
		"\u00b1\u00b4\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b5"+
		"\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b6\7,\2\2\u00b6\u00b7\7\61\2\2\u00b7"+
		"\26\3\2\2\2\u00b8\u00bc\7)\2\2\u00b9\u00bb\5\17\b\2\u00ba\u00b9\3\2\2"+
		"\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00bf"+
		"\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c0\7\f\2\2\u00c0\30\3\2\2\2\u00c1"+
		"\u00c3\5\21\t\2\u00c2\u00c1\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3"+
		"\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\32\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7"+
		"\u00cd\5\7\4\2\u00c8\u00c9\7/\2\2\u00c9\u00cd\5\7\4\2\u00ca\u00cb\7\60"+
		"\2\2\u00cb\u00cd\5\7\4\2\u00cc\u00c7\3\2\2\2\u00cc\u00c8\3\2\2\2\u00cc"+
		"\u00ca\3\2\2\2\u00cd\34\3\2\2\2\u00ce\u00cf\7t\2\2\u00cf\u00d0\7g\2\2"+
		"\u00d0\u00d1\7v\2\2\u00d1\u00d2\7w\2\2\u00d2\u00d3\7t\2\2\u00d3\u00e3"+
		"\7p\2\2\u00d4\u00d5\7g\2\2\u00d5\u00d6\7t\2\2\u00d6\u00d7\7t\2\2\u00d7"+
		"\u00d8\7q\2\2\u00d8\u00e3\7t\2\2\u00d9\u00da\7g\2\2\u00da\u00db\7z\2\2"+
		"\u00db\u00dc\7e\2\2\u00dc\u00dd\7g\2\2\u00dd\u00de\7r\2\2\u00de\u00df"+
		"\7v\2\2\u00df\u00e0\7k\2\2\u00e0\u00e1\7q\2\2\u00e1\u00e3\7p\2\2\u00e2"+
		"\u00ce\3\2\2\2\u00e2\u00d4\3\2\2\2\u00e2\u00d9\3\2\2\2\u00e3\36\3\2\2"+
		"\2\u00e4\u00e5\7e\2\2\u00e5\u00e6\7n\2\2\u00e6\u00e7\7c\2\2\u00e7\u00e8"+
		"\7u\2\2\u00e8\u00fc\7u\2\2\u00e9\u00ea\7o\2\2\u00ea\u00eb\7w\2\2\u00eb"+
		"\u00fc\7v\2\2\u00ec\u00ed\7t\2\2\u00ed\u00ee\7g\2\2\u00ee\u00ef\7c\2\2"+
		"\u00ef\u00fc\7f\2\2\u00f0\u00f1\7n\2\2\u00f1\u00f2\7g\2\2\u00f2\u00f3"+
		"\7p\2\2\u00f3\u00fc\7v\2\2\u00f4\u00f5\7e\2\2\u00f5\u00f6\7c\2\2\u00f6"+
		"\u00f7\7r\2\2\u00f7\u00f8\7u\2\2\u00f8\u00f9\7w\2\2\u00f9\u00fa\7n\2\2"+
		"\u00fa\u00fc\7g\2\2\u00fb\u00e4\3\2\2\2\u00fb\u00e9\3\2\2\2\u00fb\u00ec"+
		"\3\2\2\2\u00fb\u00f0\3\2\2\2\u00fb\u00f4\3\2\2\2\u00fc \3\2\2\2\u00fd"+
		"\u00fe\7*\2\2\u00fe\"\3\2\2\2\u00ff\u0100\7\13\2\2\u0100$\3\2\2\2\u0101"+
		"\u0102\7+\2\2\u0102&\3\2\2\2\u0103\u0104\7]\2\2\u0104(\3\2\2\2\u0105\u0106"+
		"\7_\2\2\u0106*\3\2\2\2\u0107\u0108\7}\2\2\u0108,\3\2\2\2\u0109\u010a\7"+
		"\60\2\2\u010a\u010b\7\60\2\2\u010b\u010c\7\60\2\2\u010c.\3\2\2\2\u010d"+
		"\u010e\7`\2\2\u010e\u010f\7%\2\2\u010f\u0110\7%\2\2\u0110\60\3\2\2\2\u0111"+
		"\u0112\7\177\2\2\u0112\62\3\2\2\2\u0113\u0114\7<\2\2\u0114\64\3\2\2\2"+
		"\u0115\u0116\7=\2\2\u0116\66\3\2\2\2\u0117\u0118\7\60\2\2\u01188\3\2\2"+
		"\2\u0119\u011a\7?\2\2\u011a:\3\2\2\2\u011b\u011c\7h\2\2\u011c\u011d\7"+
		"y\2\2\u011d\u011e\7f\2\2\u011e<\3\2\2\2\u011f\u0120\7k\2\2\u0120\u0121"+
		"\7o\2\2\u0121\u0122\7r\2\2\u0122\u0123\7n\2\2\u0123\u0124\7g\2\2\u0124"+
		"\u0125\7o\2\2\u0125\u0126\7g\2\2\u0126\u0127\7p\2\2\u0127\u0128\7v\2\2"+
		"\u0128\u0129\7u\2\2\u0129>\3\2\2\2\u012a\u012b\7e\2\2\u012b\u012c\7c\2"+
		"\2\u012c\u012d\7u\2\2\u012d\u012e\7g\2\2\u012e@\3\2\2\2\u012f\u0130\7"+
		"k\2\2\u0130\u0131\7h\2\2\u0131B\3\2\2\2\u0132\u0133\7g\2\2\u0133\u0134"+
		"\7n\2\2\u0134\u0135\7u\2\2\u0135\u0136\7g\2\2\u0136D\3\2\2\2\u0137\u0138"+
		"\7y\2\2\u0138\u0139\7j\2\2\u0139\u013a\7k\2\2\u013a\u013b\7n\2\2\u013b"+
		"\u013c\7g\2\2\u013cF\3\2\2\2\u013d\u013e\7n\2\2\u013e\u013f\7q\2\2\u013f"+
		"\u0140\7q\2\2\u0140\u0141\7r\2\2\u0141H\3\2\2\2\u0142\u0143\7y\2\2\u0143"+
		"\u0144\7k\2\2\u0144\u0145\7v\2\2\u0145\u0146\7j\2\2\u0146J\3\2\2\2\u0147"+
		"\u0148\7q\2\2\u0148\u0149\7p\2\2\u0149L\3\2\2\2\u014a\u014b\7k\2\2\u014b"+
		"\u014c\7p\2\2\u014cN\3\2\2\2\u014d\u014e\7e\2\2\u014e\u014f\7c\2\2\u014f"+
		"\u0150\7v\2\2\u0150\u0151\7e\2\2\u0151\u0152\7j\2\2\u0152P\3\2\2\2\u0153"+
		"\u0154\7x\2\2\u0154\u0155\7c\2\2\u0155\u0156\7t\2\2\u0156R\3\2\2\2\u0157"+
		"\u0158\7f\2\2\u0158\u0159\7g\2\2\u0159\u015a\7h\2\2\u015a\u015b\7c\2\2"+
		"\u015b\u015c\7w\2\2\u015c\u015d\7n\2\2\u015d\u015e\7v\2\2\u015eT\3\2\2"+
		"\2\u015f\u0160\7k\2\2\u0160\u0161\7p\2\2\u0161\u0162\7v\2\2\u0162\u0163"+
		"\7g\2\2\u0163\u0164\7t\2\2\u0164\u0165\7h\2\2\u0165\u0166\7c\2\2\u0166"+
		"\u0167\7e\2\2\u0167\u0168\7g\2\2\u0168V\3\2\2\2\u0169\u016a\7o\2\2\u016a"+
		"\u016b\7g\2\2\u016b\u016c\7v\2\2\u016c\u016d\7j\2\2\u016d\u016e\7q\2\2"+
		"\u016e\u016f\7f\2\2\u016fX\3\2\2\2\u0170\u0171\7w\2\2\u0171\u0172\7u\2"+
		"\2\u0172\u0173\7g\2\2\u0173Z\3\2\2\2\u0174\u0175\7e\2\2\u0175\u0176\7"+
		"j\2\2\u0176\u0177\7g\2\2\u0177\u0178\7e\2\2\u0178\u0179\7m\2\2\u0179\\"+
		"\3\2\2\2\u017a\u017b\7t\2\2\u017b\u017c\7g\2\2\u017c\u017d\7h\2\2\u017d"+
		"\u017e\7k\2\2\u017e\u017f\7p\2\2\u017f\u0180\7g\2\2\u0180^\3\2\2\2\u0181"+
		"\u0182\7%\2\2\u0182\u0183\7%\2\2\u0183\u0184\7h\2\2\u0184\u0185\7k\2\2"+
		"\u0185\u0186\7g\2\2\u0186\u0187\7n\2\2\u0187\u0188\7f\2\2\u0188`\3\2\2"+
		"\2\u0189\u018a\7%\2\2\u018a\u018b\7%\2\2\u018b\u018c\7y\2\2\u018c\u018d"+
		"\7c\2\2\u018d\u018e\7n\2\2\u018e\u018f\7m\2\2\u018f\u0190\7D\2\2\u0190"+
		"\u0191\7{\2\2\u0191b\3\2\2\2\u0192\u0193\7%\2\2\u0193\u0194\7%\2\2\u0194"+
		"\u0195\7n\2\2\u0195\u0196\7g\2\2\u0196\u0197\7u\2\2\u0197\u01bd\7u\2\2"+
		"\u0198\u0199\7%\2\2\u0199\u019a\7%\2\2\u019a\u019b\7o\2\2\u019b\u019c"+
		"\7g\2\2\u019c\u019d\7v\2\2\u019d\u01bd\7c\2\2\u019e\u019f\7%\2\2\u019f"+
		"\u01a0\7%\2\2\u01a0\u01a1\7r\2\2\u01a1\u01a2\7n\2\2\u01a2\u01a3\7w\2\2"+
		"\u01a3\u01bd\7u\2\2\u01a4\u01a5\7%\2\2\u01a5\u01a6\7%\2\2\u01a6\u01a7"+
		"\7u\2\2\u01a7\u01a8\7v\2\2\u01a8\u01a9\7c\2\2\u01a9\u01bd\7t\2\2\u01aa"+
		"\u01ab\7%\2\2\u01ab\u01ac\7%\2\2\u01ac\u01ad\7p\2\2\u01ad\u01ae\7g\2\2"+
		"\u01ae\u01af\7g\2\2\u01af\u01b0\7f\2\2\u01b0\u01b1\7c\2\2\u01b1\u01b2"+
		"\7d\2\2\u01b2\u01b3\7n\2\2\u01b3\u01bd\7g\2\2\u01b4\u01b5\7%\2\2\u01b5"+
		"\u01b6\7%\2\2\u01b6\u01b7\7p\2\2\u01b7\u01b8\7g\2\2\u01b8\u01b9\7g\2\2"+
		"\u01b9\u01ba\7f\2\2\u01ba\u01bb\7g\2\2\u01bb\u01bd\7f\2\2\u01bc\u0192"+
		"\3\2\2\2\u01bc\u0198\3\2\2\2\u01bc\u019e\3\2\2\2\u01bc\u01a4\3\2\2\2\u01bc"+
		"\u01aa\3\2\2\2\u01bc\u01b4\3\2\2\2\u01bdd\3\2\2\2\u01be\u01c4\5\t\5\2"+
		"\u01bf\u01c0\5g\64\2\u01c0\u01c1\5\t\5\2\u01c1\u01c3\3\2\2\2\u01c2\u01bf"+
		"\3\2\2\2\u01c3\u01c6\3\2\2\2\u01c4\u01c2\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5"+
		"f\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c7\u01c8\7\60\2\2\u01c8h\3\2\2\2\u01c9"+
		"\u01ca\7<\2\2\u01ca\u01cb\7<\2\2\u01cbj\3\2\2\2\u01cc\u01d3\5\5\3\2\u01cd"+
		"\u01d2\5\3\2\2\u01ce\u01d2\5\5\3\2\u01cf\u01d2\5\7\4\2\u01d0\u01d2\7%"+
		"\2\2\u01d1\u01cd\3\2\2\2\u01d1\u01ce\3\2\2\2\u01d1\u01cf\3\2\2\2\u01d1"+
		"\u01d0\3\2\2\2\u01d2\u01d5\3\2\2\2\u01d3\u01d1\3\2\2\2\u01d3\u01d4\3\2"+
		"\2\2\u01d4\u01d6\3\2\2\2\u01d5\u01d3\3\2\2\2\u01d6\u01d7\7*\2\2\u01d7"+
		"l\3\2\2\2\u01d8\u01df\5\5\3\2\u01d9\u01de\5\3\2\2\u01da\u01de\5\5\3\2"+
		"\u01db\u01de\5\7\4\2\u01dc\u01de\7%\2\2\u01dd\u01d9\3\2\2\2\u01dd\u01da"+
		"\3\2\2\2\u01dd\u01db\3\2\2\2\u01dd\u01dc\3\2\2\2\u01de\u01e1\3\2\2\2\u01df"+
		"\u01dd\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0n\3\2\2\2\u01e1\u01df\3\2\2\2"+
		"\u01e2\u01e9\7%\2\2\u01e3\u01e8\5\3\2\2\u01e4\u01e8\5\5\3\2\u01e5\u01e8"+
		"\5\7\4\2\u01e6\u01e8\7%\2\2\u01e7\u01e3\3\2\2\2\u01e7\u01e4\3\2\2\2\u01e7"+
		"\u01e5\3\2\2\2\u01e7\u01e6\3\2\2\2\u01e8\u01eb\3\2\2\2\u01e9\u01e7\3\2"+
		"\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01ec\3\2\2\2\u01eb\u01e9\3\2\2\2\u01ec"+
		"\u01ed\7*\2\2\u01edp\3\2\2\2\u01ee\u01f5\7^\2\2\u01ef\u01f4\5\3\2\2\u01f0"+
		"\u01f4\5\5\3\2\u01f1\u01f4\5\7\4\2\u01f2\u01f4\7%\2\2\u01f3\u01ef\3\2"+
		"\2\2\u01f3\u01f0\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f3\u01f2\3\2\2\2\u01f4"+
		"\u01f7\3\2\2\2\u01f5\u01f3\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6r\3\2\2\2"+
		"\u01f7\u01f5\3\2\2\2\u01f8\u01f9\7$\2\2\u01f9\u01fa\5\31\r\2\u01fa\u01fb"+
		"\7$\2\2\u01fb\u0218\3\2\2\2\u01fc\u0200\7$\2\2\u01fd\u01ff\t\b\2\2\u01fe"+
		"\u01fd\3\2\2\2\u01ff\u0202\3\2\2\2\u0200\u01fe\3\2\2\2\u0200\u0201\3\2"+
		"\2\2\u0201\u0203\3\2\2\2\u0202\u0200\3\2\2\2\u0203\u020b\7\f\2\2\u0204"+
		"\u0206\t\b\2\2\u0205\u0204\3\2\2\2\u0206\u0209\3\2\2\2\u0207\u0205\3\2"+
		"\2\2\u0207\u0208\3\2\2\2\u0208\u020a\3\2\2\2\u0209\u0207\3\2\2\2\u020a"+
		"\u020c\5\27\f\2\u020b\u0207\3\2\2\2\u020c\u020d\3\2\2\2\u020d\u020b\3"+
		"\2\2\2\u020d\u020e\3\2\2\2\u020e\u0212\3\2\2\2\u020f\u0211\t\b\2\2\u0210"+
		"\u020f\3\2\2\2\u0211\u0214\3\2\2\2\u0212\u0210\3\2\2\2\u0212\u0213\3\2"+
		"\2\2\u0213\u0215\3\2\2\2\u0214\u0212\3\2\2\2\u0215\u0216\7$\2\2\u0216"+
		"\u0218\3\2\2\2\u0217\u01f8\3\2\2\2\u0217\u01fc\3\2\2\2\u0218t\3\2\2\2"+
		"\u0219\u021a\7t\2\2\u021a\u021b\7g\2\2\u021b\u021c\7w\2\2\u021c\u021d"+
		"\7u\2\2\u021d\u021e\7g\2\2\u021e\u0220\3\2\2\2\u021f\u0221\7\"\2\2\u0220"+
		"\u021f\3\2\2\2\u0221\u0222\3\2\2\2\u0222\u0220\3\2\2\2\u0222\u0223\3\2"+
		"\2\2\u0223\u0225\3\2\2\2\u0224\u0226\5\r\7\2\u0225\u0224\3\2\2\2\u0226"+
		"\u0227\3\2\2\2\u0227\u0225\3\2\2\2\u0227\u0228\3\2\2\2\u0228\u0229\3\2"+
		"\2\2\u0229\u022a\7\f\2\2\u022av\3\2\2\2\u022b\u022c\7t\2\2\u022c\u022d"+
		"\7g\2\2\u022d\u022e\7w\2\2\u022e\u022f\7u\2\2\u022f\u0230\7g\2\2\u0230"+
		"\u0232\3\2\2\2\u0231\u0233\7\"\2\2\u0232\u0231\3\2\2\2\u0233\u0234\3\2"+
		"\2\2\u0234\u0232\3\2\2\2\u0234\u0235\3\2\2\2\u0235\u0237\3\2\2\2\u0236"+
		"\u0238\5\r\7\2\u0237\u0236\3\2\2\2\u0238\u0239\3\2\2\2\u0239\u0237\3\2"+
		"\2\2\u0239\u023a\3\2\2\2\u023ax\3\2\2\2\u023b\u023d\5\23\n\2\u023c\u023e"+
		"\5{>\2\u023d\u023c\3\2\2\2\u023d\u023e\3\2\2\2\u023e\u0240\3\2\2\2\u023f"+
		"\u023b\3\2\2\2\u0240\u0241\3\2\2\2\u0241\u023f\3\2\2\2\u0241\u0242\3\2"+
		"\2\2\u0242\u0248\3\2\2\2\u0243\u0245\5\25\13\2\u0244\u0246\5{>\2\u0245"+
		"\u0244\3\2\2\2\u0245\u0246\3\2\2\2\u0246\u0248\3\2\2\2\u0247\u023f\3\2"+
		"\2\2\u0247\u0243\3\2\2\2\u0248z\3\2\2\2\u0249\u024b\t\t\2\2\u024a\u0249"+
		"\3\2\2\2\u024b\u024c\3\2\2\2\u024c\u024a\3\2\2\2\u024c\u024d\3\2\2\2\u024d"+
		"\u024e\3\2\2\2\u024e\u024f\b>\2\2\u024f|\3\2\2\2\u0250\u0251\t\n\2\2\u0251"+
		"~\3\2\2\2\u0252\u0253\7-\2\2\u0253\u026a\7?\2\2\u0254\u0255\7/\2\2\u0255"+
		"\u026a\7?\2\2\u0256\u0257\7,\2\2\u0257\u026a\7?\2\2\u0258\u0259\7\61\2"+
		"\2\u0259\u026a\7?\2\2\u025a\u025b\7(\2\2\u025b\u026a\7?\2\2\u025c\u025d"+
		"\7~\2\2\u025d\u026a\7?\2\2\u025e\u025f\7<\2\2\u025f\u026a\7?\2\2\u0260"+
		"\u0261\7-\2\2\u0261\u0262\7-\2\2\u0262\u026a\7?\2\2\u0263\u0264\7,\2\2"+
		"\u0264\u0265\7,\2\2\u0265\u026a\7?\2\2\u0266\u0267\7/\2\2\u0267\u0268"+
		"\7/\2\2\u0268\u026a\7?\2\2\u0269\u0252\3\2\2\2\u0269\u0254\3\2\2\2\u0269"+
		"\u0256\3\2\2\2\u0269\u0258\3\2\2\2\u0269\u025a\3\2\2\2\u0269\u025c\3\2"+
		"\2\2\u0269\u025e\3\2\2\2\u0269\u0260\3\2\2\2\u0269\u0263\3\2\2\2\u0269"+
		"\u0266\3\2\2\2\u026a\u0080\3\2\2\2\u026b\u026c\t\13\2\2\u026c\u0082\3"+
		"\2\2\2\u026d\u0281\t\f\2\2\u026e\u026f\7?\2\2\u026f\u0281\7?\2\2\u0270"+
		"\u0271\7>\2\2\u0271\u0281\7?\2\2\u0272\u0273\7@\2\2\u0273\u0281\7?\2\2"+
		"\u0274\u0275\7#\2\2\u0275\u0281\7?\2\2\u0276\u0277\7>\2\2\u0277\u0281"+
		"\7>\2\2\u0278\u0279\7@\2\2\u0279\u0281\7@\2\2\u027a\u027b\7>\2\2\u027b"+
		"\u027c\7>\2\2\u027c\u0281\7?\2\2\u027d\u027e\7@\2\2\u027e\u027f\7@\2\2"+
		"\u027f\u0281\7?\2\2\u0280\u026d\3\2\2\2\u0280\u026e\3\2\2\2\u0280\u0270"+
		"\3\2\2\2\u0280\u0272\3\2\2\2\u0280\u0274\3\2\2\2\u0280\u0276\3\2\2\2\u0280"+
		"\u0278\3\2\2\2\u0280\u027a\3\2\2\2\u0280\u027d\3\2\2\2\u0281\u0084\3\2"+
		"\2\2\u0282\u028b\t\r\2\2\u0283\u0284\7/\2\2\u0284\u028b\7/\2\2\u0285\u028b"+
		"\t\16\2\2\u0286\u0287\7-\2\2\u0287\u028b\7-\2\2\u0288\u0289\7,\2\2\u0289"+
		"\u028b\7,\2\2\u028a\u0282\3\2\2\2\u028a\u0283\3\2\2\2\u028a\u0285\3\2"+
		"\2\2\u028a\u0286\3\2\2\2\u028a\u0288\3\2\2\2\u028b\u0086\3\2\2\2\u028c"+
		"\u0290\5\7\4\2\u028d\u028f\5\33\16\2\u028e\u028d\3\2\2\2\u028f\u0292\3"+
		"\2\2\2\u0290\u028e\3\2\2\2\u0290\u0291\3\2\2\2\u0291\u0088\3\2\2\2\u0292"+
		"\u0290\3\2\2\2)\2\u0093\u0095\u00a6\u00b0\u00b2\u00bc\u00c4\u00cc\u00e2"+
		"\u00fb\u01bc\u01c4\u01d1\u01d3\u01dd\u01df\u01e7\u01e9\u01f3\u01f5\u0200"+
		"\u0207\u020d\u0212\u0217\u0222\u0227\u0234\u0239\u023d\u0241\u0245\u0247"+
		"\u024c\u0269\u0280\u028a\u0290\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}