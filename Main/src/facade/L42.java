package facade;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.antlr.v4.runtime.misc.ParseCancellationException;

import coreVisitors.CloneVisitor;
import coreVisitors.InjectionOnSugar;
import profiling.Timer;
import programReduction.ProgramReduction;
import sugarVisitors.CollapsePositions;
import sugarVisitors.CollectDeclaredClassNamesAndMethodNames;
import sugarVisitors.Desugar;
import sugarVisitors.InjectionOnCore;
import sugarVisitors.ToFormattedText;
import tools.Assertions;
import tools.StringBuilders;
import ast.Ast;
import ast.Ast.Position;
import ast.ErrorMessage;
import ast.ExpCore;
import ast.ErrorMessage.FinalResult;
import ast.ExpCore.ClassB;
import ast.ExpCore.ClassB.Member;
import ast.ExpCore.ClassB.MethodWithType;
import ast.Expression;

public class L42 {
  public static enum ExecutionStage{None,Reading,Parsing,CheckingWellFormedness,Desugaring,MetaExecution,Closing;}
  private static ExecutionStage _stage=ExecutionStage.None;
  public static boolean profilerPrintOn=true;
  private static void setExecutionStage(ExecutionStage newStage){
    System.out.println(newStage);
    if(_stage!=ExecutionStage.None){
      Timer.deactivate(_stage.toString());
      }
    if(newStage!=ExecutionStage.None){
      Timer.activate(newStage.toString());
      }
    _stage=newStage;
  }
  public static ExecutionStage getStage(){return _stage;}
  public static int compilationRounds=0;
  public static boolean trustPluginsAndFinalProgram=false;
  public static StringBuilder record=new StringBuilder();
  public static String messageOfLastTopLevelError="";
  public static String reconstructedStackTrace="";
  public static String[] programArgs=null;
  public static List<URL> pluginPaths=null;
  public static final Set<String> usedNames=new HashSet<String>();
  public static void printDebug(String s){
    record.append(s);
    record.append("\n");
    System.err.println(s);
  }

