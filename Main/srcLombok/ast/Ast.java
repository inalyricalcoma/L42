package ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import ast.Ast.Mdf;
import ast.Ast.NormType;
import ast.Ast.Path;
import ast.Ast.Ph;
import sugarVisitors.Desugar;
import tools.Assertions;
import tools.StringBuilders;
//import ast.Ast.Doc;
//import ast.Ast.MethodSelector;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.Wither;
import platformSpecific.javaTranslation.Resources;

public interface Ast {

	public interface Atom {
	}

	@Value
	public class Parameters {
		Optional<Expression> e;
		List<String> xs;
		List<Expression> es;
	}


	public interface VarDec {
		<T> T match(Function<VarDecXE, T> xe, Function<VarDecE, T> e, Function<VarDecCE, T> ce);
	}

	@Value
	@Wither
	public class VarDecXE implements VarDec {
		boolean isVar;
		Optional<Type> t;
		String x;
		Expression inner;

		public <T> T match(Function<VarDecXE, T> xe, Function<VarDecE, T> e, Function<VarDecCE, T> ce) {
			return xe.apply(this);
		}
	}

	@Value
	public class VarDecE implements VarDec {
		Expression inner;

		public <T> T match(Function<VarDecXE, T> xe, Function<VarDecE, T> e, Function<VarDecCE, T> ce) {
			return e.apply(this);
		}
	}

	@Value
	public class VarDecCE implements VarDec {
		Expression.ClassB.NestedClass inner;

		public <T> T match(Function<VarDecXE, T> xe, Function<VarDecE, T> e, Function<VarDecCE, T> ce) {
			return ce.apply(this);
		}
	}


	public interface Header {
		<T> T match(Function<ConcreteHeader, T> concreteH, Function<TraitHeader, T> traitH,
				Function<InterfaceHeader, T> interfH);
	}

	@Value
	@Wither
	@EqualsAndHashCode(exclude = "p")
	@ToString(exclude = "p")
	public class ConcreteHeader implements Header, Expression.HasPos {
		Mdf mdf;
		String name;
		List<FieldDec> fs;
		Position p;

		public <T> T match(Function<ConcreteHeader, T> concreteH, Function<TraitHeader, T> traitH,
				Function<InterfaceHeader, T> interfH) {
			return concreteH.apply(this);
		}
	}

	@Value
	public class TraitHeader implements Header {
		public <T> T match(Function<ConcreteHeader, T> concreteH, Function<TraitHeader, T> traitH,
				Function<InterfaceHeader, T> interfH) {
			return traitH.apply(this);
		}
	}

	@Value
	public class InterfaceHeader implements Header {
		public <T> T match(Function<ConcreteHeader, T> concreteH, Function<TraitHeader, T> traitH,
				Function<InterfaceHeader, T> interfH) {
			return interfH.apply(this);
		}
	}

	@Value
	@Wither
	public class FieldDec {
		boolean isVar;
		Type t;
		String name;
		Doc doc;
	}

	public interface Type {
      <T> T match(Function<NormType, T> normType, Function<HistoricType, T> hType);
      default ast.Ast.NormType getNT() {
        assert this instanceof ast.Ast.NormType : this;
        return (ast.Ast.NormType) this;
        }
	  }

	@Value
	@Wither
	public class NormType implements Type {
		Mdf mdf;
		Path path;
		Ph ph;
    public static final NormType immVoid=new NormType(Mdf.Immutable,Path.Void(),Ph.None);
		public String toString() {
			return (((ph == Ph.Ph) ? "fwd " : (ph == Ph.None) ? "" : "fwd% ") + mdf.name() + "" + this.path.rowData);
		}

		public <T> T match(Function<NormType, T> normType, Function<HistoricType, T> hType) {
			return normType.apply(this);
		}
	}

	@Value
	@Wither
	public class MethodSelectorX {
		MethodSelector ms;
		String x;
	}

	@Value
	@Wither
	public class HistoricType implements Type {
		Path path;
		List<MethodSelectorX> selectors;
		boolean forcePlaceholder;

		public <T> T match(Function<NormType, T> normType, Function<HistoricType, T> hType) {
			return hType.apply(this);
		}
	}

	@Value
	public class FreeType implements Type {
		public <T> T match(Function<NormType, T> normType, Function<HistoricType, T> hType) {
			throw tools.Assertions.codeNotReachable();
		}
	}

	@Value
	@Wither
	public class MethodSelector {
		String name;
		List<String> names;
		public MethodSelector(String name,List<String>names){
		  this.name=name;
		  this.names=java.util.Collections.unmodifiableList(names);
		  assert this.invariant();
		}
		public boolean isOperator(){
		  return this.name.startsWith("#");//for now, to improve later
		}
		public boolean invariant(){
		  // not good enought, it can also be empty or operator
		  // assert checkX(name,true);
		assert !name.contains("\t"):
			name;
		  for(String n:names){assert checkX(n,false);}
		  return true;
		}

		public String toSrcEquivalent() {
			String result = "new ast.Ast.MethodSelector(\"" + name + "\",java.util.Arrays.asList(";
			result += String.join(",", tools.Map.of(ni -> "\"" + ni + "\"", names));
			return result + "))";
		}

		public String toString() {
			if (name.isEmpty() && names.isEmpty()) {
				return "()";
			}
			if (names.isEmpty()) {
				return name + "()";
			}
			StringBuilder result = new StringBuilder();
			result.append(name + "(");
			tools.StringBuilders.formatSequence(result, names.iterator(), ",", result::append);
			result.append(")");
			return result.toString();
		}

		public static MethodSelector parse(String s) {
			if (s.equals("()")) {
				return new MethodSelector(Desugar.desugarName(""), Collections.emptyList());
			}
			String name = s;
			List<String> xs = new ArrayList<String>();
			assert!s.isEmpty();
			char last = s.charAt(s.length() - 1);
			if (last != ')') {
				throw new Resources.Error("InvalidSelector: " + s);
			}
			int i = s.indexOf('(');
			if (i == -1) {
				throw new Resources.Error("InvalidSelector: " + s);
			}
			name = s.substring(0, i);
			String parenthesis = s.substring(i + 1, s.length() - 1).trim();
			if (!parenthesis.isEmpty()) {
				String[] names = parenthesis.split(",");// single representation
														// required
				for (String si : names) {
					if (!checkX(si, false)) {
						throw new Resources.Error("InvalidSelector: " + s);
					}
					if(xs.contains(si)){
					  throw new Resources.Error("InvalidSelector: " + s+" a parameter is repeted: "+si);
					}
					xs.add(si);
				}
			}
			name = Desugar.desugarName(name);
			if (!checkX(name, true)) {
				throw new Resources.Error("InvalidSelector: " + s);
			}
			return new MethodSelector(name, xs);
		}

		public static boolean checkX(String s, boolean allowHash) {
			if (s.isEmpty()) {return false;}
			char c0 = s.charAt(0);
			if (allowHash && c0 == '#') {
				if (s.length() == 1) {return false;}
				char c1 = s.charAt(1);
				if (c1 == '#') {return false;}
				//return checkX(s.substring(1), allowHash);
			}
			for (char c : s.toCharArray()) {
				if (allowHash && c == '#') {continue;}
			  if (ast.Ast.Path.isValidPathChar(c)) {continue;}
	      return false;
			}
			return c0 == '_' ||c0 == '#' || (c0 >= 'a' && c0 <= 'z');
		}
	}

	@Value
	@Wither
	public class MethodType {
	    boolean refine;
		Doc docExceptions;
		Mdf mdf;
		List<Type> ts;
		List<Doc> tDocs;
		Type returnType;
		List<Path> exceptions;
	}