  private static void setClassPath(Path p) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
   assert Files.isDirectory(p);
   List<URL> fileNames = new ArrayList<>();
   try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(p)){
     for (Path path : directoryStream) {
       fileNames.add(path.toUri().toURL());
       }
     }
   catch (IOException ex) {
     Assertions.codeNotReachable(""+ex);
     }
   //System.out.println(fileNames);
   L42.pluginPaths=fileNames;
   URLClassLoader loader= (URLClassLoader) ClassLoader.getSystemClassLoader();
   Method method= URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
   method.setAccessible(true);
   for(URL url:fileNames){
     method.invoke(loader, new Object[] { url });
     }
   }

  public static void main(String [] arg) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
    //assert false;
    setClassPath(Paths.get("Plugins"));
    L42.programArgs=arg;
    try{
      Configuration.loadAll();
      Path path = Paths.get(arg[arg.length-1]);

      String code=null;
      if(Files.isDirectory(path)){
        L42.setRootPath(path);
        code=L42.pathToString(path.resolve("This.L42"));
        }
      else {
        L42.setRootPath(path.getParent());
        code=L42.pathToString(path);
        }
      FinalResult res = L42.runSlow(path.toString(),code);
      System.out.println("------------------------------");
      System.out.println("END (zero for success): "+res.getErrCode());
    }
    catch(ParseCancellationException parser){
      System.out.println(parser.getMessage());
      //parser.printStackTrace(System.out);
      }
    catch(ErrorMessage msg){
      ErrorFormatter.topFormatErrorMessage(msg);
    }
    finally{
      if (L42.profilerPrintOn){
        System.out.print(Timer.report());
        }
    }
  }

  public static String pathToString(Path p) {
    StringBuffer b=new StringBuffer();
    try (Stream<String> lines = Files.lines(p)) {
      lines.forEach((l)->{b.append("\n");b.append(l);});
      b.append("\n");
      return b.toString();
      }
    catch (IOException e) {throw new Error(e);}
    }
  public static int runSlow(Path p) throws IOException{
    try{
      L42.setExecutionStage(ExecutionStage.Reading);
      String code=pathToString(p);
      throw runSlow(p.toString(),code);}
    catch(ErrorMessage.FinalResult e){return e.getErrCode();}
    catch(ErrorMessage e){
      int result= -1*e.getClass().getCanonicalName().hashCode();
      assert result<0;
      return result;
      }
  }
  public static void finalErr(ClassB result,String s){
    throw new ErrorMessage.MalformedFinalResult(result, s);
  }
  public static ErrorMessage.FinalResult checkFinalError(ClassB result){
    L42.setExecutionStage(ExecutionStage.Closing);
    ClassB.NestedClass last=(ClassB.NestedClass)result.getMs().get(result.getMs().size()-1);
    if(!(last.getInner() instanceof ClassB)){finalErr(result,"The last class can not be completed");}
    ClassB lastC=(ClassB)last.getInner();
    //TODO: booh what is the right kind? if(!(lastC.getH() instanceof Ast.TraitHeader)){finalErr(result,"The last class is not a Trait");}
    if(!lastC.getMs().isEmpty()){finalErr(result,"The last class contains members");}
    if(!lastC.getDoc2().isEmpty()){finalErr(result,"The last class have non empty second documentation: "+lastC.getDoc2());}
    String errCode=lastC.getDoc1().toString();
    if(!errCode.startsWith("@exitStatus\n")){
      finalErr(result,"The last class is not an exitStatus class:"+errCode);
    }
    errCode=errCode.substring("@exitStatus\n".length());
    int errCodeInt=0;
    try{errCodeInt=Integer.parseInt(errCode.substring(0,errCode.length()-1));}
    catch(NumberFormatException e){finalErr(result,"The exitStatus is not a valid number: "+errCode);}
    if(errCodeInt>0 && errCodeInt<100){finalErr(result,"The exitStatus is reserved: "+errCodeInt);}
    return new ErrorMessage.FinalResult(errCodeInt,result);
  }
  public static ErrorMessage.FinalResult runSlow(String fileName,String code){
    try{
      L42.setExecutionStage(ExecutionStage.Parsing);
      Expression code1=Parser.parse(fileName,code);
      assert code1 instanceof Expression.ClassB || code1 instanceof Expression.ClassReuse; 
      L42.setExecutionStage(ExecutionStage.CheckingWellFormedness);
      auxiliaryGrammar.WellFormedness.checkAll(code1);
      L42.setExecutionStage(ExecutionStage.Desugaring);
      Expression code2=Desugar.of(code1);
      assert auxiliaryGrammar.WellFormedness.checkAll(code2);
      ExpCore.ClassB code3=(ExpCore.ClassB)code2.accept(new InjectionOnCore());
      assert coreVisitors.CheckNoVarDeclaredTwice.of(code3);
      // L42.usedNames.addAll(CollectDeclaredVarsAndCheckNoDeclaredTwice.of(code2));
      //L42.usedNames.addAll(CollectDeclaredClassNamesAndMethodNames.of(code2));
      L42.setExecutionStage(ExecutionStage.MetaExecution);
      //ClassB result= (ClassB)Executor.stepStar(exe,code3);

       //Refresh of position identities, it is used to generate correct Java code.
      code3=(ClassB)code3.accept(new CloneVisitor(){
          @Override public ExpCore visit(ClassB cb){
            Position p=cb.getP();
            cb=cb.withP(new Position(p.getFile(),p.getLine1(),p.getPos1(),p.getLine2(),p.getPos2()));
            return super.visit(cb);
            }
          });
      //ClassB result= Configuration.reduction.of(code3);
      ClassB result= ProgramReduction.allSteps(code3);
      //System.out.println("--------------------------");
      //System.out.println(ToFormattedText.of(result));
      //System.out.println("--------------------------");
      return checkFinalError(result);
    }finally{L42.setExecutionStage(ExecutionStage.None);}
  }

  public static Path path;
  public static void setRootPath(Path path) {
    L42.path=path;
  }
}