	@Value
	/* @ToString(exclude="n") */ public class Path implements Expression, ExpCore, Atom {
		int n;
		List<String> rowData;

		private Path(int n, List<String> rowData) {
			assert!rowData.get(0).contains("-") : rowData;// as in Outer-1 :-(
			assert!rowData.contains(null) : // comment to force new line an put
											// break
			rowData;
			rowData = Collections.unmodifiableList(rowData);
			this.n = n;
			this.rowData = rowData;
		}

		public Path(List<String> rowData) {
			assert!rowData.get(0).contains("-") : rowData;// as in Outer-1 :-(
			rowData = Collections.unmodifiableList(rowData);
			this.rowData = rowData;
			String start = this.rowData.get(0);
			boolean isCore = isOutern(start);
			if (isCore) {
				start = start.substring("This".length());
				n = Integer.parseInt(start);
			} else {
				n = -1;
			}
		}

		@Override
		public <T> T accept(sugarVisitors.Visitor<T> v) {
			return v.visit(this);
		}

		@Override
		public <T> T accept(coreVisitors.Visitor<T> v) {
			return v.visit(this);
		}

		public boolean isPrimitive() {
			return this.equals(Path.Void()) || this.equals(Path.Library()) || this.equals(Path.Any());
		}

		public boolean isCore() {
			return n != -1;
		}

		public String toString() {
			return sugarVisitors.ToFormattedText.of(this);
		}

		private static boolean isOutern(String start) {
			if (!start.startsWith("This")) {
				return false;
			}
			start = start.substring("This".length());
			if (start.isEmpty()) {
				return false;
			}
			for (char c : start.toCharArray()) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
			return true;
		}

		public Path popC() {
			// assert outerNumber()==0;
			List<String> s = new ArrayList<String>(this.rowData);
			// s.remove(1);
			s.remove(s.size() - 1);
			return new Path(n, s);
		}

		public Path pushC(String c) {
			// assert outerNumber()==0;
			List<String> s = new ArrayList<String>(this.rowData);
			s.add(c);
			return new Path(n, s);
		}

		public List<String> getCBar() {
			assert!this.isPrimitive();
			if (this.isCore()) {
				return rowData.subList(1, rowData.size());
			}
			return rowData;
		}

		public Path setNewOuter(int n) {
			assert isCore();
			List<String> s = new ArrayList<String>(this.rowData);
			s.set(0, "This" + n);
			return new Path(n, s);
		}

		public int outerNumber() {
			assert isCore() : this;
			return this.n;
		}

		private static final Path _Outer0 = new Path(0, Arrays.asList("This0"));
		private static final Path _Void = new Path(-1, Arrays.asList("Void"));
		private static final Path _Any = new Path(-1, Arrays.asList("Any"));
		private static final Path _Library = new Path(-1, Arrays.asList("Library"));

		public static Path outer(int n, List<String> cs) {
			List<String> arr = new ArrayList<>();
			arr.add("This" + n);
			arr.addAll(cs);
			return new Path(n, arr);
		}

		public static Path outer(int n) {
			assert n >= 0;
			if (n == 0) {
				return _Outer0;
			}
			return new Path(n, Arrays.asList("This" + n));
		}

		public static Path Void() {
			return _Void;
		}

		public static Path Any() {
			return _Any;
		}

		public static Path Library() {
			return _Library;
		}

		public static List<String> parseValidCs(String cs) {
			if (cs.equals("This0") || cs.equals("This")) {
				return Collections.emptyList();
			}
			List<String> rowData = Collections.unmodifiableList(Arrays.asList(cs.split("\\.")));
			for (String s : rowData) {
				if (!isValidClassName(s)) {
					throw new Resources.Error("InvalidPath: " + cs);
				}
			}
			return rowData;
		}

		public static boolean isValidOuter(String name) {// thus invalid as
															// pathName
			if (name.equals("This")) {
				return true;
			}
			if (name.equals("This0")) {
				return true;
			}
			if (!name.startsWith("This")) {
				return false;
			}
			int firstN = "This".length();
			char c = name.charAt(firstN);
			// first is 1--9 and all rest is 0-9
			if ("123456789".indexOf(c) != -1) {
				return false;
			}
			for (int i = firstN + 1; i < name.length(); i++) {
				if ("0123456789".indexOf(name.charAt(i)) == -1) {
					return false;
				}
			}
			return true;
		}

		public static boolean isValidClassName(String name) {
		            if(name.isEmpty()){return false;}
			if (isValidOuter(name)) {
				return false;
			}
			if (!isValidPathStart(name.charAt(0))) {
				return false;
			}
			for (int i = 1; i < name.length(); i++) {
				if (!isValidPathChar(name.charAt(i))) {
					return false;
				}
			}
			return true;
		}

		public static Path parse(String path) {
			List<String> rowData = Arrays.asList(path.split("\\."));
			for (String s : rowData) {// TODO: make it more precise, throw real
										// error?
				assert isValidOuter(s) || isValidClassName(s) : path;
			}
			return new Path(rowData);
		}

		public static boolean isValidPathStart(char c) {
			if (c == '%') {
				return true;
			}
			if (c == '$') {
				return true;
			}
			return Character.isUpperCase(c);
		}

		public static boolean isValidPathChar(char c) {
			if (c == '%') {
				return true;
			}
			if (c == '$') {
				return true;
			}
			if (c == '_') {
				return true;
			}
			assert c!='\t':
				c;
			return Character.isUpperCase(c) || Character.isLowerCase(c) || Character.isDigit(c);
		}
	}

	@Value
	@Wither
	public static class Doc {
	            boolean multiline;
		String s;
		List<Object> annotations;

		public List<Path> getPaths() {
			List<Path> result = new ArrayList<>();
			for (Object o : annotations) {
				if (o instanceof Path) {
					result.add((Path) o);
				}
			}
			return result;
		}
		public Doc withNewlineTerminator(){
		  if(s.endsWith("\n")){return this;}
		  return this.withS(s+"\n");
		  }

		public boolean isPrivate() {
			if (this.annotations.contains("private")) {
				return true;
			}
			// if(this.toString().startsWith("@private")){return true;}
			return false;
		}
        public static Doc getPrivate(){return privateInstance;}
        private static final Doc privateInstance=Doc.factory(true,"@private");
		public static Doc factory(Path single) {
			return new Doc(true,"%s\n", Collections.singletonList((Object) single));
		}

		public static Doc factory(boolean multiline,String s) {
			if (!multiline & !s.endsWith("\n")) { s += "\n";}
			List<Object> annotations = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char ci = s.charAt(i);
				if (ci == '%') {
					sb.append('%');
					sb.append('%');
					continue;
				}
				if (ci != '@') {
					sb.append(ci);
					continue;
				} else {// ci=='@'
					char next = '\n';
					if (i + 1 < s.length()) {
						next = s.charAt(i + 1);
					}
					if (next == '.' || Path.isValidPathChar(next)) {
						sb.append("%s");
						i = readAnnotation(s, i + 1, annotations);
					} else {
						throw Assertions.codeNotReachable("invalid use of @ in |" + next + "| " + s);
					} // if(!Path.isValidPathStart(next)){sb.append(ci);continue;}
				}
			}
			return new Doc(multiline,sb.toString(), annotations);
		}

		private static final Doc empty = new Doc(true,"", Collections.emptyList());

		public static Doc empty() {
			return empty;
		}

                        public String toString() {
                                    List<Object> paths = new ArrayList<>();
                                    for (Object pi : this.annotations) {
                                                if (pi instanceof Path) {
                                                            paths.add("@" + sugarVisitors.ToFormattedText.of((Path) pi));
                                                } else {
                                                            paths.add("@" + (String) pi);
                                                }
                                    }
                                    String text=String.format(this.s, paths.toArray());
                                    return text;
                          }
                       public String toCodeFormattedString() {
                                    String text=toString();
                                    if(text.isEmpty()){return text;}
                                    if(this.multiline){return "/*"+text+"*/";}
                                    assert text.endsWith("\n"):"|"+text+"|";
                                    String[] splitted=text.substring(0,text.length()-1).split("\n",-1);//on its line for ease of testing//This was a bad move, javaSplit, you despicable bastard
                                    StringBuffer res=new StringBuffer();
                                    {int i=-1;for(String s:splitted){i+=1;
                                       if(s.isEmpty()&& i==0){continue;}
                                       res.append("//");
                                       res.append(s);
                                       res.append("\n");
                                       }}
                                     return res.toString();
                        }

		public boolean isEmpty() {
			return this.s.isEmpty();
		}

		public Doc sum(Doc that) {
			List<Object> ps = new ArrayList<>(this.annotations);
			ps.addAll(that.annotations);
			return new Doc(true,this.s + that.s, ps);
		}
		public Doc formatNewLinesAsList() {
		  String newS=this.s.trim();
		  newS=newS.replace("\n", ", ");
		  newS="["+newS+"]\n";
      return this.withS(newS);
    }

  private static int readAnnotation(String s, int start, List<Object> paths) {
    StringBuilder sb = new StringBuilder();
    for (int i = start; i < s.length(); i++) {
      char ci = s.charAt(i);
      if (ci == '.' || Path.isValidPathChar(ci)) { sb.append(ci); }
      else {break;}
      }
    String res=sb.toString();
    if (Path.isValidPathStart(s.charAt(start))) {     paths.add(Path.parse(res));   }
    else {paths.add(res);}
    return start+res.length()-1;
    }
}

	public static enum SignalKind {
		Error("error"), Exception("exception"), Return("return");
		public final String content;

		SignalKind(String content) {
			this.content = content;
		}

		public static SignalKind fromString(String s) {
			for (SignalKind sk : SignalKind.values()) {
				if (sk.content.equals(s))
					return sk;
			}
			throw tools.Assertions.codeNotReachable();
		}
	}

	public static enum Mdf {
		Immutable(""), Mutable("mut"), Readable("read"), Lent("lent"), Capsule("capsule"), Class("class");
		public final String inner;

		Mdf(String inner) {
			this.inner = inner;
		}

		public static Mdf fromString(String s) {
			for (Mdf mdf : Mdf.values()) {
				if (mdf.inner.equals(s))
					return mdf;
			}
			throw tools.Assertions.codeNotReachable();
		}

	}

	public static enum OpKind {
		Unary, BoolOp, RelationalOp, DataOp, EqOp
	}

	public static enum Op {
		Tilde("~", OpKind.Unary, true, true),//
		Bang("!", OpKind.Unary, true, true),//

		And("&", OpKind.BoolOp, true,true),//
		Or("|", OpKind.BoolOp, true, true),//

		LTEqual("<=", OpKind.RelationalOp, false, true),//
		GTEqual(">=", OpKind.RelationalOp, true, true),//
		LT("<", OpKind.RelationalOp, false, true),//
		GT(">",OpKind.RelationalOp, true,true),//
		LTLT("<<", OpKind.RelationalOp, false, false),//
                        GTGT(">>",OpKind.RelationalOp, true, false),//
                        LTLTEqual("<<=",OpKind.RelationalOp,true,true),//
                        GTGTEqual(">>=",OpKind.RelationalOp,true,true),//
		EqualEqual("==", OpKind.RelationalOp, true, false),//
		BangEqual("!=",OpKind.RelationalOp, true,true),//

		Plus("+", OpKind.DataOp, true, true),//
		Minus("-", OpKind.DataOp, true,true),//
		Times("*", OpKind.DataOp, true, true),//
		Divide("/", OpKind.DataOp,true, true),//
		PlusPlus("++",OpKind.DataOp, true, false),//
		MinusMinus("--",OpKind.DataOp, true, false),//
		TimesTimes("**",OpKind.DataOp, true,false),//

		PlusEqual("+=", OpKind.EqOp,true, true),//
		MinusEqual("-=",OpKind.EqOp, true,true),//
		TimesEqual("*=",OpKind.EqOp,true,true),//
		DivideEqual("/=",OpKind.EqOp,true,true),//
		AndEqual("&=",OpKind.EqOp,true,true),//
		OrEqual("|=",OpKind.EqOp,true,true),//
		PlusPlusEqual("++=",OpKind.EqOp,true,true),//
		MinusMinusEqual("--=",OpKind.EqOp,true,true),//
		TimesTimesEqual("**=",OpKind.EqOp,true,true),//
		ColonEqual(":=",OpKind.EqOp,true,true);//

		public final String inner;
		public final OpKind kind;
		public final boolean normalized;// false for <<,<, <=
		public final boolean leftAssociative;// false for ++ << >> ** ==

		Op(String inner, OpKind kind, boolean normalized, boolean leftAssociative) {
			this.inner = inner;
			this.kind = kind;
			this.normalized = normalized;
			this.leftAssociative = leftAssociative;
		}

		public static Op fromString(String s) {
			for (Op op : Op.values()) {
				if (op.inner.equals(s))
					return op;
			}
			throw tools.Assertions.codeNotReachable();
		}
	}

	public static enum Stage {
		None(""), Less("##less"),
		ToIterate("##toIterateTemp"),
		// Meta("##meta"),
		Plus("##plus"), Star("##star");
		// Needed("##needed"),
		// Needable("##needable");
		public final String inner;

		Stage(String inner) {
			this.inner = inner;
		}

		public static Stage fromString(String s) {
			for (Stage st : Stage.values()) {
				if (st.inner.equals(s))
					return st;
			}
			throw tools.Assertions.codeNotReachable();
		}
	}

	public static enum Ph {
		None, Ph, Partial
	}

	public static @Value class Position {
		public static final Position noInfo = new Position(null, Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2, 0, 0);
		String file;
		int line1;
		int pos1;
		int line2;
		int pos2;

		public String toString() {
			int line1 = this.line1 - 1;
			int line2 = this.line2 - 1;
			String res = "";
			if (line1 == line2) {
				res = "line:" + line1 + ", pos:" + pos1 + "--" + pos2;
			} else {
				res = "from line:" + line1 + "(pos:" + pos1 + ") to line:" + line2 + "(pos:" + pos2 + ")";
			}
			if (file == null) {
				return "fileUnknown; " + res;
			}
			String fileName = null;
			// if(file!=null){fileName="..."+file.substring(file.lastIndexOf("\\"));}
			int pos = file.lastIndexOf("\\");
			if (pos != -1) {
				pos = file.substring(0, pos).lastIndexOf("\\");
			}
			fileName = "..." + ((pos == -1) ? file : file.substring(pos));
			return "file: " + fileName + "; " + res;
		}
	}

	public static interface HasPos {
		Position getP();
	}
	public static interface HasReceiver extends HasPos,Expression{
		Expression getReceiver();
		Expression withReceiver(Expression receiver);
	}
}
